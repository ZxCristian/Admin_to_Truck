package com.example.admintotruck;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.admintotruck.Utility.Network;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class create extends AppCompatActivity {

    Network network = new Network();
    EditText fullname,em,pass;
    FirebaseAuth Auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create);


        fullname= findViewById(R.id.fullname);
        em = findViewById(R.id.em);
        pass = findViewById(R.id.pass);

        Auth=FirebaseAuth.getInstance();

    }


    public void Signup(View view) {
        String n,e,p;


        n=fullname.getText().toString().trim();
        e=em.getText().toString().trim();
        p=pass.getText().toString().trim();




        if(n.isEmpty()) {
            fullname.setError("Full Name is Required");
            fullname.requestFocus();
            return;
        }
        if(e.isEmpty()) {
            em.setError("Email is Required");
            em.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(e).matches()){
            em.setError("Please Provide a valid Email");
            em.requestFocus();
            return;
        }
        if(p.isEmpty()) {
            pass.setError("Password is Required");
            pass.requestFocus();
            return;
        }
        if(p.length() < 6){
            pass.setError("Minimum Password Length should be 6 Characters");
            pass.requestFocus();
            return;
        }

        Auth.createUserWithEmailAndPassword(e, p)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()){
                            Account account= new Account(n,e);

                            FirebaseDatabase.getInstance().getReference("Accounts")
                                     .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                     .setValue(account).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if (task.isSuccessful()){
                                        Toast.makeText(create.this,"Registration is Successful.", Toast.LENGTH_LONG).show();
                                        Intent intent= new Intent(getApplicationContext(), MainActivity.class);
                                        startActivity(intent);
                                    }else {
                                        Toast.makeText(create.this,"Registration Failed. Please Try Again Later.", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                        }



                    }
                });
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