package com.example.sona.travelcompanion.Adapters;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sona.travelcompanion.APIs.FourSquareVenuesExploreItems;
import com.example.sona.travelcompanion.APIs.FourSquareVenuesSearchElement;
import com.example.sona.travelcompanion.R;

import java.util.ArrayList;

/**
 * Created by sona on 6/28/2018.
 */

public class ViewAllHotelsAdapter extends RecyclerView.Adapter<ViewAllHotelsAdapter.ViewAllHotelsViewHolder> {


    ArrayList<FourSquareVenuesSearchElement> allHotels;

    public ViewAllHotelsAdapter(ArrayList<FourSquareVenuesSearchElement> allHotels) {
        this.allHotels = allHotels;
    }

    @NonNull
    @Override
    public ViewAllHotelsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater li = (LayoutInflater)parent.getContext().getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        View itemView = li.inflate(R.layout.venues_display_layout_card, parent, false);
        return new ViewAllHotelsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewAllHotelsViewHolder holder, int position) {
        FourSquareVenuesSearchElement oneEle = allHotels.get(position);
        holder.tvPlaceDisplay.setText(oneEle.getName());
    }

    @Override
    public int getItemCount() {
        return allHotels.size();
    }

    class ViewAllHotelsViewHolder extends RecyclerView.ViewHolder {
        TextView tvPlaceDisplay;
        public ViewAllHotelsViewHolder(View itemView) {
            super(itemView);
            tvPlaceDisplay = itemView.findViewById(R.id.tvPlaceDisplay);
        }
    }

}
