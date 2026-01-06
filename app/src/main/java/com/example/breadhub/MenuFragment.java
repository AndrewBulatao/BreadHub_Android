package com.example.breadhub;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

// TODO: Fix the pathing problem for 'viewrecipes'
public class MenuFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main_menu, container, false);

        // TODO: Add inverse colors button, settings button?

        // Create Sandwich button
        Button createSandButt = view.findViewById(R.id.createSandButt);
        createSandButt.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentContainer, new CreateSandwichFragment())
                    .addToBackStack(null)
                    .commit();
        });
        // Bread Search button
        Button breadSearchButt = view.findViewById(R.id.breadSearchButt);
        breadSearchButt.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentContainer, new BreadSearchFragment())
                    .addToBackStack(null)
                    .commit();
        });

        return view;
    }
}
