package edu.pdx.cs410J.dcobbley;

import org.junit.Ignore;
import org.junit.Test;

import static junit.framework.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import edu.pdx.cs410J.InvokeMainTestCase;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static junit.framework.Assert.assertEquals;

/**
 * Tests the functionality in the {@link Project3} main class.
 */
public class Project3Test extends InvokeMainTestCase {

    /**
     * Invokes the main method of {@link Project3} with the given arguments.
     */
    String ReadmeString;
    private MainMethodResult invokeMain(String... args) {
        setReadmeString();
        return invokeMain( Project3.class, args );

    }
    private void setReadmeString(){
        ReadmeString="README has been called\n" +
                "This program is a phonebill application which takes a very specific amount of arguments\n" +
                "You must provide a customer name, caller number, callee number, start time, and end time (mm/dd/yyyy mm:hh)\n" +
                "\n" +
                "usage: java edu.pdx.cs410J.<login-id>.Project3 [options] <args>\n" +
                "args are (in this order):\n" +
                "customer               Person whose phone bill we’re modeling\n" +
                "callerNumber           Phone number of caller\n" +
                "calleeNumber           Phone number of person who was called\n" +
                "startTime              Date and time call began\n" +
                "endTime                Date and time call ended\n" +
                "options are (options may appear in any order):\n" +
                "-pretty file           Pretty print the phone bill to a text file\n" +
                "or standard out (file -).\n" +
                "-textFile file         Where to read/write the phone bill\n" +
                "-print                 Prints a description of the new phone call\n" +
                "-README                Prints a README for this project and exits\n" +
                "Dates and times should be in the format: mm/dd/yyyy hh:mm am";
    }

    private void deleteFiles() {
        try{
            String path = System.getProperty("user.dir") + "/DavesBill.txt";
            File file = new File(path);
            file.delete();
            path = System.getProperty("user.dir") + "/DavesPretty.txt";
            file = new File(path);
            file.delete();
        }
        catch(Exception ex){
            System.out.println("Sad Day");
        }
    }

  @Test
    public void TestPrintingOutAPhoneCall(){
        MainMethodResult result = invokeMain("-print","Test8","123-456-7890","234-567-8901","03/03/2015","11:00","am","05/04/2015","12:05", "pm" );
      //System.out.println(result.getOut());
        assertEquals(result.getOut().trim(),"Customer: Test8 [Phone call from 123-456-7890 to 234-567-8901 from 03/03/2015 11:00 AM to 05/04/2015 12:05 PM]");
        assertEquals(new Integer(0), result.getExitCode());
        //System.out.println(result.getOut());
          }

    @Test
    public void TestMultiWordUserName(){
        MainMethodResult result = invokeMain("-print","Test 8","123-456-7890","234-567-8901","03/03/2015","12:00","am","09/04/2015","16:00","am");
        assertEquals(result.getOut().trim(),"Customer: Test 8 [Phone call from 123-456-7890 to 234-567-8901 from 03/03/2015 12:00 AM to 09/04/2015 04:00 PM]");
        assertEquals(new Integer(0), result.getExitCode());
        //System.out.println(result.getOut());
            }

    @Test
    public void TestMissingEndTime(){
        MainMethodResult result = invokeMain("Test9","123-456-7890","234-546-3452","03/03/2015","12:00");
        assertEquals(result.getOut().trim(),"Not enough arguments provided");
        assertEquals(new Integer(1), result.getExitCode());
        //System.out.println(result.getOut());
            }

  @Test
  public void TestUnknownCommandArgument(){
      MainMethodResult result = invokeMain("-fred", "Test6", "123-456-7890", "234-567-8901", "03/03/2015", "12:00","am", "04/04/2015", "16:00","am");
      assertEquals(result.getOut().trim(), "Non-Valid Argument\n" +
              ReadmeString);

  }
    @Test
    public void TestUnknownArgumetn(){
        MainMethodResult result = invokeMain("Test7", "123-456-7890", "234-567-8901", "03/03/2015", "12:00","am", "04/04/2015", "16:00","am", "fred");
        assertEquals(result.getOut().trim(),"Not a valid command");
            }

