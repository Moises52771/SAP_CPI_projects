import com.sap.it.api.ITApi
import com.sap.it.api.ITApiFactory
import com.sap.it.api.securestore.*;
import com.sap.gateway.ip.core.customdev.util.Message;
import java.util.HashMap;

def Message processData(Message message) {

       String hash
       String timeStamp
       String apiKey
       
       def service = ITApiFactory.getApi(SecureStoreService.class, null)
       def cred_hash = service.getUserCredential("sp_hash");
       def cred_timeStamp = service.getUserCredential("sp_time_stamp");
       def cred_apiKey = service.getUserCredential("sp_api_key");
       
        if (cred_hash == null || cred_timeStamp == null || cred_apiKey == null){
            throw new IllegalStateException("Não existe credencial com esse alias!")    
            
        }else{
            hash = new String(cred_hash.getPassword())
            timeStamp = new String(cred_timeStamp.getPassword())
            apiKey = new String(cred_apiKey.getPassword())
            
        }

        message.setProperty("hash", hash)
        message.setProperty("time_stamp", timeStamp)
        message.setProperty("api_key", apiKey)
        
       return message
}