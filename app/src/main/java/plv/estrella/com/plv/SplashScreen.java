package plv.estrella.com.plv;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.cristaliza.mvc.events.Event;
import com.cristaliza.mvc.events.EventListener;
import com.cristaliza.mvc.models.estrella.AppModel;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import plv.estrella.com.plv.custom.CustomDialog;
import plv.estrella.com.plv.custom.circleprogress.CircleProgress;
import plv.estrella.com.plv.untils.ApiManager;
import plv.estrella.com.plv.untils.Network;
import plv.estrella.com.plv.untils.ProgressDialogWorker;
import plv.estrella.com.plv.untils.SharedPreferencesManager;

public class SplashScreen extends Activity {

    private EventListener downloadListener;
    private CircleProgress mProgressView;
    private boolean mIsLoadContent = false;
    private Date lastUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        mProgressView = (CircleProgress) findViewById(R.id.progress);

        ApiManager.init(this);
        makeDownloadListener();

        checkContent();

    }

    private void checkContent(){
        if (Network.isInternetConnectionAvailable(this)) {
            if(isHasContent()){
                if (hasNewContent()) {
                    showDialogUpdate();
                } else {
                    openMainActivity();
                }
            } else {
                downloadContent();
            }
        } else {
            if (isHasContent()) {
                openMainActivity();
            } else {
                showDialogClose();
            }
        }
    }

    private void showDialogClose() {
        new AlertDialog.Builder(this)
                .setMessage(getString(R.string.check_connection))
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .create()
                .show();
    }

    private void showDialogUpdate() {
        new AlertDialog.Builder(this)
                .setMessage(getString(R.string.want_update))
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        downloadContent();
                        openMainActivity();
                        dialog.dismiss();
                    }
                })
                .setNegativeButton(R.string.button_cancel, null)
                .create()
                .show();
    }
    private boolean isHasContent() {
        return new File(ApiManager.getPath(this)).exists();
    }

    private void openMainActivity() {
        startActivity(new Intent(this, MainActivity.class));
        mProgressView.stopAnim();
        finish();
    }

    private void downloadContent() {
        mIsLoadContent = true;
        ApiManager.init(this);
        ApiManager.downloadContent(downloadListener);
        mProgressView.setVisibility(View.VISIBLE);
        mProgressView.startAnim();
    }

    private void makeDownloadListener() {
        downloadListener = new EventListener() {
            @Override
            public void onEvent(Event event) {
                Log.e("tag", "e = " + event.getId());
                switch (event.getId()) {
                    case AppModel.ChangeEvent.ON_EXECUTE_ERROR_ID:
                        Toast.makeText(getBaseContext(), event.getType() + " error", Toast.LENGTH_LONG).show();
                        break;
                    case AppModel.ChangeEvent.DOWNLOAD_ALL_CHANGED_ID:
                        SharedPreferencesManager.saveUpdateDate(getBaseContext(), System.currentTimeMillis());
                        ApiManager.setOfflineMode();
                        break;
                    case AppModel.ChangeEvent.LAST_UPDATE_CHANGED_ID:

                        Log.e("listener", "update");
                        Log.e("date", ApiManager.getDateUpdate());

                        lastUpdate = getDate(ApiManager.getDateUpdate());
                        break;
                    case AppModel.ChangeEvent.DOWNLOAD_FILE_CHANGED_ID:
                        break;
                    case AppModel.ChangeEvent.FIRST_LEVEL_CHANGED_ID:
                        Log.e("u", "1lvl");
                        ApiManager.getLastUpdateServer(downloadListener);
                        break;
                }
            }
        };

    }

    @Override
    public void onBackPressed() {
        if (!mIsLoadContent) {
            super.onBackPressed();
        }
    }

    private boolean hasNewContent() {
        Date currentUpdate = new Date(SharedPreferencesManager.getUpdateDate(getBaseContext()));
        Log.e("update","call");
//        ApiManager.setOfflineMode();
//        ApiManager.getFirstLevel(downloadListener);
        ApiManager.getLastUpdateServer(downloadListener);
//        if(currentUpdate.before(lastUpdate))
//            return true;
        return false;

    }

    private Date getDate(final String _date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(2000, 1, 1);
        if (_date != null) {
            try {
                date = format.parse(_date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return date;
    }
}
