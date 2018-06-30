package com.example.sona.travelcompanion.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sona.travelcompanion.APIs.FourSquareVenuesExplore;
import com.example.sona.travelcompanion.Fragments.RecommendedVenuesFragment;
import com.example.sona.travelcompanion.Fragments.ViewAllVenuesFragment;
import com.example.sona.travelcompanion.R;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SearchPlacesToVisitActivity extends AppCompatActivity {

    EditText etSearchPlaceNear, etPlaceName;
    Button btnPlaceOk, btnSearchPlace, btnRecommendPlace, btnViewAllPlaces;
    String location;
    String placeName;
    String tripId, placeUnder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_places_to_visit);

        Intent intentWhoCreatedThis = getIntent();
        tripId = intentWhoCreatedThis.getStringExtra("tripId");
        placeUnder = intentWhoCreatedThis.getStringExtra("placeUnder");

        etSearchPlaceNear = findViewById(R.id.etSearchPlaceNear);
        etPlaceName = findViewById(R.id.etPlaceName);
        btnPlaceOk = findViewById(R.id.btnPlaceOk);
        btnSearchPlace = findViewById(R.id.btnSearchPlace);
        btnRecommendPlace = findViewById(R.id.btnRecommendPlace);
        btnViewAllPlaces = findViewById(R.id.btnViewAllPlaces);

        location = "";
        placeName = "";

        btnPlaceOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                location = etSearchPlaceNear.getText().toString();
                if(location.length() == 0) {
                    Toast.makeText(SearchPlacesToVisitActivity.this,
                            "Enter a place first", Toast.LENGTH_SHORT).show();
                } else {
                    makeNetworkCall();
                }
            }
        });

        btnSearchPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                placeName = etPlaceName.getText().toString();
                if(location.length() == 0) {
                    Toast.makeText(SearchPlacesToVisitActivity.this,
                            "Enter a location first!!!", Toast.LENGTH_SHORT).show();
                } else if(placeName.length() == 0) {
                    Toast.makeText(SearchPlacesToVisitActivity.this,
                            "Enter place name first!!!", Toast.LENGTH_SHORT).show();
                } else {
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragmentPlaceContainer,
                            new ViewAllVenuesFragment(tripId, placeUnder, location, placeName)).commit();
                }
            }
        });

        btnRecommendPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(location.length() == 0) {
                    Toast.makeText(SearchPlacesToVisitActivity.this,
                            "Enter a location first!!!", Toast.LENGTH_SHORT).show();
                } else {
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragmentPlaceContainer,
                            new RecommendedVenuesFragment(tripId, placeUnder, location)).commit();
                }
            }
        });

        btnViewAllPlaces.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(location.length() == 0) {
                    Toast.makeText(SearchPlacesToVisitActivity.this,
                            "Enter a location first!!!", Toast.LENGTH_SHORT).show();
                } else {
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragmentPlaceContainer,
                            new ViewAllVenuesFragment(tripId, placeUnder,location)).commit();
                }
            }
        });

    }

    void makeNetworkCall() {
        String absoluteUrl = "https://api.foursquare.com/v2/venues/explore?client_id"
                +"=KKBBEJGMOVBQ10ROB11LPW4B5Q1LLQZPERTANXJXTW2W0DQI&client_secret" +
                "=LK321CFLQIG22DBN2IMMK35IZOBPKCCSXR1D4JRPSIZ20U51&v=20180626&near="
                +location;
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
                FourSquareVenuesExplore fourSquareVenuesExplore = gson.fromJson(result,
                        FourSquareVenuesExplore.class);
                Log.d("pikachu", "onResponse: "+fourSquareVenuesExplore.getMeta().getCode());
                if(fourSquareVenuesExplore.getMeta().getCode() == 400) {
                    SearchPlacesToVisitActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            location = "";
                            etSearchPlaceNear.setText("");
                            Toast.makeText(SearchPlacesToVisitActivity.this,
                                    "Enter a valid place or a smaller area", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }
}
