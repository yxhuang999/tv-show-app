package edu.northeastern.fall22_team34.sticker;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import edu.northeastern.fall22_team34.R;
import edu.northeastern.fall22_team34.sticker.models.User;
import java.util.Calendar;
import java.util.Date;

public class SendStickerActivity extends AppCompatActivity {

    private FirebaseDatabase mDatabase;
    private DatabaseReference mDatabaseRef;
    private StorageReference mStorageRef;
    private StorageTask imageTask;

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

    private List<String> senders;
    private List<String> timeReceived;
    private List<String> StickersReceived;
    private List<String> imgSent;

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

     //   if (!indicator) {
            senders  = new ArrayList<>();
            timeReceived  = new ArrayList<>();
            StickersReceived  = new ArrayList<>();
            imgSent = new ArrayList<>();
            user = new User();
      //  }

        // listener what message chatFragment. listens for a chat being sent. if received - onChildChanged. activate

        mDatabase.getReference().child("users").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                User user = snapshot.getValue(User.class);
                validUsernames.add(user.username);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                List<String> imgReceived = new ArrayList<>();
                List<String> senders  = new ArrayList<>();
                List<String> timeReceived  = new ArrayList<>();
                List<String> imgSent = new ArrayList<>();
                for(DataSnapshot ds: snapshot.getChildren()) {
                    String sender = (String) ds.child("senders").getValue();
                    String time = (String) ds.child("timeReceived").getValue();
                    String imgRe = (String) ds.child("stickersReceived").getValue();
                    String imgS = (String) ds.child("imgSent").getValue();

                    User user = snapshot.getValue(User.class);
//                    StickersReceived.add(String.valueOf(user.getImgReceived()));
//                    senders.add(String.valueOf(user.getSenderReceived()));
//                    timeReceived.add(String.valueOf(user.getTimeReceived()));
//                    imgSent.add(String.valueOf(user.getImgSent()));
                    StickersReceived.add(imgRe);
                    senders.add(String.valueOf(sender));
                    timeReceived.add(time);
                    imgSent.add(imgS);
                    validUsernames.add(user.username);
                }
//                user.setImgReceived(StickersReceived);
//                user.setTimeReceived(timeReceived);
//                user.setSenderReceived(senders);
//                user.setImgSent(imgSent);
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
//                    Date currentTime = Calendar.getInstance().getTime();
//                    senders.add(username);
//                    timeReceived.add(currentTime);
//                    StickersReceived.add(String.valueOf(imageUri));
//                    sendImage(recipientUsername, username, imageUri);
//                    toastDisplay("Successfully sent!");
//                    //sendNotification();
                    indicator = true;
                    uploadFile();
//                    user.setImgReceived(StickersReceived);
//                    user.setTimeReceived(timeReceived);
//                    user.setSenderReceived(senders);
//                    user.setImgSent(imgSent);
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

    public String getImageExtension(Uri imageUri) {
        ContentResolver c = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(c.getType(imageUri));
    }

    public void uploadFile() {
        if (imageUri != null) {
            StorageReference fileReference = mStorageRef.child(System.currentTimeMillis() + "." + getImageExtension(imageUri));

            fileReference.putFile(imageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            toastDisplay("Successfully sent!");
                            StickersReceived.add(taskSnapshot.getUploadSessionUri().toString());
                            Log.i("stickers received list", String.valueOf(StickersReceived));
                            //user.setImgReceived(StickersReceived);
//                            user.setImgReceived(StickersReceived);
                            Date currentTime = Calendar.getInstance().getTime();
                            timeReceived.add(currentTime.toString());
                           // user.setTimeReceived(timeReceived);
//                            user.setTimeReceived(timeReceived);
                            senders.add(username);
                            //user.setSenderReceived(senders);
//                            user.setSenderReceived(senders);
//                            if (!imgSent.containsKey(taskSnapshot.getUploadSessionUri().toString())) {
//                                imgSent.put(taskSnapshot.getUploadSessionUri().toString(), Integer.valueOf("1"));
//                            } else {
//                                imgSent.put(taskSnapshot.getUploadSessionUri().toString(), imgSent.get(taskSnapshot.getUploadSessionUri().toString()) + 1);
//                            }
//
                            imgSent.add(taskSnapshot.getUploadSessionUri().toString());
                            //user.setImgSent(imgSent);
                            // , timeReceived, senders, imgSent
                            //User newUser = new User(username, user.getToken(), StickersReceived);
//                            Intent intent = new Intent(getApplicationContext(), ImageAdapter.class);
//                            intent.putExtra("senders list", (Serializable) senders); // ssecond parameter is list so need to serialize
//                            intent.putExtra("received stickers list", (Serializable) StickersReceived);
//                            intent.putExtra("time list", (Serializable) timeReceived);
//                            startActivity(intent);

                            mDatabaseRef.child("users").child(recipientUsername).child("Senders").push().setValue(senders);
                            mDatabaseRef.child("users").child(recipientUsername).child("stickersReceived").push().setValue(StickersReceived);
                            mDatabaseRef.child("users").child(recipientUsername).child("timeReceived").push().setValue(timeReceived);
                            mDatabaseRef.child("users").child(username).child("imgSent").push().setValue(imgSent);


//                            user.setImgReceived(StickersReceived);
//                            user.setTimeReceived(timeReceived);
//                            user.setSenderReceived(senders);
//                            user.setImgSent(imgSent);
//                            mDatabaseRef.child("users").child(recipientUsername).push().setValue(user);

                            //String uploadID = mDatabaseRef.push().getKey();
                            //mDatabaseRef.child(uploadID).setValue(user);
                        }
                    });
            }
        }



//    public void sendImage(String recipientUsername, String username, Uri selectedImgView) {
//        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
//        recipientUsername = recipientText.getText().toString();
////        Log.i("username", recipientUsername);
////        Log.i("senders", String.valueOf(senders));
//        //String img = imageUri.toString().replaceAll("[^a-zA-Z0-9]", "");
//        if (!imgSent.containsKey(imageUri.toString())) {
//            imgSent.put(imageUri.toString(), Integer.valueOf("1"));
//        } else {
//            imgSent.put(imageUri.toString(), Integer.valueOf(imgSent.get(imageUri.toString()) + 1));
//        }
//        reference.child("users").child(recipientUsername).child("Senders").push().setValue(senders); // generate a unique key
//        reference.child("users").child(recipientUsername).child("timeReceived").push().setValue(timeReceived);
//        reference.child("users").child(recipientUsername).child("stickersReceived").push().setValue(StickersReceived);
//        reference.child("users").child(username).child("imgSent").setValue(imgSent); // imgSent - map
//    }

//
    public void sendNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("the notification", "my notification", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
        NotificationCompat.Builder builder = new NotificationCompat.Builder(SendStickerActivity.this, "the notification");
        builder.setContentTitle("Sticker received");
        builder.setContentText("You received a sticker, please have a look at it!");

        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(SendStickerActivity.this);
        managerCompat.notify(0, builder.build());
    }
//


//    public void notificationChannel() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            NotificationChannel channel = new NotificationChannel("the notification", "my notification", NotificationManager.IMPORTANCE_DEFAULT);
//            NotificationManager notificationManager = getSystemService(NotificationManager.class);
//            notificationManager.createNotificationChannel(channel);
//        }
//    }

}
