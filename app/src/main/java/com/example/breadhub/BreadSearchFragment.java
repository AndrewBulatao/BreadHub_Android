package com.example.breadhub;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

// TODO: USE SAME FRAGMENT TO DISPLAY SEARCH RESULTS. DISPLAY TABLE AT BOTTOM OF SEARCH
public class BreadSearchFragment extends Fragment {
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_create_sandwhich, container, false);

        // Find spinner
        Spinner spinner = view.findViewById(R.id.sandwichTypeSpinner);

        // Use string array from xml file
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                requireContext(),
                R.array.sandwich_types,
                android.R.layout.simple_spinner_item
        );

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);

        return view;



    }

}