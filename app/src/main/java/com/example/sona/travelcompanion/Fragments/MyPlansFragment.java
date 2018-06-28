package com.example.sona.travelcompanion.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.sona.travelcompanion.Activities.SingleTripActivity;
import com.example.sona.travelcompanion.Adapters.MyPlansAdapter;
import com.example.sona.travelcompanion.Listeners.RecyclerViewItemClickListener;
import com.example.sona.travelcompanion.Pojos.MyPlansElements;
import com.example.sona.travelcompanion.R;
import com.example.sona.travelcompanion.Activities.TakeTripTitleActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class MyPlansFragment extends Fragment {


    ArrayList<MyPlansElements> myPlansElements = new ArrayList<>();
    MyPlansAdapter myPlansElementsMyPlansAdapter;

    public MyPlansFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View framentView = inflater.inflate(R.layout.fragment_my_plans, container, false);

        RecyclerView rvMyPlans = framentView.findViewById(R.id.rvMyPlans);
        rvMyPlans.setLayoutManager(new GridLayoutManager(getContext(), 1));
        myPlansElementsMyPlansAdapter = new MyPlansAdapter(myPlansElements);
        rvMyPlans.setAdapter(myPlansElementsMyPlansAdapter);

        refreshRV();
        myPlansElementsMyPlansAdapter.notifyDataSetChanged();

        rvMyPlans.addOnItemTouchListener(
                new RecyclerViewItemClickListener(container.getContext(), rvMyPlans ,new RecyclerViewItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        // do whatever
                        Intent i = new Intent(getActivity(), SingleTripActivity.class);
                        i.putExtra("tripName", myPlansElements.get(position).getTripTitle());
                        startActivity(i);
                    }

                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );

        Button btnAddTrip = framentView.findViewById(R.id.btnAddTrip);
        btnAddTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("pikachu", "onClick: Trying to start a new Activity Pikachu");
                Intent i =new Intent(getActivity(), TakeTripTitleActivity.class);
                startActivity(i);
            }
        });

        return framentView;
    }

    void refreshRV() {
        getAllElements();
    }

    void getAllElements() {
        myPlansElements.clear();
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference dbReference = FirebaseDatabase.getInstance().getReference().child("users")
                .child(firebaseUser.getUid());

        dbReference.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()) {
                    String title = dataSnapshot1.getKey();
                    String destination = "";
                    for (DataSnapshot dataSnapshot2: dataSnapshot1.child("destination").getChildren()) {
                        if(destination.length() != 0)
                            destination = destination + ", ";
                        String temp1 = dataSnapshot2.getValue().toString();
                        String firstChar = temp1.charAt(0)+"";
                        destination = destination + firstChar.toUpperCase()+temp1.substring(1);
                    }
                    String flight = "";
                    for (DataSnapshot dataSnapshot2: dataSnapshot1.child("flight").getChildren()) {
                        if(flight.length() !=0)
                            flight = ", ";
                        flight = flight + dataSnapshot2.getValue();
                    }
                    String hotel = "";
                    for (DataSnapshot dataSnapshot2: dataSnapshot1.child("hotel").getChildren()) {
                        if(hotel.length() != 0)
                            hotel = hotel + ", ";
                        hotel = hotel + dataSnapshot2.getValue();
                    }
                    MyPlansElements temp = new MyPlansElements(title);
                    if(destination.length() != 0)
                        temp.setDestination(destination);
                    if(hotel.length() != 0)
                        temp.setHotel(hotel);
                    if(flight.length() != 0)
                        temp.setFlight_train(flight);
                    myPlansElements.add(temp);
                    myPlansElementsMyPlansAdapter.notifyDataSetChanged();
                }
                Log.d("pikachu", "onDataChange: reached end of data change");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        Log.d("pikachu", "getAllElements: "+myPlansElements.size());
    }

}