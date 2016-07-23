package net.authorize.acceptsdk.network;

/**
 * Class contains constants and methods related to Network connection.
 *
 * Created by Kiran Bollepalli on 08,July,2016.
 * kbollepa@visa.com
 */
public class ConnectionData {

  /**
   * End point url for Sandbox.
   */
  public static final String ENDPOINT_SANDBOX = "https://apitest.authorize.net/xml/v1/request.api ";

  /**
   * End point url for Production.
   */
  public static final String ENDPOINT_PRODUCTION = "https://api.authorize.net/xml/v1/request.api ";

  /**
   * Label for content-type header.
   */
  public static final String CONTENT_TYPE_LABEL = "Content-type";
  /**
   * Value for COntent-type header = application/json.
   */
  public static final String CONTENT_TYPE_APPLICATION_JSON = "application/json";

  /**
   * Timeout for making a connection.
   */
  public static final int DEFAULT_CONN_TIMEOUT = 5000;
  /**
   * Timeout for receiving data.
   */
  public static final int DEFAULT_SOCKET_TIMEOUT = 20000;

  /**
   * API current endpoint address.
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
