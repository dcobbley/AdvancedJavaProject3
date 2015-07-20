package edu.pdx.cs410J.dcobbley;

import edu.pdx.cs410J.AbstractPhoneBill;
import edu.pdx.cs410J.PhoneBillDumper;
import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

/**
 * Created by david on 7/13/15.
 */
public class TextDumper implements PhoneBillDumper {


    File file;
    String path=null;

    TextDumper(String path){
        this.path = System.getProperty("user.dir");
        file = new File(this.path+ "/" + path + ".txt");

    }

    /**
     * Dumps a phone bill to some destination.
     *
     * @param bill
     */
    @Override
    public void dump(AbstractPhoneBill bill) {

        if(bill == null){
            bill = new phonebill("", new phonecall());
        }
        try {
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = new Date();


            PrintWriter writer = new PrintWriter(file);
            writer.write("Created on: "+dateFormat.format(date) + "\n");

            writer.write("Customer: "+ bill.getCustomer()+"\n");
            //writer.write(bill.getPhoneCalls().toString());
            //Collection my = bill.getPhoneCalls();
            for(Object obj : bill.getPhoneCalls()){
                writer.write(obj.toString()+"\n");
            }

            // All done
            writer.flush();
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}
