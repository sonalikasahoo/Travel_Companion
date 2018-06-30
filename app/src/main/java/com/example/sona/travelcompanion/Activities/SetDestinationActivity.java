package com.example.sona.travelcompanion.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sona.travelcompanion.APIs.FourSquareVenuesExplore;
import com.example.sona.travelcompanion.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class SetDestinationActivity extends AppCompatActivity {

    EditText etDestination;
    Button btnSaveDestination;

    FirebaseUser firebaseUser;
    String tripId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_destination);

        Intent intentWhoCreatedThis = getIntent();
        tripId = intentWhoCreatedThis.getStringExtra("tripId");

        etDestination = findViewById(R.id.etDestination);
        btnSaveDestination = findViewById(R.id.btnSaveDestination);

        btnSaveDestination.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String destination = etDestination.getText().toString();
                if(destination.length() == 0 ) {
                    Toast.makeText(SetDestinationActivity.this,
                            "Enter a destination first", Toast.LENGTH_SHORT).show();
                } else {
                    makeNetworkCall(destination);
                }

            }
        });


    }

    void makeNetworkCall(final String destination) {
        String absoluteUrl = "https://api.foursquare.com/v2/venues/explore?ll=40.7,-74&client_id=KKBBEJGMOVBQ10ROB11LPW4B5Q1LLQZPERTANXJXTW2W0DQI&client_secret=LK321CFLQIG22DBN2IMMK35IZOBPKCCSXR1D4JRPSIZ20U51&v=20180626&near="
                +destination;
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(absoluteUrl).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Toast.makeText(SetDestinationActivity.this, "Network Failed", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //This does not run on the main thread
                String result = response.body().string();
                //ArrayList<GitHubUser> users = parseJson(result);

                Gson gson = new Gson();
                FourSquareVenuesExplore fourSquareVenuesExplore = gson.fromJson(result, FourSquareVenuesExplore.class);

                if(fourSquareVenuesExplore.getMeta().getCode() == 400) {
                    SetDestinationActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            etDestination.setText("");
                            Toast.makeText(SetDestinationActivity.this, "Enter a valid Destination!",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                    DatabaseReference dbReference = FirebaseDatabase.getInstance().getReference().child("users")
                            .child(firebaseUser.getUid()).child("alltrips").child(tripId);
                    dbReference.child("destination").push().setValue(destination);

                    SetDestinationActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            etDestination.setText("");
                            Toast.makeText(SetDestinationActivity.this,
                                    "Destination added to trip", Toast.LENGTH_SHORT).show();
                        }
                    });
                    Intent i = new Intent(SetDestinationActivity.this,
                            SingleTripActivity.class);
                    i.putExtra("tripId",tripId);
                    startActivity(i);
                }

            }
        });
    }
}
