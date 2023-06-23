package com.example.colorapp;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
public class MainActivity extends AppCompatActivity {
    private MainViewModel viewModel;
    private DataListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        FloatingActionButton fab = findViewById(R.id.fab);

        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        adapter = new DataListAdapter();

        // Set up GridLayoutManager with two columns
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        viewModel.getDataList().observe(this, data -> {
            adapter.setData(data);
            adapter.notifyDataSetChanged();
        });

        fab.setOnClickListener(view -> {
            // Create an AlertDialog for input
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("Add Data");

            // Set up the layout for the dialog
            LinearLayout layout = new LinearLayout(MainActivity.this);
            layout.setOrientation(LinearLayout.VERTICAL);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(40, 0, 40, 0);

            // Create EditText for color hash code input
            EditText colorEditText = new EditText(MainActivity.this);
            colorEditText.setHint("Color Hash Code");
            layout.addView(colorEditText, params);

            // Create EditText for date input
            EditText dateEditText = new EditText(MainActivity.this);
            dateEditText.setHint("Date");
            layout.addView(dateEditText, params);

            builder.setView(layout);

            // Set up the buttons for saving or canceling the input
            builder.setPositiveButton("Save", (dialogInterface, i) -> {
                String colorHashCode = colorEditText.getText().toString();
                String date = dateEditText.getText().toString();

                if (!TextUtils.isEmpty(colorHashCode) && !TextUtils.isEmpty(date)) {
                    viewModel.insertData(colorHashCode, date);
                } else {
                    Toast.makeText(MainActivity.this, "Please enter both color and date.", Toast.LENGTH_SHORT).show();
                }
            });

            builder.setNegativeButton("Cancel", (dialogInterface, i) -> {
                dialogInterface.dismiss();
            });

            AlertDialog dialog = builder.create();
            dialog.show();
        });
    }
}
