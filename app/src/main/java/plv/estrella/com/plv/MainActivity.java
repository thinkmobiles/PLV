package plv.estrella.com.plv;

import android.graphics.drawable.Drawable;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import plv.estrella.com.plv.fragments.MainMenuFragment;
import plv.estrella.com.plv.untils.ApiManager;
import plv.estrella.com.plv.untils.FragmentReplacer;
import plv.estrella.com.plv.untils.SlidingMenuManager;


public class MainActivity extends FragmentActivity {

    private ImageButton menuBtn;
    private LinearLayout mBackgroundLayout;
    private TextView mTitle;
    private ImageView logo;
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
        menuBtn = (ImageButton) findViewById(R.id.ibMenu);
        mTitle = (TextView) findViewById(R.id.tvMenuTitle);
        logo = (ImageView) findViewById(R.id.tvLogoTitle);
        mBackgroundLayout = (LinearLayout) findViewById(R.id.llAppContainer);

        menuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isOpen){
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
        mBackgroundLayout.setBackground(getResources().getDrawable(R.drawable.background));
    }

    public void setBackground(final String _path){
        mBackgroundLayout.setBackground(Drawable.createFromPath(ApiManager.getPath() + _path));
    }

    public void setTitle(String _title){
        mTitle.setText(_title);
    }

    public void setEnableSlideMenu(boolean isEnable){
        menuManager.enableMenu(isEnable);
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
