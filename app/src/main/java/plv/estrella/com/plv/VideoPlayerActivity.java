package plv.estrella.com.plv;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

import plv.estrella.com.plv.global.Constants;

/**
 * Created by samson on 08.07.15.
 */
public class VideoPlayerActivity extends Activity {
    WebView videoContainer;
    String videoId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_play);
        findView();

        Intent intent = getIntent();
        videoId = (String)intent.getExtras().get(Constants.YOUTUBE_VIDEO_ID);

        if(!videoId.isEmpty()){
            playVideo(videoId);
        }
    }

    public void findView()    {
        videoContainer = (WebView) findViewById(R.id.wv_video_container);
    }

    public void playVideo(String _videoId){
        videoContainer.loadUrl("https://www.youtube.com/embed/"
                + getYouTubeImageId(_videoId)
                + "/0.jpg"
        );

        videoContainer.getSettings().setJavaScriptEnabled(true);

        videoContainer.getSettings().setAllowContentAccess(true);
        WebSettings webSettings = videoContainer.getSettings();
        webSettings.setPluginState(WebSettings.PluginState.ON);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        videoContainer.canGoBack();

        videoContainer.setWebChromeClient(new WebChromeClient() {
        });
    }

    public static String getYouTubeImageId(String _videoUrl){
        int start_index = _videoUrl.lastIndexOf("/")+1;
        int end_index = _videoUrl.length();
        return  _videoUrl.substring(start_index,end_index);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        videoContainer.destroy();
        this.finish();
    }


}