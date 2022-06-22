package com.cryptosys.currencyDetail;

import com.cryptosys.models.CurrencyDetail;

public interface CurrencyDetailContract {

    interface Model
    {
        interface OnFinishedListener {
            void onFinished(CurrencyDetail currencyDetail);

            void onFailure(Throwable t);
        }

        void getCurrencyDetails(OnFinishedListener onFinishedListener, String symbol);
    }

    interface View
    {
        void showProgress();

        void hideProgress();

        void showLayout();

        void hideLayout();

        void setDataToViews(CurrencyDetail currencyDetail);

        void onResponseFailure(Throwable throwable);
    }

    interface Presenter
    {
        void onDestroy();

        void loadDataFromServer(String symbol);

        void refreshData(String symbol);
    }
}
