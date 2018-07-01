package com.example.sona.travelcompanion.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sona.travelcompanion.R;

import java.util.ArrayList;

/**
 * Created by sona on 7/1/2018.
 */

public class ViewLabelsAdapter extends RecyclerView.Adapter<ViewLabelsAdapter.ViewLabelsAdapterViewHolder> {

    ArrayList<String> labels;

    public ViewLabelsAdapter(ArrayList<String> labels) {
        this.labels = labels;
    }

    @NonNull
    @Override
    public ViewLabelsAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater li = (LayoutInflater)parent.getContext().getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        View itemView = li.inflate(R.layout.venues_display_layout_card, parent, false);
        return new ViewLabelsAdapterViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewLabelsAdapterViewHolder holder, int position) {
        String oneEle =  labels.get(position);
        holder.tvPlaceDisplay.setText(oneEle);
    }

    @Override
    public int getItemCount() {
        return labels.size();
    }

    class ViewLabelsAdapterViewHolder extends RecyclerView.ViewHolder {
        TextView tvPlaceDisplay;
        public ViewLabelsAdapterViewHolder(View itemView) {
            super(itemView);
            this.tvPlaceDisplay = itemView.findViewById(R.id.tvPlaceDisplay);
        }
    }
}
