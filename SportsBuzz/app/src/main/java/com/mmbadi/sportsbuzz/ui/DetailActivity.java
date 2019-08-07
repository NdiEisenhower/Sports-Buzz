package com.mmbadi.sportsbuzz.ui;

import android.content.Intent;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.mmbadi.sportsbuzz.R;
import com.mmbadi.sportsbuzz.models.Article;
import com.mmbadi.sportsbuzz.models.Sport;
import com.mmbadi.sportsbuzz.services.ApiService;
import com.mmbadi.sportsbuzz.services.RetroClient;
import com.mmbadi.sportsbuzz.utills.DateHelper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;

import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.View;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity implements Callback<Article> {

    private Sport sport;
    private AppCompatTextView textViewArticleHeadline, textViewArticleUpdatedDate, textViewArticleBody;
    private AppCompatImageView imageViewArticleImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            setTitle("News");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        initializeViews();

        Intent intent = getIntent();
        if (intent != null) {
            sport = (Sport) intent.getSerializableExtra(MainActivity.SELECTED_SPORT);
        }
        if (sport != null) {
            String siteName = sport.getSiteName();
            String urlName = sport.getUrlName();
            String urlFriendlyDate = sport.getUrlFriendlyDate();
            String urlFriendlyHeadline = sport.getUrlFriendlyHeadline();

            if (!TextUtils.isEmpty(siteName) && !TextUtils.isEmpty(urlName) && !TextUtils.isEmpty(urlFriendlyDate) && !TextUtils.isEmpty(urlFriendlyHeadline)) {
                ApiService api = RetroClient.getApiService();
                Call<Article> article = api.getArtcile(siteName, urlName, urlFriendlyDate, urlFriendlyHeadline);
                article.enqueue(this);
            }


        }


    }

    private void initializeViews() {
        textViewArticleHeadline = findViewById(R.id.article_headline);
        textViewArticleUpdatedDate = findViewById(R.id.article_date);
        textViewArticleBody = findViewById(R.id.article_body);
        imageViewArticleImage = findViewById(R.id.article_image);
    }

    private void setUpViews(Article article) {

        String headline = article.getHeadline();
        String updatedDate = article.getUpdatedDate();
        Spanned body = Html.fromHtml(article.getBody());
        String image = article.getImageUrlLocal() + article.getLargeImageName();

        long date = DateHelper.extractDate(updatedDate);
        updatedDate = DateHelper.getFormattedDate(date);

        textViewArticleHeadline.setVisibility(TextUtils.isEmpty(headline) ? View.GONE : View.VISIBLE);
        textViewArticleUpdatedDate.setVisibility(TextUtils.isEmpty(updatedDate) ? View.GONE : View.VISIBLE);
        textViewArticleBody.setVisibility(TextUtils.isEmpty(body) ? View.GONE : View.VISIBLE);


        textViewArticleHeadline.setText(headline);
        textViewArticleUpdatedDate.setText(updatedDate);
        textViewArticleBody.setText(body);


        Glide.with(this)
                .load(image)
                .centerCrop()
                .into(imageViewArticleImage);


    }

    @Override
    public void onResponse(Call<Article> call, Response<Article> response) {

        if (response.isSuccessful()) {
            Article article = response.body();
            if (article != null) {
                setUpViews(article);
            }
        }

    }

    @Override
    public void onFailure(Call<Article> call, Throwable t) {
        t.printStackTrace();
    }
}
