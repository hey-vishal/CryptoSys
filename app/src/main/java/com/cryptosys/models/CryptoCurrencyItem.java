package com.cryptosys.models;

import com.google.gson.annotations.SerializedName;

public class CryptoCurrencyItem{

	@SerializedName("volume")
	private String volume;

	@SerializedName("symbol")
	private String symbol;

	@SerializedName("askPrice")
	private String askPrice;

	@SerializedName("at")
	private long at;

	@SerializedName("lowPrice")
	private String lowPrice;

	@SerializedName("highPrice")
	private String highPrice;

	@SerializedName("openPrice")
	private String openPrice;

	@SerializedName("baseAsset")
	private String baseAsset;

	@SerializedName("quoteAsset")
	private String quoteAsset;

	@SerializedName("bidPrice")
	private String bidPrice;

	@SerializedName("lastPrice")
	private String lastPrice;

	public String getVolume(){
		return volume;
	}

	public String getSymbol(){
		return symbol;
	}

	public String getAskPrice(){
		return askPrice;
	}

	public long getAt(){
		return at;
	}

	public String getLowPrice(){
		return lowPrice;
	}

	public String getHighPrice(){
		return highPrice;
	}

	public String getOpenPrice(){
		return openPrice;
	}

	public String getBaseAsset(){
		return baseAsset;
	}

	public String getQuoteAsset(){
		return quoteAsset;
	}

	public String getBidPrice(){
		return bidPrice;
	}

	public String getLastPrice(){
		return lastPrice;
	}

	@Override
 	public String toString(){
		return 
			"CryptoCurrencyItem{" + 
			"volume = '" + volume + '\'' + 
			",symbol = '" + symbol + '\'' + 
			",askPrice = '" + askPrice + '\'' + 
			",at = '" + at + '\'' + 
			",lowPrice = '" + lowPrice + '\'' + 
			",highPrice = '" + highPrice + '\'' + 
			",openPrice = '" + openPrice + '\'' + 
			",baseAsset = '" + baseAsset + '\'' + 
			",quoteAsset = '" + quoteAsset + '\'' + 
			",bidPrice = '" + bidPrice + '\'' + 
			",lastPrice = '" + lastPrice + '\'' + 
			"}";
		}
}