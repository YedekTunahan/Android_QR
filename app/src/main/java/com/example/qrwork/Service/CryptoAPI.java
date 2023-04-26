package com.example.qrwork.Service;

import com.example.qrwork.Model.CryptoModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CryptoAPI {
    //GET,POST,UPDATE

    //https://raw.githubusercontent.com/
    // atilsamancioglu/K21-JSONDataSet/master/crypto.json

    @GET("atilsamancioglu/K21-JSONDataSet/master/crypto.json") //get içerisine BASE URL yazmıyoruz.BASE URL 'i Retrofit kendi nesnesini oluştururken vermemiz gerekiyor.
    Call<List<CryptoModel>>getData();  //get işlemini hangi method ile yapılacağını belirtmemiz gerekiyor.Call: asingrenus şeklinde ne indirilceğini ve hangi method ile indirileceğini söyliğyr
                // Bir liste istiyorum ve bu listin içerisdede Hangi türde veri tutulacağını belirtmiş oluyorum

}
