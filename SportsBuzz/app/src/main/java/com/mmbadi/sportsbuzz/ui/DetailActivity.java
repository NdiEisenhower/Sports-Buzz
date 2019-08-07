package com.mmbadi.sportsbuzz.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.bumptech.glide.Glide;
import com.mmbadi.sportsbuzz.R;
import com.mmbadi.sportsbuzz.models.Article;
import com.mmbadi.sportsbuzz.models.Sport;
import com.mmbadi.sportsbuzz.services.ApiService;
import com.mmbadi.sportsbuzz.services.NetworkReceiver;
import com.mmbadi.sportsbuzz.services.RetroClient;
import com.mmbadi.sportsbuzz.utills.DateHelper;
import com.mmbadi.sportsbuzz.utills.NetworkUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.mmbadi.sportsbuzz.services.NetworkReceiver.NETWORK_LISTENER;

public class DetailActivity extends AppCompatActivity implements Callback<Article> {
    private final String TAG = DetailActivity.class.getSimpleName();
    private Sport sport;
    private AppCompatTextView textViewArticleHeadline, textViewArticleUpdatedDate, textViewArticleBody, textViewArticleAuthor;
    private AppCompatImageView imageViewArticleImage;
    private int NETWORK_CURRENT = -1;
    private ApiService apiService;
    private AlertDialog alertDialog;
    private Article article;
    private String detailsDateFormat = "yyy-MM-dd HH:mm";

    private NetworkReceiver networkReceiver = new NetworkReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            NETWORK_CURRENT = NetworkUtils.getNetworkStatus(context);

            if (article != null) {
                fetchArticle();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        apiService = RetroClient.getApiService();
        initializeViews();

        Intent intent = getIntent();
        if (intent != null) {
            sport = (Sport) intent.getSerializableExtra(MainActivity.SELECTED_SPORT);
        }
        fetchArticle();
    }

    public void showAlertDialog(String title, CharSequence message) {
        if (alertDialog != null && !alertDialog.isShowing()) {
            try {

                alertDialog = new AlertDialog.Builder(this).create();
                alertDialog.setTitle(title);
                alertDialog.setMessage(message);
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.ok), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                if (!isFinishing()) {
                    alertDialog.show();
                }
            } catch (Exception e) {
                Log.d(TAG, "Show Dialog: " + e.getMessage());
            }
        }

    }

    private void fetchArticle() {

        NETWORK_CURRENT = NetworkUtils.getNetworkStatus(this);

        if (NETWORK_CURRENT == NetworkReceiver.TYPE_NO_NETWORK) {
            showAlertDialog(getString(R.string.info), getString(R.string.no_network_connection));
        } else if (sport != null && apiService != null) {
            String siteName = sport.getSiteName();
            String urlName = sport.getUrlName();
            String urlFriendlyDate = sport.getUrlFriendlyDate();
            String urlFriendlyHeadline = sport.getUrlFriendlyHeadline();

            if (!TextUtils.isEmpty(siteName) && !TextUtils.isEmpty(urlName) && !TextUtils.isEmpty(urlFriendlyDate) && !TextUtils.isEmpty(urlFriendlyHeadline)) {

                Call<Article> article = apiService.getArtcile(siteName, urlName, urlFriendlyDate, urlFriendlyHeadline);
                article.enqueue(this);
            }
        }
    }

    private void initializeViews() {
        textViewArticleHeadline = findViewById(R.id.article_headline);
        textViewArticleUpdatedDate = findViewById(R.id.article_date);
        textViewArticleBody = findViewById(R.id.article_body);
        textViewArticleAuthor = findViewById(R.id.article_author);
        imageViewArticleImage = findViewById(R.id.article_image);

    }

    private void setUpViews() {

        if (article == null) {

        } else {
            String headline = article.getHeadline();
            String updatedDate = article.getUpdatedDate();
            Spanned body = Html.fromHtml(article.getBody());
            String image = article.getImageUrlLocal() + article.getLargeImageName();
            String imageDescription = sport.getExtraImageAlt();
            String author = sport.getAuthor();
            long date = DateHelper.extractDate(updatedDate);
            updatedDate = DateHelper.getFormattedDate(date, detailsDateFormat);

            textViewArticleHeadline.setVisibility(TextUtils.isEmpty(headline) ? View.GONE : View.VISIBLE);
            textViewArticleUpdatedDate.setVisibility(TextUtils.isEmpty(updatedDate) ? View.GONE : View.VISIBLE);
            textViewArticleBody.setVisibility(TextUtils.isEmpty(body) ? View.GONE : View.VISIBLE);
            textViewArticleAuthor.setVisibility(TextUtils.isEmpty(author) ? View.GONE : View.VISIBLE);

            if (!TextUtils.isEmpty(imageDescription))
                imageViewArticleImage.setContentDescription(imageDescription);

            textViewArticleHeadline.setText(headline);
            textViewArticleUpdatedDate.setText(updatedDate);
            textViewArticleBody.setText(body);
            textViewArticleAuthor.setText(author);

            Glide.with(this)
                    .load(image)
                    .centerCrop()
                    .placeholder(R.drawable.placeholder)
                    .into(imageViewArticleImage);
        }

    }

    @Override
    public void onResponse(Call<Article> call, Response<Article> response) {

        if (response.isSuccessful()) {
            Article article = response.body();
            if (article != null) {
                this.article = article;
                setUpViews();
            }
        }

    }

    @Override
    public void onFailure(Call<Article> call, Throwable t) {
        showAlertDialog(getString(R.string.error_title), getString(R.string.error_try_again_later));
        t.printStackTrace();
    }

    @Override
    public void onResume() {
        super.onResume();

        LocalBroadcastManager.getInstance(this).registerReceiver(networkReceiver, new IntentFilter(NETWORK_LISTENER));
    }

    @Override
    public void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(networkReceiver);
    }
}
