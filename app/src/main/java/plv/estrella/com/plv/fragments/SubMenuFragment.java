package plv.estrella.com.plv.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.cristaliza.mvc.events.Event;
import com.cristaliza.mvc.events.EventListener;
import com.cristaliza.mvc.models.estrella.AppModel;
import com.cristaliza.mvc.models.estrella.Item;

import java.util.List;

import plv.estrella.com.plv.MainActivity;
import plv.estrella.com.plv.R;
import plv.estrella.com.plv.adapters.SubMenuAdapter;
import plv.estrella.com.plv.global.Constants;
import plv.estrella.com.plv.models.ItemSerializable;
import plv.estrella.com.plv.untils.ApiManager;
import plv.estrella.com.plv.untils.FragmentReplacer;
import plv.estrella.com.plv.untils.FragmentUtil;

/**
 * Created by vasia on 22.05.2015.
 */
public class SubMenuFragment extends Fragment implements AdapterView.OnItemClickListener {

    private MainActivity mCallingActivity;
    private GridView gvSubMenuContainer;
    private int mMenuOpen;
    private EventListener mMenuListener;
    private List<Item> mSubMenuItemList;
    private Item mMenuItem;

    public static SubMenuFragment newInstance(final int _open, final ItemSerializable _item){
        SubMenuFragment fragment = new SubMenuFragment();
        Bundle attr = new Bundle();
        attr.putInt(Constants.OPEN_MENU, _open);
        attr.putSerializable(Constants.ITEM, _item);
        fragment.setArguments(attr);
        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mCallingActivity = (MainActivity) activity;
        if (getArguments() != null){
            mMenuOpen = getArguments().getInt(Constants.OPEN_MENU);
            mMenuItem = ((ItemSerializable) getArguments().getSerializable(Constants.ITEM)).getItem();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_sub_menu, container, false);
        findUI(view);
        setTitle();
        makeListeners();
        ApiManager.getSecondLevel(mMenuListener, mMenuItem);

        return view;
    }

    private void findUI(final View _view){
        gvSubMenuContainer = (GridView) _view.findViewById(R.id.glSubMenuContainer_FSM);

        mCallingActivity.setBackground();
    }

    private void setTitle(){
        String title = "";
        switch (mMenuOpen){
            case Constants.MENU_COLUMNAS:
                title = getString(R.string.columnas);
                break;
            case Constants.MENU_PLV:
                title = getString(R.string.plv);
                break;
        }
        mCallingActivity.setTitle(title);
    }

    private void makeListeners() {
        mMenuListener = new EventListener() {
            @Override
            public void onEvent(Event event) {
                switch (event.getId()){
                    case AppModel.ChangeEvent.ON_EXECUTE_ERROR_ID:
                        Toast.makeText(getActivity(), event.getType() + "error", Toast.LENGTH_SHORT).show();
                        break;
                    case AppModel.ChangeEvent.SECOND_LEVEL_CHANGED_ID:
                        createSubMenu();
                        break;
                }
            }
        };
    }

    private void createSubMenu(){
        mSubMenuItemList = ApiManager.getSecondList();
        gvSubMenuContainer.setAdapter(new SubMenuAdapter(mCallingActivity, mSubMenuItemList, mMenuOpen));
        gvSubMenuContainer.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (mMenuOpen){
            case Constants.MENU_COLUMNAS:
                FragmentUtil.replaceFragmentWithStack(
                        ColumnaFragment.newInstance(new ItemSerializable(mSubMenuItemList.get(position)))
                );
                break;
            case Constants.MENU_PLV:
                FragmentUtil.replaceFragmentWithStack(
                        PLVFragment.newInstance(new ItemSerializable(mSubMenuItemList.get(position)))
                );
                break;
        }
        }
}
