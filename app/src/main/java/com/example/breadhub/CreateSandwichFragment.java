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

        // Fetching Widgets
        Spinner spinner = view.findViewById(R.id.sandwichTypeSpinner);
        Button addRecipeBtn = view.findViewById(R.id.addRecipeBtn);
        Button goBackButton = view.findViewById(R.id.goBackBtn);
        EditText sandwichNameInput = view.findViewById(R.id.sandwichNameInput);
        EditText sandwichIngredientsInput = view.findViewById(R.id.sandwichIngredientsInput);

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

        // Add database
        AppDatabase db = AppDatabase.getInstance(requireContext());

        // Ensure default sandwich types exist
        new Thread(() -> {
            List<SandwichType> types = db.appDao().getAllTypes();
            if (types.isEmpty()) {
                String[] defaultTypes = {"Sandwich", "Subs/Hoagies", "Bánh mì", "Panini", "Burger"};
                for (String tName : defaultTypes) {
                    SandwichType t = new SandwichType(tName);
                    t.name = tName;
                    db.appDao().insertType(t);
                }
                types = db.appDao().getAllTypes();
            }

            // Update spinner on main thread
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

        // TODO: Make buttons add ingredients to databse
        // Add recipe
        addRecipeBtn.setOnClickListener(v -> {
            if (getActivity() != null){
                System.out.println("TEKSLJDSFKL");
            }
        });


        addProteinBtn.setOnClickListener(createAddListener(proteinContainer, proteinInput));
        addVeggieBtn.setOnClickListener(createAddListener(veggieContainer, veggieInput));
        addCheeseBtn.setOnClickListener(createAddListener(cheeseContainer, cheeseInput));
        addSauceBtn.setOnClickListener(createAddListener(sauceContainer, sauceInput));


        // Go back
        goBackButton.setOnClickListener(v -> {
            if (getActivity() != null) {
                // Pop the current fragment off the back stack to return to TitleFragment
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        return view;
    }

    // Helper function: Sets the textboxes to ""
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
}

