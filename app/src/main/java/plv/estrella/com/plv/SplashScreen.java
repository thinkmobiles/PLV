package plv.estrella.com.plv;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.cristaliza.mvc.events.Event;
import com.cristaliza.mvc.events.EventListener;
import com.cristaliza.mvc.models.estrella.AppModel;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import plv.estrella.com.plv.custom.CustomDialog;
import plv.estrella.com.plv.custom.circleprogress.CircleProgress;
import plv.estrella.com.plv.untils.ApiManager;
import plv.estrella.com.plv.untils.Network;
import plv.estrella.com.plv.untils.SharedPreferencesManager;
import plv.estrella.com.plv.untils.WatcherContent;

public class SplashScreen extends Activity {

    private EventListener downloadListener;
    private CircleProgress mProgressView;
    private boolean mIsLoadContent = false;
    private TextView mInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        mProgressView = (CircleProgress) findViewById(R.id.progress);
        mInfo = (TextView) findViewById(R.id.tvDownloadProcess);

        ApiManager.init(this);
        WatcherContent.initContext(this);
        makeDownloadListener();

        checkContent();

    }

    private void checkContent() {
        if (Network.isInternetConnectionAvailable(this)) {
            if (WatcherContent.isNeedLoad()) {
                downloadContent();
            } else if (isHasContent()) {
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
                    }
                })
                .setNegativeButton(R.string.button_cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        openMainActivity();
                    }
                })
                .create()
                .show();
    }

    private boolean isHasContent() {
        return new File(ApiManager.getPath(this)).exists();
    }

    private void openMainActivity() {

        mProgressView.stopAnim();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(600);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    startActivity(new Intent(SplashScreen.this, MainActivity.class));
                    finish();
                }
            }
        }).start();
    }

    private void downloadContent() {
        mIsLoadContent = true;
        ApiManager.downloadContent(downloadListener);
        mProgressView.setVisibility(View.VISIBLE);
        mInfo.setVisibility(View.VISIBLE);
        mProgressView.startAnim();
    }

    private void makeDownloadListener() {
        downloadListener = new EventListener() {
            @Override
            public void onEvent(final Event event) {
                switch (event.getId()) {
                    case AppModel.ChangeEvent.DOWNLOAD_ALL_CHANGED_ID:
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                SharedPreferencesManager.saveUpdateDate(getBaseContext(), System.currentTimeMillis());
                                SharedPreferencesManager.setNeedDownload(getBaseContext(), false);
                                openMainActivity();
                            }
                        });
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
        Calendar currentUpdate = Calendar.getInstance();
        Calendar lastUpdate = Calendar.getInstance();
        currentUpdate.setTimeInMillis(SharedPreferencesManager.getUpdateDate(getBaseContext()));
        lastUpdate.setTime(getDate(ApiManager.getDate()));
        return currentUpdate.before(lastUpdate);

    }

    private Date getDate(final String _date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = format.parse(_date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
}
