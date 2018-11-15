package autokeep.auto.Communication;

import autokeep.auto.AdminModels.ReservationModel;
import autokeep.auto.AdminModels.UserModel;
import autokeep.auto.AdminModels.VehicleModel;
import java.io.IOException;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AdminSocket implements Serializable {

    private final static AdminSocket adminConnect = new AdminSocket();
    private static String reciveData;
    private static String status;
    private static String serverMSG;
    private static Socket socket;
    private static Queue<VehicleModel> carsList;

    public static Queue<VehicleModel> getCarsList() {
        return carsList;
    }
    private static Queue<UserModel> usersList;

    public static Queue<UserModel> getUsersList() {
        return usersList;
    }
    private static Queue<ReservationModel> reservationList;

    public static Queue<ReservationModel> getreservationList() {
        return reservationList;
    }

    private static UserModel user;
    private final CommunicationInterpreter dataConverter = new CommunicationInterpreter();
    private Protocol protocol;

    private AdminSocket() {
    }

    public static Queue<VehicleModel> getCarList() {
        return carsList;
    }

    public static AdminSocket getInstance() {
        return AdminSocket.adminConnect;
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
        String str;
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
                str = dataConverter.encodeObjToJson(ProtocolMessage.CREATE_NEW_VEHICLE, car);
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

    public void SendOrders() throws IOException {
        String str = dataConverter.setProtocolMsg(ProtocolMessage.RESERVATIONS_LIST);
        System.out.println(str);
        protocol.write(str);
        protocol.flush();
        readFromServer();
    }

    public void SendOrderData(ReservationModel rsv, String mode) throws IOException {
        String str;
        switch (mode) {
            case "New":
                str = dataConverter.encodeObjToJson(ProtocolMessage.CREATE_NEW_RESERVATION, rsv);
                break;
            case "Update":
                str = dataConverter.encodeObjToJson(ProtocolMessage.UPDATE_RESERVATION, rsv);
                break;
            case "Cancel":
                str = dataConverter.encodeObjToJson(ProtocolMessage.CANCEL_RESERVATION, rsv);
                break;
            default:
                str = dataConverter.setProtocolMsg(ProtocolMessage.RESERVATIONS_LIST);
                break;
        }
        System.out.println(str);
        protocol.write(str);
        protocol.flush();
        readFromServer();
    }

    public Object readFromServer() {
        try {
            socket.setSoTimeout(10 * 1000);
            reciveData = protocol.read();
            status = "";
            serverMSG = "";
            System.out.println(reciveData);
            switch (dataConverter.getProtocolMsg(reciveData)) {
                // Login
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
                case USER_ALREADY_CONNECTED:
                    status = "ERROR";
                    serverMSG = ((String) dataConverter.decodeFromJsonToObj(ProtocolMessage.USER_ALREADY_CONNECTED, reciveData));
                    break;

                // Users
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
                case USER_DELETION_FAILED:
                    status = "ERROR";
                    serverMSG = ((String) dataConverter.decodeFromJsonToObj(ProtocolMessage.USER_DELETION_FAILED, reciveData));
                    break;
                case USER_UPDATE_FAILED:
                    status = "ERROR";
                    serverMSG = ((String) dataConverter.decodeFromJsonToObj(ProtocolMessage.USER_UPDATE_FAILED, reciveData));
                    break;
                case EMAIL_ADDRESS_ALREADY_REGISTERED:
                    status = "ERROR";
                    serverMSG = ((String) dataConverter.decodeFromJsonToObj(ProtocolMessage.EMAIL_ADDRESS_ALREADY_REGISTERED, reciveData));
                    break;

                // Cars    
                case VEHICLE_MODEL_LIST:
                    status = "OK";
                    carsList = (Queue<VehicleModel>) dataConverter.decodeFromJsonToObj(ProtocolMessage.VEHICLE_MODEL_LIST, reciveData);
                    break;
                case VEHICLE_CREATED_SUCCESSFULLY:
                    status = "OK";
                    serverMSG = ((String) dataConverter.decodeFromJsonToObj(ProtocolMessage.VEHICLE_CREATED_SUCCESSFULLY, reciveData));
                    break;
                case VEHICLE_UPDATED_SUCCESSFULLY:
                    status = "OK";
                    serverMSG = ((String) dataConverter.decodeFromJsonToObj(ProtocolMessage.VEHICLE_UPDATED_SUCCESSFULLY, reciveData));
                    break;
                case VEHICLE_DELETED_SUCCESSFULLY:
                    status = "OK";
                    serverMSG = ((String) dataConverter.decodeFromJsonToObj(ProtocolMessage.VEHICLE_DELETED_SUCCESSFULLY, reciveData));
                    break;
                case VEHICLE_ALREADY_EXIST:
                    status = "ERROR";
                    serverMSG = ((String) dataConverter.decodeFromJsonToObj(ProtocolMessage.VEHICLE_ALREADY_EXIST, reciveData));
                    break;
                case VEHICLE_UPDATE_FAILED:
                    status = "ERROR";
                    serverMSG = ((String) dataConverter.decodeFromJsonToObj(ProtocolMessage.VEHICLE_UPDATE_FAILED, reciveData));
                    break;
                case VEHICLE_DELETE_FAILED:
                    status = "ERROR";
                    serverMSG = ((String) dataConverter.decodeFromJsonToObj(ProtocolMessage.VEHICLE_DELETE_FAILED, reciveData));
                    break;
                case NO_AVAILABLE_VEHICLES:
                    status = "ERROR";
                    serverMSG = ((String) dataConverter.decodeFromJsonToObj(ProtocolMessage.NO_AVAILABLE_VEHICLES, reciveData));
                    break;

                //orders
                case RESERVATION_MODEL_LIST:
                    status = "OK";
                    reservationList = (Queue<ReservationModel>) dataConverter.decodeFromJsonToObj(ProtocolMessage.RESERVATION_MODEL_LIST, reciveData);
                    break;

                // ERRORS    
                case ERROR:
                    status = "ERROR";
                    serverMSG = ((String) dataConverter.decodeFromJsonToObj(ProtocolMessage.ERROR, reciveData));
                    break;
                case INTERNAL_ERROR:
                    status = "FATAL";
                    serverMSG = ((String) dataConverter.decodeFromJsonToObj(ProtocolMessage.INTERNAL_ERROR, reciveData));
                    break;
                case WRONG_PROTOCOL_REQUEST:
                    status = "FATAL";
                    serverMSG = ((String) dataConverter.decodeFromJsonToObj(ProtocolMessage.WRONG_PROTOCOL_REQUEST, reciveData));
                    break;
                default:
                    break;
            }

        } catch (IOException | InterruptedException e) {
            MessageControl.getInstance().sendError("Server Down !");
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
                socket = new Socket();
                socket.connect(new InetSocketAddress(InetAddress.getByName("shahak18.ddns.net"), 40500), 5000);
                protocol = new Protocol(socket.getInputStream(), socket.getOutputStream());

            } else if (isConncet()) {
                return true;
            }
        } catch (java.net.SocketTimeoutException exception) {
            close();
            MessageControl.getInstance().sendError("Server Down !");
            return false;

        } catch (IOException e) {

            System.out.println(e.toString());
            close();
            return false;
        }
        return true;
    }
}
