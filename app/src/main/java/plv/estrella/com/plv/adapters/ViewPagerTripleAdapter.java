package plv.estrella.com.plv.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import java.util.ArrayList;

import plv.estrella.com.plv.fragments.ItemPagerFragment;

/**
 * Created by samson on 22.05.2015.
 */
public class ViewPagerTripleAdapter extends FragmentPagerAdapter {

    private ViewPager mPager;
    private ArrayList<ItemPagerFragment> mList;

    public ViewPagerTripleAdapter(ViewPager pager, ArrayList<ItemPagerFragment> list, FragmentManager fm) {
        super(fm);
        this.mPager = pager;
        this.mList = list;
    }

    @Override
    public Fragment getItem(int id) {
        return mList.get(id);
    }

    @Override
    public int getCount() {
        return mList.size();
    }
}
