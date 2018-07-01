package com.example.sona.travelcompanion.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.app.AlertDialog;
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
    TextView tvToDateDisplay, tvDisplayTitle;
    Button btnAddDeatils, btnTripPhotos;
    FirebaseUser firebaseUser;
    DatabaseReference dbReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_trip);

        Intent intentWhoCreatedThis = getIntent();
        final String tripId = intentWhoCreatedThis.getStringExtra("tripId");

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        tvFromDateDisplay = findViewById(R.id.tvFromDateDisplay);
        tvToDateDisplay = findViewById(R.id.tvToDateDisplay);
        btnAddDeatils = findViewById(R.id.btnAddDeatils);
        tvDisplayTitle = findViewById(R.id.tvDisplayTitle);
        btnTripPhotos = findViewById(R.id.btnTripPhotos);

        RecyclerView rvSingleTripEle = findViewById(R.id.rvSingleTripEle);
        rvSingleTripEle.setLayoutManager(new GridLayoutManager(this, 1));
        singleTripAdapter = new SingleTripAdapter(allSingleTripElements);
        rvSingleTripEle.setAdapter(singleTripAdapter);


         dbReference = FirebaseDatabase.getInstance().getReference().child("users")
                .child(firebaseUser.getUid()).child("alltrips").child(tripId);

         callListener();





        btnAddDeatils.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // setup the alert builder
                AlertDialog.Builder builder = new AlertDialog.Builder(SingleTripActivity.this);
                builder.setTitle("Choose an Item");

                // add a list
                String[] menu = {"Destination", "Flight", "Hotel", "Places To Visit",
                        "Photos", "Finance", "Note"};
                builder.setItems(menu, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                Intent i = new Intent(SingleTripActivity.this, SetDestinationActivity.class);
                                i.putExtra("tripId", tripId);
                                startActivity(i);
                                break;
                            case 1: takeFlightInput();
                                break;
                            case 2:
                                Intent ii = new Intent(SingleTripActivity.this,
                                        SearchHotelsActivity.class);
                                ii.putExtra("tripId", tripId);
                                ii.putExtra("placeUnder", "hotel");
                                startActivity(ii);
                                break;
                            case 3:
                                Intent iii = new Intent(SingleTripActivity.this,
                                        SearchPlacesToVisitActivity.class);
                                iii.putExtra("tripId", tripId);
                                iii.putExtra("placeUnder", "places to visit");
                                startActivity(iii);
                                break;
                            case 4:
                                Intent i4 = new Intent(SingleTripActivity.this, UploadPhotoActivity.class);
                                i4.putExtra("tripId", tripId);
                                startActivity(i4);
                                break;
                        }
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        btnTripPhotos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SingleTripActivity.this, TripPhotosActivity.class);
                i.putExtra("tripId", tripId);
                startActivity(i);
            }
        });

    }

    void takeFlightInput() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enter Flight");

        // Set up the input
        final EditText input = new EditText(this);
        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        //input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String myFlight = input.getText().toString();
                if(myFlight.length() == 0) {
                    Toast.makeText(SingleTripActivity.this,
                            "Enter Valid Flight Number", Toast.LENGTH_SHORT).show();
                    input.setText("");
                } else {
                    dbReference.child("flight").push().setValue(myFlight);
                    callListener();
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    void callListener() {
        dbReference.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                allSingleTripElements.clear();

                String tripTitle = dataSnapshot.child("trip title").getValue().toString();
                String fromDate = dataSnapshot.child("fromdate").getValue().toString();
                String toDate = dataSnapshot.child("todate").getValue().toString();
                tvFromDateDisplay.setText("From: " + fromDate);
                tvToDateDisplay.setText("To: "+toDate);
                tvDisplayTitle.setText(tripTitle);


                //setting all elements for the recycler view
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()) {
                    String title = dataSnapshot1.getKey();
                    if(title.equals("todate") || title.equals("fromdate")
                            || title.equals("trip title"))
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
    }
}
