package plv.estrella.com.plv.adapters;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

import plv.estrella.com.plv.fragments.ItemPagerFragment;

/**
 * Created by samson on 22.06.2015.
 */
public class ProductPagerAdapter extends FragmentPagerAdapter {

    private List<ItemPagerFragment> mList;

    public ProductPagerAdapter(FragmentManager fm, List<ItemPagerFragment> _list) {
        super(fm);
        this.mList = _list;
    }

    @Override
    public ItemPagerFragment getItem(int position) {
        return mList.get(position);
    }

    @Override
    public int getCount() {
        return mList.size();
    }
}
