package com.example.testprogram.retrofit;

import com.example.testprogram.model.SearchItem;

import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface NetworkAPIService {
    @GET("/api/v1/search_by_date?tags=story")
    Observable<Response<SearchItem>> getPosts(@Query("page") int pageNum);
}
