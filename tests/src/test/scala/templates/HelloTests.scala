/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package packages

import java.io._

import common.TestUtils.RunResult
import common.{TestHelpers, Wsk, WskProps, WskTestHelpers}
import io.restassured.RestAssured
import io.restassured.config.SSLConfig
import org.junit.runner.RunWith
import org.scalatest.BeforeAndAfterAll
import org.scalatest.junit.JUnitRunner
import spray.json.DefaultJsonProtocol._
import spray.json._
@RunWith(classOf[JUnitRunner])
class HelloTests extends TestHelpers with WskTestHelpers with BeforeAndAfterAll {

  implicit val wskprops = WskProps()
  val wsk = new Wsk()

  val deployTestRepo = "https://github.com/ibm-functions/template-hello-world"
  val helloWorldAction = "helloworld"
  val packageName = "myPackage"
  val deployAction = "/whisk.system/deployWeb/wskdeploy"
  val deployActionURL = s"https://${wskprops.apihost}/api/v1/web${deployAction}.http"

  //set parameters for deploy tests
  val nodejsRuntimePath = "runtimes/nodejs"
  val nodejsfolder = "../runtimes/nodejs/actions";
  val nodejskind = "nodejs:16"
  val phpRuntimePath = "runtimes/php"
  val phpfolder = "../runtimes/php/actions";
  val phpkind = "php:7.4"
  val pythonRuntimePath = "runtimes/python"
  val pythonfolder = "../runtimes/python/actions";
  val pythonkind = "python:3.9"
  val swiftRuntimePath = "runtimes/swift"
  val swiftfolder = "../runtimes/swift/actions";
  val swiftkind = "swift:4.2"

  // status from deployWeb
  val successStatus =
    """"status": "success""""

  behavior of "Hello World Template"

  // test to create the hello world template from github url.  Will use preinstalled folder.
  it should "create the nodejs 10 hello world action from github url" in {
    // create unique asset names
    val timestamp: String = System.currentTimeMillis.toString
    val nodejs8Package = packageName + timestamp
    val nodejs8HelloWorldAction = nodejs8Package + "/" + helloWorldAction

    makePostCallWithExpectedResult(
      JsObject(
        "gitUrl" -> JsString(deployTestRepo),
        "manifestPath" -> JsString(nodejsRuntimePath),
        "envData" -> JsObject("PACKAGE_NAME" -> JsString(nodejs8Package)),
        "wskApiHost" -> JsString(wskprops.apihost),
        "wskAuth" -> JsString(wskprops.authKey)),
      successStatus,
      200);

    withActivation(wsk.activation, wsk.action.invoke(nodejs8HelloWorldAction)) {
      _.response.result.get.toString should include("stranger")
    }

    val action = wsk.action.get(nodejs8HelloWorldAction)
    verifyAction(action, nodejs8HelloWorldAction, JsString(nodejskind))

    // clean up after test
    wsk.action.delete(nodejs8HelloWorldAction)
  }

  // test to create the hello world template from github url.  Will use preinstalled folder.
  it should "create the php hello world action from github url" in {
    // create unique asset names
    val timestamp: String = System.currentTimeMillis.toString
    val phpPackage = packageName + timestamp
    val phpHelloWorldAction = phpPackage + "/" + helloWorldAction

    makePostCallWithExpectedResult(
      JsObject(
        "gitUrl" -> JsString(deployTestRepo),
        "manifestPath" -> JsString(phpRuntimePath),
        "envData" -> JsObject("PACKAGE_NAME" -> JsString(phpPackage)),
        "wskApiHost" -> JsString(wskprops.apihost),
        "wskAuth" -> JsString(wskprops.authKey)),
      successStatus,
      200);

    withActivation(wsk.activation, wsk.action.invoke(phpHelloWorldAction)) {
      _.response.result.get.toString should include("stranger")
    }

    val action = wsk.action.get(phpHelloWorldAction)
    verifyAction(action, phpHelloWorldAction, JsString(phpkind))

    // clean up after test
    wsk.action.delete(phpHelloWorldAction)
  }

