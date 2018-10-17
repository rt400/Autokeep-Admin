package com.autokeep.AutoKeep;

import android.os.AsyncTask;

import com.autokeep.AutoKeep.UserMode.CommunicationInterpreter;
import com.autokeep.AutoKeep.UserMode.ProtocolMessage;

import java.io.IOException;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Queue;

class clientOB extends AsyncTask <Void, Void, Void> {
    public static String getDataServer;
    public static String status;
    public static boolean isAdmin;
    private static Socket socket;
    public ProtocolMessage prtMsg;
    CommunicationInterpreter cdataConverter = new CommunicationInterpreter();
    Queue <String> keys = new LinkedList <>();
    Queue <String> values = new LinkedList <>();
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

    public static void setStatus(String data) {
        status = data;
    }

    public static String getGetDataServer() {
        return getDataServer;
    }

    public static void setGetDataServer(String data) {
        getDataServer = data;
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
        keys.add("user");
        values.add("{emailAddress:" + startDate + ",password:" + endDate + "}");
        String str = cdataConverter.encodeParametersToJson(ProtocolMessage.LOGIN, keys, values);
        protocol.write(str);
        protocol.flush();
    }

    public String readFromServer() {
        try {
            setGetDataServer(protocol.read());
        } catch (IOException e) {
            e.printStackTrace();
        }
        switch (cdataConverter.getProtocolMsg(getGetDataServer())) {
            case OK:
                setStatus("OK");
                System.out.println("ok");
                break;
            case WRONG_CREDENTIAL:
                setStatus("ERROR");
                System.out.println("Error");

                break;
            default:
                System.out.println("not");

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
