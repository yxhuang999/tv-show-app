package edu.northeastern.fall22_team34.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import edu.northeastern.fall22_team34.repositories.SearchTVShowRepository;
import edu.northeastern.fall22_team34.resposes.TVShowsResponse;

public class SearchViewModel extends ViewModel {

    private SearchTVShowRepository searchTVShowRepository;

    public SearchViewModel() {
        searchTVShowRepository = new SearchTVShowRepository();
    }

    public LiveData<TVShowsResponse> searchTVShow(String query, int page) {
        return searchTVShowRepository.searchTVShow(query, page);
    }
}
