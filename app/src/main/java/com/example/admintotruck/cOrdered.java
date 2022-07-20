package com.example.admintotruck;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

public class cOrdered extends AppCompatActivity {


    private ListView listview;
    RecyclerView recyclerView;
    SubAdapter subAdapter;
    DatabaseReference reference;
    String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
    private DatabaseReference database;
    TextView total1;
    Button confirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cordered);

        Intent intent = this.getIntent();



        String Orders1 = intent.getStringExtra("Orders1");

        recyclerView = (RecyclerView) findViewById(R.id.Oitems);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        confirm = findViewById(R.id.confirm);



        FirebaseRecyclerOptions<Ordered> options =

                new FirebaseRecyclerOptions.Builder<Ordered>()
                        .setQuery(FirebaseDatabase.getInstance().getReference("Accounts").child(uid).child("Customer_Orders").child(Orders1).child("OrderedItems"), Ordered.class)
                        .build();

        database = FirebaseDatabase.getInstance().getReference("Accounts").child(uid).child("Customer_Orders").child(Orders1).child("OrderedItems");

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int t1 = 0;

                for (DataSnapshot ds : snapshot.getChildren()) {
                    Map<String, Object> map = (Map<String, Object>) ds.getValue();
                    Object sub = map.get("subtotal");
                    int subt1 = Integer.parseInt(String.valueOf(sub));
                    t1 += subt1;
                    total1.setText(String.valueOf(t1));


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        subAdapter = new SubAdapter(options);
        recyclerView.setAdapter(subAdapter);
        recyclerView.setItemAnimator(null);

        total1 = findViewById(R.id.Total1);

        listview = findViewById(R.id.listview1);

        ArrayList<String> list = new ArrayList<>();
        ArrayAdapter adapter = new ArrayAdapter<String>(this,R.layout.customer_order_address, list);
        listview.setAdapter(adapter);

        reference = FirebaseDatabase.getInstance().getReference("Accounts").child(uid).child("Customer_Orders").child(Orders1).child("Address");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    list.add(snapshot.getValue().toString());


                }
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatabaseReference fromPath = FirebaseDatabase.getInstance().getReference("Accounts").child(uid).child("Customer_Orders").child(Orders1);
                fromPath.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        fromPath.child(snapshot.getKey());
                        FirebaseDatabase.getInstance().getReference("Accounts").child(uid).child("History").push()
                                .setValue(snapshot.getValue(), new DatabaseReference.CompletionListener() {
                                    @Override
                                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                        if (error == null) {


                                            Toast.makeText(cOrdered.this, "The Order has been Moved to History", Toast.LENGTH_SHORT).show();
                                            fromPath.setValue(null);
                                            startActivity(new Intent(getApplicationContext(), Customer_Orders.class));


                                        } else {
                                            Toast.makeText(cOrdered.this, "Error Please Try Again Later", Toast.LENGTH_SHORT).show();
                                        }

                                    }

                                });


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(cOrdered.this, "Error!!!", Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
        subAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        subAdapter.stopListening();
    }

}