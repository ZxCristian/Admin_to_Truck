package com.example.admintotruck;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Maintence extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.maintence);


    }

    public void goToLogin(View view) {
            Intent intent= new Intent(Maintence.this, MainActivity.class);
            startActivity(intent);
        }
    }
