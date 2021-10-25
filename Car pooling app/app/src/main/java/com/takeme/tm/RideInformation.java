package com.takeme.tm;

/**
 * Created by Keshav on 2/24/2017.
 */

public class RideInformation {
    String From;
    String To;
    String StartAt;
    public RideInformation(String source, String dest, String time) {
        this.From=source;
        this.To=dest;
        this.StartAt=time;

    }
}
