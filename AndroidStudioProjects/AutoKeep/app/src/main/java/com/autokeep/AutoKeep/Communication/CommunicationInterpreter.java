package com.autokeep.AutoKeep.Communication;

import com.autokeep.AutoKeep.UserMode.ReservationModel;
import com.autokeep.AutoKeep.UserMode.UserModel;
import com.autokeep.AutoKeep.UserMode.VehicleModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.internal.bind.SqlDateTypeAdapter;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Queue;


public class CommunicationInterpreter {
    private Gson gson;
    private JsonParser parser;

    public CommunicationInterpreter() {
        super();
        SqlDateTypeAdapter sqlAdapter = new SqlDateTypeAdapter();
        this.gson = new GsonBuilder()
                .registerTypeAdapter(java.sql.Date.class, sqlAdapter)
                .setDateFormat("yyyy-MM-dd")
                .create();//new GsonBuilder().create();
        this.parser = new JsonParser();
    }

    /**
     * The method interpret the string and returns the received protocol message
     *
     * @param jsonString - the received string to be interpreted
     * @return the protocol message
     */
    public ProtocolMessage getProtocolMsg(String jsonString) {
        JsonObject jsonObj = (JsonObject) parser.parse(jsonString);
        return gson.fromJson(jsonObj.get("protocolMsg").toString(), ProtocolMessage.class);
    }

    public String setProtocolMsg(ProtocolMessage protocolMsg) {
        JsonObject jsonObj = new JsonObject();
        jsonObj.add("protocolMsg", gson.toJsonTree(protocolMsg, ProtocolMessage.class));
        return jsonObj.toString();
    }

    /**
     * The method return a string of json that represents the data
     *
     * @param protocolMsg - the protocol rule
     * @param obj         - the data to be converted
     * @return a string of json, if protocolMsg is not supported return null
     * @throws ClassCastException - if the protocolMsg and obj are not coordinated
     */
    public String encodeObjToJson(ProtocolMessage protocolMsg, Object obj) throws ClassCastException {
        JsonObject jsonObj = new JsonObject();
        jsonObj.add("protocolMsg", gson.toJsonTree(protocolMsg, ProtocolMessage.class));
        Type listType;

        try {
            switch (protocolMsg) {
                case USER_MODEL:
                    jsonObj.add("user", gson.toJsonTree((UserModel) obj, UserModel.class));
                    return jsonObj.toString();
                case SEARCH_VEHICLE:
                case RESERVATION_MODEL:
                    jsonObj.add("reservation", gson.toJsonTree((ReservationModel) obj, ReservationModel.class));
                    return jsonObj.toString();

                case VEHICLE_MODEL:
                    jsonObj.add("vehicle", gson.toJsonTree((VehicleModel) obj, VehicleModel.class));
                    return jsonObj.toString();

                case USER_MODEL_LIST:
                    listType = new TypeToken <Queue <UserModel>>() {
                    }.getType();
                    jsonObj.add("users", gson.toJsonTree(obj, listType));
                    return jsonObj.toString();

                case RESERVATION_MODEL_LIST:
                    listType = new TypeToken <Queue <ReservationModel>>() {
                    }.getType();
                    jsonObj.add("reservations", gson.toJsonTree(obj, listType));
                    return jsonObj.toString();

                case VEHICLE_MODEL_LIST:
                    listType = new TypeToken <Queue <VehicleModel>>() {
                    }.getType();
                    jsonObj.add("vehicles", gson.toJsonTree(obj, listType));
                    return jsonObj.toString();

                default:
                    jsonObj.add("message", gson.toJsonTree((String) obj, String.class));
                    return jsonObj.toString();
            }
        } catch (ClassCastException e) {
            return e.toString();
        }
    }

    /**
     * The method interpret the string and returns the MODEL object
     *
     * @param jsonString - the received string to be interpreted
     * @return The MODEL wrapped by Object class, if protocolMsg is not recognized return null
     */
    public Object decodeFromJsonToObj(ProtocolMessage protocolMsg, String jsonString) {
        JsonObject jsonObj = (JsonObject) parser.parse(jsonString);
        Type listType;

        switch (protocolMsg) {
            case USER_MODEL:
                UserModel user = gson.fromJson(jsonObj.get("user").toString(), UserModel.class);
                return user;
            case SEARCH_VEHICLE:
            case RESERVATION_MODEL:
                ReservationModel reservation = gson.fromJson(jsonObj.get("reservation").toString(), ReservationModel.class);
                return reservation;

            case VEHICLE_MODEL:
                VehicleModel vehicle = gson.fromJson(jsonObj.get("vehicle").toString(), VehicleModel.class);
                return vehicle;

            case USER_MODEL_LIST:
                listType = new TypeToken <Queue <UserModel>>() {
                }.getType();
                Queue <UserModel> users = gson.fromJson(jsonObj.get("users").toString(), listType);
                return users;

            case RESERVATION_MODEL_LIST:
                listType = new TypeToken <Queue <ReservationModel>>() {
                }.getType();
                Queue <ReservationModel> reservations = gson.fromJson(jsonObj.get("reservations").toString(), listType);
                return reservations;

            case VEHICLE_MODEL_LIST:
                listType = new TypeToken <Queue <VehicleModel>>() {
                }.getType();
                Queue <VehicleModel> vehicles = gson.fromJson(jsonObj.get("vehicles").toString(), listType);
                return vehicles;

            case USER_IS_BANNED:
            case TOO_MANY_AUTHENTICATION_RETRIES:
                listType = new TypeToken <Queue <Object>>() {
                }.getType();
                Queue <Object> banMessage = gson.fromJson(jsonObj.get("banMessage").toString(), listType);
                return banMessage;

            default:
                listType = new TypeToken <String>() {
                }.getType();
                String message = gson.fromJson(jsonObj.get("message").toString(), listType);
                return message;
        }
    }

    /**
     * The method get parameters and build a json string out of it
     *
     * @param protocolMsg
     * @param keys
     * @param values
     * @return json String
     */
    public String encodeParametersToJson(ProtocolMessage protocolMsg, Queue <String> keys, Queue <String> values) {
        JsonObject jsonObj = new JsonObject();
        jsonObj.add("protocolMsg", gson.toJsonTree(protocolMsg, ProtocolMessage.class));

        while (!keys.isEmpty()) {
            JsonObject jsonObjValue = (JsonObject) parser.parse(values.poll());
            jsonObj.add(keys.poll(), gson.toJsonTree(jsonObjValue));
        }
        return jsonObj.toString();
    }
}