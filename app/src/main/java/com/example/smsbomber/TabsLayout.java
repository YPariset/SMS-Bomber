package com.example.smsbomber;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;


public class TabsLayout extends FragmentPagerAdapter {

    Context context;
    int tab_id;

    public TabsLayout(Context context , FragmentManager fragmentManager , int tabs) {
        super(fragmentManager);
        tab_id = tabs;
        this.context = context;

    }

    @NonNull
    @Override
    public Fragment getItem(int tab) {
        switch (tab) {
            case 1:
                return null;
            case 2:
                return null;
            default:
                return null;

        }
    }

    @Override
    public int getCount() {
        return tab_id;
    }
}