#
#
# main() will be invoked when you Run This Action.
#
# @param Cloud Functions actions accept a single parameter,
#        which must be a JSON object.
#
# @return which must be a JSON object.
#         It will be the output of this action.
#
#
import sys

def main(dict):
    if 'message' in dict:
        name = dict['message']
    else:
        name = 'stranger'
    greeting = 'Hello ' + name + '!'
    print(greeting)
    return {'greeting':greeting}
