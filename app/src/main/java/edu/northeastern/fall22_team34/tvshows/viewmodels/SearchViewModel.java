package edu.northeastern.fall22_team34.tvshows.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import edu.northeastern.fall22_team34.tvshows.repositories.SearchTVShowRepository;
import edu.northeastern.fall22_team34.tvshows.resposes.TVShowsResponse;

public class SearchViewModel extends ViewModel {

    private SearchTVShowRepository searchTVShowRepository;

    public SearchViewModel() {
        searchTVShowRepository = new SearchTVShowRepository();
    }

    public LiveData<TVShowsResponse> searchTVShow(String query, int page) {
        return searchTVShowRepository.searchTVShow(query, page);
    }
}
