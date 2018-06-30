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

public class SearchHotelsActivity extends AppCompatActivity {


    EditText etSearchhNear, etHotelName;
    Button btnOk, btnSearchHotel, btnRecommendHotel, btnViewAllHotels;
    String location;
    String hotelName;
    String tripId;
    String placeUnder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_hotels);

        Intent intentWhoCreatedThis = getIntent();
        tripId = intentWhoCreatedThis.getStringExtra("tripId");
        placeUnder = intentWhoCreatedThis.getStringExtra("placeUnder");

        etSearchhNear = findViewById(R.id.etSearchhNear);
        etHotelName = findViewById(R.id.etHotelName);
        btnOk = findViewById(R.id.btnOk);
        btnSearchHotel = findViewById(R.id.btnSearchHotel);
        btnRecommendHotel = findViewById(R.id.btnRecommendHotel);
        btnViewAllHotels = findViewById(R.id.btnViewAllHotels);

        location = "";
        hotelName = "";


        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                location = etSearchhNear.getText().toString();
                if(location.length() == 0) {
                    Toast.makeText(SearchHotelsActivity.this,
                            "Enter a place first", Toast.LENGTH_SHORT).show();
                } else {
                    makeNetworkCall();
                }
            }
        });

        btnSearchHotel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hotelName = etHotelName.getText().toString();
                if(location.length() == 0) {
                    Toast.makeText(SearchHotelsActivity.this,
                            "Enter a location first!!!", Toast.LENGTH_SHORT).show();
                } else if(hotelName.length() == 0) {
                    Toast.makeText(SearchHotelsActivity.this,
                            "Enter hotel name first!!!", Toast.LENGTH_SHORT).show();
                } else {
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragmentHotelContainer,
                            new ViewAllVenuesFragment(tripId, placeUnder, location, hotelName)).commit();
                }
            }
        });

        btnRecommendHotel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(location.length() == 0) {
                    Toast.makeText(SearchHotelsActivity.this,
                            "Enter a location first!!!", Toast.LENGTH_SHORT).show();
                } else {
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragmentHotelContainer,
                            new RecommendedVenuesFragment(tripId, placeUnder, location)).commit();
                }
            }
        });

        btnViewAllHotels.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(location.length() == 0) {
                    Toast.makeText(SearchHotelsActivity.this,
                            "Enter a location first!!!", Toast.LENGTH_SHORT).show();
                } else {
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragmentHotelContainer,
                            new ViewAllVenuesFragment(tripId, placeUnder,location)).commit();
                }
            }
        });


    }

    void makeNetworkCall() {
        String absoluteUrl = "https://api.foursquare.com/v2/venues/explore?client_id"
                +"=KKBBEJGMOVBQ10ROB11LPW4B5Q1LLQZPERTANXJXTW2W0DQI&client_secret" +
                "=LK321CFLQIG22DBN2IMMK35IZOBPKCCSXR1D4JRPSIZ20U51&v=20180626&near="
                +location+"&query=hotel";
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
                    SearchHotelsActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            location = "";
                            etSearchhNear.setText("");
                            Toast.makeText(SearchHotelsActivity.this,
                                    "Enter a valid place or a smaller area", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }


}
