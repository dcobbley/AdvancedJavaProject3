package edu.pdx.cs410J.dcobbley;

import edu.pdx.cs410J.AbstractPhoneCall;

import java.io.IOException;

/**
 * Created by david on 7/6/15.
 * A single instance of a phonecall that has occured. Includes a caller and callee number
 */
public class phonecall extends AbstractPhoneCall {
    String callerNumber;
    String calleeNumber;
    String startTime;
    String endTime;

    /**
     * Constructor for the phonecall class. Holds all relavent data for a particular phonecall
     * @param callerNumber The phone number of the customer
     * @param calleeNumber The phone number that the customer is trying to reach
     * @param startTime The time at which the phonecall began
     * @param endTime The time at which the phonecall ended
     */
    phonecall(String callerNumber, String calleeNumber, String startTime, String endTime){
        //Check for bad data
        try{
            if(startTime.contains("\"")||endTime.contains("\""))
                throw new IllegalArgumentException("Date and time cannot contain quotes ");

            if(!callerNumber.matches("\\d{3}-\\d{3}-\\d{4}$")||!calleeNumber.matches("\\d{3}-\\d{3}-\\d{4}$"))
                throw new IllegalArgumentException("Valid phone numbers must contain exactly 10 numbers plus two dashes");

            String[] tempStart = startTime.split(" ");
            String[] tempEnd= endTime.split(" ");

            if(!tempStart[0].matches("(0?[1-9]|1[012])/(0?[1-9]|[12][0-9]|3[01])/((19|20)\\d\\d)")||!tempEnd[0].matches("(0?[1-9]|1[012])/(0?[1-9]|[12][0-9]|3[01])/((19|20)\\d\\d)")) {
                throw new IllegalArgumentException("Date format must follow mm/dd/yyyy");
            }

            if(!tempStart[1].matches("([01]?[0-9]|2[0-3]):[0-5][0-9]")||!tempEnd[1].matches("([01]?[0-9]|2[0-3]):[0-5][0-9]"))
                throw new IllegalArgumentException("Time format must follow mm:hh (24 hour time)");
        }
        catch(IllegalArgumentException ex){
            System.out.println(ex.getMessage());
            System.exit(1);
        }


        this.callerNumber = callerNumber;
        this.calleeNumber = calleeNumber;
        this.startTime = startTime;
        this.endTime = endTime;
//        System.out.println("Debug:");
//        System.out.println(endTime);
    }
    phonecall(){
        calleeNumber ="";
        callerNumber ="";
        startTime ="";
        endTime = "";
    }

    /**
     *
     * @return Returns callerNumber - Getter function
     */
    @Override
    public String getCaller() {
        return callerNumber;
    }

    /**
     *
     * @return Returns calleeNumber - Getter function
     */
    @Override
    public String getCallee() {
        return calleeNumber;
    }

    /**
     *
     * @return Returns startTime - Getter function
     */
    @Override
    public String getStartTimeString() {
        return startTime;
    }

    /**
     *
     * @return Returns endTime - Getter function
     */
    @Override
    public String getEndTimeString() {
        return endTime;
    }
}
