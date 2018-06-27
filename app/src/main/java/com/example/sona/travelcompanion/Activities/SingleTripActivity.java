package com.example.sona.travelcompanion.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sona.travelcompanion.Adapters.MyPlansAdapter;
import com.example.sona.travelcompanion.Adapters.SingleTripAdapter;
import com.example.sona.travelcompanion.Pojos.MyPlansElements;
import com.example.sona.travelcompanion.Pojos.SingleTripElement;
import com.example.sona.travelcompanion.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class SingleTripActivity extends AppCompatActivity {

    ArrayList<SingleTripElement> allSingleTripElements = new ArrayList<>();
    SingleTripAdapter singleTripAdapter;

    TextView tvFromDateDisplay;
    TextView tvToDateDisplay;
    Button btnAddDeatils;
    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_trip);

        Intent intentWhoCreatedThis = getIntent();
        final String tripName = intentWhoCreatedThis.getStringExtra("tripName");

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        tvFromDateDisplay = findViewById(R.id.tvFromDateDisplay);
        tvToDateDisplay = findViewById(R.id.tvToDateDisplay);
        btnAddDeatils = findViewById(R.id.btnAddDeatils);

        RecyclerView rvSingleTripEle = findViewById(R.id.rvSingleTripEle);
        rvSingleTripEle.setLayoutManager(new GridLayoutManager(this, 1));
        singleTripAdapter = new SingleTripAdapter(allSingleTripElements);
        rvSingleTripEle.setAdapter(singleTripAdapter);


        DatabaseReference dbReference = FirebaseDatabase.getInstance().getReference().child("users")
                .child(firebaseUser.getUid()).child(tripName);
        dbReference.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                allSingleTripElements.clear();

                for(DataSnapshot dataSnapshot1: dataSnapshot.child("fromdate").getChildren()) {
                    String fromDate = dataSnapshot1.getValue().toString();
                    tvFromDateDisplay.setText("From: " + fromDate);
                }
                for(DataSnapshot dataSnapshot1: dataSnapshot.child("todate").getChildren()) {
                    String toDate = dataSnapshot1.getValue().toString();
                    tvToDateDisplay.setText("To: " + toDate);
                }

                //setting all emlements for the recycler view
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()) {
                    String title = dataSnapshot1.getKey();
                    if(title.equals("todate") || title.equals("fromdate"))
                        continue;
                    String item = "";
                    for(DataSnapshot dataSnapshot2: dataSnapshot1.getChildren()) {
                        if(item.length() != 0)
                            item = item + ", ";
                        String temp1 = dataSnapshot2.getValue().toString();
                        String firstChar = temp1.charAt(0)+"";
                        item = item + firstChar.toUpperCase()+temp1.substring(1);
                    }
                    SingleTripElement temp = new SingleTripElement(title, item);
                    allSingleTripElements.add(temp);
                    singleTripAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        btnAddDeatils.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SingleTripActivity.this, SetDestinationActivity.class);
                i.putExtra("tripName", tripName);
                startActivity(i);
            }
        });

    }
}
