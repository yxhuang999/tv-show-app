package edu.northeastern.fall22_team34.sticker;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import edu.northeastern.fall22_team34.R;
import edu.northeastern.fall22_team34.sticker.models.User;
import java.util.Calendar;
import java.util.Date;

public class SendStickerActivity extends AppCompatActivity {

    private FirebaseDatabase mDatabase;
    private DatabaseReference mDatabaseRef;
    private StorageReference mStorageRef;

    private User user;
    private String username;
    private List<String> validUsernames;

    private EditText recipientText;
    private String recipientUsername;

    private Button btnChooseImg;
    private ImageView selectedImgView;

    private Button btnSend;
    private Button btnReceived;

    private Uri imageUri;

    private List<Uri> imgReceived = new ArrayList<>();

    private int countSent;
    private List<String> senders;
    private List<Date> timeReceived;
    private List<String> StickersReceived;

    boolean indicator = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_sticker);

        username = getIntent().getStringExtra("USERNAME");
        validUsernames = (List<String>) getIntent().getSerializableExtra("VALID_USERNAMES");

        recipientText = findViewById(R.id.recipient_text);

        btnChooseImg = findViewById(R.id.btnChooseImg);
        selectedImgView = findViewById(R.id.selectedImg);

        btnSend = findViewById(R.id.btnSend);
        btnReceived = findViewById(R.id.btnReceived);

        mDatabase = FirebaseDatabase.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference();
        mStorageRef = FirebaseStorage.getInstance().getReference();

        if (!indicator) {
            senders  = new ArrayList<>();
            timeReceived  = new ArrayList<>();
            StickersReceived  = new ArrayList<>();
            countSent = 0;
        }

        mDatabase.getReference().child("users").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                User user = snapshot.getValue(User.class);
                validUsernames.add(user.username);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                validUsernames.remove(user.username);
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btnChooseImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validUsernames.contains(recipientText.getText().toString())) {
                    Toast.makeText(getApplicationContext(),
                            "Username Does not Exist", Toast.LENGTH_SHORT).show();
                } else if (recipientText.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(),
                            "Recipient Username Cannot be Empty", Toast.LENGTH_SHORT).show();
                } else if (recipientText.getText().toString().equals(username)) {
                    Toast.makeText(getApplicationContext(),
                            "You Cannot S3end Stickers to Yourself", Toast.LENGTH_SHORT).show();
                } else {
                    recipientUsername = recipientText.getText().toString();
                    openFileSelector();
                }
            }
        });

        btnReceived.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent stickersReceivedActivity = new Intent(getApplicationContext(), StickersReceivedActivity.class);
                stickersReceivedActivity.putExtra("RECEIVED", (Serializable) imgReceived);
                startActivity(stickersReceivedActivity);
            }
        });

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(imageUri == null) {
                    toastDisplay("Please choose a sticker before sending.");
                } else {
                    countSent += 1;
                    Date currentTime = Calendar.getInstance().getTime();
                    senders.add(username);
                    timeReceived.add(currentTime);
                    StickersReceived.add(imageUri.toString());
                    sendImage(recipientUsername, username, imageUri);
                    toastDisplay("Successfully sent!");
                    indicator = true;

                }
            }
        });
    }



    public void toastDisplay(String message) {
        Toast.makeText(SendStickerActivity.this, message, Toast.LENGTH_LONG).show();
    }

    private void openFileSelector() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        SelectImgResultLauncher.launch(intent);
    }

    ActivityResultLauncher<Intent> SelectImgResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        imageUri = result.getData().getData();
                        Picasso.get().load(imageUri).into(selectedImgView);
                    }
                }
            });


//    public String imageExtension(Uri uriImage) {
//        ContentResolver c = getContentResolver();
//        MimeTypeMap mime = MimeTypeMap.getSingleton();
//        return mime.getExtensionFromMimeType(c.getType(uriImage));
//    }

    //String recipientUsername,
    public void sendImage(String recipientUsername, String username, Uri selectedImgView) {
        //countSent += 1;
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
//        HashMap<String, Object> map = new HashMap<>();
//        map.put("Sender", username);
//        map.put("Sticker", selectedImgView.toString());
//        senders.add(username);
//        timeReceived.add(currentTime);
//        StickersReceived.add(selectedImgView.toString());


        recipientUsername = recipientText.getText().toString();
        Log.i("username", recipientUsername);
        Log.i("senders", String.valueOf(senders));
        reference.child("users").child(recipientUsername).child("Senders").push().setValue(senders); // generate a unique key
        reference.child("users").child(recipientUsername).child("timeReceived").push().setValue(timeReceived);
        reference.child("users").child(recipientUsername).child("stickersReceived").push().setValue(StickersReceived);




        //reference.child("users").child(username).child("Image sent").setValue(countSent);
        //reference.child("users").child(recipientUsername).child("Sender").setValue(username);
        //reference.child("users").child(recipientUsername).child("Sticker").setValue(selectedImgView.toString());
    }


//        StorageReference reference = mStorageRef.child("Image/" + System.currentTimeMillis() + "." + imageExtension(imageUri));
//
//        reference.putFile(imageUri)
//                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                    @Override
//                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                        Toast.makeText(SendStickerActivity.this, "Successfully sent!", Toast.LENGTH_LONG).show();
//                        // create new upload item
//                        User user = new User(username, REGISTRATION_TOKEN, recipientText.getText().toStirng().trim(),
//                                taskSnapshot.getUploadSessionUri().toString());
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Toast.makeText(SendStickerActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
//                    }
//                })
//                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
//                    @Override
//                    public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
//
//                    }
//                });
}