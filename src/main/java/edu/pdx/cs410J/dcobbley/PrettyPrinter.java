package edu.pdx.cs410J.dcobbley;

import edu.pdx.cs410J.AbstractPhoneBill;
import edu.pdx.cs410J.PhoneBillDumper;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by david on 7/21/15.
 */
public class PrettyPrinter implements PhoneBillDumper {

    File file;
    String path=null;
    boolean flag=false;

    PrettyPrinter(String path){
        if(path.equals("-")) {
            flag = true;
        }
        else {
            this.path = System.getProperty("user.dir");
            file = new File(this.path + "/" + path + ".txt");
        }

    }
    /**
     * Dumps a phone bill to some destination.
     *
     * @param bill
     */
    @Override
    public void dump(AbstractPhoneBill bill){
        if(bill == null){
            bill = new phonebill("", new phonecall());
        }
        if(flag){
            //print to standard out
            System.out.println("  _____  _                        ____  _ _ _   ____   ___   ___   ___  \n" +
                    " |  __ \\| |                      |  _ \\(_) | | |___ \\ / _ \\ / _ \\ / _ \\ \n" +
                    " | |__) | |__   ___  _ __   ___  | |_) |_| | |   __) | | | | | | | | | |\n" +
                    " |  ___/| '_ \\ / _ \\| '_ \\ / _ \\ |  _ <| | | |  |__ <| | | | | | | | | |\n" +
                    " | |    | | | | (_) | | | |  __/ | |_) | | | |  ___) | |_| | |_| | |_| |\n" +
                    " |_|    |_| |_|\\___/|_| |_|\\___| |____/|_|_|_| |____/ \\___/ \\___/ \\___/ \n" +
                    "                                                                        \n" +
                    "                                                                        ");
            System.out.println();
            System.out.println("   ______           __                                \n" +
                    "  / ____/_  _______/ /_____  ____ ___  ___  _____   _ \n" +
                    " / /   / / / / ___/ __/ __ \\/ __ `__ \\/ _ \\/ ___/  (_)\n" +
                    "/ /___/ /_/ (__  ) /_/ /_/ / / / / / /  __/ /     _   " + bill.getCustomer().toUpperCase() + "\n" +
                    "\\____/\\__,_/____/\\__/\\____/_/ /_/ /_/\\___/_/     (_)  \n" +
                    "                                                      " + "\n");

            System.out.println("#     caller      callee           Start Time        End Time        Duration \n");
            int x = 0;

            for (Object obj : bill.getPhoneCalls()) {
                phonecall mine = (phonecall) obj;
                System.out.println(++x + " " + mine.getCaller() + "  " + mine.getCallee() + "   " + mine.getStartTimeString() + "  " + mine.getEndTimeString() + "   " + mine.duration() + "\n");
                //writer.write(obj.toString()+"\n");
            }

            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = new Date();
            System.out.println("\nPhone Bill 3000 Pretty Print Bill Created on: " + dateFormat.format(date) + "\n");

        }
        else {
            try {
                PrintWriter writer = new PrintWriter(file);
                writer.write("  _____  _                        ____  _ _ _   ____   ___   ___   ___  \n" +
                        " |  __ \\| |                      |  _ \\(_) | | |___ \\ / _ \\ / _ \\ / _ \\ \n" +
                        " | |__) | |__   ___  _ __   ___  | |_) |_| | |   __) | | | | | | | | | |\n" +
                        " |  ___/| '_ \\ / _ \\| '_ \\ / _ \\ |  _ <| | | |  |__ <| | | | | | | | | |\n" +
                        " | |    | | | | (_) | | | |  __/ | |_) | | | |  ___) | |_| | |_| | |_| |\n" +
                        " |_|    |_| |_|\\___/|_| |_|\\___| |____/|_|_|_| |____/ \\___/ \\___/ \\___/ \n" +
                        "                                                                        \n" +
                        "                                                                        ");
                writer.write("\n");

                if (bill == null) {
                    bill = new phonebill("", new phonecall());
                }
                writer.write("   ______           __                                \n" +
                        "  / ____/_  _______/ /_____  ____ ___  ___  _____   _ \n" +
                        " / /   / / / / ___/ __/ __ \\/ __ `__ \\/ _ \\/ ___/  (_)\n" +
                        "/ /___/ /_/ (__  ) /_/ /_/ / / / / / /  __/ /     _   " + bill.getCustomer().toUpperCase() + "\n" +
                        "\\____/\\__,_/____/\\__/\\____/_/ /_/ /_/\\___/_/     (_)  \n" +
                        "                                                      " + "\n");

                writer.write("#     caller      callee           Start Time        End Time        Duration \n");
                int x = 0;

                for (Object obj : bill.getPhoneCalls()) {
                    phonecall mine = (phonecall) obj;
                    writer.write(++x + " " + mine.getCaller() + "  " + mine.getCallee() + "   " + mine.getStartTimeString() + "  " + mine.getEndTimeString() + "   " + mine.duration() + "\n");
                    //writer.write(obj.toString()+"\n");
                }

                DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                Date date = new Date();
                writer.write("\nPhone Bill 3000 Pretty Print Bill Created on: " + dateFormat.format(date) + "\n");
                // All done
                writer.flush();
                writer.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

    }


}
