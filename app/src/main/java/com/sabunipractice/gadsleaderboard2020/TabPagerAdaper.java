package com.sabunipractice.gadsleaderboard2020;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class TabPagerAdaper extends FragmentPagerAdapter {
    int tabCount;

    public TabPagerAdaper(FragmentManager fm, int numberOfTabs){
        super(fm);
        this.tabCount = numberOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new LearningLeaders();
            case 1:
                return new SkillIQLeaders();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}
