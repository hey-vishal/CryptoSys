package com.cryptosys.currencyDetail;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.cryptosys.R;
import com.cryptosys.models.CurrencyDetail;
import com.squareup.picasso.Picasso;
import java.util.Locale;

public class CurrencyDetailActivity extends AppCompatActivity implements CurrencyDetailContract.View,
        SwipeToRefreshListener {

    private DetailPresenter detailPresenter;
    private TextView buyPriceLabel,buyPrice,lowPrice,highPrice,openPrice,volume,bidPrice,askPrice,percentTxt;
    private ProgressBar progressBar;
    private LinearLayout dataLayout;
    private SwipeRefreshLayout refreshLayout;
    private ImageView currencyIcon,indicatorIcon;
    private String currencyName,currencyAsset,symbol,openPriceStr, lastPrice;
    private final Handler handler = new Handler();
    private final Runnable runnable = new Runnable() {
        @Override
        public void run() {
            detailPresenter.refreshData(symbol);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currency_detail);

        initUI();

        Intent intent = getIntent();
        currencyName = intent.getStringExtra("baseAsset");
        currencyAsset = intent.getStringExtra("quoteAsset");
        symbol = intent.getStringExtra("symbol");

        getSupportActionBar().setTitle(currencyName.toUpperCase(Locale.ROOT));

        detailPresenter = new DetailPresenter(this);
        detailPresenter.loadDataFromServer(symbol);
    }

    private void initUI()
    {
        dataLayout = findViewById(R.id.dataLayout);
        progressBar = findViewById(R.id.progressBar);
        currencyIcon = findViewById(R.id.logoImg);
        indicatorIcon = findViewById(R.id.indicatorImg);
        percentTxt = findViewById(R.id.percentTxt);
        buyPriceLabel = findViewById(R.id.curTitle);
        buyPrice = findViewById(R.id.curPrice);
        lowPrice = findViewById(R.id.lowPriceVal);
        highPrice = findViewById(R.id.highPriceVal);
        openPrice = findViewById(R.id.openPriceTxt);
        volume = findViewById(R.id.volumeTxt);
        bidPrice = findViewById(R.id.bidPriceTxt);
        askPrice = findViewById(R.id.askPriceTxt);
        refreshLayout = findViewById(R.id.refreshLayout);

        refreshLayout.setColorSchemeResources(R.color.teal_700,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshLayout.setRefreshing(false);
                onListRefresh();
            }
        });
    }

    @Override
    public void onListRefresh() {
        detailPresenter.refreshData(symbol);
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
    public void showLayout() {
        dataLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLayout() {
        dataLayout.setVisibility(View.GONE);
    }

    @Override
    public void setDataToViews(CurrencyDetail currencyDetail) {

        buyPriceLabel.setText("Current Buy Price ("+currencyAsset.toUpperCase(Locale.ROOT)+")");
        buyPrice.setText(currencyDetail.getLastPrice());
        lowPrice.setText(currencyDetail.getLowPrice());
        highPrice.setText(currencyDetail.getHighPrice());
        openPrice.setText(currencyDetail.getOpenPrice());
        volume.setText(currencyDetail.getVolume());
        bidPrice.setText(currencyDetail.getBidPrice());
        askPrice.setText(currencyDetail.getAskPrice());

        float currentPrice = Float.parseFloat(currencyDetail.getLastPrice());
        float openPrice1 = Float.parseFloat(currencyDetail.getOpenPrice());
        float percent = ((currentPrice - openPrice1)/openPrice1)*100;
        percentTxt.setText(String.format("%.2f",percent)+"%");

        if (percent > 0.0)
        {
            percentTxt.setTextColor(Color.GREEN);
            indicatorIcon.setVisibility(View.VISIBLE);
            Picasso.get().load(R.drawable.ic_up)
                    .into(indicatorIcon);

        }
        else if(percent < 0.0)
        {
            percentTxt.setTextColor(Color.RED);
            indicatorIcon.setVisibility(View.VISIBLE);
            Picasso.get().load(R.drawable.ic_down)
                    .into(indicatorIcon);
        }
        else
        {
            percentTxt.setTextColor(R.color.colorText);
            indicatorIcon.setVisibility(View.GONE);
        }

        if (currencyName.equals("btc"))
        {
            Picasso.get().load(R.drawable.bitcoin)
                    .into(currencyIcon);
        }
        else if (currencyName.equals("eth"))
        {
            Picasso.get().load(R.drawable.ethereum)
                    .into(currencyIcon);
        }
        else if (currencyName.equals("bnb"))
        {
            Picasso.get().load(R.drawable.binance)
                    .into(currencyIcon);
        }
        else if (currencyName.equals("usdt"))
        {
            Picasso.get().load(R.drawable.tether)
                    .into(currencyIcon);
        }
        else if (currencyName.equals("xrp"))
        {
            Picasso.get().load(R.drawable.ripple)
                    .into(currencyIcon);
        }
        else if (currencyName.equals("dash"))
        {
            Picasso.get().load(R.drawable.dash)
                    .into(currencyIcon);
        }
        else if (currencyName.equals("doge"))
        {
            Picasso.get().load(R.drawable.dogecoin)
                    .into(currencyIcon);
        }
        else if (currencyName.equals("ltc"))
        {
            Picasso.get().load(R.drawable.litecoin)
                    .into(currencyIcon);
        }
        else if (currencyName.equals("shib"))
        {
            Picasso.get().load(R.drawable.shiba)
                    .into(currencyIcon);
        }
        else if (currencyName.equals("trx"))
        {
            Picasso.get().load(R.drawable.tron)
                    .into(currencyIcon);
        }
        else
        {
            Picasso.get().load(R.drawable.cryptocurrency)
                    .into(currencyIcon);
        }

        handler.postDelayed(runnable,3000);
    }

    @Override
    public void onResponseFailure(Throwable throwable) {
        Log.e("DetailActivityError", throwable.getMessage());
        Toast.makeText(this, "Oops! Something went wrong", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        handler.removeCallbacks(runnable);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(runnable);
        detailPresenter.onDestroy();
    }
}