package com.neo.smartsolutions.home;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.neo.smartsolutions.BaseActivity;
import com.neo.smartsolutions.R;

public class HomeFragment extends Fragment {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Listener listener;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.h_fragment_home, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        viewPager = view.findViewById(R.id.viewPager);
        viewPager.addOnPageChangeListener(changeTab);
        setupViewPager(viewPager);

        tabLayout = view.findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof Listener) {
            this.listener = (Listener) context;
        } else {
            throw new ClassCastException(context.toString()
                    + " must implement OnPressListener");
        }
    }

    //tabs
    private void setupViewPager(ViewPager viewPager) {
        TabAdapter adapter = new TabAdapter(getActivity().getSupportFragmentManager());
        adapter.addFragment(new LocationFragmentTab(), getResources().getString(R.string.control));
        adapter.addFragment(new SolutionFragmentTab(), getResources().getString(R.string.solutions));
        viewPager.setAdapter(adapter);
    }

    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_controll);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_solution);
    }

    //listeners

    private final ViewPager.OnPageChangeListener changeTab = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            if(position == 0) {
                listener.onTabModeSelected(BaseActivity.CONTROL_MODE_CODE);
            } else {
                listener.onTabModeSelected(BaseActivity.SOLUTIONS_MODE_CODE);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

}