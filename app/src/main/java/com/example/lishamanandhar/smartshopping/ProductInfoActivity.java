package com.example.lishamanandhar.smartshopping;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.example.lishamanandhar.smartshopping.application.MyApplication;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by LishaManandhar on 3/8/2018.
 */

public class ProductInfoActivity extends AppCompatActivity {

    TextView txtName  , txtPrice , txtManufacture , txtExpiry , txtDesciption , txtSection;
    NetworkImageView img_view;
    String url = "http://192.168.0.20:8000/product/";
    MyApplication myApp;
    ImageLoader imageLoader = MyApplication.getInstance().getImageLoader();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activty_product_info);
        Bundle bundle = getIntent().getExtras();
        String barcode = bundle.getString("barcode");
        Log.i("code",barcode);
        url = url+barcode+"/";
        txtName = (TextView) findViewById(R.id.txt_pro_name);
        txtPrice = (TextView) findViewById(R.id.txt_pro_price);
        txtManufacture = (TextView) findViewById(R.id.txt_pro_manufacture);
        txtExpiry = (TextView) findViewById(R.id.txt_pro_expiry);
        txtDesciption = (TextView) findViewById(R.id.txt_pro_description);
        txtSection = (TextView) findViewById(R.id.txt_pro_section);
        img_view = (NetworkImageView) findViewById(R.id.img_view_pro);
        myApp = MyApplication.getInstance();
        start_web_service();
    }

    public void start_web_service(){

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url,null,onSuccess,onError);
        myApp.getRequestQueue().add(jsonObjectRequest);

    }

    Response.Listener<JSONObject> onSuccess = new Response.Listener<JSONObject>() {
        @Override
        public void onResponse(JSONObject response) {
            try {
                String name = response.getString("name");
                String image = response.getString("image");
                String price = response.getString("price");
                String manufacture = response.getString("manufactured_date");
                String expiry = response.getString("expiry_date");
                String description = response.getString("description");
                String section = response.getString("section");

                DataModel dataModel = new DataModel(name,image,price,manufacture,expiry,description,section);

                txtName.setText("Name : " + name);
                String imgUrl = "http://192.168.0.20:8000" + dataModel.getImage();
                img_view.setImageUrl(imgUrl,imageLoader);
                txtPrice.setText("Price Rs. : " + price);
                txtManufacture.setText("Manufactured Date : " + manufacture);
                txtExpiry.setText("Expiry Date : " + expiry);
                txtDesciption.setText("Description : " + description);
                txtSection.setText("Section : " + section);



            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    };

    Response.ErrorListener onError = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Toast.makeText(ProductInfoActivity.this,error.toString(),Toast.LENGTH_LONG).show();
        }
    };


}
