package com.autokeep.AutoKeep.Communication;

import android.os.AsyncTask;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.LinkedList;
import java.util.Queue;

public class clientSocket extends AsyncTask <Void, Void, Boolean> {
    private final static clientSocket client = new clientSocket();
    private static String reciveData;
    private static String status;
    private static String serverMSG;
    private static String userName;
    private static String password;
    private static Socket socket;
    private static int port = 40501;
    private static String ip = "shahak18.ddns.net";
    private CommunicationInterpreter dataConverter = new CommunicationInterpreter();
    private Protocol protocol;

    public static clientSocket getClient() {
        return client;
    }

    public static String getPassword() {
        return password;
    }

    public static String getUserName() {
        return userName;
    }

    private void setUserName(String userName) {
        clientSocket.userName = userName;
    }

    public static clientSocket getInstance() {
        return clientSocket.client;
    }


    public static String getServerMSG() {
        return serverMSG;
    }

    private static void setServerMSG(String serverMSG) {
        clientSocket.serverMSG = serverMSG;
    }

    public static String getStatusData() {
        return status;
    }

    public static boolean isOnline() {
        boolean avalibale = true;
        try {
            SocketAddress sa = new InetSocketAddress(ip, port);
            Socket ss = new Socket();
            ss.connect(sa, 5000);
            ss.close();
        } catch (Exception e) {
            avalibale = false;
        }
        return avalibale;
    }

    public boolean connection() {
        setUserName("Yuval");
        return doInBackground();
    }

    public void SendLogin(String username, String user_password) throws IOException {
        userName = username;
        password = user_password;
        Queue <String> keys = new LinkedList <>();
        Queue <String> values = new LinkedList <>();
        keys.add("user");
        values.add("{emailAddress:" + username + ",password:" + password + "}");
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
        //readFromServer();
    }

    public void SendNewOrder(String plateNumber) throws IOException {
        Queue <String> keys = new LinkedList <>();
        Queue <String> values = new LinkedList <>();
        keys.add("reservation");
        values.add("{vehicle:{plateNumber:" + plateNumber + "}}");
        String str = dataConverter.encodeParametersToJson(ProtocolMessage.NEW_ORDER, keys, values);
        protocol.write(str);
        protocol.flush();
        readFromServer();
    }

    public void SendOrders() throws IOException {
        Queue <String> keys = new LinkedList <>();
        Queue <String> values = new LinkedList <>();
        keys.add("orders");
        values.add("{}");
        String str = dataConverter.encodeParametersToJson(ProtocolMessage.VIEW_ORDERS, keys, values);
        protocol.write(str);
        protocol.flush();
        readFromServer();
    }

    public void SendNewPassword(String new_password) throws IOException {
        Queue <String> keys = new LinkedList <>();
        Queue <String> values = new LinkedList <>();
        keys.add("user");
        values.add("{emailAddress:" + userName + ",password:" + new_password + "}");
        String str = dataConverter.encodeParametersToJson(ProtocolMessage.USER_CHANGE_PASSWORD, keys, values);
        protocol.write(str);
        protocol.flush();
        readFromServer();
    }

    public Object readFromServer() {
        try {
            reciveData = protocol.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //System.out.println(getReciveData());
        switch (dataConverter.getProtocolMsg(reciveData)) {
            case OK:
                status = ("OK");
                break;
            case VEHICLE_MODEL_LIST:
                return dataConverter.decodeFromJsonToObj(ProtocolMessage.VEHICLE_MODEL_LIST, reciveData);
            //break;
            case NO_AVAILABLE_VEHICLES:
                status = "ERROR";
                serverMSG = ((String) dataConverter.decodeFromJsonToObj(ProtocolMessage.NO_AVAILABLE_VEHICLES, reciveData));
                break;
            case WRONG_CREDENTIAL:
                status = "ERROR";
                serverMSG = ((String) dataConverter.decodeFromJsonToObj(ProtocolMessage.WRONG_CREDENTIAL, reciveData));
                break;
            case TOO_MANY_AUTHENTICATION_RETRIES:
                status = "ERROR";
                serverMSG = ((String) dataConverter.decodeFromJsonToObj(ProtocolMessage.TOO_MANY_AUTHENTICATION_RETRIES, reciveData));
                break;
            case USER_IS_BANNED:
                status = "ERROR";
                serverMSG = ((String) dataConverter.decodeFromJsonToObj(ProtocolMessage.USER_IS_BANNED, reciveData));
                break;
            case PASSWORD_CHANGED_SUCCESSFULLY:
                status = "OK";
                serverMSG = ((String) dataConverter.decodeFromJsonToObj(ProtocolMessage.PASSWORD_CHANGED_SUCCESSFULLY, reciveData));
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
            return false;
        }
        return true;
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        try {
            if (socket == null) {
                socket = new Socket(ip, port);
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
