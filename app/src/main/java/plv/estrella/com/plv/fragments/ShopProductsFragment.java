package plv.estrella.com.plv.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import plv.estrella.com.plv.MainActivity;
import plv.estrella.com.plv.R;
import plv.estrella.com.plv.adapters.ShopProductsAdapter;
import plv.estrella.com.plv.database.Shop;
import plv.estrella.com.plv.global.Constants;

/**
 * Created by vasia on 27.05.2015.
 */
public class ShopProductsFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener {

    private MainActivity mCallingActivity;
    private Shop mShop;
    private TextView tvClearList, tvShopName;
    private ListView lvProductContainer;
    private ShopProductsAdapter mAdapter;


    public static ShopProductsFragment newInstance(final Shop _shop) {
        final ShopProductsFragment fragment = new ShopProductsFragment();
        final Bundle bundle = new Bundle();
        bundle.putSerializable(Constants.SHOP, _shop);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mCallingActivity = (MainActivity) activity;
        if (getArguments() != null) {
            mShop = ((Shop) getArguments().getSerializable(Constants.SHOP));
            getArguments().remove(Constants.SHOP);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View inflaterView = inflater.inflate(R.layout.fragment_shop_product, container, false);
        findViews(inflaterView);
        initProductList();
        setListeners();
        setShopTitle();
        return inflaterView;
    }

    private void findViews(final View _view){
        tvClearList          = (TextView) _view.findViewById(R.id.tvClearProductList_FSP);
        tvShopName           = (TextView) _view.findViewById(R.id.tvShopName_FSP);
        lvProductContainer   = (ListView) _view.findViewById(R.id.lvProductContainer_FSP);
    }

    private void setListeners(){
        tvClearList.setOnClickListener(this);
        lvProductContainer.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tvClearProductList_FSP:
                clearProductList();
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    private void clearProductList(){
        mAdapter.deleteAllProduct();
    }

    private void initProductList(){
        mAdapter = new ShopProductsAdapter(mCallingActivity, mShop);
        lvProductContainer.setAdapter(mAdapter);
    }

    private void setShopTitle(){
        tvShopName.setText(mShop.getShopName());
    }

}