    @Test
  public void TestMalformedEndTime(){
      MainMethodResult result = invokeMain("Test5", "123-456-7890", "234-567-8901", "03/03/2015", "12:00","am", "01/04/20/1", "16:00","am");
      assertEquals(result.getOut().trim(),"Date format must follow mm/dd/yyyy");
          }

  @Test
  public void TestMalformedStartTime(){
      MainMethodResult result =invokeMain("Test4", "123-456-7890", "234-567-8901", "03/03/2015", "12:XX","am", "03/03/2015", "12:00","pm");
      assertEquals(result.getOut().trim(), "Time format must follow mm:hh (12 hour time)");
        }

  @Test
  public void TestNonIntegerPhoneNumber(){
      MainMethodResult result = invokeMain("Test3", "ABC-123-4567", "123-456-7890", "03/03/2015", "12:00","am", "03/03/2015", "16:00","am");
      assertEquals(new Integer(1), result.getExitCode());
      assertTrue(result.getOut().trim().equals("Valid phone numbers must contain exactly 10 numbers plus two dashes"));
        }

    @Test
    public void TestFromGrader(){
        MainMethodResult result = invokeMain("-print", "Test8", "123-456-7890", "234-567-8901", "03/03/2015", "12:00","pm", "05/04/2015", "12:00","am");
        //System.out.println(result.getOut());
        assertEquals(new Integer(0), result.getExitCode());
        assertTrue(result.getOut().trim().equals("Customer: Test8 [Phone call from 123-456-7890 to 234-567-8901 from 03/03/2015 12:00 PM to 05/04/2015 12:00 AM]"));

    }

  @Test
  public void TestEmptyTextFile(){
      try {
          String path = System.getProperty("user.dir") + "/DavesBill.txt";
          File file = new File(path);
          PrintWriter writer = new PrintWriter(file);
          writer.write("");
          writer.flush();
          writer.close();
      }
      catch(Exception ex){

      }
      MainMethodResult result = invokeMain("-textFile", "DavesBill");
      assertEquals(new Integer(1), result.getExitCode());
      assertTrue(result.getOut().trim().equals("Error Reading From File Empty File"));

      try{
          String path = System.getProperty("user.dir") + "/DavesBill.txt";
          File file = new File(path);
          file.delete();
      }
      catch(Exception ex){
          System.out.println("Sad Day");
      }
  }

    @Test
  public void TestTextDumper(){
      deleteFiles();
      MainMethodResult result = invokeMain("david", "503-709-4866", "503-880-6960", "10/15/2015", "09:38" ,"am", "10/15/2015", "09:42","am", "-textFile", "DavesBill");
        //System.out.println(result.getOut());
      assertEquals(new Integer(0), result.getExitCode());


  }

    @Test
  public void TestTextFileNodataNoArgsNofile(){
      MainMethodResult result = invokeMain("-textFile");
      assertEquals(new Integer(1), result.getExitCode());
      assertTrue(result.getOut().trim().equals("-textFile argument must be followed by <filename>\n" + ReadmeString));
      //System.out.println(result.getOut());

  }

  @Test
  public void TestPrintNoDataNoOtherArgs(){
      MainMethodResult result = invokeMain("-print");
      assertEquals(new Integer(1), result.getExitCode());
      assertEquals(result.getOut().trim(), "Must provide a phone bill\n" +ReadmeString);


  }

  @Test
  public void TestReadmeNoDataNoArgs(){
      MainMethodResult result = invokeMain("-README");
      assertEquals(new Integer(0), result.getExitCode());
      assertEquals(result.getOut().trim(),ReadmeString);

  }

