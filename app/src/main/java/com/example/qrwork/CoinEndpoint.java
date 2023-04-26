
package com.example.qrwork;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CoinEndpoint {

    @SerializedName("currency")
    @Expose
    private String currency;
    @SerializedName("price")
    @Expose
    private String price;

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

}
