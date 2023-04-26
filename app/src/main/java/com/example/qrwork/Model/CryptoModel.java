package com.example.qrwork.Model;

import com.google.gson.annotations.SerializedName;

public class CryptoModel {

    @SerializedName("currency")  // burada yazılan değer JSON daki değişkenimle birebir aynı aolması lazım, Aşağıdaki değişken isimleri değişik olabilir.
    public String currency;

    @SerializedName("price")
    public String price;



}
