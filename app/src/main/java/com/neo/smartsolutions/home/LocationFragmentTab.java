package com.neo.smartsolutions.home;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.neo.smartsolutions.R;
import com.neo.smartsolutions.location_recycler_view.ClickListener;
import com.neo.smartsolutions.location_recycler_view.Location;
import com.neo.smartsolutions.location_recycler_view.LocationAdapter;

import java.util.ArrayList;
import java.util.List;

public class LocationFragmentTab extends Fragment {

    private Listener listener;
    private List<Location> locations;
    private RecyclerView recyclerViewLocations;
    private LocationAdapter recyclerViewAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.h_loc_fragment_tab_location, container, false);
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

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        recyclerViewLocations = view.findViewById(R.id.recyclerViewLocations);
        displayLocationsList();
    }

    // RecyclerView implementation
    // get data source
    private void fillWithLocations() {
        locations = new ArrayList<>();
        Location location;
        for (int i = 0; i < 5; i++) {
            location = new Location(i, "Home " + i, "Pancota " , "Muresului", i + 10);
            locations.add(location);
        }
    }

    private void addLocation(String name,String city, String street, int number) {
        //todo add here the new locations to the database/firebase
    }

    private void setLocationsLayoutManager() {
        recyclerViewLocations.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private void setLocationsAdapter() {
        recyclerViewAdapter = new LocationAdapter(getActivity(), locations);

        recyclerViewAdapter.setOnItemClickListener(new ClickListener<Location>(){
            @Override
            public void onItemClick(Location data) {
                //todo from here we will fly to another frame where to add devices
                Toast.makeText(getActivity(), data.getLocationName(), Toast.LENGTH_SHORT).show();
            }
        });

        recyclerViewLocations.setAdapter(recyclerViewAdapter);
    }

    private void displayLocationsList() {
        // data source - checked
        fillWithLocations();

        // layout manager - checked
        setLocationsLayoutManager();

        // adapter - checked
        setLocationsAdapter();
    }
}