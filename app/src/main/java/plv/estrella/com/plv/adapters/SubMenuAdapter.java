package plv.estrella.com.plv.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;

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
    private RelativeLayout.LayoutParams params;

    public SubMenuAdapter(Context _context, List<Item> _items, int _type) {
        mItems = _items;
        mContext = _context;

        int height = getDisplayWidth() / 6;
        int width = height;
        if(_type == 1){
            width = getDisplayWidth() / 5;
        }

        params = new RelativeLayout.LayoutParams(width, height);
    }

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public Item getItem(int position) {
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_sub_menu, parent, false);
            holder = new ViewHolder();
            ImageView imageView = (ImageView) convertView.findViewById(R.id.ivSubMenu_ISM);
            imageView.setLayoutParams(params);
            holder.ivMenuImage = imageView;
            convertView.setTag(holder);

        }else
            holder = (ViewHolder) convertView.getTag();
        holder.ivMenuImage.setImageBitmap(BitmapCreator.getBitmapCompressedX2(mItems.get(_position).getIcon()));
        return convertView;
    }

    private int getDisplayWidth() {
        return ((WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getWidth();
    }

    class ViewHolder{
        private ImageView ivMenuImage;
    }
}
