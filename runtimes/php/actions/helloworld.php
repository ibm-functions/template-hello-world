<?php
/**
  *
  * main() will be invoked when you Run This Action.
  *
  * @param Cloud Functions actions accept a single parameter,
  *        which must be a JSON object.
  *
  * In this case, the params variable will look like:
  *     { "name": "xxxx" }
  *
  * @return which must be a JSON object.
  *         It will be the output of this action.
  *
  */
function main(array $args) : array
{
  $name = $args["name"] ?? "stranger";
  $greeting = "Hello $name!";
  echo $greeting;
  return ["greeting" => $greeting];
}
