package com.example.periodictable; //  IMPORTANT: Change this to your package name!

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.text.Html;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class MainActivity extends Activity {

    private GridLayout periodicTableGrid;
    private TextView formulaTextView;
    private TextView totalWeightTextView;
    private TextView selectedElementsTextView;
    private Button clearButton;

    private final Map<String, Double> elementWeights = new HashMap<>();
    private final Map<String, Integer> selectedElements = new HashMap<>(); // Symbol -> Count

    // Periodic Table Data (Symbol, Weight, Row, Column)
    private final String[][] periodicTableData = {
            {"H", "1.008", "1", "1"},  {"He", "4.0026", "1", "18"},
            {"Li", "6.94", "2", "1"},   {"Be", "9.0122", "2", "2"},
            {"B", "10.81", "2", "13"},  {"C", "12.011", "2", "14"},
            {"N", "14.007", "2", "15"},  {"O", "15.999", "2", "16"},
            {"F", "18.998", "2", "17"},  {"Ne", "20.180", "2", "18"},
            {"Na", "22.990", "3", "1"}, {"Mg", "24.305", "3", "2"},
            {"Al", "26.982", "3", "13"},{"Si", "28.085", "3", "14"},
            {"P", "30.974", "3", "15"},  {"S", "32.06", "3", "16"},
            {"Cl", "35.45", "3", "17"}, {"Ar", "39.948", "3", "18"},
            {"K", "39.098", "4", "1"},   {"Ca", "40.078", "4", "2"},
            {"Fe", "55.845", "4", "8"},  {"Cu", "63.546", "4", "11"},
            {"Zn", "65.38", "4", "12"}, {"Br", "79.904", "4", "17"},
            {"Ag", "107.87", "5", "11"},{"I", "126.90", "5", "17"},
            {"Au", "196.97", "6", "11"},{"Hg", "200.59", "6", "12"},
            {"Pb", "207.2", "6", "14"},
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // Load the layout

        // Initialize UI elements
        periodicTableGrid = findViewById(R.id.periodicTableGrid);
        formulaTextView = findViewById(R.id.formulaTextView);
        totalWeightTextView = findViewById(R.id.totalWeightTextView);
        selectedElementsTextView = findViewById(R.id.selectedElementsTextView);
        clearButton = findViewById(R.id.clearButton);


        // Populate elementWeights map
        for (String[] element : periodicTableData) {
            elementWeights.put(element[0], Double.parseDouble(element[1]));
        }

        // Create the periodic table grid
        createPeriodicTable();

        // Set up the clear button (using anonymous inner class)
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearSelection();
            }
        });
    }
    private void createPeriodicTable() {
      int maxRow = 0;
      int maxCol = 0;

        for (String[] element : periodicTableData) {
          if (Integer.parseInt(element[2])> maxRow) maxRow = Integer.parseInt(element[2]);
          if (Integer.parseInt(element[3]) > maxCol) maxCol = Integer.parseInt(element[3]);
        }
        periodicTableGrid.setRowCount(maxRow);
        periodicTableGrid.setColumnCount(maxCol);

        // Create placeholder buttons for all cells.
        for (int row = 0; row < maxRow; row++) {
            for (int col = 0; col < maxCol; col++) {
                Button placeholder = new Button(this);
                placeholder.setVisibility(View.INVISIBLE); // Initially invisible
                GridLayout.LayoutParams params = new GridLayout.LayoutParams();
                params.rowSpec = GridLayout.spec(row);
                params.columnSpec = GridLayout.spec(col);
                placeholder.setLayoutParams(params);
                periodicTableGrid.addView(placeholder);
            }
        }

        // Create buttons for actual elements
      for (String[] elementData : periodicTableData) {
            String symbol = elementData[0];
            double weight = Double.parseDouble(elementData[1]);
            int row = Integer.parseInt(elementData[2]) - 1; // Adjust for 0-based indexing
            int col = Integer.parseInt(elementData[3]) - 1;

            Button elementButton = new Button(this);
            elementButton.setText(String.format(Locale.US, "%s\n%.2f", symbol, weight));
            elementButton.setTag(symbol); // Store the symbol as a tag

            //Use anonymous inner class instead of lambda.
            elementButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onElementClick(view);
                }
            });


            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.rowSpec = GridLayout.spec(row); // Set row
            params.columnSpec = GridLayout.spec(col); //Set column
            elementButton.setLayoutParams(params);
            // Find the correct child index.
            int childIndex = row * maxCol + col;
            periodicTableGrid.removeViewAt(childIndex);
            periodicTableGrid.addView(elementButton, childIndex);
          elementButton.setVisibility(View.VISIBLE);

        }
    }
  public void onElementClick(View view) {
    String symbol = (String) view.getTag();
    if (symbol != null) {
        if (selectedElements.containsKey(symbol)) {
            // Simulate Ctrl key press by checking if the button is still pressed
            // This is a workaround, as detecting actual Ctrl key press on Android is tricky
            if (view.isPressed()) { // Use isPressed directly
                selectedElements.put(symbol, selectedElements.get(symbol) - 1);
                if (selectedElements.get(symbol) <= 0) {
                    selectedElements.remove(symbol);
                    view.setBackgroundColor(0xFFFFFFFF); // Reset to default
                }
            } else {
                selectedElements.put(symbol, selectedElements.get(symbol) + 1);
                view.setBackgroundColor(0xFFAAFFAA);
            }
        } else {
            selectedElements.put(symbol, 1);
            view.setBackgroundColor(0xFFAAFFAA);
        }
        updateDisplay();
    } else {
        Toast.makeText(this, "No symbol!", Toast.LENGTH_SHORT).show();
    }
}


    private void updateDisplay() {
        StringBuilder formula = new StringBuilder();
        StringBuilder selectedElementsText = new StringBuilder();
        double totalWeight = 0;

        for (Map.Entry<String, Integer> entry : selectedElements.entrySet()) {
            String symbol = entry.getKey();
            int count = entry.getValue();
            formula.append(symbol);
            if (count > 1) {
                formula.append(String.format(Locale.US, "<sub>%d</sub>", count)); // Use HTML for subscript
            }
            selectedElementsText.append(String.format(Locale.US, "%s x %d, ", symbol, count));
            totalWeight += elementWeights.get(symbol) * count;
        }

        //Use Html.fromHtml with the correct parameters for your android version.
        formulaTextView.setText(Html.fromHtml(formula.toString(), Html.FROM_HTML_MODE_LEGACY));
        totalWeightTextView.setText(String.format(Locale.US, "Total Weight: %.2f", totalWeight));
        selectedElementsTextView.setText(selectedElementsText.toString());
    }

    private void clearSelection() {
        selectedElements.clear();
        updateDisplay();
        // Reset button colors
        for (int i = 0; i < periodicTableGrid.getChildCount(); i++) {
            View child = periodicTableGrid.getChildAt(i);
            if (child instanceof Button) {
                child.setBackgroundColor(0xFFFFFFFF); // or your default button color
            }
        }
    }
}

