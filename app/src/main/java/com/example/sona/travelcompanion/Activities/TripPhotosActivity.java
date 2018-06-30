package com.example.sona.travelcompanion.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.sona.travelcompanion.Adapters.TripPhotosAdapter;
import com.example.sona.travelcompanion.Pojos.TripPhotosElements;
import com.example.sona.travelcompanion.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class TripPhotosActivity extends AppCompatActivity {

    RecyclerView rvTripPhotos;
    String tripId;
    ArrayList<TripPhotosElements> allPhotos;
    FirebaseUser firebaseUser;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_photos);

        Intent intentWhoCreatedthis = getIntent();
        tripId = intentWhoCreatedthis.getStringExtra("tripId");

        allPhotos = new ArrayList<>();

        rvTripPhotos = findViewById(R.id.rvTripPhotos);
        rvTripPhotos.setLayoutManager(new GridLayoutManager(this, 1));

        getAllPhotosAndSetAdpater();
    }

    void getAllPhotosAndSetAdpater() {

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference()
                .child("users").child(firebaseUser.getUid()).child("photos").child(tripId);

        final TripPhotosAdapter tripPhotosAdapter = new TripPhotosAdapter(allPhotos);
        rvTripPhotos.setAdapter(tripPhotosAdapter);
        allPhotos.clear();
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()) {
                    //Log.d("pikachu", "onDataChange: yayeeee"+dataSnapshot1.getValue().toString());
                    String photoUrl = dataSnapshot1.child("photoUrl").getValue().toString();
                    String caption = dataSnapshot1.child("caption").getValue().toString();
                    TripPhotosElements oneEle = new TripPhotosElements(photoUrl, caption);
                    allPhotos.add(oneEle);
                    tripPhotosAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
