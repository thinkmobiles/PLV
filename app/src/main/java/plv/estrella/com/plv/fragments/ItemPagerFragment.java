package plv.estrella.com.plv.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cristaliza.mvc.models.estrella.Item;

import plv.estrella.com.plv.R;
import plv.estrella.com.plv.models.ItemProduct;
import plv.estrella.com.plv.untils.BitmapCreator;

/**
 * Created by samson on 22.05.2015.
 */
public class ItemPagerFragment extends Fragment {

    private ImageView ivIconSolo;
    private TextView tvDescrSolo;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.item_product_solo,container,false);

        findViews(view);

        return view;
    }

    private void findViews(View v){
        ivIconSolo = (ImageView) v.findViewById(R.id.ivIconProd);
        tvDescrSolo = (TextView) v.findViewById(R.id.tvDescrSolo);

    }

    public void setData(Item _item){
        ivIconSolo.setImageBitmap(BitmapCreator.getBitmap(_item.getIcon()));
        tvDescrSolo.setText(_item.getDescription());
    }

}
