package com.example.sona.travelcompanion;

import android.app.DatePickerDialog;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.util.Locale;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

public class TakeTripTitleActivity extends AppCompatActivity {

    //UI References
    private EditText etFromDate;
    private EditText etToDate;
    private EditText etTripTitle;

    private DatePickerDialog fromDatePickerDialog;
    private DatePickerDialog toDatePickerDialog;

    private SimpleDateFormat dateFormatter;

    Button btnCreateTrip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_trip_title);


        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.UK);
        findViewsById();

        //setDateTimeField();

        btnCreateTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tripTitle = etTripTitle.getText().toString();
                String fromDate = etFromDate.getText().toString();
                String toDate = etToDate.getText().toString();
                Log.d("pikachu", "onClick: "+tripTitle+" "+fromDate+" "+toDate);
            }
        });
    }

    private void findViewsById() {
        etTripTitle = findViewById(R.id.etTripTitle);
        etFromDate = findViewById(R.id.etFromdate);
        etFromDate.setInputType(InputType.TYPE_NULL);
        etFromDate.requestFocus();

        btnCreateTrip = findViewById(R.id.btnCreateTrip);

        etToDate = findViewById(R.id.etToDate);
        etToDate.setInputType(InputType.TYPE_NULL);
    }

    private void setDateTimeField() {
        etFromDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        etToDate.setOnClickListener((View.OnClickListener) this);

        Calendar newCalendar = Calendar.getInstance();
        fromDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, month, dayOfMonth);
                etFromDate.setText(dateFormatter.format(newDate.getTime()));
            }
        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        toDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                etToDate.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }
}
