/** Description of Project2
        *
        * @author David Cobbley
        * @version 1.0 July 7, 2015.
        *Phonebill application for Advanced Java course at Portland State University
        * The main class for the CS410J Phone Bill Project
        * Parses the command line and calls appropriate functionality.
        */
package edu.pdx.cs410J.dcobbley;

import edu.pdx.cs410J.AbstractPhoneBill;
import edu.pdx.cs410J.AbstractPhoneCall;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

public class Project2 {
    //Constants
    private static final int MAXARGUMENTS = 3;//Update this value if we get more than -readme -print -textFile
    //Global variables
    static ArrayList<String> commands; //used to keep track of all the commands that will be run at the end of the program
    static phonebill MyPhoneBill;
  /**
   * Main will be called when the program is run, it parses the commands given by the user and calls the appropriate functionality.
   * @param args contains all the command line arguments passed into the program
   * @throws IllegalArgumentException Exception if there is either not enough arguments or the wrong arguments
   */
  public static void main(String[] args) {
    Class c = AbstractPhoneBill.class;  // Refer to one of Dave's classes so that we can be sure it is on the classpath
      commands = null;
      MyPhoneBill = null;
      commands = new ArrayList<String>();
      parseCommandsAtBeginning(args);

    System.exit(0);
  }


    /**
     *
     * @param args
     * @throws
     */
    private static void parseCommandsAtBeginning(String[] args){
        int element = 0;
        try {
            if (args.length == 0) {
                throw new IllegalArgumentException("Cannot have zero arguments");
            }


            //Check if one of the first 3 args is a command
            //If one or all three args are commands, put them into a command array which will get executed after work is done
            //Start parsing for a customer
            boolean flag = false;
            for (; element < MAXARGUMENTS && element < args.length; element++) {
                //check if -print, -README, -textFile filename

                switch (args[element]) {
                    case "-README":
                        //add readme to the command list
                        addArgumentCommand("-README");
                        break;
                    case "-print":
                        //add print to the list
                        addArgumentCommand("-print");
                        break;
                    case "-textFile":
                        //check for ++element
                        if (args.length > element + 1) {
                            //save -textfile Filename
                            addArgumentCommand("textFile");
                            addArgumentCommand(args[++element]);
                        } else {
                            //throw error
                            throw new IllegalArgumentException("-textFile argument must be followed by <filename>");
                        }
                        break;
                    default:
                        if(args[element].startsWith("-"))
                        {
                            throw new IllegalArgumentException("Non-Valid Argument");
                        }
                        flag = true;
                        break;
                }
                if(flag)
                    break;
            }
        }
        catch(IllegalArgumentException ex){
            System.out.println(ex.getMessage());
            Readme();
            System.exit(1);
        }
        //if first three args are all commands, then begin to parse the next bit if it exists.
        parseCustomerIfExists(args, element);


    }

    /**
     *
     * @param args
     * @param element
     * @throws
     */
    private static void parseCustomerIfExists(String[] args, int element){

        //collect all customer data and phone call data.
        //Try to use only locals as much as possible

        try {
            //check that element to element+6 exists
            if (args.length > element + 6) {
                //parse out customer information
                MyPhoneBill = new phonebill(args[element++], new phonecall(args[element++], args[element++], args[element++] + " " + args[element++], args[element++] + " " + args[element++]));
                //customer                       caller            callee        starttime         +         data          endtime           +         data
                //Parse commands at the end of the arg
                parseCommandsAtEnd(args, element);
            } else {
                if (args.length > element) {
                    //didn't provide enough args
                    //throw error
                    throw new IllegalArgumentException("Not enough arguments provided");
                } else {
                    //go straight to executing the commands

                    executeCommands();
                }
            }
        }
        catch(IllegalArgumentException ex){
            System.out.println(ex.getMessage());
            System.exit(1);
        }

        //Create a new instance of Phonebill with a new phonecall. When we have persistent data, this line will change.
        //myPhoneBill = new phonebill(customer, new phonecall(callerNumber, calleeNumber, startTime, endTime));


        //Parse for commands at the end
    }

    /**
     *
     * @param args
     * @param element
     * @throws
     */
    private static void parseCommandsAtEnd(String[] args, int element){
        //while elements<args.length keep parsing.
        //if case is a valid command, add it to the list
        try {
            int maxElement = element + 4;
            while (args.length > element && args.length < maxElement) {
                //there are args to parse
                switch (args[element]) {
                    case "-README":
                        //add readme to the command list
                        addArgumentCommand("-README");
                        break;
                    case "-print":
                        //add print to the list
                        addArgumentCommand("-print");
                        break;
                    case "-textFile":
                        //check for ++element
                        if (args.length > element + 1) {
                            //save -textfile Filename
                            addArgumentCommand("textFile");
                            addArgumentCommand(args[++element]);
                        } else {
                            //throw error
                            throw new Exception("-textFile argument must be followed by <filename>");
                        }
                        break;
                    default:
                        //throw error, cannot exist
                        throw new Exception("Not a valid command");
                }

                element++;

            }
            //no args left to parse, begin execution
            executeCommands();
        }
        catch(Exception ex){
            System.out.println(ex.getMessage());
            System.exit(1);
        }
    }

