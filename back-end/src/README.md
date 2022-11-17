# Homework 1
Complete the unfinished code to pass the tests.
Desired behavior is provided in the test cases. Each test class is worth 10 points.

Right click -> run, to run a specific test. All tests are re-run on github when pushed. Score should be the same as local run.

The last score will be taken (not the highest). 

If compilation fails, all of them will fail.

## Running with server
You can optionally turn on and run as real http server by running the main method in the Server class.

## Overview
You are building a simple chat application with 4 endpoints.
/createUser
- takes username/password as post body
- Store password hash
- Do not allow users to create an account if username already taken
/login
- Take username and password as post body
- Lookup userName and hashed password combination
- If Exists create new entry in AuthDao
- Return Set-Cookie header in http response with a custom hash
/getConversations
- Returns all conversations for a user
- User must be authenticated with auth cookie
/getConversation
- Returns all messages between 2 users
- User must be authenticated with auth cookie
- Takes conversationId as get Param
/createMessage
- Created a new message
- If no conversation object exists, create a new one for both users
- Generate a re-usable conversationId
- User must be authenticated with auth cookie

Responses return a JSON object as the body of an http response