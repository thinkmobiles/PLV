package plv.estrella.com.plv.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.cristaliza.mvc.models.estrella.Item;

import java.util.List;

/**
 * Created by samson on 22.06.2015.
 */
public class ProductPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> mList;

    public ProductPagerAdapter(FragmentManager fm, List<Fragment> _list) {
        super(fm);
        this.mList = _list;
    }

    @Override
    public Fragment getItem(int position) {
        return mList.get(position);
    }

    @Override
    public int getCount() {
        return mList.size();
    }
}
