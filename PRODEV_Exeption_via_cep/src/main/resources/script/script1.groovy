import com.sap.gateway.ip.core.customdev.util.Message;
import java.util.HashMap;
import groovy.json.*;
import java.nio.charset.StandardCharsets
import java.util.Date
import java.text.SimpleDateFormat

def Message processData(Message message) {
    manipulaJson(message)
    return message;
}

def void manipulaJson(Message message){
    def body     = message.getBody(String)
    def jsonFile = new JsonSlurper()
    def jsonObj  = jsonFile.parseText(body)
    def messageLog = messageLogFactory.getMessageLog(message)

    jsonObj.remove('ibge')
    jsonObj.remove('gia')
    jsonObj.remove('siafi')

    
    Date date = new Date()
    date.setTime(date.getTime() - (3*3600000))
    SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss")

    jsonObj.data = formatador.format(date)


    def json = new JsonBuilder(jsonObj).toPrettyString()
        json = json.replace("logradouro", "rua")

    def json2 = JsonOutput.toJson(
            'zip code' : jsonObj.xmlcep.cep,
            street : jsonObj.xmlcep.logradouro,
            complement : jsonObj.xmlcep.complemento,
            district : jsonObj.xmlcep.bairro,
            locality : jsonObj.xmlcep.localidade,
            state : jsonObj.xmlcep.uf,
            ddd: jsonObj.xmlcep.ddd,
            date: jsonObj.xmlcep.data
    )

    message.setBody(JsonOutput.prettyPrint(json2))
    messageLog.addAttachmentAsString("ViaCEP", json2.toString(), "application/json;charset=utf-8")

    message.setBody(json2)
 
}