import com.sap.gateway.ip.core.customdev.util.Message;
import java.util.HashMap;
import groovy.json.*;

def Message processData(Message message) {
    
    def prop = message.getProperties()
    
    def body = prop.get("json")
    def jsonParser = new JsonSlurper()
    def jsonObj  = jsonParser.parseText(body)
    
    def index = prop.get('CamelLoopIndex').toInteger()
    
    def name = jsonObj.data.results[index].name
    def img = JsonOutput.toJson(jsonObj.data.results[index].thumbnail.path + "/portrait_fantastic." + jsonObj.data.results[index].thumbnail.extension)
    def desc = jsonObj.data.results[index].description
    
    // def tagHTML = "<h2>" + name + "</h2><br/><br/><img src=" + img + "/><br/><br/>"
    tagHTML = "<h1>" + name + "</h1><br/><p class=\"h1\" style=\"margin-left:15%;margin-right:15%;text-align:center; margin-top:1%\">" + desc + "</p><br/><br/><img src=" + img + " /><br/><br/>"
    
    message.setHeader("Content-Type", "application/json")
    message.setHeader("img", tagHTML)
    
    return message
}
