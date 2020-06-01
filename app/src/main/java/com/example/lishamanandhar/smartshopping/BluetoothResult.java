package com.example.lishamanandhar.smartshopping;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.lishamanandhar.smartshopping.adaper.OrderRecViewAdapter;
import com.example.lishamanandhar.smartshopping.application.MyApplication;
import com.example.lishamanandhar.smartshopping.bluetooth.DeviceListActivity;
import com.example.lishamanandhar.smartshopping.session.SessionManager;
import com.google.android.gms.common.api.BooleanResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by LishaManandhar on 3/8/2018.
 */

public class BluetoothResult extends AppCompatActivity {

    //used to identify handler message
    private BluetoothAdapter btAdapter = BluetoothAdapter.getDefaultAdapter();;
    private BluetoothSocket btSocket = null;
    private StringBuilder recDataString = new StringBuilder();
    MyApplication myApp = MyApplication.getInstance();
    RecyclerView recViewOrder;
    Button btnFinish;
    int total = 0;
    String billUrl = "http://192.168.0.20:8000/bill/create/";
    String orderUrl = "http://192.168.0.20:8000/bill/ordercreate/";
    String user;
    SessionManager session;
    TextView txtFinalBill;

    ArrayList<DataModel> dataList = new ArrayList<>();
    OrderRecViewAdapter orderRecViewAdapter = new OrderRecViewAdapter(BluetoothResult.this,dataList) ;



    private ConnectedThread mConnectedThread;

    // SPP UUID service - this should work for most devices
    private static final UUID BTMODULEUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    // String for MAC address
    private static String address;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth);
        recViewOrder = findViewById(R.id.rec_view_order);
        btnFinish = (Button) findViewById(R.id.btn_finish);
        session = new SessionManager(getApplicationContext());
        user = session.getUser();
        txtFinalBill = (TextView) findViewById(R.id.txt_total_bill);
        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final JSONArray jsonArray = new JSONArray();
                Log.i("size",dataList.size()+"");
