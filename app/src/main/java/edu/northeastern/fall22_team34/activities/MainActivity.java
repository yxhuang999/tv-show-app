package edu.northeastern.fall22_team34.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import edu.northeastern.fall22_team34.R;
import edu.northeastern.fall22_team34.viewmodels.MostPopularTVShowsViewModel;

public class MainActivity extends AppCompatActivity {

    private Button TVShowsButton;

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
    }
}