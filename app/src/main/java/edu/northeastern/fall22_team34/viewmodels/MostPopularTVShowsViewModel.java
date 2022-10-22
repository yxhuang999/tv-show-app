package edu.northeastern.fall22_team34.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import edu.northeastern.fall22_team34.repositories.MostPopularTVShowsRepository;
import edu.northeastern.fall22_team34.resposes.TVShowsResponse;

public class MostPopularTVShowsViewModel extends ViewModel {

    private MostPopularTVShowsRepository mostPopularTVShowsRepository;

    public MostPopularTVShowsViewModel() {
        mostPopularTVShowsRepository = new MostPopularTVShowsRepository();
    }

    public LiveData<TVShowsResponse> getMostPopularTVShows(int page) {
        return mostPopularTVShowsRepository.getMostPopularTVShows(page);
    }
}
