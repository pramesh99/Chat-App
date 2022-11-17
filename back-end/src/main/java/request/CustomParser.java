package request;

public class CustomParser {

  // extract java useable values from a raw http request string
  // https://developer.mozilla.org/en-US/docs/Web/HTTP/Messages
  public static ParsedRequest parse(String request){
    String[] lines = request.split("(\r\n|\r|\n)");
    String requestLine = lines[0];
    String[] requestParts = requestLine.split(" ");
    var result = new ParsedRequest();
    result.setMethod(requestParts[0]);

    var parts = requestParts[1].split("\\?");
    result.setPath(parts[0]);

    // todo get body and headers
    return result;
  }
}
