import com.sap.gateway.ip.core.customdev.util.Message;
import java.util.HashMap;


def Message processData(Message message) {
    
    def properties = message.getProperties();
    def cookie = properties.get("cookie");
    message.setHeader("cookie", String.join("; ", cookie));
    
    return message;
}