# Accept Android SDK

## SDK Installation

Android Studio is preferred because Eclipse will not be supported by Google much longer.

### Android Studio (or Gradle)

Add this line to your app's `build.gradle` inside the `dependencies` section as follows:

```groovy
    dependencies {
        compile 'com.authorize:acceptsdk:+'
    }
```

## SDK Usage
After the installation is succesfully complete, perform the following steps to program an Android app with this SDK.

1) To initiate requests with the SDK, create an API client that will make API requests on your behalf. The Accept SDK API client can be built as follows:

```java
// Parameters:
// 1) Context - current context
// 2) AcceptSDKApiClient.Environment - AUTHORIZE.NET ENVIRONMENT
apiClient = new AcceptSDKApiClient.Builder (getActivity(),
                                          AcceptSDKApiClient.Environment.SANDBOX) 
                                          .sdkConnectionTimeout(5000) // optional connection time out in milliseconds
                                          .build();
```
2) To make the API call, you can create a Encryption transaction object as follows:

```java
 EncryptTransactionObject transactionObject = TransactionObject.
         createTransactionObject(TransactionType.SDK_TRANSACTION_ENCRYPTION)// type of transaction object
        .cardData(prepareCardDataFromFields()) // card data to be encrypted
        .merchantAuthentication(prepareMerchantAuthentication()) //Merchant authentication
        .build();
```
A card object can be created as follows:

```java
CardData cardData = new CardData.Builder(CARD_NUMBER,
                                               EXPIRATION_MONTH, // MM
                                               EXPIRATION_YEAR) // YYYY
                                               .setCVVCode(CARD_CVV) // Optional
                                               .setZipCode(ZIP_CODE)// Optional
                                               .setCardHolderName(CARD_HOLDER_NAME)// Optional
                                               .build();
```

Merchant Authentication can be created as follows:

```java
ClientKeyBasedMerchantAuthentication merchantAuthentication = ClientKeyBasedMerchantAuthentication.
                createMerchantAuthentication(API_LOGIN_ID, CLIENT_KEY);
```
Check out  "Obtaining a Public Client Key" in http://developer.authorize.net/api/reference/features/acceptjs.html to get CLIENT_KEY.

3) When the API client and transaction information are ready, you can make a call to perform a specific API.

```java
// Parameters: 
// 1. EncryptTransactionObject - The transaction object for current transaction
// 2. transaction response callback.
apiClient.performEncryption(transactionObject, callback);
```

4) To get a response back, the activity/fragment should implement the `EncryptTransactionCallback` interface.

```java
@Override
public void onEncryptionFinished(EncryptTransactionResponse response) 
{ 
  Toast.makeText(getActivity(), 
                 response.getDataDescriptor() + " : " + response.getDataValue(),
                 Toast.LENGTH_LONG)
                 .show();
}
```
**OR**

In case of an error:

```java
@Override
public void onErrorReceived(ErrorTransactionResponse errorResponse) 
{ 
 Message error = errorResponse.getFirstErrorMessage();
  Toast.makeText(getActivity(), 
                 error.getMessageCode() + " : " + error.getMessageText() ,
                 Toast.LENGTH_LONG)
                 .show();
}
```

##Sample Application
We have a sample application which demonstrates the SDK usage:  
   https://github.com/AuthorizeNet/accept-sample-android

