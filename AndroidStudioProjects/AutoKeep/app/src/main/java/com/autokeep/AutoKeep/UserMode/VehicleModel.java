package com.autokeep.AutoKeep.UserMode;

public class VehicleModel {
    private int carID;
    private String manufacturer;
    private int manufactureDate;
    private int seatsNumber;
    private int engineCapacity;
    private boolean isUsable = true;

    public VehicleModel(int carID ,String manufacturer, int manufactureDate, int seatsNumber, int engineCapacity,
                        boolean isUsable) {
        this.carID = carID;
        this.manufacturer = manufacturer;
        this.manufactureDate = manufactureDate;
        this.seatsNumber = seatsNumber;
        this.engineCapacity = engineCapacity;
        this.isUsable = isUsable;
    }


    public int getCarID() {
        return carID;
    }

    public void setCarID(int carID) {
        this.carID = carID;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public int getManufactureDate() {
        return manufactureDate;
    }

    public void setManufactureDate(int manufactureDate) {
        this.manufactureDate = manufactureDate;
    }

    public int getSeatsNumber() {
        return seatsNumber;
    }

    public void setSeatsNumber(int seatsNumber) {
        this.seatsNumber = seatsNumber;
    }

    public int getEngineCapacity() {
        return engineCapacity;
    }

    public void setEngineCapacity(int engineCapacity) {
        this.engineCapacity = engineCapacity;
    }

    public boolean isUsable() {
        return isUsable;
    }

    public void setUsable(boolean usable) {
        isUsable = usable;
    }
}
