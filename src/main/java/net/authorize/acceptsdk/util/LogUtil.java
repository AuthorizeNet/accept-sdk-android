package net.authorize.acceptsdk.util;

import android.util.Log;
import net.authorize.acceptsdk.BuildConfig;

/**
 * Utility class for Logging.
 *
 * Created by Kiran Bollepalli on 12,July,2016.
 * kbollepa@visa.com
 */
public final class LogUtil {

  public static final String LOG_TAG = "Accept SDK";

  public enum LOG_LEVEL {
    VERBOSE,
    DEBUG,
    INFO,
    WARN,
    ERROR
  }

  ;

  public static void log(LOG_LEVEL level, String message) {


    if (!BuildConfig.DEBUG) return;

    if (level == LOG_LEVEL.VERBOSE) {
      Log.v(LOG_TAG, message);
    } else if (level == LOG_LEVEL.DEBUG) {
      Log.d(LOG_TAG, message);
    } else if (level == LOG_LEVEL.INFO) {
      Log.i(LOG_TAG, message);
    } else if (level == LOG_LEVEL.WARN) {
      Log.w(LOG_TAG, message);
    } else if (level == LOG_LEVEL.ERROR) {
      Log.e(LOG_TAG, message);
    }
  }
}
