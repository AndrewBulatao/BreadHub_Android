package com.example.breadhub;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.breadhub.database.AppDatabase;
import com.example.breadhub.database.Sandwich;
import com.example.breadhub.database.SandwichType;

import java.util.ArrayList;
import java.util.List;

public class CreateSandwichFragment extends Fragment {

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

        LinearLayout veggieContainer = view.findViewById(R.id.veggieContainer);
        EditText veggieInput = veggieContainer.findViewById(R.id.veggieQues);
        Button addVeggieBtn = view.findViewById(R.id.addVeggieBtn);

        LinearLayout cheeseContainer = view.findViewById(R.id.cheeseContainer);
        EditText cheeseInput = cheeseContainer.findViewById(R.id.cheeseQues);
        Button addCheeseBtn = view.findViewById(R.id.addCheeseBtn);

        LinearLayout sauceContainer = view.findViewById(R.id.sauceContainer);
        EditText sauceInput = sauceContainer.findViewById(R.id.sauceQues);
        Button addSauceBtn = view.findViewById(R.id.addSauceBtn);

        // Database
        AppDatabase db = AppDatabase.getInstance(requireContext());

        // Ensure default sandwich types exist
        new Thread(() -> {
            List<SandwichType> types = db.appDao().getAllTypes();
            if (types.isEmpty()) {
                String[] defaultTypes = {"Sandwich", "Subs/Hoagies", "Bánh mì", "Panini", "Burger"};
                for (String tName : defaultTypes) {
                    SandwichType t = new SandwichType(tName);
                    db.appDao().insertType(t);
                }
                types = db.appDao().getAllTypes();
            }

            List<String> typeNames = new ArrayList<>();
            for (SandwichType t : types) typeNames.add(t.name);

            requireActivity().runOnUiThread(() -> {
                ArrayAdapter<String> adapter = new ArrayAdapter<>(
                        requireContext(),
                        android.R.layout.simple_spinner_item,
                        typeNames
                );
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(adapter);
            });
        }).start();

        // Ingredient "Add" buttons
        addProteinBtn.setOnClickListener(createAddListener(proteinContainer, proteinInput));
        addVeggieBtn.setOnClickListener(createAddListener(veggieContainer, veggieInput));
        addCheeseBtn.setOnClickListener(createAddListener(cheeseContainer, cheeseInput));
        addSauceBtn.setOnClickListener(createAddListener(sauceContainer, sauceInput));

        // Add Recipe
        addRecipeBtn.setOnClickListener(v -> {
            String name = sandwichNameInput.getText().toString().trim();
            int typePosition = spinner.getSelectedItemPosition();
            if (name.isEmpty()) return;

            new Thread(() -> {
                List<SandwichType> types = db.appDao().getAllTypes();
                SandwichType selectedType = types.get(typePosition);

                // Collect ingredients
                List<String> proteins = getIngredientsFromContainer(proteinContainer);
                List<String> veggies = getIngredientsFromContainer(veggieContainer);
                List<String> cheeses = getIngredientsFromContainer(cheeseContainer);
                List<String> sauces = getIngredientsFromContainer(sauceContainer);

                // Insert sandwich into DB
                Sandwich sandwich = new Sandwich(
                        selectedType.id,
                        name,
                        String.join(", ", proteins),
                        String.join(", ", veggies),
                        String.join(", ", cheeses),
                        String.join(", ", sauces)
                );
                db.appDao().insertSandwich(sandwich);

                // Print for testing
                List<Sandwich> allSandwiches = db.appDao().getAllSandwiches();
                for (Sandwich s : allSandwiches) {
                    System.out.println("Sandwich: " + s.name +
                            " | Proteins: " + s.proteins +
                            " | Veggies: " + s.veggies +
                            " | Cheeses: " + s.cheeses +
                            " | Sauces: " + s.sauces);
                }

                // Clear UI
                requireActivity().runOnUiThread(() -> {
                    sandwichNameInput.setText("");
                    proteinContainer.removeAllViews();
                    veggieContainer.removeAllViews();
                    cheeseContainer.removeAllViews();
                    sauceContainer.removeAllViews();
                });
            }).start();
        });

        // Go Back
        goBackButton.setOnClickListener(v -> {
            if (getActivity() != null) getActivity().getSupportFragmentManager().popBackStack();
        });

        return view;
    }

    // Helper function to add ingredients to list
    private View.OnClickListener createAddListener(LinearLayout container, EditText input) {
        return v -> {
            String text = input.getText().toString().trim();
            if (!text.isEmpty()) {
                TextView tv = new TextView(requireContext());
                tv.setText(text);
                container.addView(tv);
                input.setText("");
            }
        };
    }

    // Extract ingredients from LinearLayout
    private List<String> getIngredientsFromContainer(LinearLayout container) {
        List<String> ingredients = new ArrayList<>();
        for (int i = 0; i < container.getChildCount(); i++) {
            View child = container.getChildAt(i);
            if (child instanceof TextView) {
                ingredients.add(((TextView) child).getText().toString());
            }
        }
        return ingredients;
    }
}
