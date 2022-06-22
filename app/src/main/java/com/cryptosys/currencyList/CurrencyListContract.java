package com.cryptosys.currencyList;

import com.cryptosys.models.CryptoCurrencyItem;

import java.util.List;

public interface CurrencyListContract {

    interface Model {

        interface OnFinishedListener{

            void onFinished(List<CryptoCurrencyItem> currencyItemList);

            void onFailure(Throwable throwable);
        }

        void getCurrencyList(OnFinishedListener onFinishedListener);
    }

    interface View {

        void showProgress();

        void hideProgress();

        void setDataToRecyclerView(List<CryptoCurrencyItem> currencyItemList);

        void onResponseFailure(Throwable throwable);
    }

    interface Presenter {

        void onDestroy();

        void loadDataFromServer();

        void refreshCurrencyList();
    }

}
