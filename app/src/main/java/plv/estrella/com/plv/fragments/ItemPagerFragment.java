package plv.estrella.com.plv.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cristaliza.mvc.models.estrella.Product;

import plv.estrella.com.plv.R;
import plv.estrella.com.plv.global.Constants;
import plv.estrella.com.plv.models.ProductSerializable;
import plv.estrella.com.plv.untils.BitmapCreator;

/**
 * Created by samson on 22.05.2015.
 */
public class ItemPagerFragment extends Fragment {

    private ImageView ivIconSolo;
    private TextView tvDescrSolo, tvEanSolo;
    private Product product;

    public static ItemPagerFragment newInstance(Product _product){
        ItemPagerFragment fragment = new ItemPagerFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constants.PARAM_ITEM, new ProductSerializable(_product));
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        product = ((ProductSerializable) getArguments().getSerializable(Constants.PARAM_ITEM)).getProduct();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.item_product_solo,container,false);

        findViews(view);

        return view;
    }

    public Product getProduct() {
        return product;
    }

    @Override
    public void onResume() {
        super.onResume();
        setData();
    }

    @Override
    public void onPause() {
        super.onPause();
        clearData();
    }

    private void findViews(View v){
        ivIconSolo = (ImageView) v.findViewById(R.id.ivIconSolo);
        tvDescrSolo = (TextView) v.findViewById(R.id.tvDescrSolo);
        tvEanSolo = (TextView) v.findViewById(R.id.tvEanSolo);
    }

    private void setData(){
        ivIconSolo.setImageBitmap(BitmapCreator.getCompressedBitmap(product.getImageSmall(), Constants.RATIO_1_1, 350f));
        tvDescrSolo.setText(product.getName());
        tvEanSolo.setText(product.getEAN());
    }

    private void clearData(){
        ivIconSolo.setImageBitmap(null);
    }

}
