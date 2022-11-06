package edu.northeastern.fall22_team34.sticker;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
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

import com.squareup.picasso.Picasso;

import edu.northeastern.fall22_team34.R;
import edu.northeastern.fall22_team34.sticker.models.User;

public class SendStickerActivity extends AppCompatActivity {

    private User user;
    private String username;

    private EditText recipientText;
    private String recipientUsername;
    private Button btnChooseImg;
    private ImageView selectedImgView;
    private Button btnSend;

    private Uri imageUri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_sticker);

        username = getIntent().getStringExtra("USERNAME");

        recipientText = findViewById(R.id.recipient_text);
        recipientUsername = recipientText.getText().toString();
        btnChooseImg = findViewById(R.id.btnChooseImg);
        selectedImgView = findViewById(R.id.selectedImg);
        btnSend = findViewById(R.id.btnSend);

        btnChooseImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!recipientText.getText().toString().isEmpty()) {
                    openFileSelector();
                } else {
                    Toast.makeText(getApplicationContext(), "Enter the Recipient's Username First!!!", Toast.LENGTH_SHORT).show();
                }
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