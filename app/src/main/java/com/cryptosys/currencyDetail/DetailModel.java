package com.cryptosys.currencyDetail;

import android.util.Log;
import com.cryptosys.models.CurrencyDetail;
import com.cryptosys.network.ApiClient;
import com.cryptosys.network.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailModel implements CurrencyDetailContract.Model{

    @Override
    public void getCurrencyDetails(OnFinishedListener onFinishedListener, String symbol) {

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        Call<CurrencyDetail> call = apiService.getCurrencyDetail(symbol);

        call.enqueue(new Callback<CurrencyDetail>() {
            @Override
            public void onResponse(Call<CurrencyDetail> call, Response<CurrencyDetail> response) {

                CurrencyDetail currencyDetail = response.body();
                Log.d("CurrencyDetail", "Currency Detail: " + currencyDetail.toString());
                onFinishedListener.onFinished(currencyDetail);
            }

            @Override
            public void onFailure(Call<CurrencyDetail> call, Throwable t) {
                Log.e("CurrencyDetail", t.toString());
                onFinishedListener.onFailure(t);
            }
        });
    }
}
