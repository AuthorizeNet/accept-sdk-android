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

1. To initiate requests with the SDK, create an API client that will make API requests on your behalf. The Accept SDK API client can be built as follows:

```java
// Parameters:
// 1) Context - current context
// 2) AcceptSDKApiClient.Environment - AUTHORIZE.NET ENVIRONMENT
apiClient = new AcceptSDKApiClient.Builder (getActivity(),
                                          AcceptSDKApiClient.Environment.SANDBOX) 
                                          .sdkConnectionTimeout(5000) // optional connection time out in milliseconds
                                          .build();
```

2. To make the API call, you can create a transaction object as follows:

```java
  TransactionObject.
        createTransactionObject(
            TransactionType.SDK_TRANSACTION_ENCRYPTION) // type of transaction object
        .cardData(prepareCardDataFromFields()) // card data to be encrypted
        .merchantAuthentication(prepareMerchantAuthentication()) //Merchant authentication
        .build();
```

3. A card object can be created as follows:

```java
SDKCardData cardData = new SDKCardData.Builder(ACCOUNT_NUMBER,
                                               EXPIRATION_MONTH, // MM
                                               EXPIRATION_YEAR) // YYYY
                                               .cvNumber(CARD_CVV) // Optional
                                               .type(SDKCardAccountNumberType.PAN) // Optional if unencrypted. If the value is set to a token then it is not optional and must be set to SDKCardType.TOKEN
                                               .build();
```
