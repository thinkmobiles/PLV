package plv.estrella.com.plv.untils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import plv.estrella.com.plv.R;

/**
 * Created by Bogdan on 06.05.2015.
 */
public class MenuAdapter extends BaseAdapter {

    private static final int TYPE_SIMPLE = 0;
    private static final int TYPE_UNDERLINE = 1;
    private static final int TYPE_PADDING = 2;

    private List<String> list;
    private Context mContext;

    public MenuAdapter(List<String> list, Context context) {
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
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder = new Holder();
        if(convertView == null){
//            switch (getTypeItem(position)){
//                case TYPE_SIMPLE:
//                    convertView = LayoutInflater.from(mContext).inflate(R.layout.item_menu,null);
//                    holder.textView = (TextView) convertView.findViewById(R.id.tvMenuItem);
//                    break;
//                case TYPE_UNDERLINE:
//                    convertView = LayoutInflater.from(mContext).inflate(R.layout.item_menu_line_top,null);
//                    holder.textView = (TextView) convertView.findViewById(R.id.tvMenuItem_u);
//                    break;
//                case TYPE_PADDING:
//                    convertView = LayoutInflater.from(mContext).inflate(R.layout.item_menu_sub,null);
//                    holder.textView = (TextView) convertView.findViewById(R.id.tvMenuItem_p);
//                    break;
//            }
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_menu_sub,null);
            holder.textView = (TextView) convertView.findViewById(R.id.tvMenuItem_p);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        holder.textView.setText(list.get(position));
        return convertView;
    }


    public int getTypeItem(int position){
        if(list.get(position).equals("inicio") || list.get(position).equals("pedidos")){
            return TYPE_SIMPLE;
        }
        if(list.get(position).equals("plv") ||
                list.get(position).equals("columnas") ||
                list.get(position).equals("envios")){
            return TYPE_UNDERLINE;
        } else {
            return TYPE_PADDING;
        }
    }

    class Holder {
        TextView textView;
    }

}