    @Test
    public void testRegularCommandLineArguments(){
        MainMethodResult result = invokeMain("david", "503-709-4866", "503-880-6960", "10/15/2015", "09:38","am", "10/15/2015", "09:42","am", "-print");
        assertEquals(new Integer(0), result.getExitCode());
        assertEquals(result.getOut().trim(), "Customer: david [Phone call from 503-709-4866 to 503-880-6960 from 10/15/2015 09:38 AM to 10/15/2015 09:42 AM]");

    }


    @Test
    public void testAllCommandLineArguments(){
        MainMethodResult result = invokeMain("david", "503-709-4866", "503-880-6960", "10/15/2015", "09:38","am", "10/15/2015", "09:42","am", "-print", "-README");
        assertEquals(new Integer(0), result.getExitCode());
        assertEquals(result.getOut().trim(), "Customer: david [Phone call from 503-709-4866 to 503-880-6960 from 10/15/2015 09:38 AM to 10/15/2015 09:42 AM]\n" + ReadmeString);

    }

    @Test
    public void NoCommandLineArgs(){
        MainMethodResult result = invokeMain();
        assertEquals(new Integer(1), result.getExitCode());
        assertTrue(result.getOut().trim().equals("Cannot have zero arguments\n" + ReadmeString));
        //System.out.println(result.getOut());

    }

    @Test
    public void TestNonExistantFile(){//PRODUCES EMPTY FILES??
        deleteFiles();
        MainMethodResult result = invokeMain("-textFile", "DavesBill");
        assertEquals(new Integer(0), result.getExitCode());

    }

