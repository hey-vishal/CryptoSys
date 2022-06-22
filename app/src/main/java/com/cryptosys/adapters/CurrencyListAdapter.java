package com.cryptosys.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.cryptosys.R;
import com.cryptosys.currencyList.CurrencyListActivity;
import com.cryptosys.models.CryptoCurrencyItem;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CurrencyListAdapter extends RecyclerView.Adapter<CurrencyListAdapter.MyViewHolder> {

    private CurrencyListActivity activity;
    private List<CryptoCurrencyItem> currencyItemList;

    public CurrencyListAdapter(CurrencyListActivity activity, List<CryptoCurrencyItem> currencyItemList) {
        this.currencyItemList = currencyItemList;
        this.activity = activity;
    }

    @NonNull
    @Override
    public CurrencyListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CurrencyListAdapter.MyViewHolder holder, int position) {

        CryptoCurrencyItem currencyItem = currencyItemList.get(position);
        float currentPrice = Float.parseFloat(currencyItem.getLastPrice());
        float openPrice = Float.parseFloat(currencyItem.getOpenPrice());
        float percent = ((currentPrice - openPrice)/openPrice)*100;

        holder.currencyName.setText(currencyItem.getBaseAsset().toUpperCase(Locale.ROOT));
        holder.basePrice.setText(currencyItem.getQuoteAsset().toUpperCase(Locale.ROOT));
        if (currencyItem.getQuoteAsset().equals("inr"))
        {
            holder.currencyPrice.setText("₹ "+currencyItem.getLastPrice());
        }
        else
        {
            holder.currencyPrice.setText(currencyItem.getLastPrice());
        }
        holder.percentText.setText(String.format("%.2f",percent)+"%");

        if (percent > 0.0)
        {
            holder.percentText.setTextColor(Color.GREEN);
        }
        else if(percent < 0.0)
        {
            holder.percentText.setTextColor(Color.RED);
        }
        else
        {
            holder.percentText.setTextColor(R.color.colorText);
        }

        if (currencyItem.getBaseAsset().equals("btc"))
        {
            Picasso.get().load(R.drawable.bitcoin)
                    .into(holder.currencyIcon);
        }
        else if (currencyItem.getBaseAsset().equals("eth"))
        {
            Picasso.get().load(R.drawable.ethereum)
                    .into(holder.currencyIcon);
        }
        else if (currencyItem.getBaseAsset().equals("bnb"))
        {
            Picasso.get().load(R.drawable.binance)
                    .into(holder.currencyIcon);
        }
        else if (currencyItem.getBaseAsset().equals("usdt"))
        {
            Picasso.get().load(R.drawable.tether)
                    .into(holder.currencyIcon);
        }
        else if (currencyItem.getBaseAsset().equals("xrp"))
        {
            Picasso.get().load(R.drawable.ripple)
                    .into(holder.currencyIcon);
        }
        else if (currencyItem.getBaseAsset().equals("dash"))
        {
            Picasso.get().load(R.drawable.dash)
                    .into(holder.currencyIcon);
        }
        else if (currencyItem.getBaseAsset().equals("doge"))
        {
            Picasso.get().load(R.drawable.dogecoin)
                    .into(holder.currencyIcon);
        }
        else if (currencyItem.getBaseAsset().equals("ltc"))
        {
            Picasso.get().load(R.drawable.litecoin)
                    .into(holder.currencyIcon);
        }
        else if (currencyItem.getBaseAsset().equals("shib"))
        {
            Picasso.get().load(R.drawable.shiba)
                    .into(holder.currencyIcon);
        }
        else if (currencyItem.getBaseAsset().equals("trx"))
        {
            Picasso.get().load(R.drawable.tron)
                    .into(holder.currencyIcon);
        }
        else
        {
            Picasso.get().load(R.drawable.cryptocurrency)
                    .into(holder.currencyIcon);
        }

        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.onCurrencyListItemClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return currencyItemList.size();
    }

    public void filterList(ArrayList<CryptoCurrencyItem> filterList)
    {
        currencyItemList = filterList;
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        public TextView currencyName,currencyPrice,basePrice,percentText;
        public ImageView currencyIcon;
        public LinearLayout container;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            currencyName = itemView.findViewById(R.id.titleTxtView);
            currencyPrice = itemView.findViewById(R.id.priceTxtView);
            basePrice = itemView.findViewById(R.id.baseTxtView);
            currencyIcon = itemView.findViewById(R.id.iconView);
            container = itemView.findViewById(R.id.item_container);
            percentText = itemView.findViewById(R.id.percentTxtView);

            Typeface tf = Typeface.createFromAsset(activity.getAssets(), "fonts/poppins.ttf");
            currencyName.setTypeface(tf);
            currencyPrice.setTypeface(tf);
            basePrice.setTypeface(tf);
            percentText.setTypeface(tf);
        }
    }
}
