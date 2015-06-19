package plv.estrella.com.plv.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cristaliza.mvc.models.estrella.Item;

import java.util.ArrayList;

import plv.estrella.com.plv.R;
import plv.estrella.com.plv.database.DBItem;
import plv.estrella.com.plv.database.DBManager;
import plv.estrella.com.plv.database.Shop;
import plv.estrella.com.plv.untils.BitmapCreator;

/**
 * Created by vasia on 27.05.2015.
 */
public class ShopProductsAdapter extends BaseAdapter implements View.OnClickListener {

    private ArrayList<DBItem> mProducts;
    private Shop mShop;
    private Context mContext;

    public ShopProductsAdapter(Context _context, Shop _shop) {
        mShop = _shop;
        getProductsList();
        mContext = _context;
    }

    public void getProductsList(){

        mProducts = (ArrayList<DBItem>) DBManager.getDBItems(mShop);
    }

    @Override
    public int getCount() {
        return mProducts.size();
    }

    @Override
    public Object getItem(int _position) {
        return mProducts.get(_position);
    }

    @Override
    public long getItemId(int _position) {
        return _position;
    }

    @Override
    public View getView(int _position, View _convertView, ViewGroup _parent) {
        final ViewHolder holder;

        if (_convertView == null){
            final LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            _convertView = inflater.inflate(R.layout.item_product, _parent, false);
            holder = getHolder(_convertView, _position);

        }else {
            holder = (ViewHolder) _convertView.getTag();
        }
        fillData(holder, mProducts.get(_position).getItem());
        return _convertView;
    }

    private ViewHolder getHolder(final View _convertView, int _position){
        final ViewHolder holder = new ViewHolder();
        holder.tvProductName = (TextView) _convertView.findViewById(R.id.tvProductName_IP);
        holder.ivDeleteProduct = (ImageView) _convertView.findViewById(R.id.ivDeleteProduct_IP);
        holder.ivDeleteProduct.setTag(_position);
        holder.ivDeleteProduct.setOnClickListener(this);
        holder.ivProductPhoto = (ImageView) _convertView.findViewById(R.id.ivProductPhoto_IP);
        _convertView.setTag(holder);
        return holder;
    }

    private void fillData(ViewHolder _holder, Item _item){
        _holder.ivProductPhoto.setImageBitmap(BitmapCreator.getBitmap(_item.getIcon()));
        _holder.tvProductName.setText(_item.getName());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ivDeleteProduct_IP:
                deleteProduct((int) v.getTag());
                break;
        }
    }

    private void deleteProduct(int _position){
        DBManager.deleteItem(mProducts.get(_position).getId());
        getProductsList();
        notifyDataSetChanged();
    }

    public void deleteAllProduct(){
        DBManager.deleteAllItems(mShop);
        getProductsList();
        notifyDataSetChanged();
    }

    class ViewHolder{
        private TextView tvProductName;
        private ImageView ivDeleteProduct, ivProductPhoto;
    }
}
