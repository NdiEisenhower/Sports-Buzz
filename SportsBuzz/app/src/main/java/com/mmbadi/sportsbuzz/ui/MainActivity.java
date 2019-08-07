package com.mmbadi.sportsbuzz.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.Toast;

import com.mmbadi.sportsbuzz.R;
import com.mmbadi.sportsbuzz.adapters.ArticleViewAdapter;
import com.mmbadi.sportsbuzz.models.Sport;
import com.mmbadi.sportsbuzz.services.ApiService;
import com.mmbadi.sportsbuzz.services.NetworkReceiver;
import com.mmbadi.sportsbuzz.services.RetroClient;
import com.mmbadi.sportsbuzz.utills.NetworkUtils;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.mmbadi.sportsbuzz.services.NetworkReceiver.NETWORK_LISTENER;

public class MainActivity extends AppCompatActivity implements ArticleViewAdapter.OnArticleClickedListener, Callback<List<Sport>>, SwipeRefreshLayout.OnRefreshListener {

    private final String TAG = MainActivity.class.getSimpleName();
    public static String SELECTED_SPORT = "selected_sport";
    private RecyclerView articleRecyclerView;
    private AppCompatTextView textViewPull;
    private SwipeRefreshLayout pullToRefresh;
    private ArticleViewAdapter articleViewAdapter;
    private int NETWORK_CURRENT = -1;
    private ApiService apiService;
    private AlertDialog alertDialog;
    private NetworkReceiver networkReceiver = new NetworkReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            NETWORK_CURRENT = NetworkUtils.getNetworkStatus(context);

            if (articleViewAdapter != null && articleViewAdapter.getItemCount() == 0) {
                fetchArticles();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        NETWORK_CURRENT = NetworkUtils.getNetworkStatus(this);

        articleRecyclerView = findViewById(R.id.sportsRecyclerView);
        articleViewAdapter = new ArticleViewAdapter(this);
        articleRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        articleViewAdapter.setOnArticleClickedListener(this);
        articleRecyclerView.setAdapter(articleViewAdapter);
        pullToRefresh = findViewById(R.id.pullToRefresh);
        textViewPull = findViewById(R.id.emptyView);

        apiService = RetroClient.getApiService();
        pullToRefresh.setRefreshing(false);
        pullToRefresh.setOnRefreshListener(this);
        fetchArticles();
    }

    private void fetchArticles() {
        if (apiService != null) {
            Call<List<Sport>> allArticles = apiService.getAllArticles();

            if (NETWORK_CURRENT == NetworkReceiver.TYPE_NO_NETWORK) {
                showPullTORefresh(true);
                pullToRefresh.setRefreshing(false);
                showAlertDialog(getString(R.string.info), getString(R.string.no_network_connection));
            } else {
                dismissAlertDialog();
                allArticles.enqueue(this);
            }
        }

    }

    private void showPullTORefresh(boolean show) {
        if (show) {
            textViewPull.setVisibility(View.VISIBLE);
            articleRecyclerView.setVisibility(View.GONE);
        } else {
            textViewPull.setVisibility(View.GONE);
            articleRecyclerView.setVisibility(View.VISIBLE);
            dismissAlertDialog();
        }
    }

    public void showAlertDialog(String title, CharSequence message) {
        if (alertDialog == null) {
            try {

                AlertDialog alertDialog = new AlertDialog.Builder(this).create();

                alertDialog.setTitle(title);
                alertDialog.setMessage(message);
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
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

    private void dismissAlertDialog() {

        if (alertDialog != null && alertDialog.isShowing()) {
            alertDialog.dismiss();
        }
    }

    @Override
    public void SelectedArticle(Sport sport) {

        if (NETWORK_CURRENT == NetworkReceiver.TYPE_NO_NETWORK) {
            showAlertDialog(getString(R.string.info), getString(R.string.no_network_connection));

        } else {
            dismissAlertDialog();
            String siteName = sport.getSiteName();
            String urlName = sport.getUrlName();
            String urlFriendlyDate = sport.getUrlFriendlyDate();
            String urlFriendlyHeadline = sport.getUrlFriendlyHeadline();

            if (!TextUtils.isEmpty(siteName) && !TextUtils.isEmpty(urlName) && !TextUtils.isEmpty(urlFriendlyDate) && !TextUtils.isEmpty(urlFriendlyHeadline)) {

                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                intent.putExtra(SELECTED_SPORT, sport);
                startActivity(intent);
            } else {
                showAlertDialog(getString(R.string.error_title), getString(R.string.error_on_click_article));
            }
        }

    }

    @Override
    public void onResponse(Call<List<Sport>> call, Response<List<Sport>> response) {
        pullToRefresh.setRefreshing(false);
        if (response.isSuccessful()) {
            List<Sport> sportList = response.body();
            if (sportList != null) {
                showPullTORefresh(false);
                articleViewAdapter.addArticles(sportList);
            }
        }
    }

    @Override
    public void onFailure(Call<List<Sport>> call, Throwable t) {
        t.printStackTrace();
        showPullTORefresh(true);
        pullToRefresh.setRefreshing(false);
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

    @Override
    public void onRefresh() {
        fetchArticles();
    }
}
