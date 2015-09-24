package plv.estrella.com.plv.untils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import plv.estrella.com.plv.R;
import plv.estrella.com.plv.SplashScreen;

/**
 * Created by samson on 23.09.15.
 */
public class WatcherContent {

    private static Context context, activity;

    public static void initContext(Context context) {
        WatcherContent.context = context;
    }

    public static void initActivity(Context activity) {
        WatcherContent.activity = activity;
    }

    public static boolean isNeedLoad() {
        return SharedPreferencesManager.needDownload(context);
    }

    public static void showError() {
        SharedPreferencesManager.setNeedDownload(context, true);
        new AlertDialog.Builder(activity)
                .setMessage(activity.getString(R.string.miss_content))
                .setPositiveButton(activity.getString(R.string.ok),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (Network.isInternetConnectionAvailable(context)) {
                                    activity.startActivity(new Intent(activity, SplashScreen.class));
                                }
                                dialog.dismiss();
                            }
                        })
                .create()
                .show();
    }
}
