package com.example.sona.travelcompanion.Fragments;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.sona.travelcompanion.Adapters.MyPlansAdapter;
import com.example.sona.travelcompanion.Listeners.MyPlansItemClickListener;
import com.example.sona.travelcompanion.MainActivity;
import com.example.sona.travelcompanion.Pojos.MyPlansElements;
import com.example.sona.travelcompanion.R;
import com.example.sona.travelcompanion.TakeTripTitleActivity;
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

    public MyPlansFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View framentView = inflater.inflate(R.layout.fragment_my_plans, container, false);

        MyPlansElements temp = new MyPlansElements("title");
        temp.setDestination("Destination");
        temp.setHotel("Hotel");
        temp.setFlight_train("Flight");
        myPlansElements.add(temp);

        RecyclerView rvMyPlans = framentView.findViewById(R.id.rvMyPlans);
        rvMyPlans.setLayoutManager(new GridLayoutManager(getContext(), 1));
        MyPlansAdapter myPlansElementsMyPlansAdapter = new MyPlansAdapter(myPlansElements);
        rvMyPlans.setAdapter(myPlansElementsMyPlansAdapter);

        refreshRV();
        myPlansElementsMyPlansAdapter.notifyDataSetChanged();

        rvMyPlans.addOnItemTouchListener(
                new MyPlansItemClickListener(container.getContext(), rvMyPlans ,new MyPlansItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        // do whatever
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
        Log.d("pikachu", "refreshRV: inside refreshRV");
        ArrayList<MyPlansElements> newElements = getAllElements();
        Log.d("pikachu", "refreshRV: back to refresh");
        myPlansElements.clear();
        for(int i=0;i<newElements.size();++i) {
            myPlansElements.add(newElements.get(i));
            Log.d("pikachu", "refreshRV: "+myPlansElements.get(i).getDestination());
        }
        Log.d("pikachu", "refreshRV: "+myPlansElements.size());
    }

    ArrayList<MyPlansElements> getAllElements() {
        final ArrayList<MyPlansElements> ans = new ArrayList<>();
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference dbReference = FirebaseDatabase.getInstance().getReference().child("users")
                .child(firebaseUser.getUid());
        Log.d("pikachu", "onDataChange: teddy");
        Log.d("pikachu", "onDataChange: Sonalika "+dbReference.getDatabase());
        /*String title = dbReference.child("First Trip".toLowerCase()).getKey();
        Log.d("pikachu", "getAllElements: tatti"+title);
        String destination = dbReference.child("title").child("destination").getKey();
        String hotel = dbReference.child("title").child("hotel").getKey();
        String flight = dbReference.child("title").child("flight").getKey();
        MyPlansElements temp = new MyPlansElements(title);
        temp.setDestination(destination);
        temp.setHotel(hotel);
        temp.setFlight_train(flight);
        ans.add(temp);*/

        dbReference.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Log.d("pikachu", "onDataChange: Tatti Place");
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()) {
                    Log.d("pikachu", "onDataChange: Sonalika "+dataSnapshot1.getKey());
                }

                Log.d("pikachu", "onDataChange: teddy"+dataSnapshot.child("title").getKey());
                Log.d("pikachu", "onDataChange: teddy"+dataSnapshot.child("title").child("destination").getKey());
                String title = dataSnapshot.child("title").getKey();
                String destination = dataSnapshot.child("title").child("destination").getKey();
                String hotel = dataSnapshot.child("title").child("hotel").getKey();
                String flight = dataSnapshot.child("title").child("flight").getKey();

                MyPlansElements temp = new MyPlansElements(title);
                temp.setDestination(destination);
                temp.setHotel(hotel);
                temp.setFlight_train(flight);
                ans.add(temp);
                Log.d("pikachu", "onDataChange: reached end of data change");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        /*ArrayList<MyPlansElements> newElements = new ArrayList<>();
        //myPlansElements.clear();
        for(int i=0;i<newElements.size();++i) {
            myPlansElements.add(newElements.get(i));
            Log.d("pikachu", "refreshRV: "+myPlansElements.get(i).getDestination());
        }
        Log.d("pikachu", "getAllElements: "+myPlansElements.size());*/

        //myPlansElementsMyPlansAdapter.notifyDataSetChanged();


        return ans;
    }

}