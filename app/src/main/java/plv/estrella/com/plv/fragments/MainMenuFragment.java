package plv.estrella.com.plv.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.cristaliza.mvc.events.Event;
import com.cristaliza.mvc.events.EventListener;
import com.cristaliza.mvc.models.estrella.AppModel;
import com.cristaliza.mvc.models.estrella.Item;

import java.util.List;

import plv.estrella.com.plv.MainActivity;
import plv.estrella.com.plv.R;
import plv.estrella.com.plv.global.Constants;
import plv.estrella.com.plv.models.ItemSerializable;
import plv.estrella.com.plv.untils.ApiManager;
import plv.estrella.com.plv.untils.BitmapCreator;
import plv.estrella.com.plv.untils.FragmentReplacer;

/**
 * Created by vasia on 21.05.2015.
 */
public class MainMenuFragment extends Fragment implements View.OnClickListener {

    private ImageView ivColumnas, ivPLV;
    private MainActivity mCallingActivity;
    private EventListener mMenuListener;
    private List<Item> mMenuItemList;


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mCallingActivity = (MainActivity) activity;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_main_menu, container, false);
        findUI(view);
        makeListeners();
        ApiManager.getFirstLevel(mMenuListener);
        setListeners();
        mCallingActivity.setTitle("");
        return view;
    }

    private void findUI(final View _view){
        ivColumnas = (ImageView) _view.findViewById(R.id.ivColumnas_FMM);
        ivPLV = (ImageView) _view.findViewById(R.id.ivPLV_FMM);
    }

    private void makeListeners() {
        mMenuListener = new EventListener() {
            @Override
            public void onEvent(Event event) {
                switch (event.getId()){
                    case AppModel.ChangeEvent.ON_EXECUTE_ERROR_ID:
                        Toast.makeText(getActivity(), event.getType() + "error", Toast.LENGTH_SHORT).show();
                        break;
                    case AppModel.ChangeEvent.FIRST_LEVEL_CHANGED_ID:
                        createMenu();
                        break;
                    case AppModel.ChangeEvent.SECOND_LEVEL_CHANGED_ID:

                        break;
                }
            }
        };
    }

    private void createMenu(){
        mMenuItemList = ApiManager.getFirstList();
//        ivColumnas.setImageBitmap(BitmapCreator.getBitmap(mMenuItemList.get(0).getIcon()));
//        ivPLV.setImageBitmap(BitmapCreator.getBitmap(mMenuItemList.get(1).getIcon()));
    }

    private void setListeners(){
        ivColumnas.setOnClickListener(this);
        ivPLV.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int subMenuOpen = 0;
        int position = 0;
        switch (v.getId()){
            case R.id.ivColumnas_FMM:
                subMenuOpen = Constants.MENU_COLUMNAS;
                position = 0;
                break;
            case R.id.ivPLV_FMM:
                subMenuOpen = Constants.MENU_PLV;
                position = 1;
                break;
        }

        FragmentReplacer.replaceFragmentWithStack(
                mCallingActivity,
                SubMenuFragment.newInstance(subMenuOpen, new ItemSerializable(mMenuItemList.get(position)))
        );
    }
}
