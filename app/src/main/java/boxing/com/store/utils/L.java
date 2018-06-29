package boxing.com.store.utils;

import android.util.Log;

import boxing.com.store.BuildConfig;

public class L {
    public static boolean DEBUG = BuildConfig.LOG_DEBUG;
    private static final String TAG = "store";

    public static void i(String msg) {
        if (DEBUG) {
            Log.i(TAG, msg);
        }
    }

    public static void i(int msg) {
        if (DEBUG) {
            Log.i(TAG, msg + "");
        }
    }

    public static void e(Exception e) {
        if (DEBUG) {
            Log.e(TAG, e.getMessage(), e);
        }
    }

    public static void e(String tag, Exception e) {
        if (DEBUG) {
            Log.e(tag, e.getMessage(), e);
        }
    }

    public static void e(String e) {
        if (DEBUG) {
            Log.e(TAG, e);
        }
    }

    public static void i(float msg) {
        if (DEBUG) {
            Log.i(TAG, msg + "");
        }
    }
}