  // test to create the hello world template from github url.  Will use preinstalled folder.
  it should "create the python hello world action from github url" in {
    // create unique asset names
    val timestamp: String = System.currentTimeMillis.toString
    val pythonPackage = packageName + timestamp
    val pythonHelloWorldAction = pythonPackage + "/" + helloWorldAction

    makePostCallWithExpectedResult(
      JsObject(
        "gitUrl" -> JsString(deployTestRepo),
        "manifestPath" -> JsString(pythonRuntimePath),
        "envData" -> JsObject("PACKAGE_NAME" -> JsString(pythonPackage)),
        "wskApiHost" -> JsString(wskprops.apihost),
        "wskAuth" -> JsString(wskprops.authKey)),
      successStatus,
      200);

    withActivation(wsk.activation, wsk.action.invoke(pythonHelloWorldAction)) {
      _.response.result.get.toString should include("stranger")
    }

    val action = wsk.action.get(pythonHelloWorldAction)
    verifyAction(action, pythonHelloWorldAction, JsString(pythonkind))

    // clean up after test
    wsk.action.delete(pythonHelloWorldAction)
  }

  // test to create the hello world template from github url.  Will use preinstalled folder.
  it should "create the swift hello world action from github url" in {
    // create unique asset names
    val timestamp: String = System.currentTimeMillis.toString
    val swiftPackage = packageName + timestamp
    val swiftHelloWorldAction = swiftPackage + "/" + helloWorldAction

    makePostCallWithExpectedResult(
      JsObject(
        "gitUrl" -> JsString(deployTestRepo),
        "manifestPath" -> JsString(swiftRuntimePath),
        "envData" -> JsObject("PACKAGE_NAME" -> JsString(swiftPackage)),
        "wskApiHost" -> JsString(wskprops.apihost),
        "wskAuth" -> JsString(wskprops.authKey)),
      successStatus,
      200);

    withActivation(wsk.activation, wsk.action.invoke(swiftHelloWorldAction)) {
      _.response.result.get.toString should include("stranger")
    }

    val action = wsk.action.get(swiftHelloWorldAction)
    verifyAction(action, swiftHelloWorldAction, JsString(swiftkind))

    // clean up after test
    wsk.action.delete(swiftHelloWorldAction)
  }

  /**
   * Test the nodejs 10 "hello world" template
   */
  it should "invoke nodejs 10 helloworld.js and get the result" in withAssetCleaner(wskprops) { (wp, assetHelper) =>
    val timestamp: String = System.currentTimeMillis.toString
    val name = "helloNode" + timestamp
    val file = Some(new File(nodejsfolder, "helloworld.js").toString());
    assetHelper.withCleaner(wsk.action, name) { (action, _) =>
      action.create(name, file, kind = Some(nodejskind))
    }

    withActivation(wsk.activation, wsk.action.invoke(name, Map("name" -> "Mindy".toJson))) {
      _.response.result.get.toString should include("Mindy")
    }
  }

  it should "invoke nodejs 10 helloworld.js without input and get stranger" in withAssetCleaner(wskprops) {
    (wp, assetHelper) =>
      val timestamp: String = System.currentTimeMillis.toString
      val name = "helloNode" + timestamp
      val file = Some(new File(nodejsfolder, "helloworld.js").toString());
      assetHelper.withCleaner(wsk.action, name) { (action, _) =>
        action.create(name, file, kind = Some(nodejskind))
      }

      withActivation(wsk.activation, wsk.action.invoke(name)) {
        _.response.result.get.toString should include("stranger")
      }
  }

