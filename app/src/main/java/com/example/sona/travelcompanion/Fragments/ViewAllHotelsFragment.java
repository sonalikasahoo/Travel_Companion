package com.example.sona.travelcompanion.Fragments;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sona.travelcompanion.APIs.FourSquareVenuesExplore;
import com.example.sona.travelcompanion.APIs.FourSquareVenuesSearch;
import com.example.sona.travelcompanion.APIs.FourSquareVenuesSearchElement;
import com.example.sona.travelcompanion.Adapters.RecommendedHotelDisplayAdapter;
import com.example.sona.travelcompanion.Adapters.ViewAllHotelsAdapter;
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


public class ViewAllHotelsFragment extends Fragment {

    String location;
    String hotelHint;
    RecyclerView rvViewAllHotels;
    ViewAllHotelsAdapter viewAllHotelsAdapter;
    ArrayList<FourSquareVenuesSearchElement> allHotels = new ArrayList<>();


    public ViewAllHotelsFragment() {
        // Required empty public constructor
    }

    @SuppressLint("ValidFragment")
    public ViewAllHotelsFragment(String location) {
        this.location = location;
        this.hotelHint = "";
    }

    @SuppressLint("ValidFragment")
    public ViewAllHotelsFragment(String location, String hotelHint) {
        this.location = location;
        this.hotelHint = hotelHint;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View frameView = inflater.inflate(R.layout.fragment_view_all_hotels, container, false);

        rvViewAllHotels = frameView.findViewById(R.id.rvViewAllHotels);

        getAllElements();

        rvViewAllHotels.addOnItemTouchListener(
                new RecyclerViewItemClickListener(container.getContext(), rvViewAllHotels ,new RecyclerViewItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        // do whatever
                        /*Intent i = new Intent(getActivity(), SingleTripActivity.class);
                        i.putExtra("tripName", allRecommendedHotels.get(position).getTripTitle());
                        startActivity(i);*/
                    }

                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );

        return frameView;
    }

    void getAllElements() {
        allHotels.clear();

        String absoluteURL = "https://api.foursquare.com/v2/venues/search?client_id"
                +"=KKBBEJGMOVBQ10ROB11LPW4B5Q1LLQZPERTANXJXTW2W0DQI&client_secret" +
                "=LK321CFLQIG22DBN2IMMK35IZOBPKCCSXR1D4JRPSIZ20U51&v=20180626&near="
                +location+"&query=hotel";

        if(hotelHint.length() != 0)
            absoluteURL = absoluteURL + "&query=" + hotelHint;

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
                FourSquareVenuesSearch fourSquareVenuesSearch = gson.fromJson(result,
                        FourSquareVenuesSearch.class);

                allHotels = fourSquareVenuesSearch.getResponse().getVenues();
                viewAllHotelsAdapter = new ViewAllHotelsAdapter(allHotels);

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        rvViewAllHotels.setLayoutManager(new GridLayoutManager(getContext(), 1));
                        rvViewAllHotels.setAdapter(viewAllHotelsAdapter);

                        //recommendedHotelDisplayAdapter.notifyDataSetChanged();
                        Log.d("pikachu", "run: adapter notified that dataset changed"+allHotels.size());
                    }
                });


            }
        });
    }

}
