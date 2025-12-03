package com.example.breadhub;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class CreateSandwichFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_create_sandwhich, container, false);

        // Fetching buttons and spinners
        Spinner spinner = view.findViewById(R.id.sandwichTypeSpinner);
        Button addRecipeBtn = view.findViewById(R.id.addRecipeBtn);
        Button goBackButton = view.findViewById(R.id.goBackBtn);


        // Use string array from xml file
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                requireContext(),
                R.array.sandwich_types,
                android.R.layout.simple_spinner_item
        );

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);

        // TODO: Make 'add buttons' add another text line
        // Add recipe
        addRecipeBtn.setOnClickListener(v -> {
            if (getActivity() != null){
                System.out.println("TEKSLJDSFKL");
            }
        });



        // Go back
        goBackButton.setOnClickListener(v -> {
            if (getActivity() != null) {
                // Pop the current fragment off the back stack to return to TitleFragment
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        return view;
    }
}

