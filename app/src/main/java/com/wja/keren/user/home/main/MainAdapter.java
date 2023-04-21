package com.wja.keren.user.home.main;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import com.wja.keren.user.home.base.BaseFragment;
import com.wja.keren.user.home.find.FindDeviceFragment;
import com.wja.keren.user.home.mine.MineFragment;
import com.wja.keren.user.home.home.HomeFragment;

import java.util.ArrayList;
import java.util.List;

public class MainAdapter extends FragmentPagerAdapter
{

    private List <BaseFragment> fragments;

    public MainAdapter(FragmentManager fm) {
        super(fm);
        fragments = new ArrayList <>();
        fragments.add(new HomeFragment());
        fragments.add(new FindDeviceFragment());
        fragments.add(new MineFragment());    }


    @Override
    public Fragment getItem(int i) {
        return fragments.get(i);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}

