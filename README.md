<<<<<<< HEAD

# Authorize.Net Accept Mobile SDK for Android

## SDK Installation

Android Studio is preferred because Eclipse will not be supported by Google much longer.

### Android Studio (or Gradle)

Add this line to your app's `build.gradle` inside the `dependencies` section as follows:

```groovy
    dependencies {
        compile 'net.authorize:accept-android-sdk:+'
    }
```

## SDK Usage
After the installation is succesfully complete, perform the following steps to program an Android app with this SDK.

1) To initiate requests with the SDK, create an API client that will make API requests on your behalf. The Accept SDK API client can be built as follows:

```java
// Parameters:
// 1) Context - current context
=======
# Accept Android SDK

This SDK is Android version of [Accept JS](http://developer.authorize.net/api/reference/features/acceptjs.html). This SDK is used to fetch token for given Payment Information.

## Contents

1. [Installation](#installation-one-step)
1. [Getting Started](#getting-started-four-steps)
1. [Demo Applcation](#demo-applcation)

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

 All SDK APi's will be accessed through `AcceptSDKApiClient` Object. It can be created as follows:

```java
// Parameters:
// 1) Context - Activity context
>>>>>>> 17b4544880f005528285cb1c5927b41dda078b6b
// 2) AcceptSDKApiClient.Environment - AUTHORIZE.NET ENVIRONMENT
apiClient = new AcceptSDKApiClient.Builder (getActivity(),
                                          AcceptSDKApiClient.Environment.SANDBOX) 
                                          .sdkConnectionTimeout(5000) // optional connection time out in milliseconds
                                          .build();
```
<<<<<<< HEAD
2) To make the API call, you can create a Encryption transaction object as follows:
=======
### 2. Prepare Objects rquired to call Token API
 Fetch token API require `EncryptTransactionObject` and it can be created as follows:
>>>>>>> 17b4544880f005528285cb1c5927b41dda078b6b

```java
 EncryptTransactionObject transactionObject = TransactionObject.
         createTransactionObject(TransactionType.SDK_TRANSACTION_ENCRYPTION)// type of transaction object
        .cardData(prepareCardDataFromFields()) // card data to be encrypted
        .merchantAuthentication(prepareMerchantAuthentication()) //Merchant authentication
        .build();
```
<<<<<<< HEAD
A card object can be created as follows:
=======
`EncryptTransactionObject` require CardData object and it can be created as follows:
>>>>>>> 17b4544880f005528285cb1c5927b41dda078b6b

```java
CardData cardData = new CardData.Builder(CARD_NUMBER,
                                               EXPIRATION_MONTH, // MM
                                               EXPIRATION_YEAR) // YYYY
                                               .setCVVCode(CARD_CVV) // Optional
                                               .setZipCode(ZIP_CODE)// Optional
                                               .setCardHolderName(CARD_HOLDER_NAME)// Optional
                                               .build();
```

<<<<<<< HEAD
Merchant Authentication can be created as follows:
=======
`EncryptTransactionObject` require Merchant Authentication object and it can be created as follows:
>>>>>>> 17b4544880f005528285cb1c5927b41dda078b6b

```java
ClientKeyBasedMerchantAuthentication merchantAuthentication = ClientKeyBasedMerchantAuthentication.
                createMerchantAuthentication(API_LOGIN_ID, CLIENT_KEY);
```
<<<<<<< HEAD
Check out  "Obtaining a Public Client Key" in http://developer.authorize.net/api/reference/features/acceptjs.html to get CLIENT_KEY.

3) When the API client and transaction information are ready, you can make a call to perform a specific API.
=======
Check out "Obtaining a Public Client Key" section in [Accept JS](http://developer.authorize.net/api/reference/features/acceptjs.html) 
to get more information getting CLIENT_KEY.


### 3. Calling Token API

When transaction information are ready, you can make following call to fetch token.
>>>>>>> 17b4544880f005528285cb1c5927b41dda078b6b

```java
// Parameters: 
// 1. EncryptTransactionObject - The transaction object for current transaction
// 2. transaction response callback.
apiClient.getTokenWithRequest(transactionObject, callback);
```

<<<<<<< HEAD
4) To get a response back, the activity/fragment should implement the `EncryptTransactionCallback` interface.

=======
### 4. Implement  EncryptTransactionCallback Interface.

To get a response back, the activity/fragment should implement the `EncryptTransactionCallback` interface. It has following methods.

> [onEncryptionFinished()](#onEncryption-Finished)

> [onErrorReceived()](#onError-Received)

### onEncryptionFinished() 

   This method will be called when token is successfully generated.`EncryptTransactionResponse` object has Data Descriptor and Data value details which will be used to perform payment transaction.
   
>>>>>>> 17b4544880f005528285cb1c5927b41dda078b6b
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
<<<<<<< HEAD
**OR**

In case of an error:
=======
### onErrorReceived()

   This  method will be called in three senarios,
   
     > Validation of information is failed.
     > Network related errors.
     > API error response.
     
 `ErrorTransactionResponse` may contain one or more error messages.
>>>>>>> 17b4544880f005528285cb1c5927b41dda078b6b

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

<<<<<<< HEAD
##Sample Application
We have a sample application which demonstrates the SDK usage:  
=======
## Demo Applcation

 We have a demo application which demonstrates the SDK usage:  
>>>>>>> 17b4544880f005528285cb1c5927b41dda078b6b
   https://github.com/AuthorizeNet/accept-sample-android

