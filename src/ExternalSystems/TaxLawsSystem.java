package ExternalSystems;

public class TaxLawsSystem {
    private static int numOfTeams;
    private static int openingTax;
    private static int middleYearTax;
    private static int monthlyTax;   /** num between 0 and 1 */

    public TaxLawsSystem() {

    }

    public double getTaxRate(double revenueAmount) {
        return revenueAmount*monthlyTax;
    }

    public int getNumOfTeams() {
        return numOfTeams;
    }

    public void setNumOfTeams(int numOfTeams) {
        this.numOfTeams = numOfTeams;
    }

    public int getOpeningTax() {
        return openingTax;
    }

    public void setOpeningTax(int openingTax) {
        this.openingTax = openingTax;
    }

    public int getMiddleYearTax() {
        return middleYearTax;
    }

    public void setMiddleYearTax(int middleYearTax) {
        this.middleYearTax = middleYearTax;
    }

    public void setMonthlyTax(int monthlyTax) {
        if (!(monthlyTax > 1 || monthlyTax < 0)){
            this.monthlyTax = monthlyTax;
        }
    }
}
