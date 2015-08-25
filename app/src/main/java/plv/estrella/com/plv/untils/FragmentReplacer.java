package plv.estrella.com.plv.untils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.transition.Transition;

import plv.estrella.com.plv.R;

public abstract class FragmentReplacer {

    public static final void popSupBackStack(final FragmentActivity _activity) {
        _activity.getSupportFragmentManager().popBackStack();
    }

    public static final Fragment getLastFragment(final FragmentActivity _activity) {
        if(getSupBackStackEntryCount(_activity)  != 0) {
            return _activity.getSupportFragmentManager().getFragments().get(getSupBackStackEntryCount(_activity) - 1);
        } else {
            return new JumpFragmentHelper();
        }
    }

    public static final int getSupBackStackEntryCount(final FragmentActivity _activity) {
        return _activity.getSupportFragmentManager().getBackStackEntryCount();
    }

    public static final void clearSupBackStack(final FragmentActivity _activity){
        _activity.getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    public static final void replaceTopNavigationFragment(final FragmentActivity _activity,
                                                          final android.support.v4.app.Fragment _fragment) {
        _activity.getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, _fragment)
                .commit();
    }

    public static final void replaceFragmentWithoutBackStack(final FragmentActivity _activity,
                                                             final android.support.v4.app.Fragment _fragment) {
        if ( _activity.getSupportFragmentManager().getFragments() == null ||
                !((Object) _activity.getSupportFragmentManager().getFragments().get(0)).getClass().getName().
                        equals(((Object)_fragment).getClass().getName())) {
            _activity.getSupportFragmentManager().popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            _activity.getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, _fragment)
                    .commit();
        }
    }

    public static final void replaceCurrentFragment(final FragmentActivity _activity,
                                                    final Fragment _fragment) {
//        sortSupBackStack(_activity, _fragment);
        clearSupBackStack(_activity);
        _activity.getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, _fragment)
                .addToBackStack(_fragment.getClass().getName())
                .commit();
    }

    public static final void addFragment(final FragmentActivity _context,
                                         final Fragment _fragment) {

        _context.getSupportFragmentManager().beginTransaction()
                .add(R.id.container, _fragment)
                .addToBackStack(null)
                .commit();
    }

    public static final void replaceFragmentWithStack(final FragmentActivity _activity, final android.support.v4.app.Fragment _fragment) {
        _activity.getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, _fragment)
//                .setCustomAnimations(R.anim.slide_right_in, R.anim.slide_left_out)
                .addToBackStack(_fragment.getClass().getName())
                .commit();
    }

}
