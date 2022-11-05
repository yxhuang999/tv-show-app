package edu.northeastern.fall22_team34.tvshows.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import edu.northeastern.fall22_team34.R;
import edu.northeastern.fall22_team34.tvshows.adapters.TVShowsAdapter;
import edu.northeastern.fall22_team34.databinding.ActivityTvshowsBinding;
import edu.northeastern.fall22_team34.tvshows.models.TVShow;
import edu.northeastern.fall22_team34.tvshows.viewmodels.MostPopularTVShowsViewModel;

public class TVShowsActivity extends AppCompatActivity {

    private ActivityTvshowsBinding activityTvshowsBinding;
    private MostPopularTVShowsViewModel viewModel;
    private List<TVShow> tvShows = new ArrayList<>();
    private TVShowsAdapter tvShowsAdapter;
    private int currentPage = 1;
    private int totalAvailablePages = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityTvshowsBinding = DataBindingUtil.setContentView(this, R.layout.activity_tvshows);
        //setContentView(R.layout.activity_tvshows);

        doInitialization();
    }

    private void doInitialization() {
        activityTvshowsBinding.tvShowRecyclerView.setHasFixedSize(true);
        viewModel = new ViewModelProvider(this).get(MostPopularTVShowsViewModel.class);
        tvShowsAdapter = new TVShowsAdapter(tvShows);
        activityTvshowsBinding.tvShowRecyclerView.setAdapter(tvShowsAdapter);

        activityTvshowsBinding.tvShowRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (!activityTvshowsBinding.tvShowRecyclerView.canScrollVertically(1)) {
                    if (currentPage < totalAvailablePages) {
                        currentPage += 1;
                        getMostPopularTVShows();
                    }
                }
            }
        });

        activityTvshowsBinding.imageSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), SearchActivity.class));
            }
        });
        getMostPopularTVShows();
    }

    private void getMostPopularTVShows() {
        toggleLoading();

        viewModel.getMostPopularTVShows(currentPage).observe(this, mostPopularTVShowsResponse -> {
            toggleLoading();
            if (mostPopularTVShowsResponse != null) {
                totalAvailablePages = mostPopularTVShowsResponse.getTotalPages();

                if (mostPopularTVShowsResponse.getTvShows() != null) {
                    int oldCount = tvShows.size();
                    tvShows.addAll(mostPopularTVShowsResponse.getTvShows());
                    tvShowsAdapter.notifyItemRangeInserted(oldCount, tvShows.size());
                }
            }
        });
    }

    private void toggleLoading() {
        if (currentPage == 1) {
            if (activityTvshowsBinding.getIsLoading() != null && activityTvshowsBinding.getIsLoading()) {
                activityTvshowsBinding.setIsLoading(false);
            } else {
                activityTvshowsBinding.setIsLoading(true);
            }
        } else {
            if (activityTvshowsBinding.getIsLoadingMore() != null && activityTvshowsBinding.getIsLoadingMore()) {
                activityTvshowsBinding.setIsLoadingMore(false);
            } else {
                activityTvshowsBinding.setIsLoadingMore(true);
            }
        }
    }
}