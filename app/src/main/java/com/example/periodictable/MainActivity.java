package com.example.periodictable;

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
    // Row 1
    {"H", "1.008", "1", "1"}, {"He", "4.0026", "1", "18"},

    // Row 2
    {"Li", "6.94", "2", "1"}, {"Be", "9.0122", "2", "2"},
    {"B", "10.81", "2", "13"}, {"C", "12.011", "2", "14"},
    {"N", "14.007", "2", "15"}, {"O", "15.999", "2", "16"},
    {"F", "18.998", "2", "17"}, {"Ne", "20.180", "2", "18"},

    // Row 3
    {"Na", "22.990", "3", "1"}, {"Mg", "24.305", "3", "2"},
    {"Al", "26.982", "3", "13"}, {"Si", "28.085", "3", "14"},
    {"P", "30.974", "3", "15"}, {"S", "32.06", "3", "16"},
    {"Cl", "35.45", "3", "17"}, {"Ar", "39.948", "3", "18"},

    // Row 4
    {"K", "39.098", "4", "1"}, {"Ca", "40.078", "4", "2"},
    {"Sc", "44.956", "4", "3"}, {"Ti", "47.867", "4", "4"},
    {"V", "50.942", "4", "5"}, {"Cr", "51.996", "4", "6"},
    {"Mn", "54.938", "4", "7"}, {"Fe", "55.845", "4", "8"},
    {"Co", "58.933", "4", "9"}, {"Ni", "58.693", "4", "10"},
    {"Cu", "63.546", "4", "11"}, {"Zn", "65.38", "4", "12"},
    {"Ga", "69.723", "4", "13"}, {"Ge", "72.63", "4", "14"},
    {"As", "74.922", "4", "15"}, {"Se", "78.971", "4", "16"},
    {"Br", "79.904", "4", "17"}, {"Kr", "83.798", "4", "18"},

    // Row 5
    {"Rb", "85.468", "5", "1"}, {"Sr", "87.62", "5", "2"},
    {"Y", "88.906", "5", "3"},  {"Zr", "91.224", "5", "4"},
    {"Nb", "92.906", "5", "5"}, {"Mo", "95.95", "5", "6"},
    {"Tc", "98", "5", "7"}, {"Ru", "101.07", "5", "8"},
    {"Rh", "102.91", "5", "9"}, {"Pd", "106.42", "5", "10"},
    {"Ag", "107.87", "5", "11"}, {"Cd", "112.41", "5", "12"},
    {"In", "114.82", "5", "13"}, {"Sn", "118.71", "5", "14"},
    {"Sb", "121.76", "5", "15"}, {"Te", "127.6", "5", "16"},
    {"I", "126.90", "5", "17"}, {"Xe", "131.29", "5", "18"},

    // Row 6
    {"Cs", "132.91", "6", "1"}, {"Ba", "137.33", "6", "2"},
    // Lanthanides
    {"La", "138.91", "8", "3"},
    {"Ce", "140.12", "8", "4"}, {"Pr", "140.91", "8", "5"},
    {"Nd", "144.24", "8", "6"}, {"Pm", "145", "8", "7"},
    {"Sm", "150.36", "8", "8"}, {"Eu", "151.96", "8", "9"},
    {"Gd", "157.25", "8", "10"}, {"Tb", "158.93", "8", "11"},
    {"Dy", "162.50", "8", "12"}, {"Ho", "164.93", "8", "13"},
    {"Er", "167.26", "8", "14"}, {"Tm", "168.93", "8", "15"},
    {"Yb", "173.05", "8", "16"}, {"Lu", "174.97", "8", "17"},

    {"Hf", "178.49", "6", "4"}, {"Ta", "180.95", "6", "5"},
    {"W", "183.84", "6", "6"}, {"Re", "186.21", "6", "7"},
    {"Os", "190.23", "6", "8"}, {"Ir", "192.22", "6", "9"},
    {"Pt", "195.08", "6", "10"}, {"Au", "196.97", "6", "11"},
    {"Hg", "200.59", "6", "12"}, {"Tl", "204.38", "6", "13"},
    {"Pb", "207.2", "6", "14"}, {"Bi", "208.98", "6", "15"},
    {"Po", "209", "6", "16"}, {"At", "210", "6", "17"},
    {"Rn", "222", "6", "18"},

    // Row 7
    {"Fr", "223", "7", "1"}, {"Ra", "226", "7", "2"},
     // Actinides
    {"Ac", "227", "9", "3"},
    {"Th", "232.04", "9", "4"}, {"Pa", "231.04", "9", "5"},
    {"U", "238.03", "9", "6"}, {"Np", "237", "9", "7"},
    {"Pu", "244", "9", "8"}, {"Am", "243", "9", "9"},
    {"Cm", "247", "9", "10"}, {"Bk", "247", "9", "11"},
    {"Cf", "251", "9", "12"}, {"Es", "252", "9", "13"},
    {"Fm", "257", "9", "14"}, {"Md", "258", "9", "15"},
    {"No", "259", "9", "16"}, {"Lr", "266", "9", "17"},

    {"Rf", "267", "7", "4"}, {"Db", "268", "7", "5"},
    {"Sg", "269", "7", "6"}, {"Bh", "270", "7", "7"},
    {"Hs", "277", "7", "8"}, {"Mt", "278", "7", "9"},
    {"Ds", "281", "7", "10"}, {"Rg", "282", "7", "11"},
    {"Cn", "285", "7", "12"}, {"Nh", "286", "7", "13"},
    {"Fl", "289", "7", "14"}, {"Mc", "290", "7", "15"},
    {"Lv", "293", "7", "16"}, {"Ts", "294", "7", "17"},
    {"Og", "294", "7", "18"}
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

            // Set click listener for element selection
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
            // Add or increment the element count
            selectedElements.put(symbol, selectedElements.getOrDefault(symbol, 0) + 1);
            updateDisplay();

        } else {
            Toast.makeText(this, "No symbol!", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateDisplay() {
        StringBuilder formula = new StringBuilder();
        StringBuilder selectedElementsText = new StringBuilder();
        double totalWeight = 0;

        // Reset background color of ALL buttons
        for (int i = 0; i < periodicTableGrid.getChildCount(); i++) {
            View child = periodicTableGrid.getChildAt(i);
            if (child instanceof Button) {
                child.setBackgroundColor(0xFFFFFFFF); // Default (white or your theme's default)
            }
        }


        for (Map.Entry<String, Integer> entry : selectedElements.entrySet()) {
            String symbol = entry.getKey();
            int count = entry.getValue();
            formula.append(symbol);
            if (count > 1) {
                formula.append(String.format(Locale.US, "<sub>%d</sub>", count)); // Use HTML for subscript
            }
            selectedElementsText.append(String.format(Locale.US, "%s x %d, ", symbol, count));
            totalWeight += elementWeights.get(symbol) * count;

            // Set background color of SELECTED buttons
            for (int i = 0; i < periodicTableGrid.getChildCount(); i++) {
                View child = periodicTableGrid.getChildAt(i);
                if (child instanceof Button && symbol.equals(child.getTag())) {
                    child.setBackgroundColor(0xFFAAFFAA); // Light green for selected
                }
            }

        }

        formulaTextView.setText(Html.fromHtml(formula.toString(), Html.FROM_HTML_MODE_LEGACY));
        totalWeightTextView.setText(String.format(Locale.US, "Total Weight: %.2f", totalWeight));
        selectedElementsTextView.setText(selectedElementsText.toString());
    }

     private void clearSelection() {
        selectedElements.clear(); // Clear the selection
        updateDisplay(); // Update the display (this will also reset button colors)
    }

}
