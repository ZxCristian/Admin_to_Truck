package com.example.admintotruck;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.admintotruck.Utility.Network;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener{

    Network network = new Network();

    EditText username,password;
    SignInButton signInButton;
    private GoogleApiClient googleApiClient;
    private static final int SIGN_IN = 1;
    FirebaseAuth Auth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);

        Auth=FirebaseAuth.getInstance();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();

        googleApiClient = new GoogleApiClient.Builder(this).enableAutoManage(this, this)
                .addApi(com.google.android.gms.auth.api.Auth.GOOGLE_SIGN_IN_API, gso).build();

        signInButton = findViewById(R.id.google_sign_in);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = com.google.android.gms.auth.api.Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
                startActivityForResult(intent, SIGN_IN);
            }
        });
    }

    public void goToCreate(View view) {
        Intent intent= new Intent(MainActivity.this, create.class);
        startActivity(intent);
    }

    public void goToLogin(View view) {
        String UName,Pword;

        UName = username.getText().toString().trim();
        Pword = password.getText().toString().trim();

        if(UName.isEmpty()) {
            username.setError("Email is Required");
            username.requestFocus();
            return;
        }

        if(Pword.isEmpty()) {
            password.setError("Password is Required");
            password.requestFocus();
            return;
        }

        Auth.signInWithEmailAndPassword(UName, Pword)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Intent intent= new Intent(getApplicationContext(), Customer_Orders.class);
                            startActivity(intent);
                        }else{
                            Toast.makeText(MainActivity.this, "Invalid Password/Username", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    public void goToForgot(View view) {
        Intent intent= new Intent(MainActivity.this, Forgot.class);
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

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SIGN_IN) {
            GoogleSignInResult result = com.google.android.gms.auth.api.Auth.GoogleSignInApi.getSignInResultFromIntent(data);

            if (result.isSuccess()) {
                startActivity(new Intent(MainActivity.this, Customer_Orders.class));
            }
        }
    }
}