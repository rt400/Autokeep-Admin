package com.autokeep.AutoKeep.Communication;

import android.os.AsyncTask;

import com.autokeep.AutoKeep.UserMode.ReservationModel;
import com.autokeep.AutoKeep.UserMode.UserModel;
import com.autokeep.AutoKeep.UserMode.VehicleModel;

import java.io.IOException;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Queue;

public class clientSocket extends AsyncTask <Void, Void, Boolean> {
    private final static clientSocket client = new clientSocket();
    private static String reciveData;
    private static String status;
    private static String serverMSG;
    private static Socket socket;
    private static Queue <VehicleModel> carList;

    private static Queue <ReservationModel> reservationList;
    private static UserModel user;
    private CommunicationInterpreter dataConverter = new CommunicationInterpreter();
    private Protocol protocol;

    public static Queue <VehicleModel> getCarList() {
        return carList;
    }

    public static clientSocket getInstance() {
        return clientSocket.client;
    }

    public static String getServerMSG() {
        if (serverMSG != null) {
            return serverMSG;
        }
        return "";
    }

    public static String getStatusData() {
        return status;
    }

    public static UserModel getUser() {
        return user;
    }

    public static Queue <ReservationModel> getReservationList() {
        return reservationList;
    }

    public boolean connection() {
        return doInBackground();
    }

    public void SendLogin(String username, String user_password) throws IOException {
        Queue <String> keys = new LinkedList <>();
        Queue <String> values = new LinkedList <>();
        keys.add("user");
        values.add("{emailAddress:" + username + ",password:" + user_password + "}");
        String str = dataConverter.encodeParametersToJson(ProtocolMessage.LOGIN, keys, values);
        protocol.write(str);
        protocol.flush();
        readFromServer();
    }

    public void SendSearch(String reservationStartDate, String reservationEndDate, String vehicleType, String seatsNumber) throws IOException {
        Queue <String> keys = new LinkedList <>();
        Queue <String> values = new LinkedList <>();
        keys.add("reservation");
        values.add("{reservationStartDate:" + reservationStartDate
                + ",reservationEndDate:" + reservationEndDate
                + ",vehicle:{vehicleType:" + vehicleType + ",seatsNumber:" + seatsNumber + "}}");
        String str = dataConverter.encodeParametersToJson(ProtocolMessage.SEARCH_VEHICLE, keys, values);
        System.out.println(str);
        protocol.write(str);
        protocol.flush();
        readFromServer();
    }

    public void SendNewOrder(String plateNumber, String startDate, String endDate) throws IOException {
        Queue <String> keys = new LinkedList <>();
        Queue <String> values = new LinkedList <>();
        keys.add("reservation");
        values.add("{reservationStartDate:" + startDate
                + ",reservationEndDate:" + endDate
                + ",vehicle:{plateNumber:" + plateNumber + "}}");
        String str = dataConverter.encodeParametersToJson(ProtocolMessage.ORDER, keys, values);
        protocol.write(str);
        protocol.flush();
        readFromServer();
    }

    public void SendOrders() throws IOException {
        String str = dataConverter.setProtocolMsg(ProtocolMessage.RESERVATION_HISTORY);
        protocol.write(str);
        protocol.flush();
        readFromServer();
    }

    public void SendNewPassword(String new_password) throws IOException {
        Queue <String> keys = new LinkedList <>();
        Queue <String> values = new LinkedList <>();
        keys.add("user");
        values.add("{emailAddress:" + user.getEmailAddress() + ",password:" + new_password + "}");
        String str = dataConverter.encodeParametersToJson(ProtocolMessage.USER_CHANGE_PASSWORD, keys, values);
        System.out.println(str);
        protocol.write(str);
        protocol.flush();
        readFromServer();
    }


