package plv.estrella.com.plv.untils;

import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
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
import plv.estrella.com.plv.fragments.ColumnaFragment;
import plv.estrella.com.plv.fragments.MainMenuFragment;
import plv.estrella.com.plv.fragments.PLVFragment;
import plv.estrella.com.plv.fragments.SubMenuFragment;
import plv.estrella.com.plv.global.Constants;
import plv.estrella.com.plv.models.ItemSerializable;

public class SlidingMenuManager implements View.OnClickListener {

    private SlidingMenu menu;
    private MainActivity activity;
    private EventListener mMenuListener;
    private List<Item> mMenuItemList;

    private ListView listColumnas, listPLV;
    private TextView tvInicio, tvColumnas, tvPLV, tvEnvios, tvPedidos;
    private List<Item> mListColumnas, mListPLV;

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
        menu.setFadeDegree(0.33f);
        menu.attachToActivity(_activity, SlidingMenu.SLIDING_WINDOW);
        menu.setMenu(R.layout.menu);
        menu.setSlidingEnabled(true);
    }

    private void findUI2() {
        tvInicio        = (TextView) menu.findViewById(R.id.tvInicio);
        tvColumnas      = (TextView) menu.findViewById(R.id.tvColumnas);
        tvPLV           = (TextView) menu.findViewById(R.id.tvPLV);
        tvEnvios        = (TextView) menu.findViewById(R.id.tvEnvios);
        tvPedidos       = (TextView) menu.findViewById(R.id.tvPedidos);
        listPLV         = (ListView) menu.findViewById(R.id.lvPLV);
        listColumnas    = (ListView) menu.findViewById(R.id.lvColumnas);
    }

    private void setAdapters() {
        ArrayList<String> list = new ArrayList<>();
        for (Item item : mListColumnas) {
            list.add(item.getName());
        }
        listColumnas.setAdapter(new MenuAdapter(list, activity));
        list = new ArrayList<>();
        for (Item item : mListPLV) {
            list.add(item.getName());
        }
        listPLV.setAdapter(new MenuAdapter(list, activity));
    }

    private void setListeners() {
        tvInicio.setOnClickListener(this);
        tvColumnas.setOnClickListener(this);
        tvPLV.setOnClickListener(this);
        tvEnvios.setOnClickListener(this);
        tvPedidos.setOnClickListener(this);

        listColumnas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FragmentReplacer.replaceFragmentWithStack(
                        activity,
                        new ColumnaFragment().newInstance(new ItemSerializable(mListColumnas.get(position)))
                );
                menu.toggle();
            }
        });

        listPLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FragmentReplacer.replaceFragmentWithStack(
                        activity,
                        new PLVFragment().newInstance(new ItemSerializable(mListPLV.get(position)))
                );
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
                }
            }
        };
    }

    int c = 0;

    private void createMenu() {
        mMenuItemList = ApiManager.getFirstList();

        ApiManager.getSecondLevel(mMenuListener, mMenuItemList.get(0));
        ++c;
        ApiManager.getSecondLevel(mMenuListener, mMenuItemList.get(1));
    }

    private void createSubMenu() {
        if (c == 0) {
            mListColumnas = ApiManager.getSecondList();
        } else {
            mListPLV = ApiManager.getSecondList();
        }
    }

    private int getDisplayWidth() {
        return activity.getWindowManager().getDefaultDisplay().getWidth() / 3;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tvInicio:
                FragmentReplacer.clearBackStack(activity);
                FragmentReplacer.replaceFragmentWithStack(activity, new MainMenuFragment());
                break;
            case R.id.tvColumnas:
                FragmentReplacer.clearBackStack(activity);
                FragmentReplacer.replaceFragmentWithStack(
                        activity,
                        new SubMenuFragment().newInstance(Constants.MENU_COLUMNAS, new ItemSerializable(mMenuItemList.get(0)))
                );
                break;
            case R.id.tvPLV:
                FragmentReplacer.clearBackStack(activity);
                FragmentReplacer.replaceFragmentWithStack(
                        activity,
                        new SubMenuFragment().newInstance(Constants.MENU_PLV, new ItemSerializable(mMenuItemList.get(1)))
                );
                break;
            case R.id.tvEnvios:
                break;
            case R.id.tvPedidos:
                break;
        }
        menu.toggle();
    }
}
