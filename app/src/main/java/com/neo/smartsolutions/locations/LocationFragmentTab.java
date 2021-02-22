package com.neo.smartsolutions.locations;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.neo.smartsolutions.R;
import com.neo.smartsolutions.locations.location_local_db.*;
import com.neo.smartsolutions.home.Listener;
import com.neo.smartsolutions.locations.location_local_db.ClickListener;

import java.util.List;

public class LocationFragmentTab extends Fragment {

    private Listener listener;

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

        RecyclerView recyclerViewLocations = view.findViewById(R.id.recyclerViewLocations);
        final LocationListAdapter adapter = new LocationListAdapter(getActivity());
        recyclerViewLocations.setAdapter(adapter);
        recyclerViewLocations.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Get a new or existing ViewModel from the ViewModelProvider.
        LocationViewModel mLocationViewModel = new ViewModelProvider(this).get(LocationViewModel.class);

        // Add an observer on the LiveData returned by getAlphabetizedWords.
        // The onChanged() method fires when the observed data changes and the activity is
        // in the foreground.
        mLocationViewModel.getAllLocations().observe(getActivity(), new Observer<List<Location>>() {
            @Override
            public void onChanged(@Nullable final List<Location> locations) {
                // Update the cached copy of the words in the adapter.
                adapter.setLocations(locations);
            }
        });

        adapter.setOnItemClickListener(new ClickListener<Location>() {
            @Override
            public void onItemClick(Location data) {
                listener.onLocationSelected(data.getName());
            }
        });
    }
}