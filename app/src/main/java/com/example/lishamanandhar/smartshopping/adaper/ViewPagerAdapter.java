package com.example.lishamanandhar.smartshopping.adaper;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

/**
 * Created by LishaManandhar on 3/9/2018.
 */

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    ArrayList<Fragment> fragList;
    String titles[];


    public ViewPagerAdapter(FragmentManager fm, ArrayList<Fragment> fragList, String titles[]) {
        super(fm);
        this.fragList = fragList;
        this.titles = titles;

    }

    @Override
    public Fragment getItem(int position) {
        return fragList.get(position);
    }

    @Override
    public int getCount() {
        return fragList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}