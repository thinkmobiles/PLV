package plv.estrella.com.plv.untils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;

import java.util.ArrayList;

import plv.estrella.com.plv.R;

public abstract class FragmentUtil {

    private static FragmentActivity activity;
    private static int containerId;
    private static ArrayList<Fragment> backstack;
    private static int positionSlidemenu;

    public static void init(FragmentActivity activity, int containerId){
        FragmentUtil.activity = activity;
        FragmentUtil.containerId = containerId;
        backstack = new ArrayList<>();
        positionSlidemenu = 0;
    }

    private static void replaceFragment(Fragment fragment){
        activity.getSupportFragmentManager().beginTransaction()
                .replace(containerId, fragment)
                .commit();
    }

    public static void addFragment(Fragment fragment){
        backstack.add(fragment);
        activity.getSupportFragmentManager().beginTransaction()
                .add(containerId, fragment)
                .addToBackStack(null)
                .commit();
    }

    public static int getBackStackCount() {
        return backstack.size();
    }

    public static void clearBackStack(){
        backstack.clear();
    }

    public static void popBackStack() {
        int indexLast = getBackStackCount() - 1;
        if(activity.getSupportFragmentManager().getBackStackEntryCount() != 0){
            activity.getSupportFragmentManager().popBackStack();
            backstack.remove(indexLast);
            return;
        }
        if(indexLast > 0){
            replaceFragment(backstack.get(indexLast - 1));
            backstack.remove(indexLast);
        }
    }

    public static void removeLastFragment(){
        backstack.remove(getBackStackCount() - 1);
    }

    public static void replaceCurrentFragment(Fragment fragment, final int position) {
        removeLastFragment();
        if(position != positionSlidemenu) {
            backstack.add(fragment);
            positionSlidemenu = position;
        }
        replaceFragment(fragment);
    }

    public static final void replaceFragmentWithStack(Fragment fragment) {
        backstack.add(fragment);
        replaceFragment(fragment);
    }


}
