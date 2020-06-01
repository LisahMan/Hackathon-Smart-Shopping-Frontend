package com.example.lishamanandhar.smartshopping.adaper;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lishamanandhar.smartshopping.DataModel;
import com.example.lishamanandhar.smartshopping.R;
import com.google.android.gms.vision.text.Text;

import java.util.ArrayList;

/**
 * Created by LishaManandhar on 3/9/2018.
 */

public class OrderRecViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    ArrayList<DataModel> dataList;

    public OrderRecViewAdapter(Context context,ArrayList<DataModel> dataList){
        this.context = context;
        this.dataList = dataList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.rec_view_order_item,parent,false);
        return new MyOrderViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((MyOrderViewHolder) holder).txtOrderName.setText(dataList.get(position).getName());
        ((MyOrderViewHolder) holder).txtOrderPrice.setText(dataList.get(position).getPrice());
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class MyOrderViewHolder extends RecyclerView.ViewHolder{
        TextView txtOrderName , txtOrderPrice;
        public MyOrderViewHolder(View itemView) {
            super(itemView);
            txtOrderName = (TextView) itemView.findViewById(R.id.txt_order_name);
            txtOrderPrice = (TextView) itemView.findViewById(R.id.txt_order_price);
        }
    }

    public void addOrder(DataModel dataModel){
        dataList.add(dataModel);
        notifyDataSetChanged();
    }

    public void removeOrder(int position){
        dataList.remove(position);
        notifyDataSetChanged();
    }
}
