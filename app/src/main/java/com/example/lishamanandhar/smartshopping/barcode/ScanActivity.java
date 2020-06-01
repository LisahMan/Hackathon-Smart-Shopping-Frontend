package com.example.lishamanandhar.smartshopping.barcode;

/**
 * Created by LishaManandhar on 3/8/2018.
 */

import android.content.Intent;

import com.example.lishamanandhar.smartshopping.ProductInfoActivity;
import com.example.lishamanandhar.smartshopping.R;
import com.google.android.gms.vision.barcode.Barcode;

        import android.content.Intent;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
        import android.widget.Toast;

        import com.google.android.gms.vision.barcode.Barcode;

        import java.util.List;

        import info.androidhive.barcode.BarcodeReader;

public class ScanActivity extends AppCompatActivity implements BarcodeReader.BarcodeReaderListener {

    BarcodeReader barcodeReader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);

        // get the barcode reader instance
        barcodeReader = (BarcodeReader) getSupportFragmentManager().findFragmentById(R.id.barcode_scanner);
    }

    @Override
    public void onScanned(Barcode barcode) {

        // playing barcode reader beep sound
        barcodeReader.playBeep();

//        // ticket details activity by passing barcode
//        Intent intent = new Intent(ScanActivity.this, TicketActivity.class);
//        intent.putExtra("code", barcode.displayValue);
//        startActivity(intent);

        Intent intent = new Intent(ScanActivity.this, ProductInfoActivity.class);
        intent.putExtra("barcode",barcode.displayValue);
        startActivity(intent);
        finish();

        Log.i("barcode",barcode.displayValue);
    }

    @Override
    public void onScannedMultiple(List<Barcode> list) {

    }

    @Override
    public void onBitmapScanned(SparseArray<Barcode> sparseArray) {

    }

    @Override
    public void onCameraPermissionDenied() {
        finish();
    }

    @Override
    public void onScanError(String s) {
        Toast.makeText(getApplicationContext(), "Error occurred while scanning " + s, Toast.LENGTH_SHORT).show();
    }
}