//                for(int i=0 ;i<dataList.size();i++){
//                    JSONObject obj = new JSONObject();
//                    try {
//                        obj.put("name",dataList.get(i).getName());
//                        obj.put("price",dataList.get(i).getPrice());
//                        jsonArray.put(obj);
//                        Log.i("jsonarr",jsonArray.toString());
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }
                StringRequest stringRequest = new StringRequest(Request.Method.POST,billUrl,onSuccess,onError){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String> params = new HashMap<String, String>();
                        Log.i("total",total+"");
                        Log.i("user",user+"");
                        params.put("user",user);
                        params.put("total",total+"");
//                        params.put("orders",jsonArray.toString());
                        return params;
                }
                };

                myApp.getRequestQueue().add(stringRequest);
            }
        });
        checkBTState();
}

   Response.Listener<String> onSuccess = new Response.Listener<String>() {
       @Override
       public void onResponse(String response) {
            btnFinish.setVisibility(View.GONE);
           txtFinalBill.setVisibility(View.VISIBLE);
           try {
               JSONObject jsonObject = new JSONObject(response);
               final String bill_no = jsonObject.getString("id");
               txtFinalBill.setText("Bill no. " +bill_no+ " Total Rs." + total);

               for(int i=0;i<dataList.size();i++){
                   final String p_Name = dataList.get(i).getName();
                   final String p_Price = dataList.get(i).getPrice();
                   StringRequest stringRequest = new StringRequest(Request.Method.POST,orderUrl,onOrderSuccess,onOrderError){
                       @Override
                       protected Map<String, String> getParams() throws AuthFailureError {
                           Map<String,String> params = new HashMap<>();
                           params.put("bill",bill_no);
                           params.put("name",p_Name);
                           params.put("price",p_Price);
                           return params;
                       }
                   };
                   myApp.getRequestQueue().add(stringRequest);
               }


           } catch (JSONException e) {
               e.printStackTrace();
           }


       }
   };

   Response.Listener<String> onOrderSuccess = new Response.Listener<String>() {
       @Override
       public void onResponse(String response) {

       }
   };

   Response.ErrorListener onOrderError = new Response.ErrorListener() {
       @Override
       public void onErrorResponse(VolleyError error) {

       }
   };

   Response.ErrorListener onError = new Response.ErrorListener() {
       @Override
       public void onErrorResponse(VolleyError error) {
           Toast.makeText(BluetoothResult.this,error.toString(),Toast.LENGTH_LONG).show();
       }
   };

    private BluetoothSocket createBluetoothSocket(BluetoothDevice device) throws IOException {

        return  device.createRfcommSocketToServiceRecord(BTMODULEUUID);
        //creates secure outgoing connecetion with BT device using UUID
    }

    @Override
    public void onResume() {
        super.onResume();

        //Get MAC address from DeviceListActivity via intent
        Intent intent = getIntent();

        //Get the MAC address from the DeviceList Activty via EXTRA

        address = intent.getStringExtra(DeviceListActivity.EXTRA_DEVICE_ADDRESS);

        Log.i("address",address);

        //create device and set the MAC address
        BluetoothDevice device = btAdapter.getRemoteDevice(address);

        try {
            btSocket = createBluetoothSocket(device);
        } catch (IOException e) {
            Toast.makeText(getBaseContext(), "Socket creation failed", Toast.LENGTH_LONG).show();
        }
        // Establish the Bluetooth socket connection.
        try
        {
            btSocket.connect();
        } catch (IOException e) {
            try
            {
                btSocket.close();
            } catch (IOException e2)
            {
                //insert code to deal with this
            }
        }
        mConnectedThread = new ConnectedThread(btSocket);
        mConnectedThread.start();

        //I send a character when resuming.beginning transmission to check device is connected
        //If it is not an exception will be thrown in the write method and finish() will be called
        mConnectedThread.write("x");
    }

    @Override
    public void onPause()
    {
        super.onPause();
        try
        {
            //Don't leave Bluetooth sockets open when leaving activity
            btSocket.close();
        } catch (IOException e2) {
            //insert code to deal with this
        }
    }

    //Checks that the Android device Bluetooth is available and prompts to be turned on if off
    private void checkBTState() {

        if(btAdapter==null) {
            Toast.makeText(getBaseContext(), "Device does not support bluetooth", Toast.LENGTH_LONG).show();
        } else {
            if (btAdapter.isEnabled()) {
            } else {
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, 1);
            }
        }
    }

    //create new class for connect thread
    private class ConnectedThread extends Thread {
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;

        //creation of the connect thread
        public ConnectedThread(BluetoothSocket socket) {
            InputStream tmpIn = null;
            OutputStream tmpOut = null;

            try {
                //Create I/O streams for connection
                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();
            } catch (IOException e) { }

            mmInStream = tmpIn;
            mmOutStream = tmpOut;
        }


        public void run() {
            byte[] buffer = new byte[256];
            int bytes;

            // Keep looping to listen for received messages
            while (true) {
                try {
                    bytes = mmInStream.read(buffer);        	//read bytes from input buffer
                    String readMessage = new String(buffer, 0, bytes);
                    // Send the obtained bytes to the UI Activity via handler
                    Log.i("readMessage",readMessage);
                    String url = "http://192.168.0.20:8000/product/order/";
                    url = url+readMessage+"/";
                    startWebService(url);
                } catch (IOException e) {
                    break;
                }
            }
        }

            void startWebService(String url){
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url,null,onSuccess,onError);
                myApp.getRequestQueue().add(jsonObjectRequest);;
            }

            Response.Listener<JSONObject> onSuccess = new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {


                    try {
                        String name = response.getString("name");
                        String price = response.getString("price");
                        Log.i("ordername",name);
                        Log.i("orderprice",price);
                        DataModel dataModel = new DataModel();
                        dataModel.setName(name);
                        dataModel.setPrice(price);
                        total+=Integer.parseInt(price);
//                        dataList.add(dataModel);
                        orderRecViewAdapter.addOrder(dataModel);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Log.i("dataList",dataList.size()+"");
                    orderRecViewAdapter = new OrderRecViewAdapter(BluetoothResult.this,dataList);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                    recViewOrder.setLayoutManager(layoutManager);
                    recViewOrder.setAdapter(orderRecViewAdapter);
                }
            };

            Response.ErrorListener onError = new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(BluetoothResult.this, "Error", Toast.LENGTH_SHORT).show();
                }
            };

        //write method
        public void write(String input) {
            byte[] msgBuffer = input.getBytes();           //converts entered String into bytes
            try {
                mmOutStream.write(msgBuffer);              //write bytes over BT connection via outstream
            } catch (IOException e) {
                //if you cannot write, close the application
                Toast.makeText(getBaseContext(), "Connection Failure", Toast.LENGTH_LONG).show();
                finish();

            }
        }


    }
}
