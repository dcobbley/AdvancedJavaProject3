package edu.pdx.cs410J.dcobbley;

import edu.pdx.cs410J.AbstractPhoneCall;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by david on 7/6/15.
 * A single instance of a phonecall that has occured. Includes a caller and callee number
 */
public class phonecall extends AbstractPhoneCall {
    String callerNumber;
    String calleeNumber;
    Date startTime;
    Date endTime;
    DateFormat dateFormat;

    /**
     * Constructor for the phonecall class. Holds all relavent data for a particular phonecall
     * @param callerNumber The phone number of the customer
     * @param calleeNumber The phone number that the customer is trying to reach
     * @param startTime The time at which the phonecall began
     * @param endTime The time at which the phonecall ended
     */
    phonecall(String callerNumber, String calleeNumber, String startTime, String endTime){
        dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm");
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
        setDate(startTime,endTime);

    }
    phonecall(){
        dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm");
        try {
            calleeNumber = "";
            callerNumber = "";
            startTime =null;
            endTime = null;
        }
        catch(Exception ex){
            System.out.println(ex);
            System.exit(1);
        }
    }

    public void setDate(String start, String end){
        String[] startArray= start.split("[ /:]");
        String[] endArray=end.split("[ /:]");

        this.startTime = new Date(Integer.parseInt(startArray[2])-1900, (Integer.parseInt(startArray[0]))-1, Integer.parseInt(startArray[1]),Integer.parseInt(startArray[3]),Integer.parseInt(startArray[4]),0);
        this.endTime = new Date(Integer.parseInt(endArray[2])-1900, (Integer.parseInt(endArray[0]))-1, Integer.parseInt(endArray[1]),Integer.parseInt(endArray[3]),Integer.parseInt(endArray[4]),0);
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
        if(startTime != null)
            return (dateFormat.format(startTime));
        else
            return "";
    }

    /**
     *
     * @return Returns endTime - Getter function
     */
    @Override
    public String getEndTimeString() {
        if(endTime != null)
            return (dateFormat.format(endTime));
        else
            return "";
    }

    public String duration(){
        long duration =startTime.getTime()-endTime.getTime();
        long diffMinutes = duration / (60 * 1000) % 60;
        long diffHours = duration / (60 * 60 * 1000);
        if(diffHours ==0)
            return -1*diffMinutes + " minutes";
        else
            return "  "+-1*diffHours+":"+ -1*diffMinutes +"min";
    }
}
