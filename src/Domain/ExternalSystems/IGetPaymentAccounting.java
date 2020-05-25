package Domain.ExternalSystems;

public interface IGetPaymentAccounting {
    boolean addPayment(String teamName, String date, double amount);
}
