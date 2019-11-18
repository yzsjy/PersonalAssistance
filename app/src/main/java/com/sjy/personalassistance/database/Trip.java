package com.sjy.personalassistance.database;

public class Trip {

    private String title, location, startTime, endTime;
    private int id;

    public Trip(int id, String title, String location, String startTime, String endTime){
        this.id = id;
        this.title = title;
        this.location = location;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public int getId(){
        return id;
    }

    public String getTitle(){
        return title;
    }

    public String getLocation(){
        return location;
    }

    public String getStartTime(){
        return startTime;
    }

    public String getEndTime(){
        return endTime;
    }

}
