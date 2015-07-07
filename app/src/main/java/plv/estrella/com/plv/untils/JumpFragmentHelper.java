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
import plv.estrella.com.plv.fragments.ColumnaFragment;
import plv.estrella.com.plv.fragments.CompaniaFragment;
import plv.estrella.com.plv.fragments.ItemPagerFragment;
import plv.estrella.com.plv.fragments.MainMenuFragment;
import plv.estrella.com.plv.fragments.PLVFragment;
import plv.estrella.com.plv.fragments.ShopsFragment;
import plv.estrella.com.plv.fragments.SubMenuFragment;
import plv.estrella.com.plv.global.Constants;
import plv.estrella.com.plv.models.ItemSerializable;

/**
 * Created by samson on 23.06.2015.
 */
public class JumpFragmentHelper extends Fragment {

    private static final int JUMP_MAINMENU  = 0;
    private static final int JUMP_SUBMENU   = 2;
    private static final int JUMP_SPV       = 4;
    private static final int JUMP_PLV       = 6;
    private static final int JUMP_SHOP      = 8;
    private static final int JUMP_COMPANIA  = 10;

    private MainActivity mActivity;
    private int openItemMenu;
    private int typeItem;
    private int typeShop;
    private ItemSerializable itemSerializable;

    public void setParamToMainmenu(){
        typeItem = JUMP_MAINMENU;
    }

    public void setParamToSubmenu(final int _open, final ItemSerializable _item){
        openItemMenu = _open;
        itemSerializable = _item;
        typeItem = JUMP_SUBMENU;
    }

    public void setParamToColumna(final ItemSerializable _item){
        itemSerializable = _item;
        typeItem = JUMP_SPV;
    }

    public void setParamToPLV(final ItemSerializable _item){
        itemSerializable = _item;
        typeItem = JUMP_PLV;
    }

    public void setParamToShop(int _typeShop){
        typeItem = JUMP_SHOP;
        typeShop = _typeShop;
    }

    public void setParamToCompania(){
        typeItem = JUMP_COMPANIA;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = (MainActivity) activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        switch (typeItem){
            case JUMP_MAINMENU:
                FragmentReplacer.replaceCurrentFragment(mActivity, new MainMenuFragment());
                break;
            case JUMP_SUBMENU:
                FragmentReplacer.replaceCurrentFragment(mActivity, SubMenuFragment.newInstance(openItemMenu, itemSerializable));
                break;
            case JUMP_SPV:
                FragmentReplacer.replaceCurrentFragment(mActivity, ColumnaFragment.newInstance(itemSerializable));
                break;
            case JUMP_PLV:
                FragmentReplacer.replaceCurrentFragment(mActivity, PLVFragment.newInstance(itemSerializable));
                break;
            case JUMP_SHOP:
                FragmentReplacer.replaceCurrentFragment(mActivity, ShopsFragment.newInstance(typeShop));
                break;
            case JUMP_COMPANIA:
                FragmentReplacer.replaceCurrentFragment(mActivity, new CompaniaFragment());
                break;
        }
        return null;
    }
}
