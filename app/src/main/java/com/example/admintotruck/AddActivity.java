package com.example.admintotruck;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.admintotruck.Utility.Network;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class AddActivity extends AppCompatActivity {

    Network network = new Network();
    EditText name,desc,price,imageurl;
    Button btnAdd, btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        name = (EditText) findViewById(R.id.txtName);
        desc = (EditText) findViewById(R.id.txtDesc);
        price = (EditText) findViewById(R.id.txtPrice);
        imageurl = (EditText)findViewById(R.id.txtUrl);

        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnBack = (Button) findViewById(R.id.btnBack);



        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                insertData();
                clearAll();
                startActivity(new Intent(getApplicationContext(),shop.class));

            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),shop.class));
            }
        });

    }


    private void insertData() {
        Map<String, Object> map = new HashMap<>();
        map.put("name", name.getText().toString());
        map.put("desc", desc.getText().toString());
        map.put("price", price.getText().toString());
        map.put("imageurl", imageurl.getText().toString());



        FirebaseDatabase.getInstance().getReference().child("Items").push()
                .setValue(map)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(AddActivity.this, "The Data Inserted Sucessfully", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AddActivity.this, "Error While Inserting Data", Toast.LENGTH_SHORT).show();
                    }
                });
    }
        private void clearAll() {
            name.setText("");
            desc.setText("");
            price.setText("");
            imageurl.setText("");
        }

    @Override
    protected void onStart() {
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(network, filter);
        super.onStart();
    }

    @Override
    protected void onStop() {
        unregisterReceiver(network);
        super.onStop();
    }
}
