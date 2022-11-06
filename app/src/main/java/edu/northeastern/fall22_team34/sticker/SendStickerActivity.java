package edu.northeastern.fall22_team34.sticker;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import edu.northeastern.fall22_team34.R;
import edu.northeastern.fall22_team34.sticker.models.User;

public class SendStickerActivity extends AppCompatActivity {

    private FirebaseDatabase mDatabase;

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
                            "You Cannot Send Stickers to Yourself", Toast.LENGTH_SHORT).show();
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
}