package com.example.testprogram;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;

import com.example.testprogram.model.Hit;
import com.example.testprogram.model.SearchItem;
import com.example.testprogram.retrofit.NetworkAPIService;

import java.io.IOException;
import java.util.ArrayList;


import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.android.schedulers.AndroidSchedulers;


public class MainActivity extends AppCompatActivity {


    SearchItem searchItem;
    NetworkAPIService mAPIService;
    RecyclerView list;
    TextView tv;
    PostsAdapter adapter;
    ArrayList<Hit> hits = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Toolbar toolbar = findViewById(R.id.toolbar);
        tv = toolbar.findViewById(R.id.toolbar_title);
        setSupportActionBar(toolbar);
        list =  findViewById(R.id.list_rv);
        adapter = new PostsAdapter(R.layout.list_item, hits);
        list.setAdapter(adapter);
        list.setLayoutManager(new LinearLayoutManager(this));

//        initializeLayout(list);
        buildRetrofitService();
        mAPIService.getPosts(1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<retrofit2.Response<SearchItem>>() {
                    @Override
                    public void call(retrofit2.Response<SearchItem> response) {
                        if (response.isSuccessful()) {
                            searchItem = response.body();
                            adapter.setHits(searchItem.getHits());
                            adapter.notifyDataSetChanged();
                            setTitle();
                        }
                    }
                });


    }
    private void buildRetrofitService() {
        Interceptor headerInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();

                Request request = original.newBuilder()
                        .method(original.method(), original.body())
                        .build();

                return chain.proceed(request);
            }
        };

        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder().addInterceptor(headerInterceptor);

        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                @Override
                public void log(String message) {
                    Log.i(MainActivity.class.getName(), message);
                }
            });
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            clientBuilder.addInterceptor(interceptor);
        }

        OkHttpClient client = clientBuilder.build();

        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://hn.algolia.com")
                .client(client)
                .build();

        mAPIService = retrofit.create(NetworkAPIService.class);
    }
    private void initializeLayout(RecyclerView list){
        int visibleThreshold  = 5;
        final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) list.getLayoutManager();
        list.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
//                totalItemCount = linearLayoutManager.getItemCount();
//                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
//                if (totalItemCount <= (lastVisibleItem + visibleThreshold)) {
//                    if (onLoadMoreListener != null) {
//                        onLoadMoreListener.onLoadMore();
//                    }
//                }
            }
        });
    }
    private void setTitle(){
        if(searchItem !=null){
            tv.setText(searchItem.getHits().size());

        }
    }

    private void setData(SearchItem data){
        adapter = new PostsAdapter(R.layout.list_item, data.getHits());
        list.setAdapter(adapter);

    }

}
