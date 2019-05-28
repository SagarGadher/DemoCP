package com.example.sagar.democp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

class PagerViewAdapter extends FragmentPagerAdapter {
    public PagerViewAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                BasicsFragment basicsFragment = new BasicsFragment();
                return basicsFragment;
            case 1:
                ProgramFragment programFragment = new ProgramFragment();
                return programFragment;
            case 2:
                PatternProgramFragment patternProgramFragment = new PatternProgramFragment();
                return patternProgramFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}
