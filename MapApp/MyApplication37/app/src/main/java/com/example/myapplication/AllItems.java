package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class AllItems extends AppCompatActivity implements ClickListener {
    RecyclerView recyclerView;

    RecyclerViewAdapter recyclerViewAdapter;

    List<String> status, description, name, phone, date, location;
    double lat, lon;
    String loc;
    DbHelper db;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_items);

        Intent intent = getIntent();
        lat = Double.parseDouble(intent.getStringExtra("lat"));
        lon = Double.parseDouble(intent.getStringExtra("lon"));
        loc = intent.getStringExtra("loc");

        recyclerView = findViewById(R.id.recyclerView);
        status = new ArrayList<>();
        description = new ArrayList<>();
        recyclerViewAdapter = new RecyclerViewAdapter(this, status, description, this);
        db = new DbHelper(this);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        showData();

    }

    private void showData() {
        Cursor cursor = db.getData();

        if (cursor.getCount() == 0) {
            return;
        }

        else {
            while (cursor.moveToNext()) {
                //db.deleteUser(Integer.parseInt(cursor.getString(0)));
                status.add(cursor.getString(6));
                description.add(cursor.getString(2));
            }
        }
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("geo: " + lat + ", " + lon + "q=" + loc));
        intent.setPackage("com.google.android.apps.maps");
        startActivity(intent);
    }
}