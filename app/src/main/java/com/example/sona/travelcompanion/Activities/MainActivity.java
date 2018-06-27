package com.example.sona.travelcompanion.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.sona.travelcompanion.Fragments.MyPlansFragment;
import com.example.sona.travelcompanion.R;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN = 1000;

    FirebaseUser firebaseUser; //this user is currently using our app

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        if(firebaseUser != null) {
            //Logged In
            addListeners();
        } else {
            startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder()
            .setIsSmartLockEnabled(false).setTheme(R.style.LoginTheme).setAvailableProviders(Arrays.asList(
                            new AuthUI.IdpConfig.EmailBuilder().build(),
                            new AuthUI.IdpConfig.GoogleBuilder().build()))
                            .build(),
                    RC_SIGN_IN);
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // RC_SIGN_IN is the request code you passed into startActivityForResult(...) when starting the sign in flow.
        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            // Successfully signed in
            if (resultCode == RESULT_OK) {
                firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                addListeners();
                Log.d("pikachu", "onActivityResult: " + firebaseUser.getDisplayName());
                Log.d("pikachu", "onActivityResult: " + firebaseUser.getUid());
            } else {
                // Sign in failed
                if (response == null) {
                    // User pressed back button
                    return;
                }
                if (response.getError().getErrorCode() == ErrorCodes.NO_NETWORK) {
                    return;
                }


            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.btnLogout:
                //logout here
                return true;

            case R.id.btnAddTrip:
                //Add a new Trip to the database
                addListeners();
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
        }
    }

    public void addListeners() {
        final DatabaseReference dbReference = FirebaseDatabase.getInstance().getReference();
        /*MenuItem btnAdd = findViewById(R.id.btnAddTrip);
        btnAdd.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                
                return true;
            }
        });*/
        Button btnMyPlans;
        btnMyPlans = findViewById(R.id.btnMyPlans);
        btnMyPlans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                final DatabaseReference dbReference = FirebaseDatabase.getInstance().getReference();
                Log.d("pikachu", "onClick: Going to fragment pikachu");
                getSupportFragmentManager().beginTransaction().replace(R.id.flFragContainer,
                        new MyPlansFragment()).commit();
            }
        });
    }
}
