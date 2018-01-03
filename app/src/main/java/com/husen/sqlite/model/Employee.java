package com.husen.sqlite.model;

/**
 * Created by gulamhusen on 21-11-2017.
 */

public class Employee {
    private String userID;


    private String employeeName;
    private String employeePhone;

    public String getEmployeeName() {
        return employeeName;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }


    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getEmployeePhone() {
        return employeePhone;
    }

    public void setEmployeePhone(String employeePhone) {
        this.employeePhone = employeePhone;
    }
}
