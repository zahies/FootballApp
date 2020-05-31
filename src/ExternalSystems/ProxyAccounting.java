package ExternalSystems;

import java.util.Timer;
import java.util.TimerTask;

public class ProxyAccounting implements IGetPaymentAccounting {


    @Override
    public boolean addPayment(String teamName, String date, double amount) {
        AccountingAssociationSystem aas = new AccountingAssociationSystem();
        return aas.addPayment(teamName,date,amount);
    }

    public void connect(){
        final boolean[] done = {false};
        Timer timer = new Timer();
        final int[] i = {0};
        System.out.print("Connecting to external system (Accounting)");
        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                System.out.print(".");
                i[0]++;
                if (i[0] > 10){
                    timer.cancel();
                    done[0] = true;
                }
            }
        },0,500);
        System.out.println();
        if (done[0]){
            System.out.println("Connected to Proxy Accounting Successfully ! :)");
        }

    }
}
