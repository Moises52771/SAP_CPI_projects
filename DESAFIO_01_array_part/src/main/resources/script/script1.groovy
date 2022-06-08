import com.sap.gateway.ip.core.customdev.util.Message;
import java.util.HashMap;
import groovy.json.*;


def Message processData(Message message) {

    def body       = message.getBody(String)
    def jsonParser = new JsonSlurper()
    def jsonObj    = jsonParser.parseText(body)

    def headers = message.getHeaders()
    def size    = headers.get("size") 

    def json
    def array      = []
    def array_part = []

    jsonObj.root.Export.ET_CLIENTS.TTYPE_ZTT_CLIENTE_RESERVA.forEach{e ->
    
        json = JsonOutput.toJson(
            id                  : e.CLIENT,
            clientGmr           : e.CLIENT_GMR,
            clientGd            : e.CLIENT_GD,
            shippingPoint       : e.SHIPPING_POINT,
            region              : e.REGION,
            industryKey         : e.INDUSTRY_KEY,
            salesOrg            : e.SALES_ORG,
            distributionChannel : e.DISTRIBUTION_CHANNEL,
            division            : e.DIVISION,
            salesGroup          : e.SALES_GROUP,
            salesOffice         : e.SALES_OFFICE,
            cpf                 : e.CPF,
            cnpj                : e.CNPJ

        )

        array.push(json)
    }

    def count = array.size()

    quebraJson(message, array, size.toInteger(), 0, count, array_part)

    return message
}


// vetor   --------  array completo
// size    --------  periodos que serão enviados
// initial --------  posição inicial
// lenght  --------  lenght do array completo
// array   --------  array que será preenchido
def void quebraJson(Message message, def vetor, int size, int initial, int lenght, def array){

    def end  = initial + size
    def part = []
    def num

    for(int i = initial; i < end && i < lenght; i++){

        part.push(vetor[i])
        num = i

    }

    array.push(part)

    if(num != (lenght - 1)){
        initial = end
        quebraJson(message, vetor, size, initial, lenght, array)

    }

    message.setBody(array.toString())
    message.setProperty("JSON_Size", array.size()) 
    
}