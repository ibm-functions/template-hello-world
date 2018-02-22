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


import org.junit.runner.RunWith
import org.scalatest.BeforeAndAfterAll
import org.scalatest.junit.JUnitRunner
import common.{TestHelpers, Wsk, WskProps, WskTestHelpers}
import java.io._
import spray.json.DefaultJsonProtocol.StringJsonFormat
import spray.json.pimpAny

@RunWith(classOf[JUnitRunner])
class HelloTests extends TestHelpers
    with WskTestHelpers
    with BeforeAndAfterAll {

    implicit val wskprops = WskProps()
    val wsk = new Wsk()

    //set parameters for deploy tests
    val nodejs8folder = "../runtimes/nodejs-8/actions";
    val nodejs6folder = "../runtimes/nodejs-6/actions";
    val phpfolder = "../runtimes/php-7.1/actions";
    val pythonfolder = "../runtimes/python-3.6.4/actions";
    val swiftfolder = "../runtimes/swift-3.1.1/actions";

    behavior of "Hello World Template"

    /**
     * Test the nodejs 8 "hello world" template
     */
     it should "invoke nodejs 8 helloworld.js and get the result" in withAssetCleaner(wskprops) { (wp, assetHelper) =>
       val name = "helloNode"
       val file = Some(new File(nodejs8folder, "helloworld.js").toString());
       assetHelper.withCleaner(wsk.action, name) { (action, _) =>
         action.create(name, file)
       }

       withActivation(wsk.activation, wsk.action.invoke(name, Map("name" -> "Mindy".toJson))) {
         _.response.result.get.toString should include("Mindy")
       }
     }
      it should "invoke nodejs 8 helloworld.js without input and get stranger" in withAssetCleaner(wskprops) { (wp, assetHelper) =>
        val name = "helloNode"
        val file = Some(new File(nodejs8folder, "helloworld.js").toString());
        assetHelper.withCleaner(wsk.action, name) { (action, _) =>
          action.create(name, file)
        }

        withActivation(wsk.activation, wsk.action.invoke(name)) {
          _.response.result.get.toString should include("stranger")
        }
      }

      /**
       * Test the nodejs 6 "hello world" template
       */
      it should "invoke nodejs 6 helloworld.js and get the result" in withAssetCleaner(wskprops) { (wp, assetHelper) =>
        val name = "helloNode"
        val file = Some(new File(nodejs6folder, "helloworld.js").toString());
        assetHelper.withCleaner(wsk.action, name) { (action, _) =>
          action.create(name, file)
        }

        withActivation(wsk.activation, wsk.action.invoke(name, Map("name" -> "Mindy".toJson))) {
          _.response.result.get.toString should include("Mindy")
        }
      }
       it should "invoke nodejs 6 helloworld.js without input and get stranger" in withAssetCleaner(wskprops) { (wp, assetHelper) =>
         val name = "helloNode"
         val file = Some(new File(nodejs6folder, "helloworld.js").toString());
         assetHelper.withCleaner(wsk.action, name) { (action, _) =>
           action.create(name, file)
         }

         withActivation(wsk.activation, wsk.action.invoke(name)) {
           _.response.result.get.toString should include("stranger")
         }
       }
     /**
      * Test the php 7.1 "hello world" template
      */
      it should "invoke helloworld.php and get the result" in withAssetCleaner(wskprops) { (wp, assetHelper) =>
        val name = "helloPhp"
        val file = Some(new File(phpfolder, "helloworld.php").toString());
        assetHelper.withCleaner(wsk.action, name) { (action, _) =>
          action.create(name, file)
        }

        withActivation(wsk.activation, wsk.action.invoke(name, Map("name" -> "Mindy".toJson))) {
          _.response.result.get.toString should include("Mindy")
        }
      }
      it should "invoke helloworld.php without input and get stranger" in withAssetCleaner(wskprops) { (wp, assetHelper) =>
        val name = "helloPhp"
        val file = Some(new File(phpfolder, "helloworld.php").toString());
        assetHelper.withCleaner(wsk.action, name) { (action, _) =>
          action.create(name, file)
        }

        withActivation(wsk.activation, wsk.action.invoke(name)) {
          _.response.result.get.toString should include("stranger")
        }
      }
      /**
       * Test the python 3 "hello world" template
       */
       it should "invoke helloworld.py and get the result" in withAssetCleaner(wskprops) { (wp, assetHelper) =>
         val name = "helloPython"
         val file = Some(new File(pythonfolder, "helloworld.py").toString());
         assetHelper.withCleaner(wsk.action, name) { (action, _) =>
           action.create(name, file)
         }

         withActivation(wsk.activation, wsk.action.invoke(name, Map("name" -> "Mindy".toJson))) {
           _.response.result.get.toString should include("Mindy")
         }
       }
       it should "invoke helloworld.py without input and get stranger" in withAssetCleaner(wskprops) { (wp, assetHelper) =>
         val name = "helloPython"
         val file = Some(new File(pythonfolder, "helloworld.py").toString());
         assetHelper.withCleaner(wsk.action, name) { (action, _) =>
           action.create(name, file)
         }

         withActivation(wsk.activation, wsk.action.invoke(name)) {
           _.response.result.get.toString should include("stranger")
         }
       }
       /**
        * Test the swift 3.1.1 "hello world" template
        */
        it should "invoke helloworld.swift and get the result" in withAssetCleaner(wskprops) { (wp, assetHelper) =>
          val name = "helloSwift"
          val file = Some(new File(swiftfolder, "helloworld.swift").toString());
          assetHelper.withCleaner(wsk.action, name) { (action, _) =>
            action.create(name, file)
          }

          withActivation(wsk.activation, wsk.action.invoke(name, Map("name" -> "Mindy".toJson))) {
            _.response.result.get.toString should include("Mindy")
          }
        }
        it should "invoke helloworld.swift without input and get stranger" in withAssetCleaner(wskprops) { (wp, assetHelper) =>
          val name = "helloSwift"
          val file = Some(new File(swiftfolder, "helloworld.swift").toString());
          assetHelper.withCleaner(wsk.action, name) { (action, _) =>
            action.create(name, file)
          }

          withActivation(wsk.activation, wsk.action.invoke(name)) {
            _.response.result.get.toString should include("stranger")
          }
        }
}
