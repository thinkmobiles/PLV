package plv.estrella.com.plv.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import plv.estrella.com.plv.MainActivity;
import plv.estrella.com.plv.R;
import plv.estrella.com.plv.custom.CustomDialog;
import plv.estrella.com.plv.database.DBItem;
import plv.estrella.com.plv.database.DBManager;
import plv.estrella.com.plv.database.Shop;
import plv.estrella.com.plv.fragments.ShopProductsFragment;
import plv.estrella.com.plv.global.Constants;
import plv.estrella.com.plv.untils.FragmentReplacer;

/**
 * Created by vasia on 27.05.2015.
 */
public class ShopListAdapter extends BaseAdapter implements View.OnClickListener {

    private ArrayList<Shop> mShops;
    private MainActivity mActivity;
    private int typeShops;

    public ShopListAdapter(MainActivity _mainActivity, List<Shop> _shops, int _typeShop) {
        mActivity = _mainActivity;
        typeShops = _typeShop;
        setShops(_shops);
    }

    public void setShops(List<Shop> _shops){
        mShops = new ArrayList<>();
        for(Shop shop : _shops){
            if(shop.getType() == typeShops)
                mShops.add(shop);
        }
    }

    public ArrayList<Shop> getShops(){
        return mShops;
    }

    @Override
    public int getCount() {
        return mShops.size();
    }

    @Override
    public Object getItem(int _position) {
        return mShops.get(_position);
    }

    @Override
    public long getItemId(int _position) {
        return _position;
    }

    @Override
    public View getView(int _position, View _convertView, ViewGroup _parent) {
        final ViewHolder holder;

        if (_convertView == null){
            final LayoutInflater inflater = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            _convertView = inflater.inflate(R.layout.item_shop, _parent, false);
            holder = getHolder(_convertView, _position);

        }else {
            holder = (ViewHolder) _convertView.getTag();
        }

        setListeners(holder);
        holder.tvShopName.setText(mShops.get(_position).getName());
        return _convertView;
    }

    private ViewHolder getHolder(final View _convertView, int _position){
        final ViewHolder holder = new ViewHolder();
        holder.tvShopName   = (TextView) _convertView.findViewById(R.id.tvShopName_IS);
        holder.ivSend       = (ImageView) _convertView.findViewById(R.id.ivSend_IS);
        holder.ivSee        = (ImageView) _convertView.findViewById(R.id.ivSee_IS);
        holder.ivDelete     = (ImageView) _convertView.findViewById(R.id.ivDelete_IS);

        holder.ivSend.setTag(_position);
        holder.ivSee.setTag(_position);
        holder.ivDelete.setTag(_position);

        _convertView.setTag(holder);
        return holder;
    }

    private void setListeners(ViewHolder _holder){
        _holder.ivSend.setOnClickListener(this);
        _holder.ivSee.setOnClickListener(this);
        _holder.ivDelete.setOnClickListener(this);
    }

    @Override
    public void onClick(View _view) {
        final int position = (int)_view.getTag();
        switch (_view.getId()){
            case R.id.ivSend_IS:
                send(position);
                break;
            case R.id.ivSee_IS:
                see(position);
                break;
            case R.id.ivDelete_IS:
                startDeleteDialog(position);
                break;
        }
    }

    private void send(int _position){
        if(Constants.TYPE_SHOPS_PEDIDOS == typeShops){
            if(checkCorrect(_position)){
                Toast.makeText(mActivity, "send",Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(mActivity, "0 products",Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(mActivity, "send",Toast.LENGTH_SHORT).show();
        }
    }

    private void see(int _position){
        FragmentReplacer.replaceFragmentWithStack(mActivity, ShopProductsFragment.newInstance(mShops.get(_position)));
    }

    private void startDeleteDialog(final int _position){
        final CustomDialog dialog = new CustomDialog.Builder()
                .setPositiveButton("Ok", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        deleteShop(_position);
                    }
                })
                .setNegativeButton("cancel", null)
                .setMessage("Remove shop?")
                .create();
        dialog.show(mActivity);
    }

    private void deleteShop(int _position){
        DBManager.deleteShop(mShops.get(_position));
        setShops(DBManager.getShops());
        notifyDataSetChanged();

    }

    private boolean checkCorrect(int _pos){
        List<DBItem> items = DBManager.getDBItems(mShops.get(_pos));
        for(DBItem item : items){
            if(item.getNumber() == 0)
                return false;
        }
        return true;
    }

    class ViewHolder{
        private TextView tvShopName;
        private ImageView ivSend, ivSee, ivDelete;
    }
}
