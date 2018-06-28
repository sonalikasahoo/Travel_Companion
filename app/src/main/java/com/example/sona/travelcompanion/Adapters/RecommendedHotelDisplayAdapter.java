package com.example.sona.travelcompanion.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sona.travelcompanion.APIs.FourSquareVenuesExploreItems;
import com.example.sona.travelcompanion.R;

import java.util.ArrayList;

/**
 * Created by sona on 6/27/2018.
 */

public class RecommendedHotelDisplayAdapter extends RecyclerView.Adapter<RecommendedHotelDisplayAdapter.HotelDisplayViewHolder> {


    ArrayList<FourSquareVenuesExploreItems> allPlaces;

    public RecommendedHotelDisplayAdapter(ArrayList<FourSquareVenuesExploreItems> allPlaces) {
        this.allPlaces = allPlaces;
    }

    @NonNull
    @Override
    public HotelDisplayViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater li = (LayoutInflater)parent.getContext().getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        View itemView = li.inflate(R.layout.venues_display_layout_card, parent, false);
        return new HotelDisplayViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull HotelDisplayViewHolder holder, int position) {
        FourSquareVenuesExploreItems oneEle = allPlaces.get(position);
        holder.tvPlaceDisplay.setText(oneEle.getVenue().getName());
    }

    @Override
    public int getItemCount() {
        return allPlaces.size();
    }

    class HotelDisplayViewHolder extends RecyclerView.ViewHolder {
        TextView tvPlaceDisplay;
        public HotelDisplayViewHolder(View itemView) {
            super(itemView);
            tvPlaceDisplay = itemView.findViewById(R.id.tvPlaceDisplay);
        }
    }
}
