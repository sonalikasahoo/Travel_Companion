package com.example.sona.travelcompanion.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.sona.travelcompanion.Adapters.MyPlansAdapter;
import com.example.sona.travelcompanion.Listeners.MyPlansItemClickListener;
import com.example.sona.travelcompanion.MainActivity;
import com.example.sona.travelcompanion.Pojos.MyPlansElements;
import com.example.sona.travelcompanion.R;
import com.example.sona.travelcompanion.TakeTripTitleActivity;

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

        Button btnAddTrip = framentView.findViewById(R.id.btnAddTrip);
        btnAddTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("pikachu", "onClick: Trying to start a new Activity Pikachu");
                Intent i =new Intent(getActivity(), TakeTripTitleActivity.class);
                startActivity(i);
            }
        });

        /*RecyclerView rvMyPlans = framentView.findViewById(R.id.rvMyPlans);
        MyPlansAdapter myPlansElementsMyPlansAdapter = new MyPlansAdapter(myPlansElements);
        rvMyPlans.setAdapter(myPlansElementsMyPlansAdapter);
        rvMyPlans.addOnItemTouchListener(
                new MyPlansItemClickListener(container.getContext(), rvMyPlans ,new MyPlansItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        // do whatever
                    }

                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );*/

        return framentView;
    }

}
