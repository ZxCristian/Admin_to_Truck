package com.example.admintotruck;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
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

public class cOrder_History extends AppCompatActivity {


    private ListView listview;
    RecyclerView recyclerView;
    SubAdapter1 subAdapter1;
    DatabaseReference reference;
    String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
    private DatabaseReference database;
    TextView total1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_corder_history);

        Intent intent = this.getIntent();

        String Orders1 = intent.getStringExtra("Orders1");

        recyclerView = (RecyclerView)findViewById(R.id.orderedhistory);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<History> options =

                new FirebaseRecyclerOptions.Builder<History>()
                        .setQuery(FirebaseDatabase.getInstance().getReference("Accounts").child(uid).child("History").child(Orders1).child("OrderedItems"), History.class)
                        .build();

        database = FirebaseDatabase.getInstance().getReference("Accounts").child(uid).child("History").child(Orders1).child("OrderedItems");

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


        subAdapter1 = new SubAdapter1(options);
        recyclerView.setAdapter(subAdapter1);
       recyclerView.setItemAnimator(null);

        total1 = findViewById(R.id.Total1);

        listview = findViewById(R.id.listview2);

        ArrayList<String> list = new ArrayList<>();
        ArrayAdapter adapter = new ArrayAdapter<String>(this,R.layout.customer_order_address_history, list);
        listview.setAdapter(adapter);

        reference = FirebaseDatabase.getInstance().getReference("Accounts").child(uid).child("History").child(Orders1).child("Address");
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


    }
    @Override
    protected void onStart() {
        super.onStart();
        subAdapter1.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        subAdapter1.stopListening();
    }

}