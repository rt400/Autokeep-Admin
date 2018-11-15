/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package autokeep.auto.AdminModels;

/**
 *
 * @author Yuval
 */
public class UserModel {

    private int userID;
    private String emailAddress;
    private String password;
    private String dateOfBirth;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private boolean IsAdministrator = false;

    public void setIsAdministrator(boolean IsAdministrator) {
        this.IsAdministrator = IsAdministrator;
    }

    public UserModel(int id ,String emailAddress, String password, String dateOfBirth, String firstName, String lastName,
            String phoneNumber, boolean IsAdministrator) {
        super();
        this.userID = id;
        this.password = password;
        this.dateOfBirth = dateOfBirth;
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
        this.phoneNumber = phoneNumber;
        this.IsAdministrator = IsAdministrator;
    }
    public UserModel(String emailAddress, String password, String dateOfBirth, String firstName, String lastName,
            String phoneNumber, boolean IsAdministrator) {
        super();
        this.password = password;
        this.dateOfBirth = dateOfBirth;
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
        this.phoneNumber = phoneNumber;
        this.IsAdministrator = IsAdministrator;
    }

    public UserModel() {
    }

    public int getId() {
        return userID;
    }

    public void setId(int id) {
        this.userID = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public boolean IsAdministrator() {
        return IsAdministrator;
    }
    
    public String getFullName(){
        return this.firstName + " " + this.lastName;
    }
}
