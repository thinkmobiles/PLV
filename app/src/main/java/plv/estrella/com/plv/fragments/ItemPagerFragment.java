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

import plv.estrella.com.plv.R;
import plv.estrella.com.plv.models.ItemProduct;

/**
 * Created by samson on 22.05.2015.
 */
public class ItemPagerFragment extends Fragment {

    private ImageView ivP1, ivP2, ivP3;
    private TextView tvP1, tvP2, tvP3;
    private ItemProduct item1, item2, item3;

    public static ItemPagerFragment newInstance(ItemProduct _item1, ItemProduct _item2, ItemProduct _item3){
        ItemPagerFragment fragment = new ItemPagerFragment();
        Bundle attr = new Bundle();
        attr.putSerializable("item1",_item1);
        attr.putSerializable("item2",_item2);
        attr.putSerializable("item3",_item3);
        fragment.setArguments(attr);
        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if(getArguments() != null){
            item1 = (ItemProduct) getArguments().getSerializable("item1");
            item2 = (ItemProduct) getArguments().getSerializable("item2");
            item3 = (ItemProduct) getArguments().getSerializable("item3");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.view_container,container,false);

        findViews(view);
        initViews();
        return view;
    }

    private void findViews(View v){
        ivP1 = (ImageView) v.findViewById(R.id.ivP1);
        ivP2 = (ImageView) v.findViewById(R.id.ivP2);
        ivP3 = (ImageView) v.findViewById(R.id.ivP3);
        tvP1 = (TextView) v.findViewById(R.id.tvP1);
        tvP2 = (TextView) v.findViewById(R.id.tvP2);
        tvP3 = (TextView) v.findViewById(R.id.tvP3);

    }

    private void initViews(){
        ivP1.setImageResource(item1.getRes());
        ivP2.setImageResource(item2.getRes());
        ivP3.setImageResource(item3.getRes());

        setDescription(tvP1, item1.getmDescription());
        setDescription(tvP2, item2.getmDescription());
        setDescription(tvP3, item3.getmDescription());
    }

    private void setDescription(TextView _tv, String _text){
        if(_text == null){
            _tv.setVisibility(View.GONE);
        } else {
            _tv.setText(_text);
        }
    }


}
