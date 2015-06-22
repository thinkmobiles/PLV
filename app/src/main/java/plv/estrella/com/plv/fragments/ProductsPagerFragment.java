package plv.estrella.com.plv.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.cristaliza.mvc.models.estrella.Item;

import java.util.ArrayList;

import plv.estrella.com.plv.MainActivity;
import plv.estrella.com.plv.R;
import plv.estrella.com.plv.adapters.ViewPagerTripleAdapter;
import plv.estrella.com.plv.global.Constants;
import plv.estrella.com.plv.models.ItemProduct;
import plv.estrella.com.plv.models.ItemSerializable;
import plv.estrella.com.plv.untils.BitmapCreator;

/**
 * Created by samson on 22.05.2015.
 */
public class ProductsPagerFragment extends Fragment implements ViewPager.OnPageChangeListener, View.OnClickListener {
    private ViewPager mPager;
    private ViewPagerTripleAdapter mAdapter;
    private int mPosition;
    private RelativeLayout rlPrev, rlNext;
    private ArrayList<ItemPagerFragment> mList;
    private ImageView ivLable;
    private HorizontalScrollView hsvList;

    private MainActivity mCallingActivity;

    private ItemSerializable mCurItem;
    private Item mCurrenttItem;


    public static ProductsPagerFragment newInstance(final ItemSerializable _item){
        ProductsPagerFragment fragment = new ProductsPagerFragment();
        Bundle args = new Bundle();
        args.putSerializable(Constants.ITEM, _item);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        mCallingActivity = (MainActivity) activity;
        if(getArguments() != null){
            mCurItem = (ItemSerializable) getArguments().getSerializable(Constants.ITEM);
            mCurrenttItem = mCurItem.getItem();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pager,container,false);

        findViews(view);
        setLable();
        initAdapter();
        setListeners();

        fillHorizontalList();

        return view;
    }

    private void findViews(View v){
        mPager  = (ViewPager) v.findViewById(R.id.view);
        rlPrev  = (RelativeLayout) v.findViewById(R.id.rlPrev);
        rlNext  = (RelativeLayout) v.findViewById(R.id.rlNext);
        ivLable = (ImageView) v.findViewById(R.id.ivLable);
        hsvList = (HorizontalScrollView) v.findViewById(R.id.hsvListProducts);
    }

    private void setListeners(){
        rlPrev.setOnClickListener(this);
        rlNext.setOnClickListener(this);
    }

    private void initAdapter(){
//        List<String> list = mCurrenttItem.getExtraImagesDescription();
//        Toast.makeText(mCallingActivity, list.size() + "",Toast.LENGTH_SHORT).show();

        mList = new ArrayList<>();
        for(int i = 0; i<4; ++i) {
            mList.add(ItemPagerFragment.newInstance(
                    new ItemProduct("slide " + i, R.drawable.btn_add_envio),
                    new ItemProduct("slide dsffdbsfg " + i, R.drawable.btn_add_envio),
                    new ItemProduct("slide vs HJVKHKVHVKHGJ " + i, R.drawable.btn_add_envio)));
        }
        mAdapter = new ViewPagerTripleAdapter(mPager, mList, getChildFragmentManager());
        mPager.setAdapter(mAdapter);
        mPager.setOnPageChangeListener(this);
        mPager.setCurrentItem(mPosition);
        setVisibilityArrows(mPosition);
    }

    private void setLable(){
        ivLable.setImageBitmap(BitmapCreator.getBitmap(mCurrenttItem.getLogo()));
    }

    @Override
    public void onPageScrolled(int i, float v, int i2) {

    }

    @Override
    public void onPageSelected(int _position) {
        mPosition = _position;
        setVisibilityArrows(mPosition);
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rlPrev:
                mPager.setCurrentItem(mPager.getCurrentItem() - 1,true);
                break;
            case R.id.rlNext:
                mPager.setCurrentItem(mPager.getCurrentItem() + 1,true);
                break;
        }
    }

    private void setVisibilityArrows(int _position){
        if(mList.size() == 1 ){
            rlNext.setVisibility(View.GONE);
            rlPrev.setVisibility(View.GONE);
            return;
        }
        if(_position == 0){
            rlNext.setVisibility(View.VISIBLE);
            rlPrev.setVisibility(View.GONE);
            return;
        }
        if(_position == mList.size() - 1){
            rlNext.setVisibility(View.GONE);
            rlPrev.setVisibility(View.VISIBLE);
            return;
        }

        rlNext.setVisibility(View.VISIBLE);
        rlPrev.setVisibility(View.VISIBLE);
    }

    private void fillHorizontalList(){

    }
}
