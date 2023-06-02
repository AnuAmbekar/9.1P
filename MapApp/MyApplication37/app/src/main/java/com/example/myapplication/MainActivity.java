package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText nameEdit, phoneEdit, descriptionEdit, dateEdit, locationEdit;
    TextView textView3;
    private static final String TAG = "MainActivity";
    CheckBox found, lost;
    Button coordsBtn, mapBtn;
    String name;
    String phone;
    String description;
    String date;
    String location;
    String status;
    Double lat, lon;
    String loc;
    String msg;
    DbHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        coordsBtn = findViewById(R.id.coordsBtn);
        mapBtn = findViewById(R.id.mapBtn);
        lost = findViewById(R.id.lost);
        found = findViewById(R.id.found);
        nameEdit = findViewById(R.id.nameEdit);
        phoneEdit = findViewById(R.id.phoneEdit);
        descriptionEdit = findViewById(R.id.descriptionEdit);
        dateEdit = findViewById(R.id.dateEdit);
        locationEdit = findViewById(R.id.locationEdit);
        textView3 = findViewById(R.id.textView3);


        db = new DbHelper(this);
        Geocoder geocoder = new Geocoder(this);

        lost.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                status = "Lost";
                System.out.println(status);

            }
        });

        found.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                status = "Found";
                System.out.println(status);

            }
        });


        coordsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = nameEdit.getText().toString();
                phone = phoneEdit.getText().toString();
                description = descriptionEdit.getText().toString();
                date = dateEdit.getText().toString();
                location = locationEdit.getText().toString();

                try {
                    List<Address> addresses = geocoder.getFromLocationName(location, 1);
                    if (geocoder != null) {
                        if (addresses != null && !addresses.isEmpty()) {
                            Address address = addresses.get(0);
                            Log.d(TAG, "Map: " + address);
                            lat = address.getLatitude();
                            lon = address.getLongitude();
                            Log.d(TAG, "Coordinates: " + lat + ", " + lon);
                            msg = "Coordinates: " + lat + ", " + lon;
                            textView3.setText(msg);

                            long insert = db.insertData(new User(name, phone, description, date, location, status, lat.toString(), lon.toString()));

                            if (insert > 0) {
                                Intent intent = new Intent(MainActivity.this, AllItems.class);
                                intent.putExtra("lat", lat.toString());
                                intent.putExtra("lon", lon.toString());
                                intent.putExtra("loc", location);
                                startActivity(intent);
                                Toast.makeText(MainActivity.this, "This insert has worked", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(MainActivity.this, "This insert has not worked", Toast.LENGTH_SHORT).show();

                            }
                        }


                    }
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }
}