    public Object readFromServer() {
        try {
            reciveData = protocol.read();
        } catch (IOException | ClassNotFoundException e1) {
            System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
        }
        status = "";
        serverMSG = "";
        System.out.println(reciveData);
        switch (dataConverter.getProtocolMsg(reciveData)) {
            case OK:
                status = ("OK");
                user = (UserModel) dataConverter.decodeFromJsonToObj(ProtocolMessage.USER_MODEL, reciveData);
                break;
            case WRONG_CREDENTIAL:
                status = "ERROR";
                serverMSG = ((String) dataConverter.decodeFromJsonToObj(ProtocolMessage.WRONG_CREDENTIAL, reciveData));
                break;
            case TOO_MANY_AUTHENTICATION_RETRIES:
                status = "ERROR";
                serverMSG = ((String) dataConverter.decodeFromJsonToObj(ProtocolMessage.TOO_MANY_AUTHENTICATION_RETRIES, reciveData));
                break;
            case USER_ALREADY_CONNECTED:
                status = "ERROR";
                serverMSG = ((String) dataConverter.decodeFromJsonToObj(ProtocolMessage.USER_ALREADY_CONNECTED, reciveData));
                break;
            case USER_IS_BANNED:
                status = "BANNED";
                serverMSG = ((String) dataConverter.decodeFromJsonToObj(ProtocolMessage.USER_IS_BANNED, reciveData));
                break;
            case VEHICLE_MODEL_LIST:
                status = "OK";
                carList = (Queue <VehicleModel>) dataConverter.decodeFromJsonToObj(ProtocolMessage.VEHICLE_MODEL_LIST, reciveData);
                break;
            case ORDER_BOOKED_SUCCESSFULLY:
                status = "OK";
                serverMSG = ((String) dataConverter.decodeFromJsonToObj(ProtocolMessage.ORDER_BOOKED_SUCCESSFULLY, reciveData));
                break;
            case NO_AVAILABLE_VEHICLES:
                status = "ERROR";
                serverMSG = ((String) dataConverter.decodeFromJsonToObj(ProtocolMessage.NO_AVAILABLE_VEHICLES, reciveData));
                break;
            case ORDER_FAILED:
                status = "ERROR";
                serverMSG = ((String) dataConverter.decodeFromJsonToObj(ProtocolMessage.ORDER_FAILED, reciveData));
                break;
            case HISTORY_RESULT:
                status = "OK";
                reservationList = (Queue <ReservationModel>) dataConverter.decodeFromJsonToObj(ProtocolMessage.RESERVATION_MODEL_LIST, reciveData);
                break;
            case NO_HISTORY:
                status = "ERROR";
                serverMSG = ((String) dataConverter.decodeFromJsonToObj(ProtocolMessage.NO_HISTORY, reciveData));
                break;
            case PASSWORD_CHANGED_SUCCESSFULLY:
                status = "OK";
                serverMSG = ((String) dataConverter.decodeFromJsonToObj(ProtocolMessage.PASSWORD_CHANGED_SUCCESSFULLY, reciveData));
                break;
            case CHANGING_PASSWORD_FAILED:
                status = "ERROR";
                serverMSG = ((String) dataConverter.decodeFromJsonToObj(ProtocolMessage.CHANGING_PASSWORD_FAILED, reciveData));
                break;
            case ERROR:
                status = "ERROR";
                serverMSG = ((String) dataConverter.decodeFromJsonToObj(ProtocolMessage.NO_AVAILABLE_VEHICLES, reciveData));
                break;
            default:
                break;
        }
        return null;// msg;
    }

    private boolean isConncet() {
        return socket.isConnected();
    }

    public boolean close() {
        try {
            protocol.close();
            socket.close();
            socket = null;
        } catch (NullPointerException | IOException e) {
            System.out.println("Socket close");
            return false;
        }
        return true;
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        try {
            if (socket == null) {
                socket = new Socket("shahak18.ddns.net", 40500);
                protocol = new Protocol(socket.getInputStream(), socket.getOutputStream());
            } else if (isConncet()) {
                return true;
            }
        } catch (IOException e) {
            return false;
        }
        return true;
    }
}
