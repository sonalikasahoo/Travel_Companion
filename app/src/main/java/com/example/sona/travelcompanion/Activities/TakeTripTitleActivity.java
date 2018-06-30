package com.example.sona.travelcompanion.Activities;

import android.app.DatePickerDialog;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Parcel;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sona.travelcompanion.R;
import com.google.android.gms.internal.firebase_auth.zzao;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.FirebaseUserMetadata;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class TakeTripTitleActivity extends AppCompatActivity {

    //UI References
    private DatePicker datePicker;
    private int year, month, day;

    private EditText etFromDate;
    private EditText etToDate;
    private EditText etTripTitle;

    Button btnCreateTrip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_trip_title);

        findViewsById();

        etFromDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDate(v);
            }
        });

        etToDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDate(v);
            }
        });

        btnCreateTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String tripTitle = etTripTitle.getText().toString();
                final String fromDate = etFromDate.getText().toString();
                final String toDate = etToDate.getText().toString();
                if(fromDate.length()==0 || toDate.length()==0) {
                    Toast.makeText(TakeTripTitleActivity.this,
                            "Enter start and end date first", Toast.LENGTH_SHORT).show();
                } else {
                    Log.d("pikachu", "onClick: "+tripTitle+" "+fromDate+" "+toDate);
                    final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                    final DatabaseReference dbReference = FirebaseDatabase.getInstance().getReference();

                    String generatedId = dbReference.child("users").child(firebaseUser.getUid())
                            .push().getKey();

                    dbReference.child("users").child(firebaseUser.getUid()).child("alltrips")
                            .child(generatedId).child("trip title").setValue(tripTitle);
                    DatabaseReference dbReference2 = dbReference.child("users").child(firebaseUser.getUid())
                            .child("alltrips").child(generatedId);
                    dbReference2.child("fromdate").setValue(fromDate);
                    dbReference2.child("todate").setValue(toDate);

                    Intent i = new Intent(TakeTripTitleActivity.this, MainActivity.class);
                    startActivity(i);
                }

            }
        });


    }

    private void findViewsById() {
        etTripTitle = findViewById(R.id.etTripTitle);
        etFromDate = findViewById(R.id.etFromdate);
        btnCreateTrip = findViewById(R.id.btnCreateTrip);

        etToDate = findViewById(R.id.etToDate);
    }


    @SuppressWarnings("deprecation")
    public void setDate(View view) {
        showDialog(999);
        Toast.makeText(getApplicationContext(), "ca",
                Toast.LENGTH_SHORT)
                .show();
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {
            DatePickerDialog dialog  =new DatePickerDialog(this,
                    myDateListener, year, month, day);
            dialog.getDatePicker().setMinDate(System.currentTimeMillis());
            return dialog;
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0,
                                      int arg1, int arg2, int arg3) {
                    // TODO Auto-generated method stub
                    // arg1 = year
                    // arg2 = month
                    // arg3 = day
                    showDate(arg1, arg2+1, arg3);
                }
            };

    private void showDate(int year, int month, int day) {
        if(etFromDate.hasFocus())
            etFromDate.setText(new StringBuilder().append(day).append("/")
                .append(month).append("/").append(year));
        else
            etToDate.setText(new StringBuilder().append(day).append("/")
                    .append(month).append("/").append(year));
    }

}
