package plv.estrella.com.plv.fragments;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cristaliza.mvc.events.Event;
import com.cristaliza.mvc.events.EventListener;
import com.cristaliza.mvc.models.estrella.AppModel;
import com.cristaliza.mvc.models.estrella.Item;
import com.cristaliza.mvc.models.estrella.Product;

import java.util.List;

import plv.estrella.com.plv.MainActivity;
import plv.estrella.com.plv.R;
import plv.estrella.com.plv.custom.AddProductToShopDialog;
import plv.estrella.com.plv.custom.CustomDialog;
import plv.estrella.com.plv.global.Constants;
import plv.estrella.com.plv.models.ItemSerializable;
import plv.estrella.com.plv.untils.ApiManager;
import plv.estrella.com.plv.untils.BitmapCreator;

/**
 * Created by vasia on 26.05.2015.
 */
public class ColumnaFragment extends Fragment implements View.OnClickListener {

    private MainActivity mCallingActivity;
    private ImageView mGoToBack,mAddEnvio, mAddCarrita, mMore, mLess, mBackground;
    private TextView mCounter;
    private LinearLayout lowCont, highCont;
    private Item mCurrentItem;
    private Product mProduct;
    private EventListener eventListener;
    private int counterValue = 1;

    public static ColumnaFragment newInstance(ItemSerializable _item){
        ColumnaFragment fragment = new ColumnaFragment();
        Bundle args = new Bundle();
        args.putSerializable(Constants.PARAM_ITEM, _item);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mCallingActivity = (MainActivity) activity;
        if(getArguments() != null){
            ItemSerializable item = (ItemSerializable) getArguments().getSerializable(Constants.PARAM_ITEM);
            mCurrentItem = item.getItem();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_columna,container,false);

        findUI(view);
        setListeners();

        makeListener();
        ApiManager.getThirdLevel(eventListener, mCurrentItem);

        return view;
    }

    private void findUI(View _view){
        mAddCarrita     = (ImageView) _view.findViewById(R.id.ivAddCarrita_FC);
        mAddEnvio       = (ImageView) _view.findViewById(R.id.ivAddEnvio_FC);
        mMore           = (ImageView) _view.findViewById(R.id.ivMore_FC);
        mLess           = (ImageView) _view.findViewById(R.id.ivLess_FC);
        mBackground     = (ImageView) _view.findViewById(R.id.ivBackground_FC);
        mGoToBack       = (ImageView) _view.findViewById(R.id.btnVolver_FC);
        mCounter        = (TextView) _view.findViewById(R.id.tvCount_FC);
        lowCont         = (LinearLayout) _view.findViewById(R.id.llContLow);
        highCont        = (LinearLayout) _view.findViewById(R.id.llContHigh);

        mCallingActivity.setBackground();
    }

    private void setListeners(){
        mAddCarrita.setOnClickListener(this);
        mAddEnvio.setOnClickListener(this);
        mMore.setOnClickListener(this);
        mLess.setOnClickListener(this);
        mGoToBack.setOnClickListener(this);
    }

    private void makeListener(){
        eventListener = new EventListener() {
            @Override
            public void onEvent(Event event) {
                switch (event.getId()){
                    case AppModel.ChangeEvent.ON_EXECUTE_ERROR_ID:
                        Toast.makeText(getActivity(), event.getType() + "error", Toast.LENGTH_SHORT).show();
                        break;
                    case AppModel.ChangeEvent.THIRD_LEVEL_CHANGED_ID:
                        ApiManager.getProducts(eventListener, ApiManager.getThirdList().get(0));
                        break;
                    case AppModel.ChangeEvent.PRODUCTS_CHANGED_ID:
                        fillData();
                        break;
                }
            }
        };
    }

    private void fillData(){
        mProduct = ApiManager.getProductsList().get(0);

        mCallingActivity.setTitle(mProduct.getName());

        Bitmap bitmap = BitmapCreator.getCompressedBitmap(mProduct.getBackgroundImage(), Constants.RATIO_16_9, 1280f);
        bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight() * 2 / 3);

        mBackground.setImageBitmap(bitmap);
        List<String> families = mProduct.getFamilyImages();
        boolean inLow = true;
//        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                (int)mCallingActivity.getResources().getDimension(R.dimen.size_side_family_logo),
                (int)mCallingActivity.getResources().getDimension(R.dimen.size_side_family_logo)
        );
        params.setMargins(5, 5, 5, 5);
        for(int i = 0; i < families.size(); ++i){
            ImageView imageView = new ImageView(mCallingActivity);
            imageView.setLayoutParams(params);

            imageView.setImageBitmap(BitmapCreator.getCompressedBitmap(families.get(i), Constants.RATIO_1_1, 100f));

            if(inLow){
                lowCont.addView(imageView);
                inLow = false;
            } else {
                highCont.addView(imageView);
                inLow = true;
            }
        }
        mCounter.setText(String.valueOf(counterValue));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ivAddCarrita_FC:
                clickBtnCarrita();
                break;
            case R.id.ivAddEnvio_FC:
                AddProductToShopDialog.newInstance(new ItemSerializable(mCurrentItem))
//                        .show(mCallingActivity, Constants.TYPE_DIALOG_ADD_ENVIOS, 1);
                        .show(mCallingActivity, mProduct.getId(), mProduct.getName(), mCurrentItem.getIcon(), "", Constants.TYPE_DIALOG_ADD_ENVIOS, 1);
                break;
            case R.id.ivMore_FC:
                incCounter();
                break;
            case R.id.ivLess_FC:
                decCounter();
                break;
            case R.id.btnVolver_FC:
                mCallingActivity.onBackPressed();
                break;
        }
    }

    private void clickBtnCarrita(){
        if(counterValue == 0){
            final CustomDialog dialog = new CustomDialog.Builder()
                    .setMessage(mCallingActivity.getString(R.string.no_productos))
                    .setPositiveButton(mCallingActivity.getString(R.string.button_accept), null)
                    .create();
            dialog.show(mCallingActivity);
        } else {
            AddProductToShopDialog.newInstance(new ItemSerializable(mCurrentItem))
                    .show(mCallingActivity, mProduct.getId(), mProduct.getName(), mCurrentItem.getIcon(), mProduct.getEAN(), Constants.TYPE_DIALOG_ADD_CARRITA, counterValue);
        }
    }

    private void incCounter(){
        mCounter.setText(String.valueOf(++counterValue));
    }

    private void decCounter(){
        if(--counterValue < 1)
            counterValue = 1;
        mCounter.setText(String.valueOf(counterValue));
    }
}
