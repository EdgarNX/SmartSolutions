package com.neo.smartsolutions.welcome;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.neo.smartsolutions.R;
import com.neo.smartsolutions.WelcomeActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class SingUpFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    private Spinner spinnerCountry;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sing_up, container, false);
    }


    private List<String> getSpinnerCountryDataSource() {
        List<String> countries = new ArrayList<>();
        countries.add("Romania");
        countries.add("Hungary");
        countries.add("Serbia");
        countries.add("Austria");
        countries.add("Germany");
        return countries;
    }

    private ArrayAdapter<String> getSpinnerAdapter() {
        return new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, getSpinnerCountryDataSource());
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        spinnerCountry = (Spinner) view.findViewById(R.id.country_spinner);

        spinnerCountry.setAdapter(getSpinnerAdapter());
        spinnerCountry.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


}