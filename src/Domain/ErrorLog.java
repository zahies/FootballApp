package Domain;

import java.io.*;
import java.util.Date;


    public class ErrorLog {
        private static Domain.ErrorLog ourInstance = new Domain.ErrorLog();
        private File logfile;

        public static Domain.ErrorLog getInstance() {
            return ourInstance;
        }

        private ErrorLog() {
            if (ourInstance != null) {
                System.out.println("existing log");
            }
            this.makeFile();

        }

        private void makeFile() {
            logfile = new File("log/errorlog.txt");
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
