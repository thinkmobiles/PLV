package plv.estrella.com.plv.custom;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.cristaliza.mvc.models.estrella.Item;
import java.util.ArrayList;
import java.util.List;

import plv.estrella.com.plv.MainActivity;
import plv.estrella.com.plv.R;
import plv.estrella.com.plv.adapters.SpinnerPurchaseAdapter;
import plv.estrella.com.plv.database.DBManager;
import plv.estrella.com.plv.database.Shop;
import plv.estrella.com.plv.global.Constants;
import plv.estrella.com.plv.models.ItemSerializable;
import plv.estrella.com.plv.untils.FragmentReplacer;

/**
 * Created by vasia on 26.05.2015.
 */
public class CustomSpinnerDialog extends Fragment implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    private Activity mCallingActivity;
    private ArrayList<Item> items;
    private int selected;

    private List<Shop> shopList, subList;
    private LinearLayout spinnerLayout;
    private Spinner spinner;
    private SpinnerPurchaseAdapter spinnerPurchaseAdapter;
    private TextView positiveButton, negativeButton;
    private EditText shopName;
    private Item mCurrentItem;
    private FrameLayout flTop, flBottom, flBackground;

    public static CustomSpinnerDialog newInstance(final ItemSerializable _item) {
        final CustomSpinnerDialog fragment = new CustomSpinnerDialog();
        final Bundle bundle = new Bundle();
        bundle.putSerializable(Constants.ITEM, _item);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mCallingActivity = (MainActivity) activity;
        if (getArguments() != null) {
            mCurrentItem = ((ItemSerializable) getArguments().getSerializable(Constants.ITEM)).getItem();
            getArguments().remove(Constants.SHOP);
        }
    }

    public void addProduct() {
        new GetShopTask().execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View inflaterView = inflater.inflate(R.layout.custom_dialog_spinner, container, false);
        findViews(inflaterView);
        setListeners();
        addProduct();
        return inflaterView;
    }

    private void initSpinner(){
        spinnerPurchaseAdapter = new SpinnerPurchaseAdapter(mCallingActivity, subList);
        spinner.setAdapter(spinnerPurchaseAdapter);
    }

    private void findViews(final View _view) {
        spinnerLayout   = (LinearLayout) _view.findViewById(R.id.ll_spinner);
        spinner         = (Spinner) _view.findViewById(R.id.dialogSpinner);
        negativeButton  = (TextView) _view.findViewById(R.id.tv_cancel_action_CD);
        positiveButton  = (TextView) _view.findViewById(R.id.tv_accept_action_CD);
        shopName        = (EditText) _view.findViewById(R.id.et_new_Shop_CD);
        flTop           = (FrameLayout) _view.findViewById(R.id.flTop_CDS);
        flBottom        = (FrameLayout) _view.findViewById(R.id.flBottom_CDS);
        flBackground    = (FrameLayout) _view.findViewById(R.id.flBackground_CDS);
    }

    private void setListeners() {
        spinner.setOnItemSelectedListener(this);
        negativeButton.setOnClickListener(this);
        positiveButton.setOnClickListener(this);
        flBottom.setOnClickListener(this);
        flTop.setOnClickListener(this);
        flBackground.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_cancel_action_CD:
                FragmentReplacer.popSupBackStack(getActivity());
                break;
            case R.id.tv_accept_action_CD:
                onClickPositiveButton();
                break;
            case R.id.flTop_CDS:
            case R.id.flBottom_CDS:
                FragmentReplacer.popSupBackStack(getActivity());
                break;
        }
    }

    private void onClickPositiveButton(){
        if (shopName.getVisibility() == View.GONE) {
            if (subList.get(selected).getId() == null) {
                spinnerLayout.setVisibility(View.INVISIBLE);
                shopName.setVisibility(View.VISIBLE);
            } else {
                shopList.get(selected).getId();
                DBManager.addItem(mCurrentItem, 2, subList.get(selected));
                FragmentReplacer.popSupBackStack(getActivity());
                Toast.makeText(mCallingActivity, "Item add to shop_id= " + String.valueOf(subList.get(selected).getId()), Toast.LENGTH_SHORT).show();

            }
        } else if (!shopName.getText().toString().isEmpty()) {
            DBManager.addShop(shopName.getText().toString());
            spinnerLayout.setVisibility(View.VISIBLE);
            shopName.setVisibility(View.GONE);
            addProduct();
            FragmentReplacer.popSupBackStack(getActivity());
        } else {
            Toast.makeText(mCallingActivity, "enter shop", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        selected = position;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public class GetShopTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            shopList = new ArrayList<>();
            subList = new ArrayList<>();
            shopList = DBManager.getShops();
            if (!shopList.isEmpty()) {
                Shop lastElement = shopList.get(shopList.size() - 1);
                shopList.add(0, lastElement);
                subList = shopList.subList(0, shopList.size() - 1);
            }

            subList.add(new Shop("Create new shop"));
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
//            createCustomDialog();
            initSpinner();
        }
    }

}