  /**
   * Test the php "hello world" template
   */
  it should "invoke helloworld.php and get the result" in withAssetCleaner(wskprops) { (wp, assetHelper) =>
    val timestamp: String = System.currentTimeMillis.toString
    val name = "helloPhp" + timestamp
    val file = Some(new File(phpfolder, "helloworld.php").toString());
    assetHelper.withCleaner(wsk.action, name) { (action, _) =>
      action.create(name, file, kind = Some(phpkind))
    }

    withActivation(wsk.activation, wsk.action.invoke(name, Map("name" -> "Mindy".toJson))) {
      _.response.result.get.toString should include("Mindy")
    }
  }
  it should "invoke helloworld.php without input and get stranger" in withAssetCleaner(wskprops) { (wp, assetHelper) =>
    val timestamp: String = System.currentTimeMillis.toString
    val name = "helloPhp" + timestamp
    val file = Some(new File(phpfolder, "helloworld.php").toString());
    assetHelper.withCleaner(wsk.action, name) { (action, _) =>
      action.create(name, file, kind = Some(phpkind))
    }

    withActivation(wsk.activation, wsk.action.invoke(name)) {
      _.response.result.get.toString should include("stranger")
    }
  }

  /**
   * Test the python "hello world" template
   */
  it should "invoke helloworld.py and get the result" in withAssetCleaner(wskprops) { (wp, assetHelper) =>
    val timestamp: String = System.currentTimeMillis.toString
    val name = "helloPython" + timestamp
    val file = Some(new File(pythonfolder, "helloworld.py").toString());
    assetHelper.withCleaner(wsk.action, name) { (action, _) =>
      action.create(name, file, kind = Some(pythonkind))
    }
    withActivation(wsk.activation, wsk.action.invoke(name, Map("name" -> "Mindy".toJson))) {
      _.response.result.get.toString should include("Mindy")
    }
  }

  it should "invoke helloworld.py without input and get stranger" in withAssetCleaner(wskprops) { (wp, assetHelper) =>
    val timestamp: String = System.currentTimeMillis.toString
    val name = "helloPython" + timestamp
    val file = Some(new File(pythonfolder, "helloworld.py").toString());
    assetHelper.withCleaner(wsk.action, name) { (action, _) =>
      action.create(name, file, kind = Some(pythonkind))
    }

    withActivation(wsk.activation, wsk.action.invoke(name)) {
      _.response.result.get.toString should include("stranger")
    }
  }

  /**
   * Test the swift "hello world" template
   */
  it should "invoke helloworld.swift and get the result" in withAssetCleaner(wskprops) { (wp, assetHelper) =>
    val timestamp: String = System.currentTimeMillis.toString
    val name = "helloSwift" + timestamp
    val file = Some(new File(swiftfolder, "helloworld.swift").toString());
    assetHelper.withCleaner(wsk.action, name) { (action, _) =>
      action.create(name, file, kind = Some(swiftkind))
    }

    withActivation(wsk.activation, wsk.action.invoke(name, Map("name" -> "Mindy".toJson))) {
      _.response.result.get.toString should include("Mindy")
    }
  }

  it should "invoke helloworld.swift without input and get stranger" in withAssetCleaner(wskprops) {
    (wp, assetHelper) =>
      val timestamp: String = System.currentTimeMillis.toString
      val name = "helloSwift" + timestamp
      val file = Some(new File(swiftfolder, "helloworld.swift").toString());
      assetHelper.withCleaner(wsk.action, name) { (action, _) =>
        action.create(name, file, kind = Some(swiftkind))
      }

      withActivation(wsk.activation, wsk.action.invoke(name)) {
        _.response.result.get.toString should include("stranger")
      }
  }

  private def makePostCallWithExpectedResult(params: JsObject, expectedResult: String, expectedCode: Int) = {
    val response = RestAssured
      .given()
      .contentType("application/json\r\n")
      .config(RestAssured.config().sslConfig(new SSLConfig().relaxedHTTPSValidation()))
      .body(params.toString())
      .post(deployActionURL)
    assert(response.statusCode() == expectedCode)
    response.body.asString should include(expectedResult)
    response.body.asString.parseJson.asJsObject.getFields("activationId") should have length 1
  }

  private def verifyAction(action: RunResult, name: String, kindValue: JsString): Unit = {
    val stdout = action.stdout
    assert(stdout.startsWith(s"ok: got action $name\n"))
    wsk.parseJsonString(stdout).fields("exec").asJsObject.fields("kind") shouldBe kindValue
  }
}
