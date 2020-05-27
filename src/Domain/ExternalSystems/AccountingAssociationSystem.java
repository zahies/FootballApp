package Domain.ExternalSystems;

import Domain.Users.Commissioner;
import javafx.util.Pair;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class AccountingAssociationSystem {

    private List<Commissioner> commissioners;
    private static HashMap<String,LinkedList<Pair<Double,String>>> payments;

    public AccountingAssociationSystem() {
        this.commissioners = new LinkedList<>();
        payments = new HashMap<>();
    }



    public boolean addPayment(String teamName, String date, double amount){
        if (teamName.length() == 0 || amount < 0 ){
            return false;
        }
        Pair<Double,String> pair = new Pair<>(amount,date);
        if (payments.containsKey(teamName)){
            payments.get(teamName).add(pair);
        }else {
            LinkedList<Pair<Double,String>> newList = new LinkedList();
            newList.add(pair);
            payments.put(teamName,newList);
        }
        return true;
    }


    /** only manager permission can use this func */
    public void resetPayments(){
        payments.clear();
    }



}
