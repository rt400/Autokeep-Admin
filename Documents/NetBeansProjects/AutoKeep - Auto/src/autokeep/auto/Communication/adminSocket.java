package autokeep.auto.Communication;

import autokeep.auto.AdminModels.ReservationModel;
import autokeep.auto.AdminModels.UserModel;
import autokeep.auto.AdminModels.VehicleModel;
import java.io.IOException;
import java.io.Serializable;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Queue;

public class adminSocket implements Serializable {

    private final static adminSocket adminConnect = new adminSocket();
    private static String reciveData;
    private static String status;
    private static String serverMSG;
    private static Socket socket;
    private static Queue<VehicleModel> carsList;
    private static Queue<UserModel> usersList;

    public static Queue<UserModel> getUsersList() {
        return usersList;
    }
    private static Queue<ReservationModel> reservationList;
    private static UserModel user;
    private final CommunicationInterpreter dataConverter = new CommunicationInterpreter();
    private Protocol protocol;

    private adminSocket() {
    }

    public static Queue<VehicleModel> getCarList() {
        return carsList;
    }

    public static adminSocket getInstance() {
        return adminSocket.adminConnect;
    }

    public static String getServerMSG() {
        return serverMSG;
    }

    public static String getStatusData() {
        return status;
    }

    public static UserModel getUser() {
        return user;
    }

    public static Queue<ReservationModel> getReservationList() {
        return reservationList;
    }

    public boolean connection() {
        return connectServer();
    }

    public void SendLogin(String username, String user_password) throws IOException {
        Queue<String> keys = new LinkedList<>();
        Queue<String> values = new LinkedList<>();
        keys.add("user");
        values.add("{emailAddress:" + username + ",password:" + user_password + "}");
        String str = dataConverter.encodeParametersToJson(ProtocolMessage.LOGIN, keys, values);
        System.out.println(str);
        protocol.write(str);
        protocol.flush();
        readFromServer();
    }

    public void SendUsers() throws IOException {
        String str = dataConverter.setProtocolMsg(ProtocolMessage.USERS_LIST);
        System.out.println(str);
        protocol.write(str);
        protocol.flush();
        readFromServer();
    }

    public void SendUserData(UserModel user, String mode) throws IOException {
        String str ;
        switch (mode) {
            case "New":
                str = dataConverter.encodeObjToJson(ProtocolMessage.CREATE_NEW_USER, user);
                break;
            case "Update":
                str = dataConverter.encodeObjToJson(ProtocolMessage.UPDATE_USER, user);
                break;
            case "Delete":
                str = dataConverter.encodeObjToJson(ProtocolMessage.DELETE_USER, user);
                break;
            default:
                str = dataConverter.setProtocolMsg(ProtocolMessage.USERS_LIST);
        }

        System.out.println(str);
        protocol.write(str);
        protocol.flush();
        readFromServer();
    }

    public void SendCars() throws IOException {
        String str = dataConverter.setProtocolMsg(ProtocolMessage.VEHICLES_LIST);
        System.out.println(str);
        protocol.write(str);
        protocol.flush();
        readFromServer();
    }

    public void SendCarsData(VehicleModel car, String mode) throws IOException {
        String str;
        switch (mode) {
            case "New":
                str = dataConverter.encodeObjToJson(ProtocolMessage.CREATE_NEW_USER, car);
                break;
            case "Update":
                str = dataConverter.encodeObjToJson(ProtocolMessage.UPDATE_VEHICLE, car);
                break;
            case "Delete":
                str = dataConverter.encodeObjToJson(ProtocolMessage.DELETE_VEHICLE, car);
                break;
            default:
                str = dataConverter.setProtocolMsg(ProtocolMessage.VEHICLES_LIST);
                break;
        }
        System.out.println(str);
        protocol.write(str);
        protocol.flush();
        readFromServer();
    }

    public Object readFromServer() {
        try {
            reciveData = protocol.read();
        } catch (IOException e) {
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
            case USER_IS_BANNED:
                status = "ERROR";
                serverMSG = ((String) dataConverter.decodeFromJsonToObj(ProtocolMessage.USER_IS_BANNED, reciveData));
                break;
            case USER_MODEL_LIST:
                status = "OK";
                usersList = (Queue<UserModel>) dataConverter.decodeFromJsonToObj(ProtocolMessage.USER_MODEL_LIST, reciveData);
                break;
            case USER_DELETED_SUCCESSFULLY:
                status = "OK";
                serverMSG = ((String) dataConverter.decodeFromJsonToObj(ProtocolMessage.USER_DELETED_SUCCESSFULLY, reciveData));
                break;
            case USER_UPDATED_SUCCESSFULLY:
                status = "OK";
                serverMSG = ((String) dataConverter.decodeFromJsonToObj(ProtocolMessage.USER_UPDATED_SUCCESSFULLY, reciveData));
                break;
            case VEHICLE_MODEL_LIST:
                status = "OK";
                carsList = (Queue<VehicleModel>) dataConverter.decodeFromJsonToObj(ProtocolMessage.VEHICLE_MODEL_LIST, reciveData);
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

    private Boolean connectServer() {
        try {
            if (socket == null) {
                socket = new Socket("shahak18.ddns.net", 40500);
                protocol = new Protocol(socket.getInputStream(), socket.getOutputStream());
            } else if (isConncet()) {
                return true;
            }
        } catch (IOException e) {
            System.out.println(e.toString());
            return false;
        }
        return true;
    }
}
