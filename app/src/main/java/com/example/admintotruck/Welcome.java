package com.example.admintotruck;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;

import com.example.admintotruck.Utility.Network;

public class Welcome extends AppCompatActivity {
    Network network = new Network();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome);
    }

    public void Store(View view) {
        Intent intent= new Intent(Welcome.this, Maintence.class);
        startActivity(intent);
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