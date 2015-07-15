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
import plv.estrella.com.plv.untils.SlidingMenuManager;


public class MainActivity extends FragmentActivity {

    private LinearLayout header, footer;
    private TextView mTitle;
    private ImageView background;
    private double doubleBackToExitPressedOnce;
    private SlidingMenuManager menuManager;

    private boolean isOpen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findUI();
        ApiManager.init(this);
        ApiManager.setOfflineMode();
        setBackground();
        startMainMenu();

        menuManager = new SlidingMenuManager();
        menuManager.initMenu(this);

    }

    private void findUI() {
        background          = (ImageView) findViewById(R.id.mainBg);
        mTitle              = (TextView) findViewById(R.id.tvMenuTitle);
        header              = (LinearLayout) findViewById(R.id.llHeader);
        footer              = (LinearLayout) findViewById(R.id.llFooter);

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
        FragmentReplacer.replaceFragmentWithStack(this, new MainMenuFragment());
    }

    public void setBackground() {
        background.setImageBitmap(BitmapCreator.getCompressedBitmap(this, R.drawable.background, 1080f));
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

    public void setFullScreenSetting(boolean isFull){
        if(isFull){
            header.setVisibility(View.GONE);
            footer.setVisibility(View.GONE);
        } else {
            header.setVisibility(View.VISIBLE);
            footer.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onBackPressed() {
        if (FragmentReplacer.getSupBackStackEntryCount(this) == 1) {
            if (doubleBackToExitPressedOnce + 2000 > System.currentTimeMillis()) {
                super.onBackPressed();
                MainActivity.this.finish();
                return;
            }

            doubleBackToExitPressedOnce = System.currentTimeMillis();
            Toast.makeText(this, getString(R.string.click_back), Toast.LENGTH_SHORT).show();

        } else {
            goBack();
        }
    }

    private void goBack() {
        if (FragmentReplacer.getSupBackStackEntryCount(this) != 0) {
            FragmentReplacer.popSupBackStack(this);
        }
    }
}
