package com.autokeep.AutoKeep.UserActivityPack;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.autokeep.AutoKeep.R;
import com.autokeep.AutoKeep.UserMode.ReservationModel;
import com.bumptech.glide.Glide;

import java.util.List;

public class Orders_Adapter extends RecyclerView.Adapter <Orders_Adapter.OrdersViewHolder> {

    private Context mCtx;
    private List <ReservationModel> ordersList;
    private int selectedPos = RecyclerView.NO_POSITION;

    public Orders_Adapter(Context mCtx, List <ReservationModel> list) {
        this.mCtx = mCtx;
        this.ordersList = list;
    }

    @Override
    public OrdersViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.orderlists, null);
        return new OrdersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final OrdersViewHolder holder, final int position) {
        ReservationModel order = ordersList.get(position);
        holder.itemView.setSelected(selectedPos == position);
        //loading the image
        Glide.with(mCtx)
                .load(order.getVehicle().getVehicleImage())
                .into(holder.imageView);
        holder.orderCarName.setText(order.getVehicle().getVehicleFullName());
        holder.orderShortDesc.setText(order.getOrderShortdesc());
        holder.orderCarID.setText(order.orderIsActive());
        if (order.orderIsActive().equals("Order is Active")) {
            holder.orderCarID.setBackgroundColor(Color.parseColor("red"));
        } else if (order.orderIsActive().equals("Order is Waiting")) {
            holder.orderCarID.setBackgroundColor(Color.parseColor("green"));
        } else if (order.orderIsActive().equals("Order is Cancelled")) {
            holder.orderCarID.setBackgroundColor(Color.parseColor("white"));
        } else {
            holder.orderCarID.setBackgroundColor(Color.parseColor("white"));
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notifyItemChanged(selectedPos);
                selectedPos = holder.getLayoutPosition();
                notifyItemChanged(selectedPos);
            }
        });


    }

    public ReservationModel getOrderSelected(int position) {
        return ordersList.get(position);
    }

    @Override
    public int getItemCount() {
        return ordersList.size();
    }

    class OrdersViewHolder extends RecyclerView.ViewHolder {

        TextView orderCarName, orderShortDesc, orderCarID;
        ImageView imageView;

        public OrdersViewHolder(View itemView) {
            super(itemView);

            orderCarName = itemView.findViewById(R.id.OrderCarName);
            orderShortDesc = itemView.findViewById(R.id.OrderShortDesc);
            orderCarID = itemView.findViewById(R.id.OrderCarID);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }
}
