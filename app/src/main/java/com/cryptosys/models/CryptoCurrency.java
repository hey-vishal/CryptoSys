package com.cryptosys.models;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CryptoCurrency{

	@SerializedName("CryptoCurrency")
	@Expose
	private List<CryptoCurrencyItem> cryptoCurrency;

	public List<CryptoCurrencyItem> getCryptoCurrency(){
		return cryptoCurrency;
	}

	@Override
 	public String toString(){
		return 
			"CryptoCurrency{" + 
			"cryptoCurrency = '" + cryptoCurrency + '\'' + 
			"}";
		}
}