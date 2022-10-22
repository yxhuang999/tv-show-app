package edu.northeastern.fall22_team34.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.widget.Toast;

import edu.northeastern.fall22_team34.R;
import edu.northeastern.fall22_team34.viewmodels.MostPopularTVShowsViewModel;

public class TVShowsActivity extends AppCompatActivity {

    private MostPopularTVShowsViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tvshows);

        viewModel = new ViewModelProvider(this).get(MostPopularTVShowsViewModel.class);

        getMostPopularTVShows();
    }

    private void getMostPopularTVShows() {
        viewModel.getMostPopularTVShows(0).observe(this, mostPopularTVShowsResponse ->
                Toast.makeText(getApplicationContext(),
                        "Total Pages: " + mostPopularTVShowsResponse.getTotalPages(),
                        Toast.LENGTH_SHORT).show()
        );
    }
}