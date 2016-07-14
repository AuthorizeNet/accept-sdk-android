package net.authorize.acceptsdk.network;

import android.content.Intent;
import android.test.ServiceTestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Kiran Bollepalli on 14,July,2016.
 * kbollepa@visa.com
 */
public class AcceptServiceTest extends ServiceTestCase<AcceptService> {

  public AcceptServiceTest(Class<AcceptService> serviceClass) {
    super(serviceClass);
  }

  @Before public void setUp() throws Exception {
    super.setUp();
  }

  @Test public void testStartActionEncrypt() throws Exception {
    AcceptService.startActionEncrypt(getSystemContext(), null, null);
  }

  @Test public void testOnHandleIntent() throws Exception {
    Intent intent = new Intent(getSystemContext(), AcceptService.class);
    intent.setAction(AcceptService.ACTION_ENCRYPT);
    //Bundle bundle = new Bundle();
    //intent.putExtras(bundle);
    startService(intent);

    assertNotNull(getService());
  }

  @Test public void testOnPostHandleAction() throws Exception {

  }
}