package plv.estrella.com.plv.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cristaliza.mvc.models.estrella.Item;

import plv.estrella.com.plv.MainActivity;
import plv.estrella.com.plv.R;
import plv.estrella.com.plv.global.Constants;
import plv.estrella.com.plv.models.ItemSerializable;

/**
 * Created by vasia on 22.05.2015.
 */
public class ProductFragment extends Fragment {

    private MainActivity mCallingActivity;
    private Item mCurrentItem;

    public static ProductFragment newInstance(final ItemSerializable _item) {
        final ProductFragment fragment = new ProductFragment();
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
            getArguments().remove(Constants.ITEM);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_product, container, false);
        findUI(view);

        return view;
    }

    private void findUI(final View _view){

    }
}
