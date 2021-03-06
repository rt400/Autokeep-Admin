package com.autokeep.AutoKeep.UserActivityPack;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.autokeep.AutoKeep.Communication.LogOutTimerUtil;
import com.autokeep.AutoKeep.Communication.clientSocket;
import com.autokeep.AutoKeep.R;
import com.autokeep.AutoKeep.UserMode.ReservationModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import static com.autokeep.AutoKeep.Communication.LogOutTimerUtil.startLogoutTimer;
import static com.autokeep.AutoKeep.Communication.LogOutTimerUtil.stopLogoutTimer;

public class OrderResult extends AppCompatActivity implements LogOutTimerUtil.LogOutListener {
    List <ReservationModel> ordersList;
    //the recyclerview
    RecyclerView recyclerView;
    Orders_Adapter adapter;
    ReservationModel selectedOrder;
    private static final String TAG = "OrderResult";

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
                selectedOrder = adapter.getOrderSelected(position);
                AlertDialog.Builder builder = new AlertDialog.Builder(recyclerView.getContext());
                builder.setTitle("Order No' : " + selectedOrder.getReservationID() + "\n----------------------\n");
                builder.setMessage(
                        "Date Order : " + selectedOrder.getReservationDate() + "\n" +
                                "Rental Start Date : " + selectedOrder.getReservationStartDate() + "\n" +
                                "Rental End Date : " + selectedOrder.getReservationEndDate() +
                                "\n\nOrder Status : " + selectedOrder.orderIsActive() +
                                "\n\n" +
                                "Vehicle Order :\n-----------------\n" + selectedOrder.getVehicle().getVehicleFullName() + "\n" +
                                selectedOrder.getVehicle().getCarShortdesc() + "\nID : " + selectedOrder.getVehicle().getPlateNumber() +
                                "\n\nNotes : \n--------\n" + selectedOrder.getNotes() + "\n\n----------------\n\n"
                );
                builder.setPositiveButton("Close", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });

                builder.show();
            }

        }));

        //initializing the productlist
        ordersList = new ArrayList <>();

        //this method will fetch and parse json 
        //to display it in recyclerview
        loadOrdersData();
    }

    private void loadOrdersData() {
        Queue <ReservationModel> list = clientSocket.getReservationList();
        if (list == null) {
            Toast.makeText(getBaseContext(), "Error , please try again later", Toast.LENGTH_LONG).show();
            finish();
        }
        while (!list.isEmpty()) {
            ordersList.add(list.poll());
        }

        //creating adapter object and setting it to recyclerview
        adapter = new Orders_Adapter(OrderResult.this, ordersList);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        startLogoutTimer(this, this);
        Log.e(TAG, "OnStart () &&& Starting timer");
    }

    @Override
    public void onUserInteraction() {
        super.onUserInteraction();
        startLogoutTimer(this, this);
        Log.e(TAG, "User interacting with screen , Timer Start");
    }


    @Override
    protected void onPause() {
        super.onPause();
        startLogoutTimer(this, this);
        Log.e(TAG, "onPause()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        stopLogoutTimer();
        Log.e(TAG, "onResume()");
    }

    /**
     * Performing idle time logout
     */
    @Override
    public void doLogout() {
    }
}