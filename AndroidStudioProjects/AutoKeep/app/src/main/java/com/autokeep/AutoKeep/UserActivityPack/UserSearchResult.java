package com.autokeep.AutoKeep.UserActivityPack;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.autokeep.AutoKeep.Communication.clientSocket;
import com.autokeep.AutoKeep.R;
import com.autokeep.AutoKeep.UserMode.VehicleModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class UserSearchResult extends AppCompatActivity {
    List <VehicleModel> carsList;
    //the recyclerview
    RecyclerView recyclerView;
    Cars_Adapter adapter;
    VehicleModel selectedCar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_search_result);
        //getting the recyclerview from xml 
        recyclerView = findViewById(R.id.recylcerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(UserSearchResult.this, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                selectedCar = adapter.getCarSelected(position);
                AlertDialog.Builder builder = new AlertDialog.Builder(recyclerView.getContext());
                builder.setTitle("Car Keep :");
                builder.setMessage(selectedCar.getVehicleFullName() + " " + selectedCar.getManufactureYear() + "\n" +
                        selectedCar.getCarShortdesc() + "\nID: " + selectedCar.getCarFixedID() + "\n\nPlease confirm ?");
                builder.setPositiveButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
                builder.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(getApplicationContext(), UserMenu.class);
                        startActivity(intent);
                        finish();
                    }
                });
                builder.show();
            }

        }));
        //initializing the carlist
        carsList = new ArrayList <>();
        loadCarsData();

    }

    private void loadCarsData() {

        Queue <VehicleModel> list = (Queue <VehicleModel>) clientSocket.getInstance().readFromServer();
        while (!list.isEmpty()) {
            carsList.add(list.poll());
        }
        //creating adapter object and setting it to recyclerview
        adapter = new Cars_Adapter(UserSearchResult.this, carsList);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Warnning !!!");
        builder.setMessage("All search data will lost...\n\nAre you sure ? ");
        builder.setPositiveButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            }
        });
        builder.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                finish();
            }
        });
        builder.show();
    }
}
