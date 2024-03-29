package plv.estrella.com.plv;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import plv.estrella.com.plv.fragments.MainMenuFragment;
import plv.estrella.com.plv.global.Constants;
import plv.estrella.com.plv.untils.ApiManager;
import plv.estrella.com.plv.untils.BitmapCreator;
import plv.estrella.com.plv.untils.FragmentReplacer;
import plv.estrella.com.plv.untils.FragmentUtil;
import plv.estrella.com.plv.untils.SlidingMenuManager;
import plv.estrella.com.plv.untils.WatcherContent;


public class MainActivity extends FragmentActivity {

    private TextView mTitle;
    private ImageView background;
    private long doubleBackToExitPressedOnce;
    private SlidingMenuManager menuManager;

    private boolean isOpen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findUI();
        ApiManager.init(this);
        WatcherContent.initActivity(this);
        initFragmentUtil();
        ApiManager.setOfflineMode();
        setBackground();
        startMainMenu();

        menuManager = new SlidingMenuManager();
        menuManager.initMenu(this);

    }

    private void findUI() {
        background          = (ImageView) findViewById(R.id.mainBg);
        mTitle              = (TextView) findViewById(R.id.tvMenuTitle);

        (findViewById(R.id.ivMenu)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isOpen) {
                    menuManager.toggle();
                    isOpen = false;
                } else {
                    menuManager.show();
                    isOpen = true;
                }
            }
        });
    }

    private void startMainMenu(){
        FragmentUtil.replaceFragmentWithStack(new MainMenuFragment());
    }

    private void initFragmentUtil(){
        FragmentUtil.init(this, R.id.container);
    }

    public void setBackground() {
        background.setImageBitmap(BitmapCreator.getCompressedBitmap(this, R.drawable.background, 800f));
    }

    public void setBackground(final String _path){
        background.setImageBitmap(BitmapCreator.getCompressedBitmap(_path, Constants.RATIO_16_9, 600f));
    }

    public void setTitle(String _title){
        mTitle.setText(_title);
    }

    public void setEnableSlideMenu(boolean isEnable){
        menuManager.enableMenu(isEnable);
    }

    @Override
    public void onBackPressed() {
        if (FragmentUtil.getBackStackCount() == 1) {
            if (doubleBackToExitPressedOnce + 2000 > System.currentTimeMillis()) {
                super.onBackPressed();
                FragmentUtil.popBackStack();
                MainActivity.this.finish();
                return;
            }

            doubleBackToExitPressedOnce = System.currentTimeMillis();
            Toast.makeText(this, getString(R.string.click_back), Toast.LENGTH_SHORT).show();

        } else {
            FragmentUtil.popBackStack();
        }
    }
}
