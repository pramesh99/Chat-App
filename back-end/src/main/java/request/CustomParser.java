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

    if(parts.length == 2){
      System.out.println(parts[1]);
      String[] queryParts = parts[1].split("&");
      for (int i = 0; i < queryParts.length; i++) {
        String[] pair = queryParts[i].split("=");
        result.setQueryParam(pair[0], pair[1]);
      }
    }

    String body = "";
    boolean emptyLine = false;
    for (String line: lines){
      if(line.contains(":") && !emptyLine){
        String[] headerParts = line.split(":");
        result.setHeaderValue(headerParts[0].trim(), headerParts[1].trim());
      }
      if(line.equals("")){
        emptyLine = true;
      }
      if(emptyLine){
        body += line;
      }
    }
    result.setBody(body);
    return result;
  }
}
