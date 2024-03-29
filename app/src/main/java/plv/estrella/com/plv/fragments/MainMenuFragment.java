package plv.estrella.com.plv.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
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
import plv.estrella.com.plv.untils.FragmentUtil;

/**
 * Created by vasia on 21.05.2015.
 */
public class MainMenuFragment extends Fragment implements View.OnClickListener {

    private ImageView ivColumnas, ivPLV;
    private TextView tvCompania;
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
        return view;
    }

    private void findUI(final View _view){
        ivColumnas  = (ImageView) _view.findViewById(R.id.ivColumnas_FMM);
        ivPLV       = (ImageView) _view.findViewById(R.id.ivPLV_FMM);
        tvCompania  = (TextView) _view.findViewById(R.id.tvCompania);

        mCallingActivity.setBackground();
        mCallingActivity.setTitle("");
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
                }
            }
        };
    }

    private void createMenu(){
        mMenuItemList = ApiManager.getFirstList();

//        ivColumnas.setImageBitmap(BitmapCreator.getCompressedBitmap(mMenuItemList.get(1).getIcon(), Constants.RATIO_3_4, 600f));
//        ivPLV.setImageBitmap(BitmapCreator.getCompressedBitmap(mMenuItemList.get(2).getIcon(), Constants.RATIO_3_4, 600f));
        ivColumnas.setImageBitmap(BitmapCreator.getBitmap(mMenuItemList.get(1).getIcon()));
        ivPLV.setImageBitmap(BitmapCreator.getBitmap(mMenuItemList.get(2).getIcon()));
    }

    private void setListeners(){
        ivColumnas.setOnClickListener(this);
        ivPLV.setOnClickListener(this);
        tvCompania.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ivColumnas_FMM:
                FragmentUtil.replaceFragmentWithStack(
                        SubMenuFragment.newInstance(Constants.MENU_COLUMNAS, new ItemSerializable(mMenuItemList.get(1)))
                );
                break;
            case R.id.ivPLV_FMM:
                FragmentUtil.replaceFragmentWithStack(
                        SubMenuFragment.newInstance(Constants.MENU_PLV, new ItemSerializable(mMenuItemList.get(2)))
                );
                break;
            case R.id.tvCompania:
                FragmentUtil.replaceFragmentWithStack(
                        CompaniaFragment.newInstance(new ItemSerializable(mMenuItemList.get(0)))
                );
                break;
        }
    }
}
