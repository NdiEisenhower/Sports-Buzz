package com.mmbadi.sportsbuzz.ui;

import android.content.Intent;
import android.os.Bundle;

import com.mmbadi.sportsbuzz.R;
import com.mmbadi.sportsbuzz.adapters.ArticleViewAdapter;
import com.mmbadi.sportsbuzz.models.Sport;
import com.mmbadi.sportsbuzz.services.ApiService;
import com.mmbadi.sportsbuzz.services.RetroClient;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements ArticleViewAdapter.OnArticleClickedListener, Callback<List<Sport>> {


    public static String SELECTED_SPORT = "selected_sport";
    private RecyclerView articleRecyclerView;
    private ArticleViewAdapter articleViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        articleRecyclerView = findViewById(R.id.sportsRecyclerView);
        articleViewAdapter = new ArticleViewAdapter(this);
        articleRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        articleViewAdapter.setOnArticleClickedListener(this);
        articleRecyclerView.setAdapter(articleViewAdapter);

        ApiService api = RetroClient.getApiService();
        Call<List<Sport>> allArticles = api.getAllArticles();

        allArticles.enqueue(this);

    }

    @Override
    public void SelectedArticle(Sport sport) {

        Intent intent = new Intent(MainActivity.this, DetailActivity.class);
        intent.putExtra(SELECTED_SPORT, sport);
        startActivity(intent);

    }

    @Override
    public void onResponse(Call<List<Sport>> call, Response<List<Sport>> response) {
        if (response.isSuccessful()) {
            List<Sport> sportList = response.body();
            if (sportList != null) {
                articleViewAdapter.addArticles(sportList);
            }
        }
    }

    @Override
    public void onFailure(Call<List<Sport>> call, Throwable t) {
        t.printStackTrace();
    }
}
