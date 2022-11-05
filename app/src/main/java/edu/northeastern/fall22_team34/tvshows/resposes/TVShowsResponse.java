package edu.northeastern.fall22_team34.tvshows.resposes;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import edu.northeastern.fall22_team34.tvshows.models.TVShow;

public class TVShowsResponse {

    @SerializedName("page")
    private int page;

    @SerializedName("pages")
    private int totalPages;

    @SerializedName("tv_shows")
    private List<TVShow> tvShows;

    public int getPage() {
        return page;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public List<TVShow> getTvShows() {
        return tvShows;
    }
}
