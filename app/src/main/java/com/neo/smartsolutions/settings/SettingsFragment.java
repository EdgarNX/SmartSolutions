package com.neo.smartsolutions.settings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.neo.smartsolutions.HomeActivity;
import com.neo.smartsolutions.R;

import java.util.ArrayList;

public class SettingsFragment extends Fragment {

    private ListView appSettings;
    private ListView accountSettings;

    ArrayList<String> appSettingsList;
    ArrayList<String> accountSettingsList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.s_fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        ListView appSettings = view.findViewById(R.id.appListView);
        ListView accountSettings = view.findViewById(R.id.accountListView);

        appSettingsList = new ArrayList<String>();
        appSettingsList.add("Change Language");
        appSettingsList.add("Delete Something");

        accountSettingsList = new ArrayList<String>();
        accountSettingsList.add("Change Account");
        accountSettingsList.add("Change Country");

        ArrayAdapter<String> appArrayAdapter =
                new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, appSettingsList);
        appSettings.setAdapter(appArrayAdapter);


        ArrayAdapter<String> accountArrayAdapter =
                new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, accountSettingsList);
        accountSettings.setAdapter(accountArrayAdapter);
    }

}