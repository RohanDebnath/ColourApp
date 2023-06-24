package com.example.colorapp;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private MainViewModel viewModel;
    private DataListAdapter adapter;
    private TextView tvPendingCount;
    private DatabaseReference databaseRef;
    private int count = 0; // Count of newly added data

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        ImageButton fab = findViewById(R.id.fab);

        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        adapter = new DataListAdapter();

        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        viewModel.getDataList().observe(this, new Observer<List<DataModel>>() {
            @Override
            public void onChanged(List<DataModel> data) {
                adapter.setData(data);
                adapter.notifyDataSetChanged();

                updatePendingCount(count);
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddDataDialog();
            }
        });

        // Initialize Firebase Database reference
        databaseRef = FirebaseDatabase.getInstance().getReference("data");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_sync) {
            syncDataWithFirebase();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showAddDataDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Add Data");

        LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
        View dialogView = inflater.inflate(R.layout.dialog_add_data, null);
        builder.setView(dialogView);

        EditText etColor = dialogView.findViewById(R.id.etColor);
        TextView tvDate = dialogView.findViewById(R.id.tvDate); // TextView to display the selected date
        Button btnDatePicker = dialogView.findViewById(R.id.etDate); // Button to open the date picker dialog

        // Set up calendar dialog
        final Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        btnDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Show DatePickerDialog
                DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                // Set the selected date in the TextView
                                String selectedDate = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                                tvDate.setText(selectedDate);
                            }
                        }, year, month, day);
                datePickerDialog.show();
            }
        });

        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String color = etColor.getText().toString();
                String date = tvDate.getText().toString();

                if (!TextUtils.isEmpty(color) && !TextUtils.isEmpty(date)) {
                    viewModel.insertData(color, date);
                    count++;
                    updatePendingCount(count);
                } else {
                    Toast.makeText(MainActivity.this, "Please enter both color and date.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }


    private void syncDataWithFirebase() {
        List<DataModel> dataList = viewModel.getDataList().getValue();
        if (dataList != null && !dataList.isEmpty()) {

            for (DataModel data : dataList) {
                if (!data.isSynced()) {
                    // Upload data to Firebase
                    String key = databaseRef.push().getKey();
                    databaseRef.child(key).setValue(data);

                    // Mark data as synced
                    data.setSynced(true);
                    viewModel.updateData(data); // Update the data in the local database
                }
            }
            Toast.makeText(this, "Data uploaded to Firebase", Toast.LENGTH_SHORT).show();

            // Reset the counts
            count=0;
            updatePendingCount(count);
        } else {
            Toast.makeText(this, "No data to upload", Toast.LENGTH_SHORT).show();
        }
    }
    private void updatePendingCount(int count) {
        tvPendingCount = findViewById(R.id.pendingCountTextView);
        if (tvPendingCount != null) {
            if (count > 0) {
                tvPendingCount.setVisibility(View.VISIBLE);
                tvPendingCount.setText(String.valueOf(count));
            } else {
                tvPendingCount.setVisibility(View.GONE);
            }
        }
    }

}