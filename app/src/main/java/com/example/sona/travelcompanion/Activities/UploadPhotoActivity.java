package com.example.sona.travelcompanion.Activities;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sona.travelcompanion.Pojos.TripPhotosElements;
import com.example.sona.travelcompanion.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.label.FirebaseVisionLabel;
import com.google.firebase.ml.vision.label.FirebaseVisionLabelDetector;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.List;

public class UploadPhotoActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 123;
    Button btnChoose, btnUpload;
    ImageView ivChosenImage;
    TextView tvGeneratedCaption;
    Uri filePath;
    String tripId;
    Bitmap bitmap;
    String caption = "Has no Caption";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_photo);

        Intent intentWhoCreatedThis = getIntent();
        tripId = intentWhoCreatedThis.getStringExtra("tripId");

        btnChoose = findViewById(R.id.btnChoose);
        btnUpload = findViewById(R.id.btnUpload);
        ivChosenImage = findViewById(R.id.ivChosenImage);
        tvGeneratedCaption = findViewById(R.id.tvGeneratedCaption);

        btnChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ContextCompat.checkSelfPermission(UploadPhotoActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        == PackageManager.PERMISSION_GRANTED) {
                    selectFile();
                } else {
                    ActivityCompat.requestPermissions(UploadPhotoActivity.this,
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 9);
                }
            }
        });

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(filePath == null) {
                    Toast.makeText(UploadPhotoActivity.this, "Choose a file", Toast.LENGTH_SHORT).show();
                } else {
                    uploadFile();
                }
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == 9 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            selectFile();
        } else {
            Toast.makeText(this, "Grant Permission First", Toast.LENGTH_SHORT).show();
        }
    }

    void selectFile() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select an image"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null
                && data.getData()!= null) {
            filePath = data.getData();

            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),filePath);
                ivChosenImage.setImageBitmap(bitmap);


                //Geneartingt the caption
                FirebaseVisionImage image = FirebaseVisionImage.fromBitmap(bitmap);
                FirebaseVisionLabelDetector detector = FirebaseVision.getInstance()
                        .getVisionLabelDetector();
                Task<List<FirebaseVisionLabel>> result =
                        detector.detectInImage(image)
                                .addOnSuccessListener(
                                        new OnSuccessListener<List<FirebaseVisionLabel>>() {
                                            @Override
                                            public void onSuccess(List<FirebaseVisionLabel> labels) {
                                                // Task completed successfully
                                                float max = Float.MIN_VALUE;
                                                String finalLabel = "";
                                                for (FirebaseVisionLabel label: labels) {
                                                    String text = label.getLabel();
                                                    String entityId = label.getEntityId();
                                                    float confidence = label.getConfidence();
                                                    if(confidence > max) {
                                                        max = confidence;
                                                        finalLabel = text;
                                                    }
                                                }
                                                tvGeneratedCaption.setText(finalLabel);
                                                caption = finalLabel;
                                            }
                                        })
                                .addOnFailureListener(
                                        new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                // Task failed with an exception
                                                // ...
                                            }
                                        });



            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    void uploadFile() {


        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        final StorageReference storageReference = FirebaseStorage.getInstance().getReference();

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setProgress(0);
        progressDialog.setTitle("Uploading File...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.show();

        final String fileName = System.currentTimeMillis() + "";




        //storing the image in storage and updating the database
        storageReference.child("allPhotosUploads/"+fileName).putFile(filePath)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        storageReference.child("allPhotosUploads").child(fileName).getDownloadUrl()
                                .addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        String photoUrl = uri.toString();
                                        TripPhotosElements tripPhotosElements = new TripPhotosElements(
                                                photoUrl, caption);
                                        DatabaseReference databaseReference = FirebaseDatabase.getInstance()
                                                .getReference();
                                        databaseReference.child("users").child(firebaseUser.getUid())
                                                .child("photos").child(tripId).child(fileName)
                                                .setValue(tripPhotosElements)
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if(task.isSuccessful()) {
                                                            Toast.makeText(UploadPhotoActivity.this,
                                                                    "Image uploaded successfully!", Toast.LENGTH_SHORT).show();
                                                        } else {
                                                            Toast.makeText(UploadPhotoActivity.this,
                                                                    "Could not upload image! Try Later", Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                });
                                    }
                                });
                        progressDialog.dismiss();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(UploadPhotoActivity.this,
                                "Could not Upload Image! Try later.", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                        double progress = (100.0 *taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                        progressDialog.setProgress((int)progress);
                    }
                });

    }


}
