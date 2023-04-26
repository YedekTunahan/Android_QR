package com.example.qrwork.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.example.qrwork.Model.CryptoModel;
import com.example.qrwork.R;
import com.example.qrwork.Service.CryptoAPI;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.zxing.Result;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    //qr values
    CodeScanner mCodeQRScanner;
    CodeScannerView QRScanner;

    // API values
    ArrayList<CryptoModel>cryptoModels; // İndirilecek data için bir list oluşturduk
   // private  String BASE_URL = "https://raw.githubusercontent.com/";
     // private String BASE_URL = "https://api.nomics.com/v1/";
   private  String BASE_URL = "https://raw.githubusercontent.com/";
    Retrofit retrofit;
    CompositeDisposable compositeDisposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

      /*  Log.e("Çalıştı...","***********");

        // API - START (Retrofit-JSON)
        Gson gson = new GsonBuilder().setLenient().create(); //JSON' ı aldığını gösterir.

        try {
            Log.e("Retrofit","create");
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson)) // GELEN JSON verisini MODEL de yazdığımız nesneleri bildirmemiz gerekiyor.Bir Model çekeceğimizi bildirdik
                    .build();
            Log.e("LOAD","load");
            loadData();
        }catch (Exception e) {
            Log.e("CATCH","CATCH");
        }*/
        QRScanner = findViewById(R.id.QR_Scanner);

        // İzinler Kısımı

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
                String[] permissionList = new String[]{Manifest.permission.CAMERA};
                requestPermissions(permissionList,1);
            }
        }

        //CodeScanner
        mCodeQRScanner = new CodeScanner(this, QRScanner);
        mCodeQRScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull Result result) { // Cod çözme açık = on decode
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() { //Decode edildikten sonra ne yapılacak ona bakmış olacağız
                        Toast.makeText(MainActivity.this, result.toString(), Toast.LENGTH_SHORT).show();
                        Log.e("Çalıştı...","***********");

                        // API - START (Retrofit-JSON)
                        Gson gson = new GsonBuilder().setLenient().create(); //JSON' ı aldığını gösterir.

                        try {
                            Log.e("Retrofit","create");
                            retrofit = new Retrofit.Builder()
                                    .baseUrl(BASE_URL)
                                    .addConverterFactory(GsonConverterFactory.create(gson)) // GELEN JSON verisini MODEL de yazdığımız nesneleri bildirmemiz gerekiyor.Bir Model çekeceğimizi bildirdik
                                    .build();
                            Log.e("LOAD","load");
                            loadData();
                        }catch (Exception e) {
                            Log.e("CATCH","CATCH");
                        }

                    }
                });
            }
        });

        //Tıklandığında ne olacak
        QRScanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCodeQRScanner.startPreview();
            }
        });

    }

    @Override
    protected void onResume() { // uygulamanın o sayfada ne olacağını söylemiş oluyoruz.
        super.onResume();
        mCodeQRScanner.startPreview(); //Görüntülemeyi başlat diyorum
    }

    @Override
    protected void onPause() { //Durursa ( başka sayfaya geçti veya başka bir uygulamaya fean geçti )
        mCodeQRScanner.releaseResources();
        super.onPause();
    }
//-------------
    private void  loadData(){
       final CryptoAPI cryptoAPI = retrofit.create(CryptoAPI.class);

        Call<List<CryptoModel>> call = cryptoAPI.getData();  // veriyi çekiyoruz Call methodu ile

       /* compositeDisposable = new CompositeDisposable();

        compositeDisposable.add(cryptoAPI.getData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleResponse));*/

        call.enqueue(new Callback<List<CryptoModel>>() { // içine new Callback yapınca aşağıdaki methodlar otomatik geliyor
            @Override
            public void onResponse(Call<List<CryptoModel>> call, Response<List<CryptoModel>> response) {

                if (response.isSuccessful()){ //.isSuccessful() gelen veri başarılıysa demek

                    List<CryptoModel> responseList = response.body();
                    cryptoModels = new ArrayList<>(responseList); // List değişenini ArrayList' ine çevirme olayı
                    for (CryptoModel cryptoModel : cryptoModels){
                        System.out.println(cryptoModel.currency);
                        System.out.println(cryptoModel.price);
                    }
                }else {
                    Log.e("onResponse","Else");
                }
            }

            @Override
            public void onFailure(Call<List<CryptoModel>> call, Throwable t) { // hata çıkarsa çalışacak olan method

                Toast.makeText(MainActivity.this, t.toString(), Toast.LENGTH_SHORT).show();

                Log.e("LOAD HATA",t.toString());
            }
        });



    }
}