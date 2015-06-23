package plv.estrella.com.plv.untils;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import plv.estrella.com.plv.MainActivity;
import plv.estrella.com.plv.R;
import plv.estrella.com.plv.fragments.ItemPagerFragment;
import plv.estrella.com.plv.fragments.SubMenuFragment;
import plv.estrella.com.plv.global.Constants;
import plv.estrella.com.plv.models.ItemSerializable;

/**
 * Created by samson on 23.06.2015.
 */
public class JumpFragmentHelper extends Fragment {

    private MainActivity mActivity;

    public static JumpFragmentHelper newInstance(final int _open, final ItemSerializable _item){
        JumpFragmentHelper fragment = new JumpFragmentHelper();
        Bundle attr = new Bundle();
        attr.putInt(Constants.OPEN_MENU, _open);
        attr.putSerializable(Constants.ITEM, _item);
        fragment.setArguments(attr);
        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = (MainActivity) activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        int openMenu = 0;
        ItemSerializable item  = null;
        if(getArguments() != null){
            openMenu = getArguments().getInt(Constants.OPEN_MENU);
            item = (ItemSerializable) getArguments().getSerializable(Constants.PARAM_ITEM);
        }
        FragmentReplacer.replaceCurrentFragment(mActivity,new SubMenuFragment().newInstance(openMenu, item));
        return null;
    }
}
