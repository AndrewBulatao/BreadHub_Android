package com.example.breadhub;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.room.TypeConverter;

import com.example.breadhub.database.AppDao;
import com.example.breadhub.database.AppDatabase;

import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// TODO: Also check if information on sandwiches gets uploaded into databases

public class CreateSandwichFragment extends Fragment {

    // Arrays for ingredient types
    private final List<String> proteins = new ArrayList<>();
    private final List<String> veggies = new ArrayList<>();
    private final List<String> cheeses = new ArrayList<>();
    private final List<String> sauces = new ArrayList<>();

    // Database
    private AppDao dao;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_create_sandwhich, container, false);
        // Widgets
        Spinner spinner = view.findViewById(R.id.sandwichTypeSpinner);
        Button addRecipeBtn = view.findViewById(R.id.addRecipeBtn);
        Button goBackButton = view.findViewById(R.id.goBackBtn);
        EditText sandwichNameInput = view.findViewById(R.id.sandwichNameInput);

        LinearLayout proteinContainer = view.findViewById(R.id.proteinContainer);
        EditText proteinInput = proteinContainer.findViewById(R.id.proteinQues);
        Button addProteinBtn = view.findViewById(R.id.addProteinBtn);
        TextView proteinView = view.findViewById(R.id.proteinView);

        LinearLayout veggieContainer = view.findViewById(R.id.veggieContainer);
        EditText veggieInput = veggieContainer.findViewById(R.id.veggieQues);
        Button addVeggieBtn = view.findViewById(R.id.addVeggieBtn);
        TextView veggieView = view.findViewById(R.id.veggieView);

        LinearLayout cheeseContainer = view.findViewById(R.id.cheeseContainer);
        EditText cheeseInput = cheeseContainer.findViewById(R.id.cheeseQues);
        Button addCheeseBtn = view.findViewById(R.id.addCheeseBtn);
        TextView cheeseView = view.findViewById(R.id.cheeseView);

        LinearLayout sauceContainer = view.findViewById(R.id.sauceContainer);
        EditText sauceInput = sauceContainer.findViewById(R.id.sauceQues);
        Button addSauceBtn = view.findViewById(R.id.addSauceBtn);
        TextView sauceView = view.findViewById(R.id.sauceView);

        // Database
        AppDatabase db = AppDatabase.getInstance(requireContext());



        // Ingredient "Add" buttons
        addProteinBtn.setOnClickListener(v -> {
            String text = proteinInput.getText().toString().trim();
            if (!text.isEmpty()) {
                // Append
                proteins.add(text);

                // Update the label using string resources
                proteinView.setText(getString(R.string.protein_label, TextUtils.join(", ", proteins)));

                // Clear input
                proteinInput.setText("");
            }
        });


        addVeggieBtn.setOnClickListener(v -> {
            String text = veggieInput.getText().toString().trim();
            if (!text.isEmpty()) {
                // Append
                veggies.add(text);

                // Update the label using string resources
                veggieView.setText(getString(R.string.veggie_label, TextUtils.join(", ", veggies)));

                // Clear input
                veggieInput.setText("");
            }
        });
        addCheeseBtn.setOnClickListener(v -> {
            String text = cheeseInput.getText().toString().trim();
            if (!text.isEmpty()) {
                // Append
                cheeses.add(text);

                // Update the label using string resources
                cheeseView.setText(getString(R.string.cheese_label, TextUtils.join(", ", cheeses)));

                // Clear input
                cheeseInput.setText("");
            }
        });
        addSauceBtn.setOnClickListener(v -> {
            String text = sauceInput.getText().toString().trim();
            if (!text.isEmpty()) {
                // Append
                sauces.add(text);

                // Update the label using string resources
                sauceView.setText(getString(R.string.sauce_label, TextUtils.join(", ", sauces)));

                // Clear input
                sauceInput.setText("");
            }
        });
        // Add Recipe
        // TODO: Make a try/catch to make sure the user adds a name.
        addRecipeBtn.setOnClickListener(v -> {
            // First check if the user input a recipe name
            String sandwichName = sandwichNameInput.getText().toString().trim();

            // Check if recipe name exists
            if (sandwichName.isEmpty()) {
                // Have pop up for user to insert name
                new AlertDialog.Builder(requireContext())
                        .setTitle("Missing Recipe Name")
                        .setMessage("Please provide the name of your recipe")
                        .setPositiveButton("Ok", (dialog, which) -> {
                            dialog.dismiss();
                        })
                        .show();
            }else{
                Toast.makeText(requireContext(), "Recipe added!", Toast.LENGTH_SHORT).show();
                // TODO: Insert arrays into db

                // Clear input texts
                sandwichNameInput.setText("");
                proteinInput.setText("");
                veggieInput.setText("");
                cheeseInput.setText("");
                sauceInput.setText("");
            }
        });

        goBackButton.setOnClickListener(v -> {
            if (getActivity() == null) return;

            new AlertDialog.Builder(requireContext())
                    .setTitle("Confirm Exit")
                    .setMessage("Are you sure you want to leave? All progress will be lost.")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        // User confirmed, go back
                        getActivity().getSupportFragmentManager().popBackStack();
                    })
                    .setNegativeButton("No", (dialog, which) -> {
                        // User canceled, do nothing
                        dialog.dismiss();
                    })
                    .show();
        });



        return view;
    }

}
