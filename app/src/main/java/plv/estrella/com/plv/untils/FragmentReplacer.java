package plv.estrella.com.plv.untils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.transition.Transition;

import plv.estrella.com.plv.R;

public abstract class FragmentReplacer {

    public static void popSupBackStack(final FragmentActivity _activity) {
        _activity.getSupportFragmentManager().popBackStack();
    }

    public static void addFragment(final FragmentActivity _activity, final Fragment _fragment) {
        _activity.getSupportFragmentManager().beginTransaction()
                .add(R.id.container, _fragment)
                .addToBackStack(null)
                .commit();
    }

}
