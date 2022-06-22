package com.cryptosys.currencyList;

import com.cryptosys.models.CryptoCurrencyItem;

import java.util.List;

public class ListPresenter implements CurrencyListContract.Presenter, CurrencyListContract.Model.OnFinishedListener {

    private CurrencyListContract.View currencyListView;
    private CurrencyListContract.Model currencyListModel;

    public ListPresenter(CurrencyListContract.View currencyListView) {
        this.currencyListView = currencyListView;
        this.currencyListModel = new ListModel();
    }

    @Override
    public void onFinished(List<CryptoCurrencyItem> currencyItemList) {

        currencyListView.setDataToRecyclerView(currencyItemList);

        if (currencyListView != null)
        {
            currencyListView.hideProgress();
        }
    }

    @Override
    public void onFailure(Throwable throwable) {

        currencyListView.onResponseFailure(throwable);

        if (currencyListView != null)
        {
            currencyListView.hideProgress();
        }
    }

    @Override
    public void onDestroy() {
        this.currencyListView = null;
    }

    @Override
    public void loadDataFromServer() {

        if (currencyListView != null)
        {
            currencyListView.showProgress();
        }
        currencyListModel.getCurrencyList(this);
    }

    @Override
    public void refreshCurrencyList() {

        currencyListModel.getCurrencyList(this);
    }
}
