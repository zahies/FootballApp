package ExternalSystems;

import java.util.Timer;
import java.util.TimerTask;

public class ProxyTaxLaws implements ITaxValidation{




    @Override
    public double getTaxRate(double revenueAmount) {
        ProxyTaxLaws ptl = new ProxyTaxLaws();
        return ptl.getTaxRate(revenueAmount);
    }



    public void connect(){
        final boolean[] done = {false};
        Timer timer = new Timer();
        final int[] i = {0};
        System.out.print("Connecting to external system (Tax Laws)");
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
            System.out.println("Connected to Proxy Tax Laws Successfully ! :)");
        }
    }
}
