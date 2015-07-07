package plv.estrella.com.plv.untils;

import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.cristaliza.mvc.events.Event;
import com.cristaliza.mvc.events.EventListener;
import com.cristaliza.mvc.models.estrella.AppModel;
import com.cristaliza.mvc.models.estrella.Item;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import java.util.ArrayList;
import java.util.List;

import plv.estrella.com.plv.MainActivity;
import plv.estrella.com.plv.R;
import plv.estrella.com.plv.adapters.MenuAdapter;
import plv.estrella.com.plv.global.Constants;
import plv.estrella.com.plv.models.ItemSerializable;

/**
 * Created by samson on 22.06.2015.
 */
public class SlidingMenuManager {

    private SlidingMenu menu;
    private MainActivity activity;
    private EventListener mMenuListener;
    private List<Item> mMenuItemList;

    private ListView listMenu;
    private List<Item> mListColumnas, mListPLV;
    private ArrayList<String> mTitleList;
    private MenuAdapter adapter;

    public void initMenu(Activity _activity) {

        activity = (MainActivity) _activity;
        menu = new SlidingMenu(_activity);

        makeLitener();
        ApiManager.getFirstLevel(mMenuListener);

        setPropertyMenu(_activity);

        findUI2();
        setAdapters();
        setListeners();
    }

    private void setPropertyMenu(Activity _activity) {
        menu.setMode(SlidingMenu.LEFT);
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
        menu.setShadowWidthRes(R.dimen.slidingmenu_shadow_width);
        menu.setBehindWidth(getDisplayWidth());
        menu.setFadeDegree(0.3f);
        menu.attachToActivity(_activity, SlidingMenu.SLIDING_WINDOW);
        menu.setMenu(R.layout.menu);
        menu.setSlidingEnabled(true);
    }

    private void findUI2() {
        listMenu = (ListView) menu.findViewById(R.id.lvMenu);
    }

    private void setAdapters() {
        adapter = new MenuAdapter(activity, R.id.tvMenuItem,mTitleList);
        listMenu.setAdapter(adapter);
    }

    private void setListeners() {
        listMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                JumpFragmentHelper jumper = new JumpFragmentHelper();
                switch (adapter.getItemViewType(position)){
                    case Constants.TYPE_INICIO:
                        jumper.setParamToMainmenu();
                        break;
                    case Constants.TYPE_SPV:
                        jumper.setParamToSubmenu(Constants.MENU_COLUMNAS, new ItemSerializable(mMenuItemList.get(0)));
                        break;
                    case Constants.TYPE_SPV_PADDING:
                        jumper.setParamToColumna(new ItemSerializable(mListColumnas.get(position - 2)));
                        break;
                    case Constants.TYPE_PLV:
                        jumper.setParamToSubmenu(Constants.MENU_PLV, new ItemSerializable(mMenuItemList.get(1)));
                        break;
                    case Constants.TYPE_PLV_PADDING:
                        jumper.setParamToPLV(new ItemSerializable(mListPLV.get(position - 11)));
                        break;
                    case Constants.TYPE_ENVIOS:
                        jumper.setParamToShop(Constants.TYPE_SHOPS_ENVIOS);
                        break;
                    case Constants.TYPE_PEDIDOS:
                        jumper.setParamToShop(Constants.TYPE_SHOPS_PEDIDOS);
                        break;
                }
                FragmentReplacer.replaceFragmentWithStack(activity, jumper);
                menu.toggle();
            }
        });
    }

    public void show() {
        menu.showMenu();
    }

    public void toggle() {
        menu.toggle();
    }

    public void enableMenu(final boolean _state) {
        menu.setSlidingEnabled(_state);
    }

    private void makeLitener() {
        mMenuListener = new EventListener() {
            @Override
            public void onEvent(Event event) {
                switch (event.getId()) {
                    case AppModel.ChangeEvent.ON_EXECUTE_ERROR_ID:
                        Toast.makeText(activity, event.getType() + "error", Toast.LENGTH_SHORT).show();
                        break;
                    case AppModel.ChangeEvent.FIRST_LEVEL_CHANGED_ID:
                        createMenu();
                        break;
                    case AppModel.ChangeEvent.SECOND_LEVEL_CHANGED_ID:
                        createSubMenu();
                        break;
                    case AppModel.ChangeEvent.THIRD_LEVEL_CHANGED_ID:
                        mTitleList.add(ApiManager.getThirdList().get(0).getName());
                        break;
                }
            }
        };
    }

    int c = 0;

    private void createMenu() {
        mTitleList = new ArrayList<>();
        mMenuItemList = ApiManager.getFirstList();

        mTitleList.add(Constants.ITEM_INICIO);
        mTitleList.add(Constants.ITEM_COLUMNAS);
        ApiManager.getSecondLevel(mMenuListener, mMenuItemList.get(0));
        mTitleList.add(Constants.ITEM_PLV);
        ++c;
        ApiManager.getSecondLevel(mMenuListener, mMenuItemList.get(1));
        mTitleList.add(Constants.ITEM_ENVIOS);
        mTitleList.add(Constants.ITEM_PEDIDOS);
    }

    private void createSubMenu() {
        List<Item> list = ApiManager.getSecondList();
        if (c == 0) {
            mListColumnas = list;
            for(Item item : list){
                ApiManager.getThirdLevel(mMenuListener, item);
            }
        } else {
            mListPLV = list;
            for(Item item : list){
                mTitleList.add(item.getName());
            }
        }
    }

    private int getDisplayWidth() {
        return activity.getWindowManager().getDefaultDisplay().getWidth() * 3 / 10;
    }

}
