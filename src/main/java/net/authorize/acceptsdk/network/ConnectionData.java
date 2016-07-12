package net.authorize.acceptsdk.network;

/**
 * Created by Kiran Bollepalli on 08,July,2016.
 * kbollepa@visa.com
 */
public class ConnectionData {

  public static final String ENDPOINT_SANDBOX = "https://apitest.authorize.net/xml/v1/request.api ";
  public static final String ENDPOINT_PRODUCTION = "https://api.authorize.net/xml/v1/request.api ";

  /**
   * Soap action to use if none is specified.
   */
  public static final String BLANK_SOAP_ACTION = "";
  /**
   * Port for HTTPS communication.
   */
  public static final int DEFAULT_HTTPS_PORT = 443;
  /**
   * Port for HTTP communication
   */
  public static final int DEFAULT_HTTP_PORT = 80;
  /**
   * Name of HTTPS.
   */
  public static final String HTTPS_NAME = "https";
  /**
   * Name of HTTP.
   */
  public static final String HTTP_NAME = "http";
  /**
   * HTTP content type submitted in HTTP POST request for SOAP calls.
   */
  public static final String XML_CONTENT_TYPE_PREFIX = "text/xml; charset=";
  /**
   * Label for content-type header.
   */
  public static final String CONTENT_TYPE_LABEL = "Content-type";
  /**
   * Value for COntent-type header = application/json.
   */
  public static final String CONTENT_TYPE_APPLICATION_JSON = "application/json";
  /**
   * Key for SOAP action header.
   */
  public static final String HEADER_KEY_SOAP_ACTION = "SOAPAction";
  /**
   * Timeout for making a connection.
   */
  public static final int DEFAULT_CONN_TIMEOUT = 5000;
  /**
   * Timeout for recieving data.
   */
  public static final int DEFAULT_SOCKET_TIMEOUT = 20000;

  /**
   * SOAP Payments CURRENT endpoint address.
   */
  private static String sActiveEndPointUrl;

  private static int sConnectionTimeout = DEFAULT_CONN_TIMEOUT;

  public static int getConnectionTimeout() {
    return sConnectionTimeout;
  }

  public static void setConnectionTimeout(int timeout) {
    sConnectionTimeout = timeout;
  }

  public static void setActiveEndPointUrl(String url) {
    sActiveEndPointUrl = url;
  }

  public static String getActiveEndPointUrl() {
    return sActiveEndPointUrl;
  }
}
