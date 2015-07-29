package plv.estrella.com.plv.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cristaliza.mvc.events.Event;
import com.cristaliza.mvc.models.estrella.AppModel;
import com.cristaliza.mvc.models.estrella.Item;
import com.cristaliza.mvc.models.estrella.Product;

import java.util.ArrayList;
import java.util.List;

import plv.estrella.com.plv.MainActivity;
import plv.estrella.com.plv.R;
import plv.estrella.com.plv.adapters.ProductPagerAdapter;
import plv.estrella.com.plv.custom.AddProductToShopDialog;
import plv.estrella.com.plv.custom.CustomDialog;
import plv.estrella.com.plv.global.Constants;
import plv.estrella.com.plv.models.ItemSerializable;
import plv.estrella.com.plv.untils.ApiManager;

/**
 * Created by samson on 17.06.2015.
 */
public class ProductPagerFragment extends Fragment implements View.OnClickListener, ViewPager.OnPageChangeListener {

    private MainActivity mCallingActivity;

    private ViewPager mPager;
    private ImageView ivPrev, ivNext, ivCarrita, ivMore, ivLess, ivCross;
    private TextView tvCount;

    private Item mCurrentItem;
    private List<Item> mListProducts;
    private Product mProduct;
    private ProductPagerAdapter adapter;
    private com.cristaliza.mvc.events.EventListener eventListener;

    private int targetPos;
    private int[] massivCounters;

    public static ProductPagerFragment newInstance(ItemSerializable _item, int _position) {
        ProductPagerFragment fragment = new ProductPagerFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constants.PARAM_ITEM, _item);
        bundle.putInt(Constants.PARAM_POSITION, _position);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mCallingActivity = (MainActivity) activity;
        if (getArguments() != null) {
            mCurrentItem = ((ItemSerializable) getArguments().getSerializable(Constants.PARAM_ITEM)).getItem();
            targetPos = getArguments().getInt(Constants.PARAM_POSITION);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pager_detail, null);

        findUI(view);
        setListeners();

        makeListener();
        ApiManager.getThirdLevel(eventListener, mCurrentItem);
        return view;
    }

    private void findUI(View v) {
        ivPrev = (ImageView) v.findViewById(R.id.ivPrev_P);
        ivNext = (ImageView) v.findViewById(R.id.ivNext_P);
        ivCarrita = (ImageView) v.findViewById(R.id.ivCarrita_P);
        ivMore = (ImageView) v.findViewById(R.id.ivMore_P);
        ivLess = (ImageView) v.findViewById(R.id.ivLess_P);
        ivCross = (ImageView) v.findViewById(R.id.ivCross_P);
        tvCount = (TextView) v.findViewById(R.id.tvCount_P);
        mPager = (ViewPager) v.findViewById(R.id.pagerSolo);

        mCallingActivity.setTitle(mCurrentItem.getName());
        mCallingActivity.setBackground(mCurrentItem.getBackgroundImage());
    }

    private void setListeners(){
        ivPrev.setOnClickListener(this);
        ivNext.setOnClickListener(this);
        ivCarrita.setOnClickListener(this);
        ivMore.setOnClickListener(this);
        ivLess.setOnClickListener(this);
        ivCross.setOnClickListener(this);

        mPager.setOnPageChangeListener(this);
    }

    private void setAdapter(){
        mListProducts = ApiManager.getThirdList();
        massivCounters = new int[mListProducts.size()];
        ArrayList<ItemPagerFragment> fragments = new ArrayList<>();
        for(int i = 0; i < mListProducts.size(); ++i){
            ApiManager.getProducts(eventListener, mListProducts.get(i));
            massivCounters[i] = 0;
            fragments.add(ItemPagerFragment.newInstance(mProduct));
        }
        adapter = new ProductPagerAdapter(getChildFragmentManager(), fragments);
        mPager.setAdapter(adapter);
        mPager.setCurrentItem(targetPos);
        setVisibilityArrows(targetPos);
    }

    private void makeListener(){
        eventListener = new com.cristaliza.mvc.events.EventListener() {
            @Override
            public void onEvent(Event event) {
                switch (event.getId()){
                    case AppModel.ChangeEvent.ON_EXECUTE_ERROR_ID:
                        Toast.makeText(getActivity(), event.getType() + "error", Toast.LENGTH_SHORT).show();
                        break;
                    case AppModel.ChangeEvent.THIRD_LEVEL_CHANGED_ID:
                        setAdapter();
                        break;
                    case AppModel.ChangeEvent.PRODUCTS_CHANGED_ID:
                        mProduct = ApiManager.getProductsList().get(0);
                        break;
                }
            }
        };
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ivCross_P:
                mCallingActivity.onBackPressed();
                break;
            case R.id.ivMore_P:
                incCounter();
                break;
            case R.id.ivLess_P:
                decCounter();
                break;
            case R.id.ivPrev_P:
                scrollPrev();
                break;
            case R.id.ivNext_P:
                scrollNext();
                break;
            case R.id.ivCarrita_P:
                clickBtnCarrita();
                break;
        }
    }

    private void clickBtnCarrita(){
        if(massivCounters[targetPos] == 0){
            final CustomDialog dialog = new CustomDialog.Builder()
                    .setMessage(mCallingActivity.getString(R.string.no_productos))
                    .setPositiveButton(mCallingActivity.getString(R.string.button_accept), null)
                    .create();
            dialog.show(mCallingActivity);
        } else {
            Product product = adapter.getItem(targetPos).getProduct();
            AddProductToShopDialog.newInstance(new ItemSerializable(mCurrentItem))
                    .show(mCallingActivity, product.getId(), product.getName(), product.getImage(), product.getEAN(), Constants.TYPE_DIALOG_ADD_CARRITA, massivCounters[targetPos]);
        }
    }

    private void incCounter(){
        tvCount.setText(String.valueOf(++massivCounters[targetPos]));
    }

    private void decCounter(){
        if(--massivCounters[targetPos] < 0)
            massivCounters[targetPos] = 0;
        tvCount.setText(String.valueOf(massivCounters[targetPos]));
    }

    private void scrollPrev(){
        mPager.setCurrentItem(--targetPos, true);
    }

    private void scrollNext(){
        mPager.setCurrentItem(++targetPos, true);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        targetPos = position;
        setVisibilityArrows(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private void setVisibilityArrows(int _position){
        if (mListProducts.size() == 1){
            ivNext.setVisibility(View.GONE);
            ivPrev.setVisibility(View.GONE);
            return;
        }
        if (_position == mListProducts.size() - 1) {
            ivNext.setVisibility(View.GONE);
            ivPrev.setVisibility(View.VISIBLE);
            return;
        }
        if (_position == 0) {
            ivPrev.setVisibility(View.GONE);
            ivNext.setVisibility(View.VISIBLE);
            return;
        }

        ivPrev.setVisibility(View.VISIBLE);
        ivNext.setVisibility(View.VISIBLE);

        tvCount.setText(String.valueOf(massivCounters[_position]));
    }
}
