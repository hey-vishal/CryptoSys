package com.cryptosys.network;

import com.cryptosys.models.CryptoCurrency;
import com.cryptosys.models.CryptoCurrencyItem;
import com.cryptosys.models.CurrencyDetail;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("/sapi/v1/tickers/24hr")
    Call<List<CryptoCurrencyItem>> getAllCurrencies();

    @GET("/sapi/v1/ticker/24hr")
    Call<CurrencyDetail> getCurrencyDetail(@Query("symbol") String symbol);
}
