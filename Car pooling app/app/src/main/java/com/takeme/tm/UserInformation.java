package com.takeme.tm;

/**
 * Created by Keshav on 2/11/2017.
 */

public class UserInformation {
    public String Name;
    public String Phone_Number;
    public String Address;
    public String City;
    public String PIN_Code;
    public String Car_Number;
    public String Car_Type;
    public String Source;
    public String Destination;
    public int Rating,Ride_Points,Driving,Language,Behaviour,Rides;

    public UserInformation(String name, String ph, String address, String city, String pin, String carno, String type) {
        this.Name = name;
        this.Phone_Number = ph;
        this.Address = address;
        this.City= city;
        this.PIN_Code = pin;
        this.Car_Number = carno;
        this.Car_Type= type;
        this.Rating=5;
        this.Ride_Points=100;
        this.Driving=5;
        this.Language=5;
        this.Behaviour=5;
        this.Rides=1;

    }

}
