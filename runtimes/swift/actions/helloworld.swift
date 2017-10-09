/**
  *
  * main() will be invoked when you Run This Action.
  *
  * @param Cloud Functions actions accept a single parameter,
  *        which must be a JSON object.
  *
  * In this case, the params variable will look like:
  *     { "message": "xxxx" }
  *
  * @return which must be a JSON object.
  *         It will be the output of this action.
  *
  */
func main(args: [String:Any]) -> [String:Any] {
      if let message = args["message"] as? String {
          return [ "greeting" : "Hello \(message)!" ]
      } else {
          return [ "greeting" : "Hello stranger!" ]
      }
}
