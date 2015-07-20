package edu.pdx.cs410J.dcobbley;

import org.junit.Ignore;
import org.junit.Test;
import static org.junit.Assert.assertTrue;
import edu.pdx.cs410J.InvokeMainTestCase;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static junit.framework.Assert.assertEquals;

/**
 * Tests the functionality in the {@link Project2} main class.
 */
public class Project2Test extends InvokeMainTestCase {

    /**
     * Invokes the main method of {@link Project2} with the given arguments.
     */
    private MainMethodResult invokeMain(String... args) {
        return invokeMain( Project2.class, args );
    }
//      /*
//    @Test
//    public void Test(){
//        MainMethodResult result = invokeMain("");
//        //assertEquals(result.getOut().trim(),"");
//        assertEquals(new Integer(0), result.getExitCode());
//        System.out.println(result.getOut());
//    }


  @Test
    public void TestPrintingOutAPhoneCall(){
        MainMethodResult result = invokeMain("-print","Test8","123-456-7890","234-567-8901","03/03/2015","12:00","05/04/2015","16:00");
        assertEquals(result.getOut().trim(),"Customer: Test8 [Phone call from 123-456-7890 to 234-567-8901 from 03/03/2015 12:00 to 05/04/2015 16:00]");
        assertEquals(new Integer(0), result.getExitCode());
        //System.out.println(result.getOut());
      System.out.println("Test Number 1");
    }


    @Test
    public void TestMultiWordUserName(){
        MainMethodResult result = invokeMain("-print","Test 8","123-456-7890","234-567-8901","03/03/2015","12:00","09/04/2015","16:00");
        assertEquals(result.getOut().trim(),"Customer: Test 8 [Phone call from 123-456-7890 to 234-567-8901 from 03/03/2015 12:00 to 09/04/2015 16:00]");
        assertEquals(new Integer(0), result.getExitCode());
        //System.out.println(result.getOut());
        System.out.println("Test Number 2");
    }


    @Test
    public void TestMissingEndTime(){
        MainMethodResult result = invokeMain("Test9","123-456-7890","234-546-3452","03/03/2015","12:00");
        assertEquals(result.getOut().trim(),"Not enough arguments provided");
        assertEquals(new Integer(1), result.getExitCode());
        //System.out.println(result.getOut());
        System.out.println("Test Number 3");
    }

