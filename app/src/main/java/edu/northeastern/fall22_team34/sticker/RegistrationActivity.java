package edu.northeastern.fall22_team34.sticker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;

import edu.northeastern.fall22_team34.R;
import edu.northeastern.fall22_team34.sticker.models.User;

public class RegistrationActivity extends AppCompatActivity {

    private FirebaseDatabase mDatabase;
    protected String REGISTRATION_TOKEN;

    private EditText mEditText;
    private String username;
    private Button mRegisterBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && task.getResult() != null) {
                        mDatabase = FirebaseDatabase.getInstance();
                        REGISTRATION_TOKEN = task.getResult();
                    }
                });

        mEditText = findViewById(R.id.username_text);
        mRegisterBtn = findViewById(R.id.registerUserBtn);

        Intent selectReceiverActivity = new Intent(getApplicationContext(), SendStickerActivity.class);
        selectReceiverActivity.putExtra("USERNAME", username);
        selectReceiverActivity.putExtra("REGISTRATION_TOKEN", REGISTRATION_TOKEN);

        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mEditText.getText().toString().isEmpty()) {
                    username = mEditText.getText().toString();
                    createUser(username, REGISTRATION_TOKEN);
                    startActivity(selectReceiverActivity);
                } else {
                    Toast.makeText(getApplicationContext(), "Username cannot be empty!!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void createUser(String username, String token) {
        User user = new User(username, token);
        mDatabase.getReference().child("users").child(username).setValue(user);
    }
}