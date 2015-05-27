package plv.estrella.com.plv.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

import plv.estrella.com.plv.R;
import plv.estrella.com.plv.database.DBManager;
import plv.estrella.com.plv.database.Shop;

/**
 * Created by vasia on 27.05.2015.
 */
public class ShopListAdapter extends BaseAdapter implements View.OnClickListener {

    private ArrayList<Shop> mShops;
    private Context mContext;

    public ShopListAdapter(Context _context, List<Shop> _shops) {
        mContext = _context;
        setShops(_shops);
    }

    public void setShops(List<Shop> _shops){
        mShops = (ArrayList<Shop>) _shops;
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
            final LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            _convertView = inflater.inflate(R.layout.item_shop, _parent, false);
            holder = getHolder(_convertView, _position);

        }else {
            holder = (ViewHolder) _convertView.getTag();
        }

        setListeners(holder);
        holder.tvShopName.setText(mShops.get(_position).getShopName());
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
                delete(position);
                break;
        }
    }

    private void send(int _position){

    }

    private void see(int _position){

    }

    private void delete(int _position){
        DBManager.deleteShop(mShops.get(_position));
        setShops(DBManager.getShops());
        notifyDataSetChanged();

    }

    class ViewHolder{
        private TextView tvShopName;
        private ImageView ivSend, ivSee, ivDelete;
    }
}
