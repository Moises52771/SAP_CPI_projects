import com.sap.gateway.ip.core.customdev.util.Message;
import groovy.xml.*;
import java.io.*;
import groovy.json.*;
import java.util.Date
import java.text.SimpleDateFormat
 
def Message processData(Message message){
    
    def headers = message.getHeaders()
    
    def cookie = headers.get("Set-Cookie");
    StringBuffer bufferedCookie = new StringBuffer();
    for (Object item : cookie){
        bufferedCookie.append(item + "; ");      
    }
    message.setHeader("Cookie", bufferedCookie.toString());
    
    def messageLog = messageLogFactory.getMessageLog(message);
    if(messageLog != null){
        
        messageLog.setStringProperty("Logging_Cookie", bufferedCookie.toString());
    }
    
    traduzJson(message)
    return message;
}

def void traduzJson(Message message){
    
    def body     = message.getBody(String)
    def jsonFile = new JsonSlurper()
    def jsonObj  = jsonFile.parseText(body)
    
    def date = jsonObj."Last Modified"
    
    String[] d = date.split('-')

    jsonObj."Last Modified" = d[2] + d[1] + d[0]
    
    def json2 = JsonOutput.toJson(
        
            "Codigo": jsonObj.Code,
            "Descricao": jsonObj.Description,
            "GrupoMateial": jsonObj.MaterialGroup,
            "CriadoPor": jsonObj.CreatedBy,
            "ModificadoPor": jsonObj.ModifiedBy,
            "UltimaModificacao": jsonObj."Last Modified",
            "Ativo": jsonObj.Active
        
    )

    message.setBody(JsonOutput.prettyPrint(json2))
}