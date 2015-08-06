package plv.estrella.com.plv.global;

import android.content.Context;
import com.orm.SugarApp;
import plv.estrella.com.plv.untils.TypefaceUtil;

public class App extends SugarApp {
    @Override
    public void onCreate() {
        super.onCreate();
        TypefaceUtil.overrideFont(getApplicationContext(), "SERIF", "fonts/helvetica_light.ttf"); // font from assets: "assets/fonts/helvetica_roman.ttf
    }
}
