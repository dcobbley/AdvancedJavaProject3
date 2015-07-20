package edu.pdx.cs410J.dcobbley;

import edu.pdx.cs410J.AbstractPhoneBill;
import edu.pdx.cs410J.ParserException;
import edu.pdx.cs410J.PhoneBillParser;
import org.omg.CORBA.SystemException;
import sun.plugin.dom.core.Text;

import java.io.*;

/**
 * Created by david on 7/13/15.
 */
public class TextParser implements PhoneBillParser {


    File file;
    String path=null;
    /**
     * Parses some source and returns a phone bill
     *
     * @throws ParserException If the source cannot be parsed
     */

    TextParser(String path){
        this.path = System.getProperty("user.dir");
        file = new File(this.path+ "/" + path + ".txt");
    }

    TextParser() throws SystemException{
    }

    @Override
    public AbstractPhoneBill parse() {
        BufferedReader reader = null;
        AbstractPhoneBill bill = null;
        try{
            reader=new BufferedReader(new FileReader(file));
            String line;
            String allLines="";
            while ((line = reader.readLine()) != null) {
                allLines+=line +"\n";
            }
            if(allLines == "")
                throw new IOException("Empty File");
            bill = ParseString(allLines);
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

        return bill;
    }


    public AbstractPhoneBill ParseString(String line){
        AbstractPhoneBill myPhoneBill=null;
        String callerNumber;
        String calleeNumber;
        String startTime;
        String endTime;
        //String[] tokens = line.split("\\s+|,\\s*|\\.\\s*");
        String[] tokens = line.split("\\r?\\n");
        int length = tokens.length;
        /*for(int x =0;x<tokens.length;x++)
        {
            System.out.println(x+": "+tokens[x]);
        }*/

        myPhoneBill = new phonebill(tokens[1].substring(10));//add a customer to the phonebill

        for(int counter = 2;counter < length;counter++ ){
            if(tokens[counter] != null){
                String[] temp = tokens[counter].split("\\s+|,\\s*|\\.\\s*");
                if(temp.length>10) {
                    myPhoneBill.addPhoneCall(new phonecall(temp[3],temp[5],temp[7]+" " +temp[8], temp[10]+" " +temp[11]));
                }
            }
        }
        return myPhoneBill;
    }
    public boolean ifFileExists()
    {
        if(file.exists())
            return true;
        else
            return false;
    }
}
