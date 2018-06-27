package com.example.sona.travelcompanion.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sona.travelcompanion.Pojos.MyPlansElements;
import com.example.sona.travelcompanion.Pojos.SingleTripElement;
import com.example.sona.travelcompanion.R;

import java.util.ArrayList;

/**
 * Created by sona on 6/27/2018.
 */

public class SingleTripAdapter extends RecyclerView.Adapter<SingleTripAdapter.SingleTripViewHolder> {


    ArrayList<SingleTripElement> singleTripElements;

    public SingleTripAdapter(ArrayList<SingleTripElement> singleTripElements) {
        this.singleTripElements = singleTripElements;
    }

    @NonNull
    @Override
    public SingleTripViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater li = (LayoutInflater)parent.getContext().getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        View itemView = li.inflate(R.layout.single_trip_card_layout, parent, false);
        return new SingleTripViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SingleTripViewHolder holder, int position) {
        SingleTripElement oneEle = singleTripElements.get(position);
        String firstChar = oneEle.getTitle().charAt(0)+"";
        String myTitle = firstChar.toUpperCase()+oneEle.getTitle().substring(1);
        holder.tvSingleEleTitle.setText(myTitle);
        holder.tvSingleTripListDisplay.setText(oneEle.getList());
    }

    @Override
    public int getItemCount() {
        return singleTripElements.size();
    }

    class SingleTripViewHolder extends RecyclerView.ViewHolder {
        TextView tvSingleEleTitle, tvSingleTripListDisplay;
        public SingleTripViewHolder(View itemView) {
            super(itemView);
            tvSingleEleTitle = itemView.findViewById(R.id.tvSingleEleTitle);
            tvSingleTripListDisplay = itemView.findViewById(R.id.tvSingleTripListDisplay);
        }
    }
}
