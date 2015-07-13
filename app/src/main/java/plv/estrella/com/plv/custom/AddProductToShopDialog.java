package plv.estrella.com.plv.custom;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.FrameLayout;
import android.widget.ImageView;
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
import plv.estrella.com.plv.fragments.ColumnaFragment;
import plv.estrella.com.plv.fragments.ProductPagerFragment;
import plv.estrella.com.plv.fragments.ShopProductsFragment;
import plv.estrella.com.plv.fragments.ShopsFragment;
import plv.estrella.com.plv.global.Constants;
import plv.estrella.com.plv.models.ItemSerializable;
import plv.estrella.com.plv.untils.FragmentReplacer;

/**
 * Created by samson on 25.06.2015.
 */
public class AddProductToShopDialog extends Fragment implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    private MainActivity mCallingActivity;
    private int selected;

    private List<Shop> subList;
    private Spinner spinner;
    private TextView tvAccept, tvCancel, tvTitle;
    private Item mCurrentItem;
    private Shop currentShop;
    private FrameLayout flTop, flBottom;
    private AutoCompleteTextView autoCompleteTextView;
    private ImageView allShop;
    private boolean isSelectChek, questionCheck=false;
    private boolean isShowListShop = false;
    private int typeDialogShop;
    private int numProducts;
    private String mIcon, mName, mItemId;
    private ArrayAdapter<String> adapter;

    public static AddProductToShopDialog newInstance(final ItemSerializable _item) {
        final AddProductToShopDialog fragment = new AddProductToShopDialog();
        final Bundle bundle = new Bundle();
        bundle.putSerializable(Constants.PARAM_ITEM, _item);
        fragment.setArguments(bundle);
        return fragment;
    }

    public void show(MainActivity _mActivity, int _typeDialog, int _numProducts){
        typeDialogShop = _typeDialog;
        numProducts = _numProducts;
        FragmentReplacer.addFragment(_mActivity, this);
    }

    public void show(MainActivity _mActivity, String _itemId, String _name, String _icon, int _typeDialog, int _numProducts){
        mItemId = _itemId;
        mIcon = _icon;
        mName = _name;
        typeDialogShop = _typeDialog;
        numProducts = _numProducts;
        FragmentReplacer.addFragment(_mActivity, this);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mCallingActivity = (MainActivity) activity;
        if (getArguments() != null) {
            mCurrentItem = ((ItemSerializable) getArguments().getSerializable(Constants.PARAM_ITEM)).getItem();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View inflaterView = inflater.inflate(R.layout.dialog_product_to_shop, container, false);

        findViews(inflaterView);
        setListeners();
        initTypeDialog();

        return inflaterView;
    }

    private void findViews(final View _view) {
        tvTitle         = (TextView) _view.findViewById(R.id.tv_dialogTitle_CD);
        spinner         = (Spinner) _view.findViewById(R.id.spinner_PSD);
        tvCancel        = (TextView) _view.findViewById(R.id.tvCancel_PSD);
        tvAccept        = (TextView) _view.findViewById(R.id.tvAccept_PSD);
        flTop           = (FrameLayout) _view.findViewById(R.id.flTop_PSD);
        flBottom        = (FrameLayout) _view.findViewById(R.id.flBottom_PSD);
        autoCompleteTextView = (AutoCompleteTextView) _view.findViewById(R.id.etNewShop_PSD1);
        allShop         = (ImageView) _view.findViewById(R.id.iv_all_ItemShop_PSD);

        mCallingActivity.setEnableSlideMenu(false);
    }

    public void setVisible(){
        tvTitle.setText(mCallingActivity.getString(R.string.want_continue));
        if(typeDialogShop == Constants.TYPE_DIALOG_ADD_ENVIOS)
            tvCancel.setText(mCallingActivity.getString(R.string.ver_envio));
        else
            tvCancel.setText(mCallingActivity.getString(R.string.ver_pedido));
        tvAccept.setText(mCallingActivity.getString(R.string.continuar));
        allShop.setVisibility(View.GONE);
        autoCompleteTextView.setVisibility(View.GONE);
    }

    private void setViewSettings(){
        autoCompleteTextView.setDropDownBackgroundResource(R.color.bg_grey);
        if(subList.isEmpty())
            allShop.setVisibility(View.GONE);
    }

    private void initSpinner(){
        allShop.setVisibility(View.VISIBLE);

        String[] names = new String[subList.size()];
        for (int i = 0; i<subList.size(); i++ ){
            names[i] = subList.get(i).getName();
        }
        adapter = new ArrayAdapter<>(mCallingActivity, android.R.layout.simple_dropdown_item_1line, names);
        spinner.setAdapter(new SpinnerPurchaseAdapter(mCallingActivity, subList));
        autoCompleteTextView.setAdapter(adapter);
    }

    private void initTypeDialog(){
        if(typeDialogShop == Constants.TYPE_DIALOG_ADDED) {
            questionCheck = true;
            setVisible();
        } else {
            if(typeDialogShop == Constants.TYPE_DIALOG_ADD_CARRITA){
                tvTitle.setText(mCallingActivity.getString(R.string.anadir_pedido));
            }

            fillShopList();
            setViewSettings();
        }
    }

    private void setListeners() {
        spinner.setOnItemSelectedListener(this);
        tvCancel.setOnClickListener(this);
        tvAccept.setOnClickListener(this);
        flBottom.setOnClickListener(this);
        flTop.setOnClickListener(this);
        allShop.setOnClickListener(this);
        makeAutocompleteItemClickListener();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tvCancel_PSD:
                onClickCancel();
                break;
            case R.id.tvAccept_PSD:
                if(questionCheck) {
                    FragmentReplacer.popSupBackStack(getActivity());
                    questionCheck = false;
                } else
                    onClickPositiveButton();
                break;
            case R.id.flTop_PSD:
            case R.id.flBottom_PSD:
                FragmentReplacer.popSupBackStack(getActivity());
                break;
            case R.id.iv_all_ItemShop_PSD :
                changeDownUpList();
                break;
        }
    }

    private void onClickCancel(){

        FragmentReplacer.popSupBackStack(getActivity());

        if (questionCheck){
            if(typeDialogShop == Constants.TYPE_DIALOG_ADD_CARRITA){
                FragmentReplacer.replaceFragmentWithStack(getActivity(), ShopProductsFragment.newInstance(currentShop));
            } else {
                FragmentReplacer.replaceFragmentWithStack(getActivity(), ShopsFragment.newInstance(typeDialogShop));
            }
            questionCheck = false;
        }
    }

    private void changeDownUpList(){
        if(isShowListShop){
            autoCompleteTextView.dismissDropDown();
        }else{
            autoCompleteTextView.showDropDown();
        }
        isShowListShop = !isShowListShop;
    }

    private void onClickPositiveButton(){

        if (!autoCompleteTextView.getText().toString().isEmpty()) {
            questionCheck = true;
            if (isSelectChek) {

                DBManager.addItem(mItemId, mName, mIcon, mCurrentItem.getPdf(), numProducts, subList.get(selected));

                currentShop = subList.get(selected);
                isSelectChek = false;
            } else {
                Shop shop = DBManager.addShop(autoCompleteTextView.getText().toString(), typeDialogShop);
                fillShopList();

                DBManager.addItem(mItemId, mName, mIcon, mCurrentItem.getPdf(), numProducts, shop);

                currentShop = shop;
                Toast.makeText(mCallingActivity, mCallingActivity.getString(R.string.add_shop_succesfull), Toast.LENGTH_SHORT).show();
                adapter.notifyDataSetChanged();
                autoCompleteTextView.showDropDown();
            }
            setVisible();
        }else {
            Toast.makeText(mCallingActivity, mCallingActivity.getString(R.string.enter_shop), Toast.LENGTH_SHORT).show();
        }

    }

    private void makeAutocompleteItemClickListener(){
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selected = position;
                isSelectChek=true;
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        selected = position;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void fillShopList(){
        subList = new ArrayList<>();
        List<Shop> shops = DBManager.getShops();
        ArrayList<Shop> shopList = new ArrayList<>();
        for(Shop shop :shops){
            if(shop.getType() == typeDialogShop)
                shopList.add(shop);
        }
        if (!shopList.isEmpty()) {
            Shop lastElement = shopList.get(shopList.size() - 1);
            shopList.add(0, lastElement);
            subList = shopList.subList(0, shopList.size() - 1);
        }

        initSpinner();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mCallingActivity.setEnableSlideMenu(true);
    }
}
