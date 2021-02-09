package com.neo.smartsolutions.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.neo.smartsolutions.R;

public class HomeFragment extends Fragment {

    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.h_fragment_home, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        //tabs
        viewPager = (ViewPager) view.findViewById(R.id.viewPager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) view.findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();
    }

    //methods for tabs
    private void setupViewPager(ViewPager viewPager) {
        TabAdapter adapter = new TabAdapter(getActivity().getSupportFragmentManager());
        adapter.addFragment(new TabControlFragment(), getResources().getString(R.string.control));
        adapter.addFragment(new TabSolutionFragment(), getResources().getString(R.string.solutions));
        viewPager.setAdapter(adapter);
    }


    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_controll);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_solution);
    }

}