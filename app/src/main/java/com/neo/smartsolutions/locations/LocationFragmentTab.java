package com.neo.smartsolutions.locations;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.neo.smartsolutions.R;
import com.neo.smartsolutions.devices.device_local_db.Device;
import com.neo.smartsolutions.locations.location_local_db.*;
import com.neo.smartsolutions.home.Listener;
import com.neo.smartsolutions.locations.location_local_db.ClickListener;

import java.util.List;
import java.util.concurrent.ExecutionException;

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

        LocationViewModel mLocationViewModel = new ViewModelProvider(this).get(LocationViewModel.class);

        mLocationViewModel.getAllLocations().observe(getActivity(), new Observer<List<Location>>() {
            @Override
            public void onChanged(@Nullable final List<Location> locations) {
                // Update the cached copy of the words in the adapter.
                adapter.setLocations(locations);
            }
        });

        ItemTouchHelper helper = new ItemTouchHelper(
                new ItemTouchHelper.SimpleCallback(0,
                        ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove(@NonNull RecyclerView recyclerView,
                                          @NonNull RecyclerView.ViewHolder viewHolder,
                                          @NonNull RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        builder.setMessage(getString(R.string.device_delete_question));
                        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int arg1) {
                                int position = viewHolder.getAdapterPosition();
                                Location myLocation = adapter.getLocationAtPosition(position);
                                // Toast.makeText(getActivity(), getString(R.string.delete_word_preamble) + " " + myDevice.getName(), Toast.LENGTH_LONG).show();
                                try {
                                    listener.onDeleteLocationButtonPressed(myLocation);
                                } catch (ExecutionException | InterruptedException e) {
                                    e.printStackTrace();
                                }
                                dialog.cancel();
                            }
                        });
                        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int arg1) {
                                adapter.notifyItemChanged(viewHolder.getAdapterPosition());
                                dialog.cancel();
                            }
                        });
                        builder.create()
                                .show();
                    }

                });

        helper.attachToRecyclerView(recyclerViewLocations);

        adapter.setOnItemClickListener(new ClickListener<Location>() {
            @Override
            public void onItemClick(Location data) {
                listener.onLocationSelected(data.getName(), data.getCity());
            }
        });
    }
}