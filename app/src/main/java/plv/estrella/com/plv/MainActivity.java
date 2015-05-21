package plv.estrella.com.plv;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import plv.estrella.com.plv.untils.ApiManager;


public class MainActivity extends ActionBarActivity {

    private ImageButton menuBtn;
    private LinearLayout mBackgroundLayout;
    private TextView mTitle;
    private ImageView logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findUI();
        ApiManager.init(this);
        ApiManager.setOfflineMode();

    }


    private void findUI() {
        menuBtn = (ImageButton) findViewById(R.id.ibMenu);
        mTitle = (TextView) findViewById(R.id.tvMenuTitle);
        logo = (ImageView) findViewById(R.id.tvLogoTitle);
        mBackgroundLayout = (LinearLayout) findViewById(R.id.llAppContainer);
    }
}
