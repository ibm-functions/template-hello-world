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
func main(args: [String:Any]) -> [String:Any] {
  if let name = args["name"] as? String {
    return [ "greeting" : "Hello \(name)!" ]
  } else {
    return [ "greeting" : "Hello stranger!" ]
  }
}
