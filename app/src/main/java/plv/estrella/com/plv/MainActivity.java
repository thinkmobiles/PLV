package plv.estrella.com.plv;

import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import plv.estrella.com.plv.fragments.MainMenuFragment;
import plv.estrella.com.plv.untils.ApiManager;
import plv.estrella.com.plv.untils.FragmentReplacer;


public class MainActivity extends FragmentActivity {

    private ImageButton menuBtn;
    private LinearLayout mBackgroundLayout;
    private TextView mTitle;
    private ImageView logo;
    private double doubleBackToExitPressedOnce;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findUI();
        ApiManager.init(this);
        ApiManager.setOfflineMode();
        setBackground();
        startMainMenu();
    }


    private void findUI() {
        menuBtn = (ImageButton) findViewById(R.id.ibMenu);
        mTitle = (TextView) findViewById(R.id.tvMenuTitle);
        logo = (ImageView) findViewById(R.id.tvLogoTitle);
        mBackgroundLayout = (LinearLayout) findViewById(R.id.llAppContainer);
    }

    private void startMainMenu(){
        FragmentReplacer.replaceFragmentWithStack(this, new MainMenuFragment());
    }

    public void setBackground() {
        mBackgroundLayout.setBackground(getResources().getDrawable(R.drawable.bg_splashscreen));
    }

    public void setTitle(String _title){
        mTitle.setText(_title);
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
            Toast.makeText(this, getResources().getString(R.string.click_back), Toast.LENGTH_SHORT).show();

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
