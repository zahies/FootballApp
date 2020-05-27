package Domain.ExternalSystems;

public class ProxyTaxLaws implements ITaxValidation{



    @Override
    public double getTaxRate(double revenueAmount) {
        ProxyTaxLaws ptl = new ProxyTaxLaws();
        return ptl.getTaxRate(revenueAmount);
    }
}
