package plv.estrella.com.plv.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.cristaliza.mvc.events.Event;
import com.cristaliza.mvc.events.EventListener;
import com.cristaliza.mvc.models.estrella.AppModel;
import com.cristaliza.mvc.models.estrella.Item;
import com.cristaliza.mvc.models.estrella.Product;

import java.util.List;

import plv.estrella.com.plv.MainActivity;
import plv.estrella.com.plv.R;
import plv.estrella.com.plv.global.Constants;
import plv.estrella.com.plv.models.ItemSerializable;
import plv.estrella.com.plv.untils.ApiManager;
import plv.estrella.com.plv.untils.BitmapCreator;
import plv.estrella.com.plv.untils.FragmentReplacer;

/**
 * Created by samson on 22.05.2015.
 */
public class PLVFragment extends Fragment implements View.OnClickListener {

    private ImageView ivPrev, ivNext, ivLable;
    private HorizontalScrollView hsvList;
    private LinearLayout llContProd;
    private EventListener mMenuListener;
    private List<Item> mListProducts;
    private Product mProduct;
    private MainActivity mCallingActivity;
    private Item mCurrentItem;

    public static PLVFragment newInstance(final ItemSerializable _item) {
        PLVFragment fragment = new PLVFragment();
        Bundle args = new Bundle();
        args.putSerializable(Constants.ITEM, _item);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        mCallingActivity = (MainActivity) activity;
        if (getArguments() != null) {
            ItemSerializable item = (ItemSerializable) getArguments().getSerializable(Constants.ITEM);
            mCurrentItem = item.getItem();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_plv, container, false);

        findViews(view);
        makeListener();

        ApiManager.getThirdLevel(mMenuListener, mCurrentItem);

        setData();
        setListeners();
        return view;
    }

    private void findViews(View v) {
        ivPrev      = (ImageView) v.findViewById(R.id.ivPrev);
        ivNext      = (ImageView) v.findViewById(R.id.ivNext);
        ivLable     = (ImageView) v.findViewById(R.id.ivLable);
        hsvList     = (HorizontalScrollView) v.findViewById(R.id.hsvListProducts);
        llContProd  = (LinearLayout) v.findViewById(R.id.llContProd);
    }

    private void setListeners() {
        ivPrev.setOnClickListener(this);
        ivNext.setOnClickListener(this);
    }

    private void makeListener() {
        mMenuListener = new EventListener() {
            @Override
            public void onEvent(Event event) {
                switch (event.getId()){
                    case AppModel.ChangeEvent.ON_EXECUTE_ERROR_ID:
                        Toast.makeText(getActivity(), event.getType() + "error", Toast.LENGTH_SHORT).show();
                        break;
                    case AppModel.ChangeEvent.THIRD_LEVEL_CHANGED_ID:
                        fillHorizontalList();
                        break;
                    case AppModel.ChangeEvent.PRODUCTS_CHANGED_ID:
                        mProduct = ApiManager.getProductsList().get(0);
                        break;
                }
            }
        };
    }



    private void fillHorizontalList() {
        mListProducts = ApiManager.getThirdList();

        for(int i = 0; i < mListProducts.size(); ++i){
            ApiManager.getProducts(mMenuListener, mListProducts.get(i));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(500, ViewGroup.LayoutParams.MATCH_PARENT);
            params.setMargins(30, 10, 30, 10);

            View view = LayoutInflater.from(mCallingActivity).inflate(R.layout.item_product_horizontal_list, null);
            view.setLayoutParams(params);

            ImageView imageView = (ImageView) view.findViewById(R.id.ivIconProd);

            imageView.setImageBitmap(BitmapCreator.compressImage(mProduct.getImage()));

            view.setOnClickListener(getClickListener(i));
            llContProd.addView(view);

        }
    }

    private void setData() {
        ivLable.setImageBitmap(BitmapCreator.getBitmap(mCurrentItem.getIcon()));
        mCallingActivity.setTitle(mCurrentItem.getName());
        mCallingActivity.setBackground(mCurrentItem.getBackgroundImage());

        setVisibilityArrows();
    }

    private void setVisibilityArrows() {
        if(mListProducts.size() < 4) {
            ivNext.setVisibility(View.GONE);
            ivPrev.setVisibility(View.GONE);
        }
    }

    private View.OnClickListener getClickListener(final int pos){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentReplacer.replaceFragmentWithStack(
                        mCallingActivity,
                        ProductPagerFragment.newInstance(new ItemSerializable(mCurrentItem), pos)
                );
            }
        };
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivPrev:
                hsvList.pageScroll(View.FOCUS_LEFT);
                break;
            case R.id.ivNext:
                hsvList.pageScroll(View.FOCUS_RIGHT);
                break;
        }
    }
}
