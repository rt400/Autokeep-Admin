package com.autokeep.AutoKeep.UserActivityPack;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.autokeep.AutoKeep.R;
import com.autokeep.AutoKeep.UserMode.VehicleModel;

import java.util.ArrayList;
import java.util.List;

public class OrderResult extends AppCompatActivity {
    List <VehicleModel> carsList;
    //the recyclerview
    RecyclerView recyclerView;
    Orders_Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_orders_result);

        //getting the recyclerview from xml 
        recyclerView = findViewById(R.id.recylcerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(OrderResult.this, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                //Toast.makeText(getBaseContext(), adapter.getSelectedCarID(position), Toast.LENGTH_LONG).show();

            }

        }));

        //initializing the productlist
        carsList = new ArrayList <>();

        //this method will fetch and parse json 
        //to display it in recyclerview
        //loadProducts();
    }

  /*  private void loadProducts() {

        String response = "[{\"carID\":83-206-75,\"manufacturer\":\"Fiat 500\",\"manufactureDate\":2007,\"seatsNumber\":5,\"engineCapacity\":1600,\"image\":\"https://images.netdirector.co.uk/gforces-auto/image/upload/w_372,h_279,dpr_3.0,q_auto,c_fill,f_auto,fl_lossy/auto-client/6568a6ec36a96329407fa020f1c1a9c2/20150907145453_preview_img00155_new_fiat_500_white.jpg\"}," +
                "{\"carID\":43-205-78,\"manufacturer\":\"Toyota Corolla\",\"manufactureDate\":2018,\"seatsNumber\":5,\"engineCapacity\":1600,\"image\":\"https://www.toyotaofgreer.com/assets/stock/expanded/white/640/2017toc040002_640/2017toc040002_640_07.jpg\"}," +
                "{\"carID\":68-345-79,\"manufacturer\":\"Ford Focus\",\"manufactureDate\":2012,\"seatsNumber\":5,\"engineCapacity\":1600,\"image\":\"https://cdn2.autoexpress.co.uk/sites/autoexpressuk/files/2018/04/dsc_6270.jpg\"}," +
                "{\"carID\":68-345-79,\"manufacturer\":\"Ford Focus\",\"manufactureDate\":2012,\"seatsNumber\":5,\"engineCapacity\":1600,\"image\":\"https://cdn2.autoexpress.co.uk/sites/autoexpressuk/files/2018/04/dsc_6270.jpg\"}," +
                "{\"carID\":68-345-79,\"manufacturer\":\"Ford Focus\",\"manufactureDate\":2012,\"seatsNumber\":5,\"engineCapacity\":1600,\"image\":\"https://cdn2.autoexpress.co.uk/sites/autoexpressuk/files/2018/04/dsc_6270.jpg\"}," +
                "{\"carID\":68-345-79,\"manufacturer\":\"Ford Focus\",\"manufactureDate\":2012,\"seatsNumber\":5,\"engineCapacity\":1600,\"image\":\"https://cdn2.autoexpress.co.uk/sites/autoexpressuk/files/2018/04/dsc_6270.jpg\"}," +
                "{\"carID\":68-345-79,\"manufacturer\":\"Ford Focus\",\"manufactureDate\":2012,\"seatsNumber\":5,\"engineCapacity\":1600,\"image\":\"https://cdn2.autoexpress.co.uk/sites/autoexpressuk/files/2018/04/dsc_6270.jpg\"}]";
        try {
            //converting the string to json array object
            JSONArray array = new JSONArray(response);

            //traversing through all the object
            for (int i = 0; i < array.length(); i++) {

                //getting product object from json array
                JSONObject product = array.getJSONObject(i);

                //adding the product to product list
                carsList.add(new VehicleModel(
                        product.getString("carID"),
                        product.getString("manufacturer"),
                        product.getString("manufactureDate"),
                        product.getString("seatsNumber"),
                        product.getString("engineCapacity"),
                        product.getString("image")
                ));
            }

            //creating adapter object and setting it to recyclerview
            adapter = new Orders_Adapter(OrderResult.this, carsList);
            recyclerView.setAdapter(adapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }*/

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), UserMenu.class);
        startActivity(intent);
        finish();
    }
}