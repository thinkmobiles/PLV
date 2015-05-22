package plv.estrella.com.plv.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import com.cristaliza.mvc.models.estrella.Item;
import java.util.List;
import plv.estrella.com.plv.R;
import plv.estrella.com.plv.untils.BitmapCreator;


/**
 * Created by vasia on 22.05.2015.
 */
public class SubMenuAdapter extends BaseAdapter {

    private List<Item> mItems;
    private Context mContext;
    private static final int[] COLUMNAS_IMAGES = {R.drawable.index_plv_eg, R.drawable.index_plv_1906, R.drawable.index_plv_1906bc, R.drawable.index_plv_1906rv, R.drawable.index_plv_eg00, R.drawable.index_plv_cabreiroa, R.drawable.index_plv_magma};
    public SubMenuAdapter(List<Item> _items, Context _context) {
        mItems = _items;
        mContext = _context;
    }

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public Object getItem(int position) {
        return mItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int _position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null){
            final LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_sub_menu, parent, false);
            holder = new ViewHolder();

            holder.ivMenuImage = (ImageView) convertView.findViewById(R.id.ivSubMenu_ISM);
            convertView.setTag(holder);

        }else
            holder = (ViewHolder) convertView.getTag();
        if (mItems.get(_position).getIcon() != null && !mItems.get(_position).getIcon().equals(""))
            holder.ivMenuImage.setImageDrawable(BitmapCreator.getDrawable(mItems.get(_position).getIcon()));
        else
            holder.ivMenuImage.setImageResource(R.drawable.index_plv_cabreiroa);
        return convertView;
    }

    class ViewHolder{
        private ImageView ivMenuImage;
    }
}
