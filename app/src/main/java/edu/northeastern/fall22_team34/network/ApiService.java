package edu.northeastern.fall22_team34.network;

import edu.northeastern.fall22_team34.resposes.TVShowsResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    @GET("most-popular")
    Call<TVShowsResponse> getMostPopularTVShows(@Query("page") int page);

    @GET("search")
    Call<TVShowsResponse> searchTVShow(@Query("q") String query, @Query("page") int page);
}
