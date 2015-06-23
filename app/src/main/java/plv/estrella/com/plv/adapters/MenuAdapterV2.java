package plv.estrella.com.plv.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import plv.estrella.com.plv.R;
import plv.estrella.com.plv.global.Constants;

/**
 * Created by samson on 22.06.2015.
 */
public class MenuAdapterV2 extends ArrayAdapter {

    private List<String> list;
    private Context mContext;

    public MenuAdapterV2(Context context, int resourse, List<String> list) {
        super(context,resourse,list);
        this.list = list;
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public String getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        return 7;
    }

    @Override
    public int getItemViewType(int position) {
        if(list.get(position).equals(Constants.ITEM_INICIO)){
            return Constants.TYPE_INICIO;
        }
        if(list.get(position).equals(Constants.ITEM_PEDIDOS)){
            return Constants.TYPE_PEDIDOS;
        }
        if(list.get(position).equals(Constants.ITEM_PLV)){
            return Constants.TYPE_PLV;
        }
        if(list.get(position).equals(Constants.ITEM_COLUMNAS)){
            return Constants.TYPE_SPV;
        }
        if(list.get(position).equals(Constants.ITEM_ENVIOS)){
            return Constants.TYPE_ENVIOS;
        }
        if(position < 12){
            return Constants.TYPE_SPV_PADDING;
        } else {
            return Constants.TYPE_PLV_PADDING;
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder = null;
        if(convertView == null){
            switch (getItemViewType(position)){
                case Constants.TYPE_INICIO:
                case Constants.TYPE_PEDIDOS:
                    convertView = LayoutInflater.from(mContext).inflate(R.layout.item_menu_s,null);
                    break;
                case Constants.TYPE_SPV:
                case Constants.TYPE_PLV:
                case Constants.TYPE_ENVIOS:
                    convertView = LayoutInflater.from(mContext).inflate(R.layout.item_menu_u,null);
                    break;
                default:
                    convertView = LayoutInflater.from(mContext).inflate(R.layout.item_menu,null);
                    break;
            }

            TextView textView = (TextView) convertView.findViewById(R.id.tvMenuItem);
            holder = new Holder(textView);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        holder.textView.setText(list.get(position));
        return convertView;
    }

    class Holder {
        TextView textView;

        public Holder(TextView text){
            this.textView = text;
        }
    }

}
