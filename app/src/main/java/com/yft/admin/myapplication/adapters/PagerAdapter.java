package com.yft.admin.myapplication.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.yft.admin.myapplication.fragments.ChooseTraining;
import com.yft.admin.myapplication.fragments.Main;
import com.yft.admin.myapplication.fragments.Schedule;


public class PagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public PagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                ChooseTraining frChooseTraining= new ChooseTraining();
                return frChooseTraining;
            case 1:
                Main frMain = new Main();
                return frMain;
            case 2:
                Schedule frSchedule= new Schedule();
                return frSchedule;
            default:
                return null;
        }
    }
    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}