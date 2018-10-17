package com.autokeep.AutoKeep;

import com.autokeep.AutoKeep.UserMode.VehicleModel;

import java.util.ArrayList;

public class UserSearchResult {

    private ArrayList<VehicleModel> cars = new ArrayList<VehicleModel>();

    public UserSearchResult(ArrayList <VehicleModel> cars) {
        this.setCars(new VehicleModel(12345,"Fiat",2007,5,
                1600,true));
        this.cars = cars;
    }


    public void setCars(VehicleModel car) {
        this.cars.add(car);
    }



}