    @Test
    public void TestDuplicates(){
        deleteFiles();
        MainMethodResult result = invokeMain("david", "503-709-4866", "503-880-6960", "10/15/2015", "09:38","am", "10/15/2015", "09:42","am", "-textFile", "DavesBill");
        assertEquals(new Integer(0), result.getExitCode());
        MainMethodResult anotherResult = invokeMain("david", "503-709-4866", "503-880-6960", "10/15/2015", "09:38" ,"am", "10/15/2015", "09:42","am", "-textFile", "DavesBill");
        assertEquals(new Integer(0), anotherResult.getExitCode());

        BufferedReader reader = null;
        try{
            String path = System.getProperty("user.dir") + "/DavesBill.txt";
            File file = new File(path);
            reader=new BufferedReader(new FileReader(file));
            String line;
            String allLines="";
            while ((line = reader.readLine()) != null) {
                allLines+=line +"\n";
            }
            if(allLines == "")
                throw new IOException("Empty File");

            //System.out.println(allLines);
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = new Date();
            assertEquals(allLines.trim(),"Created on: "+dateFormat.format(date)+"\n" +
                    "Customer: david\n" +
                    "Phone call from 503-709-4866 to 503-880-6960 from 10/15/2015 09:38 AM to 10/15/2015 09:42 AM");
        }
        catch(IOException ex){
            System.out.println("Error Reading From File " + ex.getMessage());
        }finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    @Test
    public void TestMultiplePhonecalls(){
        deleteFiles();
        MainMethodResult result = invokeMain("david", "503-709-4866", "503-880-6960", "10/15/2015", "09:38","am", "10/15/2015", "09:42","am", "-textFile", "DavesBill");
        assertEquals(new Integer(0), result.getExitCode());
        MainMethodResult anotherResult = invokeMain("david", "503-709-4866", "503-555-7777", "10/17/2015", "10:18","am", "10/17/2015", "10:40","am", "-textFile", "DavesBill", "-print");
        assertEquals(new Integer(0), anotherResult.getExitCode());
        assertEquals(anotherResult.getOut().trim(), "Customer: david [Phone call from 503-709-4866 to 503-880-6960 from 10/15/2015 09:38 AM to 10/15/2015 09:42 AM, Phone call from 503-709-4866 to 503-555-7777 from 10/17/2015 10:18 AM to 10/17/2015 10:40 AM]");

        BufferedReader reader = null;
        try{
            String path = System.getProperty("user.dir") + "/DavesBill.txt";
            File file = new File(path);
            reader=new BufferedReader(new FileReader(file));
            String line;
            String allLines="";
            while ((line = reader.readLine()) != null) {
                allLines+=line +"\n";
            }
            if(allLines == "")
                throw new IOException("Empty File");

            //System.out.println(allLines);
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = new Date();
            assertEquals(allLines.trim(),"Created on: "+ dateFormat.format(date)+"\n" +
                    "Customer: david\n" +
                    "Phone call from 503-709-4866 to 503-880-6960 from 10/15/2015 09:38 AM to 10/15/2015 09:42 AM\n" +
                    "Phone call from 503-709-4866 to 503-555-7777 from 10/17/2015 10:18 AM to 10/17/2015 10:40 AM");
        }

        catch(IOException ex){
            System.out.println("Error Reading From File " + ex.getMessage());
        }finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    @Test
    public void TestMinutePrettyPrint(){
        MainMethodResult result = invokeMain("david", "503-709-4866", "503-880-6960", "10/15/2015", "09:38","am", "10/15/2015", "09:42","am", "-pretty", "DavesPretty");
        assertEquals(new Integer(0), result.getExitCode());

        BufferedReader reader = null;
        try{
            String path = System.getProperty("user.dir") + "/DavesPretty.txt";
            File file = new File(path);
            reader=new BufferedReader(new FileReader(file));
            String line;
            String allLines="";
            while ((line = reader.readLine()) != null) {
                allLines+=line +"\n";
            }
            if(allLines == "")
                throw new IOException("Empty File");

            //System.out.println(allLines);
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = new Date();

            assertEquals(allLines.trim(),
                    "_____  _                        ____  _ _ _   ____   ___   ___   ___  \n" +
                    " |  __ \\| |                      |  _ \\(_) | | |___ \\ / _ \\ / _ \\ / _ \\ \n" +
                    " | |__) | |__   ___  _ __   ___  | |_) |_| | |   __) | | | | | | | | | |\n" +
                    " |  ___/| '_ \\ / _ \\| '_ \\ / _ \\ |  _ <| | | |  |__ <| | | | | | | | | |\n" +
                    " | |    | | | | (_) | | | |  __/ | |_) | | | |  ___) | |_| | |_| | |_| |\n" +
                    " |_|    |_| |_|\\___/|_| |_|\\___| |____/|_|_|_| |____/ \\___/ \\___/ \\___/ \n" +
                    "                                                                        \n" +
                    "                                                                        \n" +
                    "   ______           __                                \n" +
                    "  / ____/_  _______/ /_____  ____ ___  ___  _____   _ \n" +
                    " / /   / / / / ___/ __/ __ \\/ __ `__ \\/ _ \\/ ___/  (_)\n" +
                    "/ /___/ /_/ (__  ) /_/ /_/ / / / / / /  __/ /     _   DAVID\n" +
                    "\\____/\\__,_/____/\\__/\\____/_/ /_/ /_/\\___/_/     (_)  \n" +
                    "                                                      \n" +
                    "#     caller      callee           Start Time        End Time        Duration \n" +
                    "1 503-709-4866  503-880-6960   10/15/2015 09:38 AM  10/15/2015 09:42 AM   4 minutes\n" +
                    "\n" +
                    "Phone Bill 3000 Pretty Print Bill Created on: "+ dateFormat.format(date));
        }
        catch(IOException ex){
            System.out.println("Error Reading From File " + ex.getMessage());
        }finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    @Test
    public void TestNullPrettyPrint(){
        MainMethodResult result = invokeMain("-pretty", "DavesPretty");
        assertEquals(new Integer(1), result.getExitCode());
        assertEquals(result.getOut().trim(), "Must supply valid phone bill for pretty print");

    }

    @Test
    public void TestPrettyPrintWithNonExistantTextFile(){
        deleteFiles();
        MainMethodResult result = invokeMain("-textFile", "DavesBill", "-pretty", "DavesPretty");
        assertEquals(new Integer(1), result.getExitCode());
        assertEquals(result.getOut().trim(), "Must supply valid phone bill for pretty print");

    }

    @Test
    public void TestAllArguments() {
        deleteFiles();
        //Delete both davesBill and davesPretty
        MainMethodResult otherResult = invokeMain("david", "503-709-4866", "503-880-6960", "10/15/2015", "09:38","am", "10/15/2015", "09:42","am", "-textFile", "DavesBill");
        MainMethodResult result = invokeMain("-textFile", "DavesBill", "-pretty", "DavesPretty", "-print", "-README");

        assertEquals(result.getOut().trim(),"Customer: david [Phone call from 503-709-4866 to 503-880-6960 from 10/15/2015 09:38 AM to 10/15/2015 09:42 AM]\n" + ReadmeString);


        //Now look at external files
        BufferedReader reader = null;
        try{
            String path = System.getProperty("user.dir") + "/DavesPretty.txt";
            File file = new File(path);
            reader=new BufferedReader(new FileReader(file));
            String line;
            String allLines="";
            while ((line = reader.readLine()) != null) {
                allLines+=line +"\n";
            }
            if(allLines == "")
                throw new IOException("Empty File");

            //System.out.println(allLines);
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = new Date();

            assertEquals(allLines.trim(),
                    "_____  _                        ____  _ _ _   ____   ___   ___   ___  \n" +
                            " |  __ \\| |                      |  _ \\(_) | | |___ \\ / _ \\ / _ \\ / _ \\ \n" +
                            " | |__) | |__   ___  _ __   ___  | |_) |_| | |   __) | | | | | | | | | |\n" +
                            " |  ___/| '_ \\ / _ \\| '_ \\ / _ \\ |  _ <| | | |  |__ <| | | | | | | | | |\n" +
                            " | |    | | | | (_) | | | |  __/ | |_) | | | |  ___) | |_| | |_| | |_| |\n" +
                            " |_|    |_| |_|\\___/|_| |_|\\___| |____/|_|_|_| |____/ \\___/ \\___/ \\___/ \n" +
                            "                                                                        \n" +
                            "                                                                        \n" +
                            "   ______           __                                \n" +
                            "  / ____/_  _______/ /_____  ____ ___  ___  _____   _ \n" +
                            " / /   / / / / ___/ __/ __ \\/ __ `__ \\/ _ \\/ ___/  (_)\n" +
                            "/ /___/ /_/ (__  ) /_/ /_/ / / / / / /  __/ /     _   DAVID\n" +
                            "\\____/\\__,_/____/\\__/\\____/_/ /_/ /_/\\___/_/     (_)  \n" +
                            "                                                      \n" +
                            "#     caller      callee           Start Time        End Time        Duration \n" +
                            "1 503-709-4866  503-880-6960   10/15/2015 09:38 AM  10/15/2015 09:42 AM   4 minutes\n" +
                            "\n" +
                            "Phone Bill 3000 Pretty Print Bill Created on: "+ dateFormat.format(date));
        }
        catch(IOException ex){
            System.out.println("Error Reading From File " + ex.getMessage());
        }finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        reader = null;
        try{
            String path = System.getProperty("user.dir") + "/DavesBill.txt";
            File file = new File(path);
            reader=new BufferedReader(new FileReader(file));
            String line;
            String allLines="";
            while ((line = reader.readLine()) != null) {
                allLines+=line +"\n";
            }
            if(allLines == "")
                throw new IOException("Empty File");

            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = new Date();

            assertEquals(allLines.trim(),"Created on: "+dateFormat.format(date) +"\n" +
                    "Customer: david\n" +
                    "Phone call from 503-709-4866 to 503-880-6960 from 10/15/2015 09:38 AM to 10/15/2015 09:42 AM");
        }
        catch(IOException ex){
            System.out.println("Error Reading From File " + ex.getMessage());
        }finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }



    @Test
    public void TestTextFileReadingPrettyPrint(){
    //add a text file that contains a standard phone bill, then have pretty read from it
        deleteFiles();
        MainMethodResult otherResult = invokeMain("david", "503-709-4866", "503-880-6960", "10/15/2015", "09:38","am", "10/15/2015", "09:42","am", "-textFile", "DavesBill");
        MainMethodResult result = invokeMain("-pretty", "DavesBill");
        assertEquals(result.getOut().trim(),"Must supply valid phone bill for pretty print");

        try{
            String path = System.getProperty("user.dir") + "/DavesPretty.txt";
            File file = new File(path);
            assertFalse(file.exists());
        }
        catch(Exception ex){
            System.out.println("Sad Day");
        }

    }

    @Test
    public void TestHourPrettyPrint(){
        MainMethodResult result = invokeMain("david", "503-709-4866", "503-880-6960", "10/15/2015", "08:38","am", "10/15/2015", "09:42","am", "-pretty", "DavesPretty");
        BufferedReader reader = null;
        try{
            String path = System.getProperty("user.dir") + "/DavesPretty.txt";
            File file = new File(path);
            reader=new BufferedReader(new FileReader(file));
            String line;
            String allLines="";
            while ((line = reader.readLine()) != null) {
                allLines+=line +"\n";
            }
            if(allLines == "")
                throw new IOException("Empty File");

            //System.out.println(allLines);
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = new Date();

            assertEquals(allLines.trim(),
                    "_____  _                        ____  _ _ _   ____   ___   ___   ___  \n" +
                            " |  __ \\| |                      |  _ \\(_) | | |___ \\ / _ \\ / _ \\ / _ \\ \n" +
                            " | |__) | |__   ___  _ __   ___  | |_) |_| | |   __) | | | | | | | | | |\n" +
                            " |  ___/| '_ \\ / _ \\| '_ \\ / _ \\ |  _ <| | | |  |__ <| | | | | | | | | |\n" +
                            " | |    | | | | (_) | | | |  __/ | |_) | | | |  ___) | |_| | |_| | |_| |\n" +
                            " |_|    |_| |_|\\___/|_| |_|\\___| |____/|_|_|_| |____/ \\___/ \\___/ \\___/ \n" +
                            "                                                                        \n" +
                            "                                                                        \n" +
                            "   ______           __                                \n" +
                            "  / ____/_  _______/ /_____  ____ ___  ___  _____   _ \n" +
                            " / /   / / / / ___/ __/ __ \\/ __ `__ \\/ _ \\/ ___/  (_)\n" +
                            "/ /___/ /_/ (__  ) /_/ /_/ / / / / / /  __/ /     _   DAVID\n" +
                            "\\____/\\__,_/____/\\__/\\____/_/ /_/ /_/\\___/_/     (_)  \n" +
                            "                                                      \n" +
                            "#     caller      callee           Start Time        End Time        Duration \n" +
                            "1 503-709-4866  503-880-6960   10/15/2015 08:38 AM  10/15/2015 09:42 AM     1:4min\n" +
                            "\n" +
                            "Phone Bill 3000 Pretty Print Bill Created on: "+ dateFormat.format(date));
        }
        catch(IOException ex){
            System.out.println("Error Reading From File " + ex.getMessage());
        }finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    @Test
    public void TestPrettyPrintWithExistantTextFile(){
        deleteFiles();
        MainMethodResult otherResult = invokeMain("david", "503-709-4866", "503-880-6960", "10/15/2015", "09:38","am", "10/15/2015", "09:42","am", "-textFile", "DavesBill");
        MainMethodResult result = invokeMain("-textFile", "DavesBill", "-pretty", "DavesPretty");
        BufferedReader reader = null;
        try{
            String path = System.getProperty("user.dir") + "/DavesPretty.txt";
            File file = new File(path);
            reader=new BufferedReader(new FileReader(file));
            String line;
            String allLines="";
            while ((line = reader.readLine()) != null) {
                allLines+=line +"\n";
            }
            if(allLines == "")
                throw new IOException("Empty File");

            //System.out.println(allLines);
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = new Date();

            assertEquals(allLines.trim(),
                    "_____  _                        ____  _ _ _   ____   ___   ___   ___  \n" +
                            " |  __ \\| |                      |  _ \\(_) | | |___ \\ / _ \\ / _ \\ / _ \\ \n" +
                            " | |__) | |__   ___  _ __   ___  | |_) |_| | |   __) | | | | | | | | | |\n" +
                            " |  ___/| '_ \\ / _ \\| '_ \\ / _ \\ |  _ <| | | |  |__ <| | | | | | | | | |\n" +
                            " | |    | | | | (_) | | | |  __/ | |_) | | | |  ___) | |_| | |_| | |_| |\n" +
                            " |_|    |_| |_|\\___/|_| |_|\\___| |____/|_|_|_| |____/ \\___/ \\___/ \\___/ \n" +
                            "                                                                        \n" +
                            "                                                                        \n" +
                            "   ______           __                                \n" +
                            "  / ____/_  _______/ /_____  ____ ___  ___  _____   _ \n" +
                            " / /   / / / / ___/ __/ __ \\/ __ `__ \\/ _ \\/ ___/  (_)\n" +
                            "/ /___/ /_/ (__  ) /_/ /_/ / / / / / /  __/ /     _   DAVID\n" +
                            "\\____/\\__,_/____/\\__/\\____/_/ /_/ /_/\\___/_/     (_)  \n" +
                            "                                                      \n" +
                            "#     caller      callee           Start Time        End Time        Duration \n" +
                            "1 503-709-4866  503-880-6960   10/15/2015 09:38 AM  10/15/2015 09:42 AM   4 minutes\n" +
                            "\n" +
                            "Phone Bill 3000 Pretty Print Bill Created on: "+ dateFormat.format(date));
        }
        catch(IOException ex){
            System.out.println("Error Reading From File " + ex.getMessage());
        }finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    @Test
    public void TestSortingPhoneCallsByDate(){
        deleteFiles();
        MainMethodResult result4 = invokeMain("david", "503-709-4866", "503-880-6960", "10/18/2015", "12:38","am", "10/15/2015", "13:42","am", "-textFile", "DavesBill");
        MainMethodResult result3 = invokeMain("david", "503-709-4866", "503-880-6960", "10/18/2015", "09:38","am", "10/15/2015", "09:42","am", "-textFile", "DavesBill");
        MainMethodResult result2 = invokeMain("david", "503-709-4866", "503-880-6960", "10/17/2015", "09:38","am", "10/15/2015", "09:42","am", "-textFile", "DavesBill");
        MainMethodResult result1 = invokeMain("david", "503-709-4866", "503-880-6960", "10/15/2015", "09:38","am", "10/15/2015", "09:42","am", "-textFile", "DavesBill");

        MainMethodResult result = invokeMain("-textFile", "DavesBill", "-print");
        assertEquals(result.getOut().trim(), "Customer: david [Phone call from 503-709-4866 to 503-880-6960 from 10/15/2015 09:38 AM to 10/15/2015 09:42 AM, Phone call from 503-709-4866 to 503-880-6960 from 10/17/2015 09:38 AM to 10/15/2015 09:42 AM, Phone call from 503-709-4866 to 503-880-6960 from 10/18/2015 12:38 AM to 10/15/2015 01:42 PM, Phone call from 503-709-4866 to 503-880-6960 from 10/18/2015 09:38 AM to 10/15/2015 09:42 AM]");

        //System.out.println(result.getOut());
        assertEquals(new Integer(0), result.getExitCode());
    }

    @Test
    public void TestSortingPhoneCallsByNumber(){
        deleteFiles();
        MainMethodResult result33 = invokeMain("david", "503-888-4866", "503-880-6960", "10/15/2015", "09:38","am", "10/15/2015", "09:52","am", "-textFile", "DavesBill");

        MainMethodResult result1 = invokeMain("david", "503-709-4866", "503-880-6960", "10/15/2015", "09:38","am", "10/15/2015", "09:32","am", "-textFile", "DavesBill");
        MainMethodResult result2 = invokeMain("david", "503-709-4866", "503-880-6960", "10/15/2015", "09:38","am", "10/15/2015", "09:42","am", "-textFile", "DavesBill");
        MainMethodResult result3 = invokeMain("david", "503-888-4866", "503-880-6960", "10/15/2015", "09:38","am", "10/15/2015", "09:22","am", "-textFile", "DavesBill");
        MainMethodResult result4 = invokeMain("david", "503-555-4866", "503-880-6960", "10/15/2015", "09:38","am", "10/15/2015", "13:42","am", "-textFile", "DavesBill");

        MainMethodResult result = invokeMain("-textFile", "DavesBill", "-print");
        assertEquals(new Integer(0), result.getExitCode());
        assertEquals(result.getOut(), "Customer: david [Phone call from 503-555-4866 to 503-880-6960 from 10/15/2015 09:38 AM to 10/15/2015 01:42 PM, Phone call from 503-709-4866 to 503-880-6960 from 10/15/2015 09:38 AM to 10/15/2015 09:42 AM, Phone call from 503-709-4866 to 503-880-6960 from 10/15/2015 09:38 AM to 10/15/2015 09:32 AM, Phone call from 503-888-4866 to 503-880-6960 from 10/15/2015 09:38 AM to 10/15/2015 09:22 AM, Phone call from 503-888-4866 to 503-880-6960 from 10/15/2015 09:38 AM to 10/15/2015 09:52 AM]\n");
        //System.out.println(result.getOut());
    }



    /*
    @Test
    public void Test(){
        MainMethodResult result = invokeMain("");
        //assertEquals(result.getOut().trim(),"");
        assertEquals(new Integer(0), result.getExitCode());
        System.out.println(result.getOut());
    }
    */

    //Tests from grader from Project 2

    //Test 7
    @Test
    public void TestCreateANewDirectory(){
        MainMethodResult result = invokeMain("-textFile","dcobbley.txt","-print","Project2","123-456-7890","234-567-9081","01/07/2015","07:00", "am","01/17/2015","17:00" ,"pm");
        //assertEquals(result.getOut().trim(),"");
        System.out.println(result.getOut());
        assertEquals(new Integer(0), result.getExitCode());

    }
    //Test 8-using an existing phone bill file
    @Test
    public void TestExistingPhoneBillFile(){
        MainMethodResult result = invokeMain("-textFile","dcobbley.txt","-print","Project2","123-456-7890","456-789-0123","01/08/2015","08:00", "am","01/08/2015","18:00", "am");
        //assertEquals(result.getOut().trim(),"");
        System.out.println(result.getOut());
        assertEquals(new Integer(0), result.getExitCode());

    }
    //Test 9 - Different customer name
    @Test
    public void TestDifferentCustomerName(){
        MainMethodResult result = invokeMain("-textFile","dcobbley.txt","DIFFERENT","123-456-7890","789-012-3456","01/09/2015","09:00","am","02/04/2015","16:00","am");
        //assertEquals(result.getOut().trim(),"");
        System.out.println(result.getOut());
        assertEquals(new Integer(0), result.getExitCode());

    }
    //Test 10 - Malformatted text file
    @Test
    public void TestMalformedTextFile(){
        //Need to create bogus text file
        /*
        File dcobbley/bogus.txt:
        asdfsadfa
        2r9sadvas
        lcv913r
        2r7o9av
         */
        MainMethodResult result = invokeMain("-textFile","dcobbley/bogus.txt","Project2","123-456-7890","385-284-2342","01/10/2015","10:00","01/20/2015","20:00");
        //assertEquals(result.getOut().trim(),"");
        System.out.println(result.getOut());
        assertEquals(new Integer(0), result.getExitCode());

    }

}