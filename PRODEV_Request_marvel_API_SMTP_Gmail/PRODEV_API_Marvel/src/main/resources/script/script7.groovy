import com.sap.gateway.ip.core.customdev.util.Message;
import java.util.HashMap;
import groovy.json.*;

def Message processData(Message message) {
    
    def prop = message.getProperties()
    
    def body = prop.get("json")
    def jsonParser = new JsonSlurper()
    def jsonObj  = jsonParser.parseText(body)
    def tagHTML = ""
    
    jsonObj.data.results.each{e -> 
    
        def name = e.name
        def desc = e.description
        def img = JsonOutput.toJson(e.thumbnail.path + "/portrait_fantastic." + e.thumbnail.extension)
        
        tagHTML += "<h1>" + name + "</h1><br/><p class=\"h1\" style=\"margin-left:15%;margin-right:15%;text-align:center; margin-top:1%\">" + desc + "</p><br/><br/><img src=" + img + " /><br/><br/>"
        
    }
    
    message.setHeader("Content-Type", "application/json")
    message.setHeader("img", tagHTML)
    
    return message;
}
