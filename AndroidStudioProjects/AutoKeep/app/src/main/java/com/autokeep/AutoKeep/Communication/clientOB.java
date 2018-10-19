package com.autokeep.AutoKeep.Communication;

import android.os.AsyncTask;

import java.io.IOException;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Queue;

public class clientOB extends AsyncTask <Void, Void, Void> {
    private static String serverReadData;
    private static String status;
    private static boolean isAdmin;
    private static Socket socket;
    private CommunicationInterpreter cdataConverter = new CommunicationInterpreter();
    private Queue <String> keys = new LinkedList <>();
    private Queue <String> values = new LinkedList <>();
    private Protocol protocol;
    private int port;
    private String ip;

    public clientOB(String ip, int port) {
        this.ip = ip;
        this.port = port;
        doInBackground();
    }

    public static String getStatusData() {
        return status;
    }

    private static void setStatus(String data) {
        status = data;
    }

    private static String getServerReadData() {
        return serverReadData;
    }

    private static void setServerReadData(String data) {
        serverReadData = data;
    }

    public static boolean isIsAdmin() {
        return isAdmin;
    }

    public static void setIsAdmin(boolean isAdmin) {
        clientOB.isAdmin = isAdmin;
    }

    public void SendLogin(String username, String password) throws IOException {
        keys.add("user");
        values.add("{emailAddress:" + username + ",password:" + password + "}");
        String str = cdataConverter.encodeParametersToJson(ProtocolMessage.LOGIN, keys, values);
        protocol.write(str);
        protocol.flush();
    }

    public void SendSearch(String startDate, String endDate, String carType, String carSit) throws IOException {
        keys.add("searchCar");
        values.add("{startDate:" + startDate + ",endDate:" + endDate + ",carType:" +
                carType + ",carSit:" + carSit + "}");
        String str = cdataConverter.encodeParametersToJson(ProtocolMessage.SEARCH_CAR, keys, values);
        protocol.write(str);
        protocol.flush();
    }

    public void SendOrders(String startDate, String endDate, String carType, String carSit) throws IOException {
        keys.add("orders");
        values.add("{startDate:" + startDate + ",endDate:" + endDate + ",carType:" +
                carType + ",carSit:" + carSit + "}");
        String str = cdataConverter.encodeParametersToJson(ProtocolMessage.SEARCH_CAR, keys, values);
        protocol.write(str);
        protocol.flush();
    }


    public String readFromServer() {
        try {
            setServerReadData(protocol.read());
        } catch (IOException e) {
            e.printStackTrace();
        }
        switch (cdataConverter.getProtocolMsg(getServerReadData())) {
            case OK:
                setStatus("OK");
                //System.out.println("ok");
                break;
            case WRONG_CREDENTIAL:
                setStatus("ERROR");
                //System.out.println("Error");
                break;
            default:
                //System.out.println("not");
                break;
        }
        return null;// msg;
    }

    public boolean close() {
        try {
            socket.close();
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    @Override
    protected Void doInBackground(Void... voids) {

        try {
            socket = new Socket(ip, port);
            protocol = new Protocol(socket.getInputStream(), socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
