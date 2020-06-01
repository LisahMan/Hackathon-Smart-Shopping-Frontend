package com.example.lishamanandhar.smartshopping;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.lishamanandhar.smartshopping.adaper.ViewPagerAdapter;
import com.example.lishamanandhar.smartshopping.barcode.ScanActivity;
import com.example.lishamanandhar.smartshopping.bluetooth.DeviceListActivity;
import com.example.lishamanandhar.smartshopping.fragment.Frag1;
import com.example.lishamanandhar.smartshopping.fragment.frag2;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    Button btnBluetooth;
    ViewPager viewPager;
    TabLayout tabLayout;
    ViewPagerAdapter viewPagerAdapter;
    ArrayList<Fragment> fragList;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnBluetooth = (Button) findViewById(R.id.btn_bluetooth);
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);

        fragList = new ArrayList<>();
        fragList.add(new Frag1());
        fragList.add(new frag2());

        String titles[] = {"",""};

        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(),fragList,titles);
        viewPager.setAdapter(viewPagerAdapter);

        tabLayout.setupWithViewPager(viewPager);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        btnBluetooth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DeviceListActivity.class);
                startActivity(intent);
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.search :
                Intent intent1 = new Intent(MainActivity.this,SearchActivity.class);
                startActivity(intent1);
                break;

            case R.id.scan :
                Intent intent2 = new Intent(MainActivity.this, ScanActivity.class);
                startActivity(intent2);
                break;

        }
        return super.onOptionsItemSelected(item);
    }


}
