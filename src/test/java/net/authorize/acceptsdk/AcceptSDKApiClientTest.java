package net.authorize.acceptsdk;

import android.content.Context;
import junit.framework.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
/**
 * Created by Kiran Bollepalli on 14,July,2016.
 * kbollepa@visa.com
 */
@RunWith(MockitoJUnitRunner.class)
public class AcceptSDKApiClientTest {


  private final String API_LOGIN_ID = "6AB64hcB";

  AcceptSDKApiClient apiClient;
  @Mock
  Context context;
  String apiLoginID;

  @Before public void setUp() throws Exception {
    apiLoginID = API_LOGIN_ID;
    apiClient =
        new AcceptSDKApiClient.Builder(context, AcceptSDKApiClient.Environment.SANDBOX).build();
  }

  @After public void tearDown() throws Exception {
    context = null;
    apiClient = null;
  }

  @Test public void testGetContext() throws Exception {
    Assert.assertNotNull(AcceptSDKApiClient.getContext().get());
  }
}