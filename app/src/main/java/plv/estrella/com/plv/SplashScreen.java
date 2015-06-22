package plv.estrella.com.plv;

import android.app.Activity;
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

import plv.estrella.com.plv.custom.circleprogress.CircleProgress;
import plv.estrella.com.plv.untils.ApiManager;
import plv.estrella.com.plv.untils.ProgressDialogWorker;
import plv.estrella.com.plv.untils.SharedPreferencesManager;

public class SplashScreen extends Activity {

    private EventListener downloadListener;
    private CircleProgress mProgressView;
    private boolean mIsLoadContent = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        mProgressView = (CircleProgress) findViewById(R.id.progress);

        if (isHasContent()) {
//            if(hasNewContent()){
//                makeDownloadListener();
//                updateContent();
//            } else {
//            updateContent();
                openMainActivityDelay();
//            }
        } else {
//            ProgressDialogWorker.createDialog(this);
            makeDownloadListener();
            downloadContent();
        }
    }


    private void getLastUpdate(){
        makeDownloadListener();
        ScheduledExecutorService worker =
                Executors.newSingleThreadScheduledExecutor();
        worker.schedule(mUpdateRunnable, 2, TimeUnit.SECONDS);
    }

    private void openMainActivityDelay(){
        ScheduledExecutorService worker =
                Executors.newSingleThreadScheduledExecutor();
        Runnable task = new Runnable() {
            public void run() {
                openMainActivity();
            }
        };
        worker.schedule(task, 1, TimeUnit.SECONDS);
    }


    private Runnable mUpdateRunnable = new Runnable() {
        @Override
        public void run() {
            updateContent();
        }
    };

    private void updateContent() {
        makeDownloadListener();
        ApiManager.init(this);
        ApiManager.getLastUpdateServer(downloadListener);
    }


    private boolean isHasContent() {
        File f = new File(ApiManager.getPath(this));
        return f.exists();
    }

    private void openMainActivity() {
        startActivity(new Intent(SplashScreen.this, MainActivity.class));
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
                Log.d("tag", "e = " + event);
                switch (event.getId()) {
                    case AppModel.ChangeEvent.ON_EXECUTE_ERROR_ID:
                        Toast.makeText(getBaseContext(), event.getType() + " error", Toast.LENGTH_LONG).show();
                        break;
                    case AppModel.ChangeEvent.DOWNLOAD_ALL_CHANGED_ID:
//                        todo if download finish
//                        runOnUiThread (new Thread(new Runnable() {
//                            @Override
//                            public void run() {
                                SharedPreferencesManager.saveUpdateDate(getBaseContext(), System.currentTimeMillis());
//                                ProgressDialogWorker.dismissDialog();
                                ApiManager.setOfflineMode();
                                openMainActivity();
//                            }
//                        }));

                        break;
                    case AppModel.ChangeEvent.LAST_UPDATE_CHANGED_ID:
//                        todo Last Update
                        runOnUiThread (new Thread(new Runnable() {
                            @Override
                            public void run() {
                                SharedPreferencesManager.saveUpdateDate(getBaseContext(), System.currentTimeMillis());
                                ProgressDialogWorker.dismissDialog();
                                ApiManager.setOfflineMode();
                                openMainActivity();
                                String date  = ApiManager.getDateUpdate();
                                Log.d("tag", "update date" + date);
                            }
                        }));
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
        ApiManager.init(this);
        final Date currentUpdate = new Date(SharedPreferencesManager.getUpdateDate(getBaseContext()));
        final Date lastUpdate = getDate(ApiManager.getDateUpdate());
//        if(currentUpdate.before(lastUpdate))
//            return true;
        return false;

    }

    private Date getDate(final String _date){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(2000,1,1);
        if(_date != null) {
            try {
                date = format.parse(_date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return date;
    }
}
