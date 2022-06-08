import com.sap.gateway.ip.core.customdev.util.Message;
import java.util.HashMap;
import groovy.json.*;

def Message processData(Message message) {
    
    def body       = message.getBody(String)
    def jsonParser = new JsonSlurper()
    def jsonObj    = jsonParser.parseText(body)
    
    def properties = message.getProperties()
    def index      = properties.get("CamelLoopIndex")

    json = JsonOutput.toJson(jsonObj[index.toInteger()])
    
    message.setBody(json)
    
    return message;
}