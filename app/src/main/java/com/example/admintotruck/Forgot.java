package com.example.admintotruck;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.admintotruck.Utility.Network;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class Forgot extends AppCompatActivity {

    Network network = new Network();
    EditText EmailAddress;
    FirebaseAuth Auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot);

        EmailAddress = findViewById(R.id.EmailAddress);
        Auth = FirebaseAuth.getInstance();





    }

    public void Forgot(View view) {
        String r;
        r=EmailAddress.getText().toString().trim();


        if(r.isEmpty()) {
            EmailAddress.setError("Email is Required");
            EmailAddress.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(r).matches()) {
            EmailAddress.setError("Please Provide a valid Email");
            EmailAddress.requestFocus();
            return;
        }
        Auth.sendPasswordResetEmail(r)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(Forgot.this,"Please Check your Email to Reset Password.", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(Forgot.this, MainActivity.class);
                            startActivity(intent);
                        }else{
                            Toast.makeText(Forgot.this,"Please Check Your Email Properly and Try Again.", Toast.LENGTH_LONG).show();
                        }

                    }
                });



    }




    public void Login(View view) {
        Intent intent = new Intent(Forgot.this, MainActivity.class);
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