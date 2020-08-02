package Domain;

import java.io.*;
import java.util.Date;

public class SystemLog {
    private static SystemLog ourInstance = new SystemLog();
    private File logfile;

    public static SystemLog getInstance() {
        return ourInstance;
    }

    private SystemLog() {
        if (ourInstance != null) {
            System.out.println("existing log");
        }
        this.makeFile();

    }

    private void makeFile() {
        logfile = new File("log/log.txt");
    }

    public void UpdateLog(String note) {
        BufferedWriter bW = null;
        try {
            Date xdate = new Date();
            bW = new BufferedWriter(new FileWriter(logfile, true));
            bW.append(xdate.toString() + " " + note);
            bW.newLine();
            bW.flush();
        } catch (IOException e1) {
            e1.printStackTrace();
        }

    }

    public String showLog() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(logfile));
        String st = "";
        String fullLog = "";
        //if((st = br.readLine())!=null){
        while ((st = br.readLine()) != null) {
            fullLog += st + "/n";
        }

        return fullLog;
    }
}


