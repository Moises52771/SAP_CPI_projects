/* Refer the link below to learn more about the use cases of script.
https://help.sap.com/viewer/368c481cd6954bdfa5d0435479fd4eaf/Cloud/en-US/148851bf8192412cba1f9d2c17f4bd25.html

If you want to know more about the SCRIPT APIs, refer the link below
https://help.sap.com/doc/a56f52e1a58e4e2bac7f7adbf45b2e26/Cloud/en-US/index.html */
import com.sap.gateway.ip.core.customdev.util.Message;
import java.util.HashMap;
def Message processData(Message message) {
    
public class CustomAuthenticationProvider extends StandardAuthenticationProvider {
  private String token = "fetch";
  @Override
  public Map<String, List<String>> getHTTPHeaders(String url) {
    Map<String, List<String>> httpHeaders = super.getHTTPHeaders(url);
    if(httpHeaders==null) {
      httpHeaders = new HashMap<String, List<String>>();
   }
   httpHeaders.put("X-CSRF-Token", Collections.singletonList(token));
   return httpHeaders;
  }
  @Override
  public void putResponseHeaders(String url, int statusCode, Map<String, List<String>> headers) {
    super.putResponseHeaders(url, statusCode, headers);
    if(headers!=null) {
      for(String headerName:headers.keySet()) { // loop for a ignore case check -> header names are case-insensitive (RFC 2616)
	if(headerName!=null && headerName.equalsIgnoreCase("X-CSRF-Token") && !headers.get(headerName).isEmpty()) {
	  this.token = headers.get(headerName).get(0);
	}
      }
    }
  }
}
    
    return message;
}