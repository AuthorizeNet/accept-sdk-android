package net.authorize.acceptsdk.parser;

import android.util.JsonReader;
import android.util.Log;

import net.authorize.acceptsdk.datamodel.merchant.ClientKeyBasedMerchantAuthentication;
import net.authorize.acceptsdk.datamodel.merchant.MerchantAuthenticationType;
import net.authorize.acceptsdk.datamodel.transaction.CardData;
import net.authorize.acceptsdk.datamodel.transaction.EncryptTransactionObject;
import net.authorize.acceptsdk.datamodel.transaction.response.EncryptTransactionResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Kiran Bollepalli on 08,July,2016.
 * kbollepa@visa.com
 */
public class AcceptSDKParser {

  /*  {
        "securePaymentContainerRequest": {
        "merchantAuthentication": {
            "name": "5KP3u95bQpv",
                    "clientKey": "5FcB6WrfHGS76gHW3v7btBCE3HuuBuke9Pj96Ztfn5R32G5ep42vne7MCWZtAucY"
        },
        "data": {
            "type": "TOKEN",
                    "id": "ac210aef-cf2d-656c-69a5-a8e145d8f1fc",
                    "token": {
                "cardNumber": "378282246310005",
                        "expirationDate": "122021"
            }
        }
    }
    } */

    public static String getJsonFromEncryptTransaction(EncryptTransactionObject transactionObject) throws JSONException {


        // Json related to data
        JSONObject tokenData = new JSONObject();
        CardData cardData = transactionObject.getCardData();
        tokenData.put("cardNumber", cardData.getAccountNumber());
        tokenData.put("expirationDate", cardData.getExpirationMonth() + cardData.getExpirationYear());

        JSONObject data = new JSONObject();
        data.put("type","TOKEN");
        data.put("id",transactionObject.getGuid());
        data.put("token", tokenData);

        // Json related to merchant authentication
        JSONObject authentication = new JSONObject();
        authentication.put("name", transactionObject.getMerchantAuthentication().getApiLoginID());

        MerchantAuthenticationType authenticationType = transactionObject.getMerchantAuthentication()
                .getMerchantAuthenticationType();
        if (authenticationType == MerchantAuthenticationType.CLIENT_KEY){
            ClientKeyBasedMerchantAuthentication clientKeyAuth = (ClientKeyBasedMerchantAuthentication) transactionObject
                    .getMerchantAuthentication();
            authentication.put("clientKey", clientKeyAuth.getClientKey());
        }else if (authenticationType == MerchantAuthenticationType.FINGERPRINT){ //TODO : Need to implement this

        }

        JSONObject paymentContainer = new JSONObject();
        paymentContainer.put("merchantAuthentication",authentication);
        paymentContainer.put("data",data);

        JSONObject request = new JSONObject();
        request.put("securePaymentContainerRequest",paymentContainer);
        return request.toString();
    }


    public static EncryptTransactionResponse createEncryptionResponse(InputStream inputStream) throws IOException {
        JsonReader reader = new JsonReader(new InputStreamReader(inputStream, "UTF-8"));
        reader.beginObject();
        while (reader.hasNext()){
            String name = reader.nextName();
            if (name.equals("opaqueData")){
                getEncryptionData(reader);
            }else
                reader.skipValue();
        }
        reader.endObject();

        return null;
    }

    private static void getEncryptionData(JsonReader reader) throws IOException{
        String dataDescriptor = null;
        String dataValue = null;

        reader.beginObject();

        while (reader.hasNext()){
            String name = reader.nextName();
            if (name.equals("dataDescriptor"))
                dataDescriptor = reader.nextString();
            else if (name.equals("dataValue"))
                dataValue = reader.nextString();
            else
                reader.skipValue();
        }

        reader.endObject();
        Log.i("Kiran","encypted code " + dataValue);

    }
}
