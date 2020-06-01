package com.example.lishamanandhar.smartshopping.adaper;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.lishamanandhar.smartshopping.DataModel;
import com.example.lishamanandhar.smartshopping.R;
import com.example.lishamanandhar.smartshopping.application.MyApplication;

import java.util.ArrayList;

/**
 * Created by LishaManandhar on 3/8/2018.
 */

public class SearchRecViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    ArrayList<DataModel> dataList;
    ImageLoader imageLoader = MyApplication.getInstance().getImageLoader();

    public SearchRecViewAdapter(Context context , ArrayList<DataModel> dataList){
        this.context = context;
        this.dataList = dataList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.activty_product_info,parent,false);
        return new MyViewHolder(v);
     }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((MyViewHolder) holder).txtName.setText("Name : " + dataList.get(position).getName());
        String url = "http://192.168.0.20:8000" + dataList.get(position).getImage();
        ((MyViewHolder) holder).img_view.setImageUrl(url,imageLoader);
        ((MyViewHolder) holder).txtPrice.setText("Price : " +  dataList.get(position).getPrice());
        ((MyViewHolder) holder).txtManufacture.setText("Manufacture Date : " + dataList.get(position).getManufacture_date());
        ((MyViewHolder) holder).txtExpiry.setText("Expiriy Date : " + dataList.get(position).getExpiriy_date());
        ((MyViewHolder) holder).txtDescription.setText("Description : " + dataList.get(position).getDescription());
        ((MyViewHolder) holder).txtSection.setText("Section : " + dataList.get(position).getSection());
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView txtName,txtPrice,txtManufacture , txtExpiry , txtDescription , txtSection;
        NetworkImageView img_view;
        public MyViewHolder(View itemView) {
            super(itemView);
            txtName = (TextView) itemView.findViewById(R.id.txt_pro_name);
            txtPrice = (TextView) itemView.findViewById(R.id.txt_pro_price);
            txtManufacture = (TextView) itemView.findViewById(R.id.txt_pro_manufacture);
            txtExpiry = (TextView) itemView.findViewById(R.id.txt_pro_expiry);
            txtDescription = (TextView) itemView.findViewById(R.id.txt_pro_description);
            txtSection = (TextView) itemView.findViewById(R.id.txt_pro_section);
            img_view = (NetworkImageView) itemView.findViewById(R.id.img_view_pro);
        }
    }
}
