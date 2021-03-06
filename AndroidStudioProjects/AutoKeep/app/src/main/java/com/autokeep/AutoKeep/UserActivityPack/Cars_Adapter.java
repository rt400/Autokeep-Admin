package com.autokeep.AutoKeep.UserActivityPack;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.autokeep.AutoKeep.R;
import com.autokeep.AutoKeep.UserMode.VehicleModel;
import com.bumptech.glide.Glide;

import java.util.List;

public class Cars_Adapter extends RecyclerView.Adapter <Cars_Adapter.CarsViewHolder> {

    private Context mCtx;
    private List <VehicleModel> calLists;
    private int selectedPos = RecyclerView.NO_POSITION;

    public Cars_Adapter(Context mCtx, List <VehicleModel> calLists) {
        this.mCtx = mCtx;
        this.calLists = calLists;
    }

    @Override
    public CarsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.carlists, null);
        return new CarsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final CarsViewHolder holder, final int position) {
        VehicleModel car = calLists.get(position);
        holder.itemView.setSelected(selectedPos == position);
        //loading the image
        Glide.with(mCtx)
                .load(car.getVehicleImage())
                .into(holder.imageView);
        holder.textViewName.setText(car.getVehicleFullName());
        holder.textViewShortDesc.setText(car.getCarShortdesc());
        holder.textViewID.setText(car.getCarFixedID());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notifyItemChanged(selectedPos);
                selectedPos = holder.getLayoutPosition();
                notifyItemChanged(selectedPos);
            }
        });


    }


    @Override
    public int getItemCount() {
        return calLists.size();
    }

    public String getSelectedCarID(int position) {
        return calLists.get(position).getPlateNumber();
    }

    public VehicleModel getCarSelected(int position) {
        return calLists.get(position);
    }

    class CarsViewHolder extends RecyclerView.ViewHolder {

        TextView textViewName, textViewShortDesc, textViewID;
        ImageView imageView;

        public CarsViewHolder(View itemView) {
            super(itemView);

            textViewName = itemView.findViewById(R.id.textViewCarName);
            textViewShortDesc = itemView.findViewById(R.id.textViewShortDesc);
            textViewID = itemView.findViewById(R.id.textViewID);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }

}
