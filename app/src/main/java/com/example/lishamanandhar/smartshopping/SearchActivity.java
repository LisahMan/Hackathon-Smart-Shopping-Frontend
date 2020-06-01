package com.example.lishamanandhar.smartshopping;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.lishamanandhar.smartshopping.adaper.SearchRecViewAdapter;
import com.example.lishamanandhar.smartshopping.application.MyApplication;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by LishaManandhar on 3/8/2018.
 */

public class SearchActivity extends AppCompatActivity{

    EditText etSearch;
    Button btnSearch;
    RecyclerView recView;
    MyApplication myApp;
    String searchUrl = "http://192.168.0.20:8000/product/search/";
    SearchRecViewAdapter searchRecViewAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        etSearch = (EditText) findViewById(R.id.et_search);
        btnSearch = (Button) findViewById(R.id.btn_search);
        recView = (RecyclerView) findViewById(R.id.rec_view);
        myApp = MyApplication.getInstance();

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSearchService();
            }
        });

    }

    public void startSearchService(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST,searchUrl,onSuccess,onError){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String , String> params = new HashMap<>();
                params.put("name",etSearch.getText().toString());
                Log.i("etname",etSearch.getText().toString());
                return params;
            }
        };
        myApp.getRequestQueue().add(stringRequest);
    }

    Response.Listener<String> onSuccess = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            Log.i("success",response);
            ArrayList<DataModel> dataList = new ArrayList<>();

            try {
                JSONArray jsonArray = new JSONArray(response);
                for(int i=0;i<jsonArray.length();i++){
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String name = jsonObject.getString("name");
                    String image = jsonObject.getString("image");
                    String price = jsonObject.getString("price");
                    String manufacture = jsonObject.getString("manufactured_date");
                    String expiry = jsonObject.getString("expiry_date");
                    String description = jsonObject.getString("description");
                    String section = jsonObject.getString("section");
                    Log.i("name",name);
                    Log.i("price",price);
                    DataModel dataModel = new DataModel(name,image,price,manufacture,expiry,description,section);
                    dataList.add(dataModel);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            searchRecViewAdapter = new SearchRecViewAdapter(SearchActivity.this,dataList);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
            recView.setLayoutManager(layoutManager);
            recView.setAdapter(searchRecViewAdapter);



        }
    };

    Response.ErrorListener onError = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Toast.makeText(SearchActivity.this,"Could not find the product",Toast.LENGTH_LONG).show();

        }
    };
}
