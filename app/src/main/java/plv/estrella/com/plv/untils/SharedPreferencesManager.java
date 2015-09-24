package plv.estrella.com.plv.untils;

import android.content.Context;
import android.content.SharedPreferences;

import plv.estrella.com.plv.SplashScreen;
import plv.estrella.com.plv.global.Constants;


/**
 * Created by Sasha on 17.10.2014.
 */
public abstract class SharedPreferencesManager {

    public static void saveUpdateDate(final Context _context, final long time) {
        final SharedPreferences prefs = _context.getSharedPreferences(
                SplashScreen.class.getSimpleName(), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putLong(Constants.UPDATE_TIME, time).commit();
    }

    public static long getUpdateDate(final Context _context) {
        final SharedPreferences prefs = _context.getSharedPreferences(
                 SplashScreen.class.getSimpleName(), Context.MODE_PRIVATE);
        return prefs.getLong(Constants.UPDATE_TIME, 0);
    }

    public static void setNeedDownload(final Context context, boolean needDownload){
        final SharedPreferences prefs = context.getSharedPreferences(SplashScreen.class.getSimpleName(), Context.MODE_PRIVATE);
        prefs.edit()
                .putBoolean(Constants.NEED_LOAD, needDownload)
                .apply();
    }

    public static boolean needDownload(final Context context){
        final SharedPreferences prefs = context.getSharedPreferences(
                SplashScreen.class.getSimpleName(), Context.MODE_PRIVATE);
        return prefs.getBoolean(Constants.NEED_LOAD, true);
    }
}
