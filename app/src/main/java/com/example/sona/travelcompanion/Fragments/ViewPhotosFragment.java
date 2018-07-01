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
import android.widget.TextView;

import com.example.sona.travelcompanion.Activities.SingleTripActivity;
import com.example.sona.travelcompanion.Activities.TakeTripTitleActivity;
import com.example.sona.travelcompanion.Activities.TripPhotosActivity;
import com.example.sona.travelcompanion.Adapters.MyPlansAdapter;
import com.example.sona.travelcompanion.Adapters.ViewLabelsAdapter;
import com.example.sona.travelcompanion.Listeners.RecyclerViewItemClickListener;
import com.example.sona.travelcompanion.Pojos.MyPlansElements;
import com.example.sona.travelcompanion.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ViewPhotosFragment extends Fragment {

    ArrayList<String> labels = new ArrayList<>();
    ViewLabelsAdapter viewLabelsAdapter;


    public ViewPhotosFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View framentView = inflater.inflate(R.layout.fragment_view_photos, container, false);

        Button btnAllPhotos;
        RecyclerView rvViewAllLabels = framentView.findViewById(R.id.rvViewAllLabels);
        rvViewAllLabels.setLayoutManager(new GridLayoutManager(getContext(), 1));
        viewLabelsAdapter = new ViewLabelsAdapter(labels);
        rvViewAllLabels.setAdapter(viewLabelsAdapter);

        getAllElements();


        rvViewAllLabels.addOnItemTouchListener(
                new RecyclerViewItemClickListener(container.getContext(), rvViewAllLabels ,new RecyclerViewItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        // do whatever
                        Intent i = new Intent(getActivity(), TripPhotosActivity.class);
                        i.putExtra("label", labels.get(position));
                        startActivity(i);
                    }

                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );




        btnAllPhotos = framentView.findViewById(R.id.btnAllPhotos);
        btnAllPhotos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("pikachu", "onClick: Trying to start a new Activity Pikachu");
                Intent i =new Intent(getActivity(), TripPhotosActivity.class);
                i.putExtra("label", "all");
                startActivity(i);
            }
        });

        return framentView;
    }


    void getAllElements() {
        labels.clear();
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference dbReference = FirebaseDatabase.getInstance().getReference().child("users")
                .child(firebaseUser.getUid()).child("labels");

        dbReference.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()) {
                    String label = dataSnapshot1.getKey();
                    labels.add(label);
                    viewLabelsAdapter.notifyDataSetChanged();
                }
                Log.d("pikachu", "onDataChange: reached end of data change");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


}
