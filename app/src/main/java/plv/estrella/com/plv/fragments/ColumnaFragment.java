package plv.estrella.com.plv.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cristaliza.mvc.models.estrella.Item;

import plv.estrella.com.plv.MainActivity;
import plv.estrella.com.plv.R;
import plv.estrella.com.plv.custom.AddProductToShopDialog;
import plv.estrella.com.plv.custom.CustomDialog;
import plv.estrella.com.plv.global.Constants;
import plv.estrella.com.plv.models.ItemSerializable;
import plv.estrella.com.plv.untils.BitmapCreator;
import plv.estrella.com.plv.untils.FragmentReplacer;

/**
 * Created by vasia on 26.05.2015.
 */
public class ColumnaFragment extends Fragment implements View.OnClickListener {

    private MainActivity mCallingActivity;
    private ImageView mGoToBack,mAddEnvio, mAddCarrita, mMore, mLess, mProdAward, mPhotoGal1, mPhotoGal2, mBackground;
    private TextView mCounter, mNameColumna;
    private Item mCurrentItem;
    private int counterValue = 0;
    private int typeDialogEnvio = Constants.TYPE_DIALOG_ADD_ENVIOS;

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
        fillData();

        return view;
    }

    private void findUI(View _view){
        mAddCarrita     = (ImageView) _view.findViewById(R.id.ivAddCarrita_FC);
        mAddEnvio       = (ImageView) _view.findViewById(R.id.ivAddEnvio_FC);
        mMore           = (ImageView) _view.findViewById(R.id.ivMore_FC);
        mLess           = (ImageView) _view.findViewById(R.id.ivLess_FC);
        mPhotoGal1      = (ImageView) _view.findViewById(R.id.ivPhotoGal1_FC);
        mPhotoGal2      = (ImageView) _view.findViewById(R.id.ivPhotoGal2_FC);
        mProdAward      = (ImageView) _view.findViewById(R.id.ivColumnaAward_FC);
        mBackground     = (ImageView) _view.findViewById(R.id.ivBackground_FC);
        mGoToBack       = (ImageView) _view.findViewById(R.id.btnVolver_FC);
        mCounter        = (TextView) _view.findViewById(R.id.tvCount_FC);
        mNameColumna    = (TextView) _view.findViewById(R.id.tvNameColumna);

        mCallingActivity.setBackground();
    }

    private void setListeners(){
        mAddCarrita.setOnClickListener(this);
        mAddEnvio.setOnClickListener(this);
        mMore.setOnClickListener(this);
        mLess.setOnClickListener(this);
        mPhotoGal1.setOnClickListener(this);
        mPhotoGal2.setOnClickListener(this);
        mGoToBack.setOnClickListener(this);
    }

    private void fillData(){
        mCallingActivity.setTitle(mCurrentItem.getName());
        mCallingActivity.setBackground(mCurrentItem.getBackgroundImage());

        mNameColumna.setText(mCurrentItem.getName());
        mBackground.setImageBitmap(BitmapCreator.getBitmap(mCurrentItem.getBackgroundImage()));
//        mProdAward.setImageBitmap(BitmapCreator.getBitmap(mCurrentItem.getPrizes().get(0)));
//        mPhotoGal1.setImageBitmap(BitmapCreator.getBitmap(mCurrentItem.));
//        mPhotoGal2.setImageBitmap(BitmapCreator.getBitmap(mCurrentItem.));
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
                        .show(mCallingActivity, Constants.TYPE_DIALOG_ADD_ENVIOS, this, 1);
                break;
            case R.id.ivMore_FC:
                incCounter();
                break;
            case R.id.ivLess_FC:
                decCounter();
                break;
            case R.id.ivPhotoGal1_FC:
                clickPhotoGaleria(1);
                break;
            case R.id.ivPhotoGal2_FC:
                clickPhotoGaleria(2);
                break;
            case R.id.btnVolver_FC:
                mCallingActivity.onBackPressed();
                break;
        }
    }

    private void clickBtnCarrita(){
        if(counterValue == 0){
            final CustomDialog dialog = new CustomDialog.Builder()
                    .setMessage("0 producto")
                    .setPositiveButton(mCallingActivity.getString(R.string.button_accept), null)
                    .create();
            dialog.show(mCallingActivity);
        } else {
            AddProductToShopDialog.newInstance(new ItemSerializable(mCurrentItem))
                    .show(mCallingActivity, Constants.TYPE_DIALOG_ADD_CARRITA, this, counterValue);
        }
    }

    private void incCounter(){
        mCounter.setText(String.valueOf(++counterValue));
    }

    private void decCounter(){
        if(--counterValue < 0)
            counterValue = 0;
        mCounter.setText(String.valueOf(counterValue));
    }

    private void clickPhotoGaleria(int pos){
        FragmentReplacer.replaceFragmentWithStack(mCallingActivity, new GaleriaFragment());
    }
}
