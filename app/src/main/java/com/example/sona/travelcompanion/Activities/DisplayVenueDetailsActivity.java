package com.example.sona.travelcompanion.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sona.travelcompanion.APIs.SpecificVenue;
import com.example.sona.travelcompanion.APIs.SpecificVenueLocation;
import com.example.sona.travelcompanion.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DisplayVenueDetailsActivity extends AppCompatActivity {

    TextView tvVenueName, tvVenueAddress, tvVenueLikes, tvVenueTips;
    Button btnAddVenue;
    ImageView ivDisplayImage;
    String tripName, placeUnder, venueId;
    FirebaseUser firebaseUser;
    DatabaseReference dbReference;
    String venueName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_venue_details);

        tvVenueName = findViewById(R.id.tvVenueName);
        tvVenueAddress = findViewById(R.id.tvVenueAddress);
        tvVenueLikes = findViewById(R.id.tvVenueLikes);
        tvVenueTips = findViewById(R.id.tvVenueTips);
        btnAddVenue = findViewById(R.id.btnAddVenue);
        ivDisplayImage = findViewById(R.id.ivDisplayImage);
        venueName = "";

        Intent intentWhoCreatedThis = getIntent();
        tripName = intentWhoCreatedThis.getStringExtra("tripName");
        placeUnder = intentWhoCreatedThis.getStringExtra("placeUnder");
        venueId = intentWhoCreatedThis.getStringExtra("venueId");

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        dbReference = FirebaseDatabase.getInstance().getReference().child("users")
                .child(firebaseUser.getUid()).child(tripName).child(placeUnder);


        //make a network call to display all details of a venue

        makeNetworkCall();

        btnAddVenue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dbReference.push().setValue(venueName);

                Intent i = new Intent(DisplayVenueDetailsActivity.this,
                        SingleTripActivity.class);
                i.putExtra("tripName",tripName);
                startActivity(i);
            }
        });



    }

    void makeNetworkCall() {
        String absoluteUrl = "https://api.foursquare.com/v2/venues/"
                +venueId+"/?client_id=KKBBEJGMOVBQ10ROB11LPW4B5Q1LLQZPERTANXJXTW2W0DQI" +
                "&client_secret=LK321CFLQIG22DBN2IMMK35IZOBPKCCSXR1D4JRPSIZ20U51" +
                "&v=20180626";
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url(absoluteUrl).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();

                Gson gson = new Gson();
                final SpecificVenue specificVenue = gson.fromJson(result, SpecificVenue.class);
                DisplayVenueDetailsActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //setting venue name
                        tvVenueName.setText(specificVenue.getResponse()
                        .getVenue().getName());
                        venueName = specificVenue.getResponse().getVenue().getName();

                        //setting VenueAddress
                        String address = "";
                        if(specificVenue.getResponse().getVenue().getLocation().getFormattedAddress()
                                != null) {
                            ArrayList<String> temp = specificVenue.getResponse().getVenue()
                                    .getLocation().getFormattedAddress();

                            for(int i=0;i<temp.size()-1;++i)
                                address = address +temp.get(i) + ", ";
                            address = address + temp.get(temp.size()-1);
                            tvVenueAddress.setText(address);
                        } else {
                            tvVenueAddress.setText("Not Available");
                        }

                        //setting venue likes
                        if(specificVenue.getResponse().getVenue().getLikes() != null)
                            tvVenueLikes.setText(String.valueOf(specificVenue.getResponse()
                                    .getVenue().getLikes().getCount()));
                        else
                            tvVenueLikes.setText("Not Available");

                        //setting venue tips
                        if(specificVenue.getResponse().getVenue()
                                .getTips().getGroups().get(0).getItems().size() != 0)
                            tvVenueTips.setText(specificVenue.getResponse().getVenue()
                                    .getTips().getGroups().get(0).getItems().get(0).getText());
                        else
                            tvVenueTips.setText("Not Available");

                        //setting the image
                        if(specificVenue.getResponse().getVenue().getBestPhoto() != null) {

                            String prefix = specificVenue.getResponse().getVenue()
                                    .getBestPhoto().getPrefix();
                            int height = specificVenue.getResponse().getVenue()
                                    .getBestPhoto().getHeight();
                            int width = specificVenue.getResponse().getVenue()
                                    .getBestPhoto().getWidth();
                            String suffix = specificVenue.getResponse().getVenue()
                                    .getBestPhoto().getSuffix();
                            String photoUrl = prefix + height + "x" + width + suffix;
                            Picasso.get().load(photoUrl).into(ivDisplayImage);
                        }

                    }
                });
            }
        });
    }
}
