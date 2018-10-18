package com.autokeep.AutoKeep.UserMode;

public class VehicleModel {
    private String carID;
    private String manufacturer;
    private String manufactureDate;
    private String seatsNumber;
    private String engineCapacity;
    private String image;
    private boolean isUsable = true;

    public VehicleModel(String carID, String manufacturer, String manufactureDate, String seatsNumber,
                        String engineCapacity, String image, boolean isUsable) {
        this.carID = carID;
        this.manufacturer = manufacturer;
        this.manufactureDate = manufactureDate;
        this.seatsNumber = seatsNumber;
        this.engineCapacity = engineCapacity;
        this.image = image;
        this.isUsable = isUsable;
    }

    public VehicleModel() {

    }

    public VehicleModel(String carID, String manufacturer, String manufactureDate, String seatsNumber,
                        String engineCapacity, String image) {
        this.carID = carID;
        this.manufacturer = manufacturer;
        this.manufactureDate = manufactureDate;
        this.seatsNumber = seatsNumber;
        this.engineCapacity = engineCapacity;
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCarID() {
        return carID;
    }

    public void setCarID(String carID) {
        this.carID = carID;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getManufactureDate() {
        return manufactureDate;
    }

    public void setManufactureDate(String manufactureDate) {
        this.manufactureDate = manufactureDate;
    }

    public String getSeatsNumber() {
        return seatsNumber;
    }

    public void setSeatsNumber(String seatsNumber) {
        this.seatsNumber = seatsNumber;
    }

    public String getEngineCapacity() {
        return engineCapacity;
    }

    public void setEngineCapacity(String engineCapacity) {
        this.engineCapacity = engineCapacity;
    }

    public boolean isUsable() {
        return isUsable;
    }

    public void setUsable(boolean usable) {
        isUsable = usable;
    }

    public String getShortdesc() {
        return "Engine: " + getEngineCapacity() + " , Seats: " + getSeatsNumber();
    }
}
