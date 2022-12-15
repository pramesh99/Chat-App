package handler;

import dao.FriendsDao;
import request.ParsedRequest;

public class HandlerFactory {
  // routes based on the path. Add your custom handlers here
  public static BaseHandler getHandler(ParsedRequest request) {
      FriendsDao friendsDao = FriendsDao.getInstance();
    switch (request.getPath()) {
        case "/createUser":
            return new CreateUserHandler();
        case "/login":
            return new LoginHandler();
        case "/getConversations":
            return new GetConversationsHandler();
        case "/getConversation":
            return new GetConversationHandler();
        case "/createMessage":
            return new CreateMessageHandler();
        case "/friendRequest":
            return new FriendRequestHandler();
        case "/getFriendRequest":
            return new GetFriendRequestHandler();
        case "/friendRequestRemove":
            return new RemoveFriendHandler();
        case "/searchFriends":
            return new SearchFriendsHandler(friendsDao);
        default:
            return new FallbackHandler();
    }
  }
}
