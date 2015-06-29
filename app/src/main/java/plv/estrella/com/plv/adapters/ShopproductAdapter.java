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
 * Created by samson on 29.06.15.
 */
public class ShopproductAdapter extends BaseAdapter implements View.OnClickListener {
    private ArrayList<DBItem> mProducts;
    private Shop mShop;
    private Context mContext;

    public ShopproductAdapter(Context _context, Shop _shop) {
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
            _convertView = LayoutInflater.from(mContext).inflate(R.layout.item_product_pedido, _parent, false);
            holder = getHolder(_convertView, _position);
        }else {
            holder = (ViewHolder) _convertView.getTag();
        }

        fillData(holder, mProducts.get(_position));
        return _convertView;
    }

    private ViewHolder getHolder(final View _convertView, int _position){
        final ViewHolder holder = new ViewHolder();
        holder.tvProductName    = (TextView) _convertView.findViewById(R.id.tvName_PP);
        holder.tvCountProduct   = (TextView) _convertView.findViewById(R.id.tvCount_PP);
        holder.ivDeleteProduct  = (ImageView) _convertView.findViewById(R.id.ivDelete_PP);
        holder.ivProductPhoto   = (ImageView) _convertView.findViewById(R.id.ivIcon_PP);
        holder.ivMoreProduct    = (ImageView) _convertView.findViewById(R.id.ivMore_PP);
        holder.ivLessProduct    = (ImageView) _convertView.findViewById(R.id.ivLess_PP);

        holder.ivDeleteProduct.setTag(_position);
        holder.ivMoreProduct.setTag(_position);
        holder.ivLessProduct.setTag(_position);

        holder.ivDeleteProduct.setOnClickListener(this);
        holder.ivMoreProduct.setOnClickListener(this);
        holder.ivLessProduct.setOnClickListener(this);

        _convertView.setTag(holder);
        return holder;
    }

    private void fillData(ViewHolder _holder, DBItem _dbItem){
        _holder.ivProductPhoto.setImageBitmap(BitmapCreator.getBitmap(_dbItem.getItem().getIcon()));
        _holder.tvProductName.setText(_dbItem.getItem().getName());
        _holder.tvCountProduct.setText(String.valueOf(_dbItem.getNumber()));
    }

    @Override
    public void onClick(View v) {
        int position = (int) v.getTag();
        switch (v.getId()){
            case R.id.ivDelete_PP:
                deleteProduct(position);
                break;
            case R.id.ivMore_PP:
                incCounter(position);
                break;
            case R.id.ivLess_PP:
                decCounter(position);
                break;
        }
    }

    private void incCounter(int _pos){
        int count = mProducts.get(_pos).getNumber();
        mProducts.get(_pos).setNumber(++count);
        notifyDataSetChanged();
    }

    private void decCounter(int _pos){
        int count = mProducts.get(_pos).getNumber();
        if(--count < 0)
            count = 0;
        mProducts.get(_pos).setNumber(count);
        notifyDataSetChanged();
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
        private TextView tvProductName, tvCountProduct;
        private ImageView ivDeleteProduct, ivProductPhoto, ivMoreProduct, ivLessProduct;
    }
}
