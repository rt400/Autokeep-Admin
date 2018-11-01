package com.autokeep.AutoKeep.UserMode;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ReservationModel {
    private int reservationID;
    private UserModel user;
    private VehicleModel vehicle;
    private String reservationDate;
    private String reservationStartDate;
    private String reservationEndDate;

    public ReservationModel(int reservationID, UserModel user, VehicleModel vehicle, String reservationDate,
                            String reservationStartDate, String reservationEndDate) {
        super();
        this.reservationID = reservationID;
        this.user = user;
        this.vehicle = vehicle;
        this.reservationDate = reservationDate;
        this.reservationStartDate = reservationStartDate;
        this.reservationEndDate = reservationEndDate;
    }

    public int getReservationID() {
        return reservationID;
    }

    public void setReservationID(int reservationID) {
        this.reservationID = reservationID;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

    public VehicleModel getVehicle() {
        return vehicle;
    }

    public void setVehicle(VehicleModel vehicle) {
        this.vehicle = vehicle;
    }

    private static String modifyDateLayout(String inputDate) throws ParseException {
        if (inputDate.length() == 10) {
            Date date = new SimpleDateFormat("yyyy-MM-dd").parse(inputDate);
            return new SimpleDateFormat("dd/MM/yyyy").format(date);
        }
        Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(inputDate);
        return new SimpleDateFormat("dd/MM/yyyy HH:mm").format(date);
    }

    public String getReservationDate() {
        try {
            return modifyDateLayout(reservationDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return reservationDate;
    }

    public void setReservationDate(String reservationDate) {
        this.reservationDate = reservationDate;
    }

    public String getNotes() {
        return "None";
    }

    public void setReservationStartDate(String reservationStartDate) {
        this.reservationStartDate = reservationStartDate;
    }

    public String getReservationStartDate() {
        try {
            return modifyDateLayout(reservationStartDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return reservationStartDate;
    }

    public String getReservationEndDate() {
        try {
            return modifyDateLayout(reservationEndDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return reservationEndDate;
    }

    public void setReservationEndDate(String reservationEndDate) {
        this.reservationEndDate = reservationEndDate;
    }

    public String orderIsActive() {
        Date startDate, endDate;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            startDate = sdf.parse(reservationStartDate);
            endDate = sdf.parse(reservationEndDate);
            endDate = addDays(endDate, 1);
            if (startDate.before(new Date()) && endDate.before(new Date())) {
                return "Order is Pass";
            } else if ((startDate.equals(new Date()) && endDate.after(new Date())) ||
                    (startDate.before(new Date()) && endDate.after(new Date())) ||
                    (startDate.before(new Date()) && endDate.equals(new Date()))
                    ) {
                return "Order is Active";
            }
        } catch (ParseException e) {
            return e.toString();
        }
        return "Order is Waiting";
    }

    public Date addDays(Date date, int days) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, days); //minus number would decrement the days
        return cal.getTime();
    }

    public String getOrderShortdesc() {
        return "Order No': " + getReservationID() + "\n" + getReservationDate();
    }
}