    /*
    @Test
    public void Test(){
        MainMethodResult result = invokeMain("");
        //assertEquals(result.getOut().trim(),"");
        assertEquals(new Integer(0), result.getExitCode());
        System.out.println(result.getOut());
    }

    /*
    @Test
    public void Test(){
        MainMethodResult result = invokeMain("");
        //assertEquals(result.getOut().trim(),"");
        assertEquals(new Integer(0), result.getExitCode());
        System.out.println(result.getOut());
    }

    /*
    @Test
    public void Test(){
        MainMethodResult result = invokeMain("");
        //assertEquals(result.getOut().trim(),"");
        assertEquals(new Integer(0), result.getExitCode());
        System.out.println(result.getOut());
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



  @Test
  public void TestUnknownCommandArgument(){
      MainMethodResult result = invokeMain("-fred", "Test6", "123-456-7890", "234-567-8901", "03/03/2015", "12:00", "04/04/2015", "16:00");
      assertEquals(result.getOut().trim(), "Non-Valid Argument\n" +
              "README has been called\n" +
              "This program is a phonebill application which takes a very specific amount of arguments\n" +
              "You must provide a customer name, caller number, callee number, start time, and end time (mm/dd/yyyy mm:hh)\n" +
              "\n" +
              "usage: java edu.pdx.cs410J.<login-id>.Project2 [options] <args>\n" +
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
      System.out.println("Test Number 4");

  }
    @Test
    public void TestUnknownArgumetn(){
        MainMethodResult result = invokeMain("Test7", "123-456-7890", "234-567-8901", "03/03/2015", "12:00", "04/04/2015", "16:00", "fred");
        assertEquals(result.getOut().trim(),"Not a valid command");
        System.out.println("Test Number 5");
    }

    @Test
  public void TestMalformedEndTime(){
      MainMethodResult result = invokeMain("Test5", "123-456-7890", "234-567-8901", "03/03/2015", "12:00", "01/04/20/1", "16:00");
      assertEquals(result.getOut().trim(),"Date format must follow mm/dd/yyyy");
        System.out.println("Test Number 6");
  }

  @Test
  public void TestMalformedStartTime(){
      MainMethodResult result =invokeMain("Test4", "123-456-7890", "234-567-8901", "03/03/2015", "12:XX", "03/03/2015", "16:00");
      assertEquals(result.getOut().trim(), "Time format must follow mm:hh (24 hour time)");
      System.out.println("Test Number 7");
  }


  @Test
  public void TestNonIntegerPhoneNumber(){
      MainMethodResult result = invokeMain("Test3", "ABC-123-4567", "123-456-7890", "03/03/2015", "12:00", "03/03/2015", "16:00");
      assertEquals(new Integer(1), result.getExitCode());
      assertTrue(result.getOut().trim().equals("Valid phone numbers must contain exactly 10 numbers plus two dashes"));
      System.out.println("Test Number 8");
  }

    @Test
    public void TestFromGrader(){
        MainMethodResult result = invokeMain("-print", "Test8", "123-456-7890", "234-567-8901", "03/03/2015", "12:00", "05/04/2015", "16:00");
        assertEquals(new Integer(0), result.getExitCode());
        assertTrue(result.getOut().trim().equals("Customer: Test8 [Phone call from 123-456-7890 to 234-567-8901 from 03/03/2015 12:00 to 05/04/2015 16:00]"));
        System.out.println("Test Number 9");

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
      System.out.println("Test Number 10");
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
        try{
            String path = System.getProperty("user.dir") + "/DavesBill.txt";
            File file = new File(path);
            file.delete();
        }
        catch(Exception ex){
            System.out.println("Sad Day");
        }
      MainMethodResult result = invokeMain("david", "503-709-4866", "503-880-6960", "10/15/2015", "09:38", "10/15/2015", "09:42", "-textFile", "DavesBill");
        //System.out.println(result.getOut());
      assertEquals(new Integer(0), result.getExitCode());
        System.out.println("Test Number 12");

  }

    @Test
  public void TestTextFileNodataNoArgsNofile(){
      MainMethodResult result = invokeMain("-textFile");
      assertEquals(new Integer(1), result.getExitCode());
      assertTrue(result.getOut().trim().equals("-textFile argument must be followed by <filename>\n" +
              "README has been called\n" +
              "This program is a phonebill application which takes a very specific amount of arguments\n" +
              "You must provide a customer name, caller number, callee number, start time, and end time (mm/dd/yyyy mm:hh)\n" +
              "\n" +
              "usage: java edu.pdx.cs410J.<login-id>.Project2 [options] <args>\n" +
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
              "Dates and times should be in the format: mm/dd/yyyy hh:mm"));
      //System.out.println(result.getOut());
        System.out.println("Test Number 13");
  }

  @Test
  public void TestPrintNoDataNoOtherArgs(){
      MainMethodResult result = invokeMain("-print");
      assertEquals(new Integer(1), result.getExitCode());
        assertTrue(result.getOut().trim().equals("Must provide a phone bill\n" +
                "README has been called\n" +
                "This program is a phonebill application which takes a very specific amount of arguments\n" +
                "You must provide a customer name, caller number, callee number, start time, and end time (mm/dd/yyyy mm:hh)\n" +
                "\n" +
                "usage: java edu.pdx.cs410J.<login-id>.Project2 [options] <args>\n" +
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
                "Dates and times should be in the format: mm/dd/yyyy hh:mm"));
      System.out.println("Test Number 14");

  }

  @Test
  public void TestReadmeNoDataNoArgs(){
      MainMethodResult result = invokeMain("-README");
      assertEquals(new Integer(0), result.getExitCode());
      assertTrue(result.getOut().trim().equals("README has been called\n" +
              "This program is a phonebill application which takes a very specific amount of arguments\n" +
              "You must provide a customer name, caller number, callee number, start time, and end time (mm/dd/yyyy mm:hh)\n" +
              "\n" +
              "usage: java edu.pdx.cs410J.<login-id>.Project2 [options] <args>\n" +
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
              "Dates and times should be in the format: mm/dd/yyyy hh:mm"));
      System.out.println("Test Number 15");
  }

    @Test
    public void testRegularCommandLineArguments(){
        MainMethodResult result = invokeMain("david", "503-709-4866", "503-880-6960", "10/15/2015", "09:38", "10/15/2015", "09:42", "-print");
        assertEquals(new Integer(0), result.getExitCode());
        assertEquals(result.getOut().trim(), "Customer: david [Phone call from 503-709-4866 to 503-880-6960 from 10/15/2015 09:38 to 10/15/2015 09:42]");
        System.out.println("Test Number 16");
    }


    @Test
    public void testAllCommandLineArguments(){
        MainMethodResult result = invokeMain("david", "503-709-4866", "503-880-6960", "10/15/2015", "09:38", "10/15/2015", "09:42", "-print", "-README");
        assertEquals(new Integer(0), result.getExitCode());
        assertTrue(result.getOut().trim().equals("Customer: david [Phone call from 503-709-4866 to 503-880-6960 from 10/15/2015 09:38 to 10/15/2015 09:42]\n" +
                "README has been called\n" +
                "This program is a phonebill application which takes a very specific amount of arguments\n" +
                "You must provide a customer name, caller number, callee number, start time, and end time (mm/dd/yyyy mm:hh)\n" +
                "\n" +
                "usage: java edu.pdx.cs410J.<login-id>.Project2 [options] <args>\n" +
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
                "Dates and times should be in the format: mm/dd/yyyy hh:mm"));
        System.out.println("Test Number 17");
    }

    @Test
    public void NoCommandLineArgs(){
        MainMethodResult result = invokeMain();
        assertEquals(new Integer(1), result.getExitCode());
        assertTrue(result.getOut().trim().equals("Cannot have zero arguments\n" +
                "README has been called\n" +
                "This program is a phonebill application which takes a very specific amount of arguments\n" +
                "You must provide a customer name, caller number, callee number, start time, and end time (mm/dd/yyyy mm:hh)\n" +
                "\n" +
                "usage: java edu.pdx.cs410J.<login-id>.Project2 [options] <args>\n" +
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
                "Dates and times should be in the format: mm/dd/yyyy hh:mm"));
        //System.out.println(result.getOut());
        System.out.println("Test Number 18");
    }

    @Test
    public void TestNonExistantFile(){//PRODUCES EMPTY FILES??
        try{
            String path = System.getProperty("user.dir") + "/DavesBill.txt";
            File file = new File(path);
            file.delete();
        }
        catch(Exception ex){
            System.out.println("Sad Day");
        }
        MainMethodResult result = invokeMain("-textFile", "DavesBill");
        assertEquals(new Integer(0), result.getExitCode());
        System.out.println("Test Number 19");

    }


    @Test
    public void TestDuplicates(){
        try{
            String path = System.getProperty("user.dir") + "/DavesBill.txt";
            File file = new File(path);
            file.delete();
        }
        catch(Exception ex){
            System.out.println("Sad Day");
        }
        MainMethodResult result = invokeMain("david", "503-709-4866", "503-880-6960", "10/15/2015", "09:38", "10/15/2015", "09:42", "-textFile", "DavesBill");
        assertEquals(new Integer(0), result.getExitCode());
        MainMethodResult anotherResult = invokeMain("david", "503-709-4866", "503-880-6960", "10/15/2015", "09:38", "10/15/2015", "09:42", "-textFile", "DavesBill");
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
                    "Phone call from 503-709-4866 to 503-880-6960 from 10/15/2015 09:38 to 10/15/2015 09:42");
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
        System.out.println("Test Number 20");

    }
    @Test
    public void TestMultiplePhonecalls(){
        try{
            String path = System.getProperty("user.dir") + "/DavesBill.txt";
            File file = new File(path);
            file.delete();
        }
        catch(Exception ex){
            System.out.println("Sad Day");
        }
        MainMethodResult result = invokeMain("david", "503-709-4866", "503-880-6960", "10/15/2015", "09:38", "10/15/2015", "09:42", "-textFile", "DavesBill");
        assertEquals(new Integer(0), result.getExitCode());
        MainMethodResult anotherResult = invokeMain("david", "503-709-4866", "503-555-7777", "10/17/2015", "10:18", "10/17/2015", "10:40", "-textFile", "DavesBill", "-print");
        assertEquals(new Integer(0), anotherResult.getExitCode());
        assertEquals(anotherResult.getOut().trim(),"Customer: david [Phone call from 503-709-4866 to 503-555-7777 from 10/17/2015 10:18 to 10/17/2015 10:40, Phone call from 503-709-4866 to 503-880-6960 from 10/15/2015 09:38 to 10/15/2015 09:42]");

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
                    "Phone call from 503-709-4866 to 503-555-7777 from 10/17/2015 10:18 to 10/17/2015 10:40\n" +
                    "Phone call from 503-709-4866 to 503-880-6960 from 10/15/2015 09:38 to 10/15/2015 09:42");
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
        System.out.println("Test Number 21");

    }


}