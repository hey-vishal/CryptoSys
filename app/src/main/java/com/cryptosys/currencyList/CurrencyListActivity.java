package com.cryptosys.currencyList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;
import com.cryptosys.R;
import com.cryptosys.adapters.CurrencyListAdapter;
import com.cryptosys.currencyDetail.CurrencyDetailActivity;
import com.cryptosys.models.CryptoCurrencyItem;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CurrencyListActivity extends AppCompatActivity implements CurrencyListContract.View,
        SwipeToRefreshListener, CurrencyItemClickListener {

    private ListPresenter listPresenter;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private List<CryptoCurrencyItem> currencyItemList;
    private CurrencyListAdapter adapter;
    private ProgressBar progressBar;
    private final Handler handler = new Handler();
    private final Runnable runnable = new Runnable() {
        @Override
        public void run() {
            listPresenter.refreshCurrencyList();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setTitle(R.string.app_name);
        initUI();
        listPresenter = new ListPresenter(this);
        listPresenter.loadDataFromServer();
    }

    private void initUI()
    {
        recyclerView = findViewById(R.id.recyclerView);
        swipeRefreshLayout = findViewById(R.id.refreshLayout);
        progressBar = findViewById(R.id.progressBar);
        currencyItemList = new ArrayList<>();
        adapter = new CurrencyListAdapter(this,currencyItemList);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        swipeRefreshLayout.setColorSchemeResources(R.color.teal_700,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
                onListRefresh();
            }
        });
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void setDataToRecyclerView(List<CryptoCurrencyItem> currencyItems) {
        if (!currencyItemList.isEmpty())
        {
            currencyItemList.clear();
        }
        currencyItemList.addAll(currencyItems);
        adapter.notifyDataSetChanged();

        handler.postDelayed(runnable,3000);
    }

    @Override
    public void onListRefresh() {
        listPresenter.refreshCurrencyList();
    }

    @Override
    public void onCurrencyListItemClick(int position) {

        Intent i = new Intent(this, CurrencyDetailActivity.class);
        i.putExtra("baseAsset",currencyItemList.get(position).getBaseAsset());
        i.putExtra("quoteAsset",currencyItemList.get(position).getQuoteAsset());
        i.putExtra("symbol",currencyItemList.get(position).getSymbol());
        startActivity(i);
    }

    @Override
    public void onResponseFailure(Throwable throwable) {
        Log.e("ListActivityError", throwable.getMessage());
        Toast.makeText(this, "Oops! Something went wrong", Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);

        MenuItem searchViewItem = menu.findItem(R.id.search_menu);
        SearchView searchView = (SearchView) searchViewItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                filter(s);
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    private void filter(String searchText)
    {
        ArrayList<CryptoCurrencyItem> filteredList = new ArrayList<>();

        for (CryptoCurrencyItem currencyItem:currencyItemList) {
            if (currencyItem.getBaseAsset().contains(searchText.toLowerCase(Locale.ROOT)))
            {
                filteredList.add(currencyItem);
            }
        }

        if (filteredList.isEmpty())
        {
            Toast.makeText(this, "No Data Found..", Toast.LENGTH_SHORT).show();
        }
        else
        {
            adapter.filterList(filteredList);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(runnable);
        listPresenter.onDestroy();
    }
}