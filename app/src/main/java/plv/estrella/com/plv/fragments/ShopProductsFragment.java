package plv.estrella.com.plv.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import plv.estrella.com.plv.MainActivity;
import plv.estrella.com.plv.R;
import plv.estrella.com.plv.adapters.ShopProductsEAdapter;
import plv.estrella.com.plv.adapters.ShopProductsPAdapter;
import plv.estrella.com.plv.database.Shop;
import plv.estrella.com.plv.global.Constants;
import plv.estrella.com.plv.untils.PDFSender;

/**
 * Created by vasia on 27.05.2015.
 */
public class ShopProductsFragment extends Fragment implements View.OnClickListener {

    private MainActivity mCallingActivity;
    private Shop mShop;
    private TextView tvClearList, tvFichaListado, tvCantidad, tvEnviar;
    private ListView lvProductContainer;
    private ImageView mGoToBack;
    private ShopProductsEAdapter mAdapterE;
    private ShopProductsPAdapter mAdapterP;


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
        tvFichaListado       = (TextView) _view.findViewById(R.id.tvShopName_FSP);
        tvEnviar             = (TextView) _view.findViewById(R.id.tvEnviar);
        tvCantidad           = (TextView) _view.findViewById(R.id.tvCantidad);
        lvProductContainer   = (ListView) _view.findViewById(R.id.lvProductContainer_FSP);
        mGoToBack            = (ImageView)_view.findViewById(R.id.btnVolver_FSP);

        if(Constants.TYPE_SHOPS_PEDIDOS == mShop.getType()){
            tvCantidad.setVisibility(View.VISIBLE);
            tvFichaListado.setText(mCallingActivity.getString(R.string.listado));
        }
    }

    private void setListeners(){
        tvClearList.setOnClickListener(this);
        tvEnviar.setOnClickListener(this);
        mGoToBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tvClearProductList_FSP:
                clearProductList();
                break;
            case R.id.btnVolver_FSP:
                getActivity().onBackPressed();
                break;
            case R.id.tvEnviar:
                if(mShop.getType() == Constants.TYPE_SHOPS_PEDIDOS){
                    if(mAdapterP.checkCorrectNumbers())
                        PDFSender.sendShopPDFs(mCallingActivity, mAdapterP.getListProducts());
                } else
                    PDFSender.sendShopPDFs(mCallingActivity, mAdapterE.getListProducts());
                break;
        }
    }

    private void clearProductList(){
        if(mShop.getType() == Constants.TYPE_SHOPS_ENVIOS){
            mAdapterE.deleteAllProduct();
        } else {
            mAdapterP.deleteAllProduct();
        }
    }

    private void initProductList(){
        if(mShop.getType() == Constants.TYPE_SHOPS_ENVIOS){
            mAdapterE = new ShopProductsEAdapter(mCallingActivity, mShop);
            lvProductContainer.setAdapter(mAdapterE);
        } else {
            mAdapterP = new ShopProductsPAdapter(mCallingActivity, mShop);
            lvProductContainer.setAdapter(mAdapterP);
        }
    }

    private void setShopTitle(){
        mCallingActivity.setTitle(mShop.getName());
    }

}
