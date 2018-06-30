package com.example.sona.travelcompanion.Fragments;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sona.travelcompanion.APIs.FourSquareVenuesExplore;
import com.example.sona.travelcompanion.APIs.FourSquareVenuesExploreItems;
import com.example.sona.travelcompanion.APIs.FourSquareVenuesExploreItemsVenues;
import com.example.sona.travelcompanion.Activities.DisplayVenueDetailsActivity;
import com.example.sona.travelcompanion.Activities.SingleTripActivity;
import com.example.sona.travelcompanion.Adapters.RecommendedHotelDisplayAdapter;
import com.example.sona.travelcompanion.Listeners.RecyclerViewItemClickListener;
import com.example.sona.travelcompanion.R;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecommendedVenuesFragment extends Fragment {

    ArrayList<FourSquareVenuesExploreItems> allRecommendedHotels = new ArrayList<>();
    RecommendedHotelDisplayAdapter recommendedHotelDisplayAdapter;
    String location;
    RecyclerView rvRecommendedHotels;
    String tripId, placeUnder;


    public RecommendedVenuesFragment() {
        // Required empty public constructor
    }

    @SuppressLint("ValidFragment")
    public RecommendedVenuesFragment(String tripId, String placeUnder, String location) {
        this.placeUnder = placeUnder;
        this.location = location;
        this.tripId = tripId;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View frameView = inflater.inflate(R.layout.fragment_recommended_hotels, container, false);

        rvRecommendedHotels = frameView.findViewById(R.id.rvRecommendedHotels);



        getAllElements();

        rvRecommendedHotels.addOnItemTouchListener(
                new RecyclerViewItemClickListener(container.getContext(), rvRecommendedHotels ,new RecyclerViewItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        // do whatever
                        Intent i = new Intent(getActivity(), DisplayVenueDetailsActivity.class);
                        i.putExtra("tripId", tripId);
                        i.putExtra("placeUnder",placeUnder);
                        i.putExtra("venueId", allRecommendedHotels.get(position).getVenue().getId());
                        startActivity(i);
                    }

                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );


        return frameView;
    }


    void getAllElements() {

        allRecommendedHotels.clear();

        String absoluteURL = "https://api.foursquare.com/v2/venues/explore?client_id"
                +"=KKBBEJGMOVBQ10ROB11LPW4B5Q1LLQZPERTANXJXTW2W0DQI&client_secret" +
                "=LK321CFLQIG22DBN2IMMK35IZOBPKCCSXR1D4JRPSIZ20U51&v=20180626&near="
                +location;
        if(placeUnder.equals("hotel"))
            absoluteURL = absoluteURL +"&query=hotel";

        OkHttpClient okHttpClient = new OkHttpClient();
        final Request request = new Request.Builder().url(absoluteURL).build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                String result = response.body().string();
                Gson gson = new Gson();
                FourSquareVenuesExplore fourSquareVenuesExplore = gson.fromJson(result,
                        FourSquareVenuesExplore.class);
                allRecommendedHotels = fourSquareVenuesExplore.getResponse().getGroups().get(0).getItems();
                recommendedHotelDisplayAdapter = new RecommendedHotelDisplayAdapter(allRecommendedHotels);

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        rvRecommendedHotels.setLayoutManager(new GridLayoutManager(getContext(), 1));
                        rvRecommendedHotels.setAdapter(recommendedHotelDisplayAdapter);

                        //recommendedHotelDisplayAdapter.notifyDataSetChanged();
                        Log.d("pikachu", "run: adapter notified that dataset changed"+allRecommendedHotels.size());
                    }
                });


            }
        });
    }

}
