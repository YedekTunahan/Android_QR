package com.example.qrwork;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.zxing.Result;

public class MainActivity extends AppCompatActivity {

    CodeScanner mCodeQRScanner;
    CodeScannerView QRScanner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
                        Toast.makeText(MainActivity.this, result.getText(), Toast.LENGTH_SHORT).show();
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
}