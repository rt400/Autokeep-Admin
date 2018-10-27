package com.autokeep.AutoKeep.Communication;

import android.os.AsyncTask;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.LinkedList;
import java.util.Queue;

public class clientSocket extends AsyncTask <Void, Void, Boolean> {
    private static String reciveData;
    private static String status;
    private static String serverMSG;
    private static boolean isAdmin;
    private static Socket socket;
    private static int port = 40500;
    private static String ip = "shahak18.ddns.net";
    private CommunicationInterpreter dataConverter = new CommunicationInterpreter();
    private Queue <String> keys = new LinkedList <>();
    private Queue <String> values = new LinkedList <>();
    private Protocol protocol;

    private final static clientSocket client = new clientSocket();

    public static clientSocket getInstance() {
        return clientSocket.client;
    }

    public static int getPort() {
        return port;
    }

    public static void setPort(int port) {
        clientSocket.port = port;
    }

    public static String getIp() {
        return ip;
    }

    public static void setIp(String ip) {
        clientSocket.ip = ip;
    }

    public static String getServerMSG() {
        return serverMSG;
    }

    public static void setServerMSG(String serverMSG) {
        clientSocket.serverMSG = serverMSG;
    }

    public static String getStatusData() {
        return status;
    }

    private static void setStatus(String data) {
        status = data;
    }

    public static String getReciveData() {
        return reciveData;
    }

    private static void setReciveData(String data) {
        reciveData = data;
    }

    public static boolean isIsAdmin() {
        return isAdmin;
    }

    public static void setIsAdmin(boolean isAdmin) {
        clientSocket.isAdmin = isAdmin;
    }

    public static boolean isOnline() {
        boolean avalibale = true;
        try {
            SocketAddress sa = new InetSocketAddress(getIp(), getPort());
            Socket ss = new Socket();
            ss.connect(sa, 5000);
            ss.close();
        } catch (Exception e) {
            avalibale = false;
        }
        return avalibale;
    }

    public boolean connection() {
        if (doInBackground()) {
            return true;
        }
        return false;
    }

    public void SendLogin(String username, String password) throws IOException {
        keys.add("user");
        values.add("{emailAddress:" + username + ",password:" + password + "}");
        String str = dataConverter.encodeParametersToJson(ProtocolMessage.LOGIN, keys, values);
        protocol.write(str);
        protocol.flush();
    }

    public void SendSearch(String reservationStartDate, String reservationEndDate, String vehicleType, String seatsNumber) throws IOException {
        keys.add("reservation");
        values.add("{reservationStartDate:" + reservationStartDate
                + ",reservationEndDate:" + reservationEndDate
                + ",vehicle:{vehicleType:" + vehicleType + ",seatsNumber:" + seatsNumber + "}}");
        System.out.println(reservationStartDate);
        String str = dataConverter.encodeParametersToJson(ProtocolMessage.SEARCH_VEHICLE, keys, values);
        protocol.write(str);
        protocol.flush();

    }

    public void SendOrders(String startDate, String endDate, String carType, String carSit) throws IOException {
        keys.add("orders");
        values.add("{startDate:" + startDate + ",endDate:" + endDate + ",carType:" +
                carType + ",carSit:" + carSit + "}");
        String str = dataConverter.encodeParametersToJson(ProtocolMessage.SEARCH_VEHICLE, keys, values);
        protocol.write(str);
        protocol.flush();
    }

    public Object readFromServer() {
        try {
            setReciveData(protocol.read());
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(getReciveData());
        switch (dataConverter.getProtocolMsg(getReciveData())) {
            case OK:
                setStatus("OK");
                break;
            case VEHICLE_MODEL_LIST:
                return dataConverter.decodeFromJsonToObj(ProtocolMessage.VEHICLE_MODEL_LIST, reciveData);
            //break;
            case WRONG_CREDENTIAL:
                setStatus((String) dataConverter.decodeFromJsonToObj(ProtocolMessage.WRONG_CREDENTIAL, getReciveData()));
                setServerMSG(ProtocolMessage.WRONG_CREDENTIAL.toString());
                break;
            case TOO_MANY_AUTHENTICATION_RETRIES:
                setStatus((String) dataConverter.decodeFromJsonToObj(ProtocolMessage.TOO_MANY_AUTHENTICATION_RETRIES, getReciveData()));
                break;
            case ERROR:

            default:
                //System.out.println("not");
                break;
        }
        return null;// msg;
    }

    public boolean isConncet() {
        return socket.isConnected();
    }

    public boolean close() {
        try {
            protocol.close();
            socket.close();
            socket = null;
        } catch (NullPointerException | IOException e) {
            return false;
        }
        return true;
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        try {
            if (socket == null) {
                socket = new Socket(getIp(), getPort());
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
