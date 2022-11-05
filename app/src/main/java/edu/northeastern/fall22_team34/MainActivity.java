package edu.northeastern.fall22_team34;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import edu.northeastern.fall22_team34.sticker.ShareStickerActivity;
import edu.northeastern.fall22_team34.tvshows.activities.TVShowsActivity;

public class MainActivity extends AppCompatActivity {

    private Button TVShowsButton;
    private Button StickerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       TVShowsButton = findViewById(R.id.EnterTVShowsButton);
       TVShowsButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent intent = new Intent(MainActivity.this, TVShowsActivity.class);
               startActivity(intent);
           }
       });

       StickerButton = findViewById(R.id.EnterStickerButton);
       StickerButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent = new Intent(MainActivity.this, ShareStickerActivity.class);
               startActivity(intent);
           }
       });
    }
}