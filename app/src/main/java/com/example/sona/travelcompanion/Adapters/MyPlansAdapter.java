package com.example.sona.travelcompanion.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sona.travelcompanion.Pojos.MyPlansElements;
import com.example.sona.travelcompanion.R;

import java.util.ArrayList;

/**
 * Created by sona on 6/25/2018.
 */

public class MyPlansAdapter extends RecyclerView.Adapter<MyPlansAdapter.MyPlansViewHolder> {

    ArrayList<MyPlansElements> myPlansElements;

    public MyPlansAdapter(ArrayList<MyPlansElements> myPlansElements) {
        this.myPlansElements = myPlansElements;
    }

    @NonNull
    @Override
    public MyPlansViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater li = (LayoutInflater)parent.getContext().getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        View itemView = li.inflate(R.layout.my_plans_layout_card, parent, false);
        return new MyPlansViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyPlansViewHolder holder, int position) {
        MyPlansElements oneEle = myPlansElements.get(position);
        holder.tvTripTitle.setText(oneEle.getTripTitle());
        holder.tvDestination.setText(oneEle.getDestination());
        holder.tvHotel.setText(oneEle.getHotel());
        holder.tvFlight.setText(oneEle.getHotel());
    }

    @Override
    public int getItemCount() {
        return myPlansElements.size();
    }

    class MyPlansViewHolder extends RecyclerView.ViewHolder {
        TextView tvTripTitle, tvDestination, tvHotel, tvFlight;
        public MyPlansViewHolder(View itemView) {
            super(itemView);
            tvTripTitle = itemView.findViewById(R.id.tvTripTitle);
            tvDestination = itemView.findViewById(R.id.tvDestination);
            tvHotel = itemView.findViewById(R.id.tvHotel);
            tvFlight = itemView.findViewById(R.id.tvFlight);
        }
    }
}
