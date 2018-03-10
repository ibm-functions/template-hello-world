#!/bin/bash
# Build script for Travis-CI.

SCRIPTDIR=$(cd $(dirname "$0") && pwd)
ROOTDIR="$SCRIPTDIR/../.."
WHISKDIR="$ROOTDIR/../openwhisk"
PACKAGESDIR="$WHISKDIR/catalog/extra-packages"
IMAGE_PREFIX="testing"

# Set Environment
export OPENWHISK_HOME=$WHISKDIR

cd $WHISKDIR

tools/build/scanCode.py "$SCRIPTDIR/../.."

./gradlew distDocker

docker pull openwhisk/controller
docker tag openwhisk/controller ${IMAGE_PREFIX}/controller

docker pull openwhisk/invoker
docker tag openwhisk/invoker ${IMAGE_PREFIX}/invoker

docker pull ibmfunctions/action-nodejs-v8
docker tag ibmfunctions/action-nodejs-v8 whisk/action-nodejs-v8:latest

docker pull ibmfunctions/action-python-v3
docker tag ibmfunctions/action-python-v3 ${IMAGE_PREFIX}/action-python-v3:latest

cd $WHISKDIR/ansible

#ANSIBLE_CMD="ansible-playbook -i environments/local"
ANSIBLE_CMD="ansible-playbook -i ${ROOTDIR}/ansible/environments/local -e ${IMAGE_PREFIX}"

$ANSIBLE_CMD setup.yml
$ANSIBLE_CMD prereq.yml
$ANSIBLE_CMD couchdb.yml
$ANSIBLE_CMD initdb.yml

$ANSIBLE_CMD wipe.yml
$ANSIBLE_CMD openwhisk.yml

cd $WHISKDIR
VCAP_SERVICES_FILE="$(readlink -f ${WHISKDIR}/../tests/credentials.json)"

ls $WHISKDIR

#update whisk.properties to add tests/credentials.json file to vcap.services.file, which is needed in tests
WHISKPROPS_FILE="$WHISKDIR/whisk.properties"
sed -i 's:^[ \t]*vcap.services.file[ \t]*=\([ \t]*.*\)$:vcap.services.file='$VCAP_SERVICES_FILE':'  $WHISKPROPS_FILE
cat whisk.properties

WSK_CLI=$WHISKDIR/bin/wsk
AUTH_KEY=$(cat $WHISKDIR/ansible/files/auth.whisk.system)
EDGE_HOST=$(grep '^edge.host=' $WHISKPROPS_FILE | cut -d'=' -f2)
#Deployment
# WHISK_APIHOST="172.17.0.1"
# WHISK_AUTH=`cat ${WHISKDIR}/ansible/files/auth.guest`
# WHISK_CLI="${WHISKDIR}/bin/wsk -i"

# ${WHISK_CLI} property set --apihost ${WHISK_APIHOST} --auth ${WHISK_AUTH}
# ${WHISK_CLI} property get

# Place this template in correct location to be included in packageDeploy
mkdir -p $PACKAGESDIR/preInstalled/ibm-functions
cp -r $ROOTDIR $PACKAGESDIR/preInstalled/ibm-functions/

# Install the package
cd $PACKAGESDIR/packageDeploy/packages
source $PACKAGESDIR/packageDeploy/packages/installCatalog.sh $AUTH_KEY $EDGE_HOST $WSK_CLI

# Test
cd $ROOTDIR
./gradlew :tests:test
