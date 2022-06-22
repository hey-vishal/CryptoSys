package com.cryptosys.currencyDetail;

import com.cryptosys.models.CurrencyDetail;

public class DetailPresenter implements CurrencyDetailContract.Presenter,
        CurrencyDetailContract.Model.OnFinishedListener {

    private CurrencyDetailContract.View detailsView;
    private CurrencyDetailContract.Model detailsModel;

    public DetailPresenter(CurrencyDetailContract.View detailsView) {
        this.detailsView = detailsView;
        this.detailsModel = new DetailModel();
    }

    @Override
    public void onDestroy() {

        detailsView = null;
    }

    @Override
    public void loadDataFromServer(String symbol) {

        if (detailsView != null)
        {
            detailsView.showProgress();
            detailsView.hideLayout();
        }
        detailsModel.getCurrencyDetails(this,symbol);
    }

    @Override
    public void refreshData(String symbol) {
        detailsModel.getCurrencyDetails(this,symbol);
    }

    @Override
    public void onFinished(CurrencyDetail currencyDetail) {

        if (detailsView != null)
        {
            detailsView.hideProgress();
            detailsView.showLayout();
        }

        detailsView.setDataToViews(currencyDetail);
    }

    @Override
    public void onFailure(Throwable t) {

        if (detailsView != null)
        {
            detailsView.hideProgress();
        }
        detailsView.onResponseFailure(t);
    }
}
