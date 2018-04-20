package com.pa1jeswani.restaurantap.model;

/**
 * Created by PAWAN on 2/17/2018.
 */

public class User {
    private String UName;
    private String UID;

    public String getPhno() {
        return phno;
    }

    public void setPhno(String phno) {
        this.phno = phno;
    }

    private String phno;
    private String Email;
    private String Pswd;
    private String Dob;
    private String FoodPref;
    private String photoURL;
    private String initULocLong;
    private String initULocLat;
    private String initCLocLong;
    private String initCLocLat;

    public User() {
    }

    public User(String UID, String UName, String Email,
                String phno,String Pswd, String Dob,
                String foodPref, String initULocLong) {
        this.UName = UName;
        this.UID = UID;
        this.Email = Email;
        this.phno= phno;
        this.Pswd = Pswd;
        this.Dob = Dob;
        this.FoodPref = foodPref;
        this.initULocLong = initULocLong;
    }

    public String getInitULocLong() {
        return initULocLong;
    }

    public void setInitULocLong(String initULocLong) {
        this.initULocLong = initULocLong;
    }

    public String getInitULocLat() {
        return initULocLat;
    }

    public void setInitULocLat(String initULocLat) {
        this.initULocLat = initULocLat;
    }

    public String getInitCLocLong() {
        return initCLocLong;
    }

    public void setInitCLocLong(String initCLocLong) {
        this.initCLocLong = initCLocLong;
    }

    public String getInitCLocLat() {
        return initCLocLat;
    }

    public void setInitCLocLat(String initCLocLat) {
        this.initCLocLat = initCLocLat;
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public String getUName() {
        return UName;
    }

    public void setUName(String UName) {
        this.UName = UName;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPswd() {
        return Pswd;
    }

    public void setPswd(String pswd) {
        Pswd = pswd;
    }

    public String getDob() {
        return Dob;
    }

    public void setDob(String dob) {
        Dob = dob;
    }

    public String getFoodPref() {
        return FoodPref;
    }

    public void setFoodPref(String foodPref) {
        FoodPref = foodPref;
    }

    public String getPhotoURL() {
        return photoURL;
    }

    public void setPhotoURL(String photoURL) {
        this.photoURL = photoURL;
    }
}
