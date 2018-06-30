package com.example.sona.travelcompanion.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sona.travelcompanion.Pojos.TripPhotosElements;
import com.example.sona.travelcompanion.R;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by sona on 6/30/2018.
 */

public class TripPhotosAdapter extends RecyclerView.Adapter<TripPhotosAdapter.TripPhotosViewHolder> {

    ArrayList<TripPhotosElements> allPhotos;

    public TripPhotosAdapter(ArrayList<TripPhotosElements> allPhotos) {
        this.allPhotos = allPhotos;
    }

    @NonNull
    @Override
    public TripPhotosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = (LayoutInflater)parent.getContext().getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        View itemView = layoutInflater.inflate(R.layout.trip_photos_card_layout, parent, false);
        return new TripPhotosViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TripPhotosViewHolder holder, int position) {
        TripPhotosElements oneEle = allPhotos.get(position);
        Picasso.get().load(oneEle.getPhotoUrl()).into(holder.ivTripImage);
        holder.tvCaption.setText(oneEle.getCaption());
    }

    @Override
    public int getItemCount() {
        return allPhotos.size();
    }

    class TripPhotosViewHolder extends RecyclerView.ViewHolder {
        ImageView ivTripImage;
        TextView tvCaption;
        public TripPhotosViewHolder(View itemView) {
            super(itemView);
            this.ivTripImage = itemView.findViewById(R.id.ivTripImage);
            this.tvCaption = itemView.findViewById(R.id.tvCaption);
        }
    }

}
