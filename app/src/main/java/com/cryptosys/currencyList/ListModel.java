package com.cryptosys.currencyList;

import android.util.Log;
import androidx.annotation.NonNull;
import com.cryptosys.models.CryptoCurrency;
import com.cryptosys.models.CryptoCurrencyItem;
import com.cryptosys.network.ApiClient;
import com.cryptosys.network.ApiInterface;
import java.util.List;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListModel implements CurrencyListContract.Model {

    @Override
    public void getCurrencyList(OnFinishedListener onFinishedListener) {

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        Call<List<CryptoCurrencyItem>> call = apiService.getAllCurrencies();
        call.enqueue(new Callback<List<CryptoCurrencyItem>>() {
            @Override
            public void onResponse(@NonNull Call<List<CryptoCurrencyItem>> call, @NonNull Response<List<CryptoCurrencyItem>> response) {

                List<CryptoCurrencyItem> currencyItemList = response.body();
                /*Log.d("CurrencyList", "Response: " + response.body().toString());
                Log.d("CurrencyList", "Size of list: " + currencyItemList.size());*/
                onFinishedListener.onFinished(currencyItemList);
            }

            @Override
            public void onFailure(@NonNull Call<List<CryptoCurrencyItem>> call, @NonNull Throwable t) {
                Log.e("ListModelError", t.toString());
                onFinishedListener.onFailure(t);
            }
        });
    }

}