    /**
     *
     * @param arg
     * @throws
     */
    private static void addArgumentCommand(String arg){
        //Modify the list array of commands.
        //This list of commands ie -README, -print, -textFile will get executed after any other work is done.
        if(!commands.contains(arg)){
            //Check if the list already contains it
            commands.add(arg);
        }

        //Check if arg exists in the list before adding
    }

    private static void executeCommands(){
        boolean printFlag = false;
        boolean textFileFlag = false;
        boolean ReadmeFlag = false;
        String fileName=null;
        //Begin executing commands
        //check if the commands exist and execute in this order.
        //textFile-

        //print
        //simply print myPhoneBill, should contain an up to date version of whatever it needs

        //readme
        //Simply call the readme
        try {
            for (String comm : commands) {
                switch(comm){
                    case "textFile":
                        textFileFlag=true;
                    break;
                    case "-README":
                        ReadmeFlag = true;
                    break;
                    case "-print":
                        printFlag = true;
                    break;
                    default:
                        fileName = comm;
                        break;

                }
            }
            if(textFileFlag){
                textFileFunction(fileName);
            }
            if(printFlag){
                if (MyPhoneBill != null) {
                    System.out.println("Customer: " + MyPhoneBill.getCustomer() + " " + MyPhoneBill.getPhoneCalls());
                    printFlag = true;
                } else {
                    //MyphoneBill is null, throw exception
                    throw new Exception("Must provide a phone bill");
                }
            }
            if(ReadmeFlag){
                Readme();
            }
        }
        catch(Exception ex)
        {
            if(ex.getMessage()!=null) {
                System.out.println(ex.getMessage());
                Readme();
            }

            System.exit(1);
        }
    }

    private static void textFileFunction(String path){
        try {
            if (path == null)
                throw new IOException("Path name missing");

            TextParser par = new TextParser(path);
            if(par.ifFileExists()){
                //file exists
                // read in and compare with myPhoneBill.
                //if customer name not equal throw error
                //if myPhoneBill == null, set TemporaryPhoneBill to myPhoneBill
                AbstractPhoneBill textFilePhoneBill = par.parse();
                if(textFilePhoneBill == null) {
                    throw new IOException("File IO Error");
                }

                if(MyPhoneBill != null && MyPhoneBill.getCustomer().equals(textFilePhoneBill.getCustomer())){
                    //Transfer in data from text file phone bill to MyPhoneBill
                    Collection<phonecall> tempPhoneCall = textFilePhoneBill.getPhoneCalls();
                    for(phonecall ph:tempPhoneCall){
                        MyPhoneBill.addPhoneCall(ph);
                    }
                }
                else if(MyPhoneBill != null){
                    //throw new exception, names don't match
                    throw new IOException("Phonebill names don't match");
                }
                else{
                    //create a new phonebill and add in data?
                    MyPhoneBill = new phonebill(textFilePhoneBill.getCustomer());
                    Collection<phonecall> tempPhoneCall = textFilePhoneBill.getPhoneCalls();
                    for(phonecall ph:tempPhoneCall){
                            MyPhoneBill.addPhoneCall(ph);
                    }
                }
                //Write data back to the file
                TextDumper dump = new TextDumper(path);
                dump.dump(MyPhoneBill);
            }
            else{
                //File does not exist
                //create a new empty phone bill
                //add myPhoneBill to it, if myPhoneBill is null, create emptyphonebill
                TextDumper dump = new TextDumper(path);
                dump.dump(MyPhoneBill);

            }

        }
        catch(IOException ex){

            System.exit(1);
        }

    }
    /**
     * Readme function contains the readme of all useful information the user may need to know.
     */
    private static void Readme() {
        System.out.println("README has been called");
        System.out.println("This program is a phonebill application which takes a very specific amount of arguments");
        System.out.println("You must provide a customer name, caller number, callee number, start time, and end time (mm/dd/yyyy mm:hh)");
        System.out.println();
        System.out.println("usage: java edu.pdx.cs410J.<login-id>.Project2 [options] <args>\n" +
                "args are (in this order):\n" +
                "customer               Person whose phone bill we’re modeling\n" +
                "callerNumber           Phone number of caller\n" +
                "calleeNumber           Phone number of person who was called\n" +
                "startTime              Date and time call began (24-hour time)\n" +
                "endTime                Date and time call ended (24-hour time)\n" +
                "options are (options may appear in any order):\n" +
                "-textFile file         Where to read/write the phone bill\n" +
                "-print                 Prints a description of the new phone call\n" +
                "-README                Prints a README for this project and exits\n" +
                "Dates and times should be in the format: mm/dd/yyyy hh:mm");
    }

}