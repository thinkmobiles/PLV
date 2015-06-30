package plv.estrella.com.plv.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import plv.estrella.com.plv.MainActivity;
import plv.estrella.com.plv.R;
import plv.estrella.com.plv.adapters.ShopListAdapter;
import plv.estrella.com.plv.database.DBManager;
import plv.estrella.com.plv.global.Constants;
import plv.estrella.com.plv.untils.FragmentReplacer;

/**
 * Created by vasia on 27.05.2015.
 */
public class ShopsFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener {

    private MainActivity mCallingActivity;
    private ListView lvShopContainer;
    private TextView tvClearShopList;
    private ShopListAdapter mAdapter;
    private ImageView mGoToBack;
    private int typeShops;

    public static ShopsFragment newInstance(int _type){
        ShopsFragment fragment = new ShopsFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.PARAM_TYPE, _type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mCallingActivity = (MainActivity) activity;
        if (getArguments() != null) {
            typeShops = getArguments().getInt(Constants.PARAM_TYPE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater _inflater, ViewGroup _container, Bundle _savedInstanceState) {
        final View inflaterView = _inflater.inflate(R.layout.fragment_shops, _container, false);
        findViews(inflaterView);
        initShopList();
        setListeners();
        return inflaterView;
    }

    private void findViews(final View _view){
        lvShopContainer = (ListView) _view.findViewById(R.id.lvShopContainer_FS);
        tvClearShopList = (TextView) _view.findViewById(R.id.tvClearShopList_FS);
        mGoToBack       = (ImageView)_view.findViewById(R.id.btnVolver_FS);
    }

    private void initShopList(){
        mAdapter = new ShopListAdapter(mCallingActivity, DBManager.getShops());
        lvShopContainer.setAdapter(mAdapter);
    }

    private void setListeners(){
        tvClearShopList.setOnClickListener(this);
        lvShopContainer.setOnItemClickListener(this);
        mGoToBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View _v) {
        switch (_v.getId()){
            case R.id.tvClearShopList_FS:
                clearShopList();
                break;
            case R.id.btnVolver_FS:
                getActivity().onBackPressed();
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int _position, long id) {
        Log.e("type", mAdapter.getShops().get(_position).getType() + "");
        FragmentReplacer.replaceFragmentWithStack(mCallingActivity, ShopProductsFragment.newInstance(mAdapter.getShops().get(_position)));
    }

    private void clearShopList(){
        DBManager.deleteAllShop();
        mAdapter.setShops(DBManager.getShops());
        mAdapter.notifyDataSetChanged();
    }
}
