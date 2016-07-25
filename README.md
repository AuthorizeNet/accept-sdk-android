# Accept Android SDK

This SDK is Android version of [Accept JS](http://developer.authorize.net/api/reference/features/acceptjs.html). This SDK is used to fetch token for given Payment Information.

## Contents

1. [Installation](#installation-one-step)
1. [Getting Started](#getting-started-four-steps)
1. [Demo Applcation](#running-the-demo-app)

## Installation (One Step)
Add the dependency from jCenter to your app's (not project) `build.gradle` file.

```groovy
repositories {
    jcenter()
}

dependencies {
    compile 'net.authorize:accept-android-sdk:+'
}
```

## Getting Started (Four Steps)

### Prerequisites

 Android API 14+ is required as the `minSdkVersion` in your build.gradle


### 1. Initialize AcceptSDKApiClient

 All SDK APi's will be accessed through AcceptSDKApiClient Object. It can be created as follows:

```java
// Parameters:
// 1) Context - Activity context
// 2) AcceptSDKApiClient.Environment - AUTHORIZE.NET ENVIRONMENT
apiClient = new AcceptSDKApiClient.Builder (getActivity(),
                                          AcceptSDKApiClient.Environment.SANDBOX) 
                                          .sdkConnectionTimeout(5000) // optional connection time out in milliseconds
                                          .build();
```
### 2. Prepare Objects rquired to call Token API
 Fetch token API require EncryptTransactionObject and it can be created as follows:

```java
 EncryptTransactionObject transactionObject = TransactionObject.
         createTransactionObject(TransactionType.SDK_TRANSACTION_ENCRYPTION)// type of transaction object
        .cardData(prepareCardDataFromFields()) // card data to be encrypted
        .merchantAuthentication(prepareMerchantAuthentication()) //Merchant authentication
        .build();
```
EncryptTransactionObject rquire CardData object and it can be created as follows:

```java
CardData cardData = new CardData.Builder(CARD_NUMBER,
                                               EXPIRATION_MONTH, // MM
                                               EXPIRATION_YEAR) // YYYY
                                               .setCVVCode(CARD_CVV) // Optional
                                               .setZipCode(ZIP_CODE)// Optional
                                               .setCardHolderName(CARD_HOLDER_NAME)// Optional
                                               .build();
```

EncryptTransactionObject rquire Merchant Authentication object and it can be created as follows:

```java
ClientKeyBasedMerchantAuthentication merchantAuthentication = ClientKeyBasedMerchantAuthentication.
                createMerchantAuthentication(API_LOGIN_ID, CLIENT_KEY);
```
Check out "Obtaining a Public Client Key" section in [Accept JS](http://developer.authorize.net/api/reference/features/acceptjs.html) 
to get more information getting CLIENT_KEY.


### 3. Calling Token API

When transaction information are ready, you can make following call to fetch token.

```java
// Parameters: 
// 1. EncryptTransactionObject - The transaction object for current transaction
// 2. transaction response callback.
apiClient.getTokenWithRequest(transactionObject, callback);
```

### 4. Implement  EncryptTransactionCallback Interface.

To get a response back, the activity/fragment should implement the `EncryptTransactionCallback` interface. It has following methods.
 * [onEncryptionFinished] (#onEncryption-Finished)
 * [onErrorReceived](#onError-Received)
 
 * onEncryptionFinished : 
   This method will be called when token is successfully generated.
    > response.getDataDescriptor() returns  "COMMON.ACCEPT.INAPP.PAYMENT" 
    > response.getDataValue()  returns Token data Ex: 9469429169768019305001
    This information are used to perform Payment transaction.

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
* onErrorReceived :
   This  method will be called in three senarios,
     > Validation of information is failed.
     > Network related errors.
     > API error response.
 "ErrorTransactionResponse" may contain one or more error messages.

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

## Demo Applcation
 We have a demo application which demonstrates the SDK usage:  
   https://github.com/AuthorizeNet/accept-sample-android

