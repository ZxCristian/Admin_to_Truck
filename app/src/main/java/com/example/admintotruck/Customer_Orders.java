package com.example.admintotruck;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.admintotruck.Utility.Network;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Customer_Orders extends AppCompatActivity {

    Network network = new Network();
    private ListView listView;
    String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
    DatabaseReference reference;
    FirebaseAuth FbAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_costumer_orders);


        listView = findViewById(R.id.listView);

        ArrayList<String> list = new ArrayList<>();
        ArrayAdapter adapter = new ArrayAdapter<String>(this,R.layout.cus_ord_list, list);
        listView.setAdapter(adapter);





        reference = FirebaseDatabase.getInstance().getReference("Accounts").child(uid).child("Customer_Orders");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    list.add(snapshot.getKey());


                }
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        listView.setClickable(true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                Intent intent = new Intent (Customer_Orders.this,cOrdered.class);
                intent.putExtra("Orders1",listView.getItemAtPosition(position).toString());
                startActivity(intent);
            }
        });




        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId() == R.id.nav_home){
                    Toast.makeText(Customer_Orders.this, "shop", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), shop.class));

                }

                else if(item.getItemId() == R.id.nav_gallery) {
                    Toast.makeText(Customer_Orders.this, "Costumer Orders", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), Customer_Orders.class));
                }

                else if(item.getItemId() == R.id.nav_slideshow) {
                    Toast.makeText(Customer_Orders.this, "Order History", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), Admin_History.class));
                }
                else if(item.getItemId() == R.id.logout) {
                    Toast.makeText(Customer_Orders.this, "Login Page", Toast.LENGTH_SHORT).show();
                    FbAuth.getInstance().signOut();
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                }
                DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
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


