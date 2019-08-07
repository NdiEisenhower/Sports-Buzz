package com.mmbadi.sportsbuzz.services;

import com.mmbadi.sportsbuzz.models.Article;
import com.mmbadi.sportsbuzz.models.Sport;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiService {
    @GET("news?format=json")
    Call<List<Sport>> getAllArticles();

    @GET("{siteName}/{urlName}/news/{urlFriendlyDate}/{urlFriendlyHeadline}?format=json")
    Call<Article> getArtcile(@Path("siteName") String siteName, @Path("urlName") String urlName, @Path("urlFriendlyDate") String urlFriendlyDate, @Path("urlFriendlyHeadline") String urlFriendlyHeadline);
}
