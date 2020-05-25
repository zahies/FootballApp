package Domain.ExternalSystems;

public class ProxyAccounting implements IGetPaymentAccounting {


    @Override
    public boolean addPayment(String teamName, String date, double amount) {
        AccountingAssociationSystem aas = new AccountingAssociationSystem();
        return aas.addPayment(teamName,date,amount);
    }
}
