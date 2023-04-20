package demo.model;

/**
 * @author Justin R. Zweifel
 */
class Outsourced extends Part {

    private String companyName;

    // Constructor
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /**
     * Change/set the company name
     * @param companyName the new name to set the company to
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     * Get the name of the company for the specific instance
     * @return the name of the comapny
     */
    public String getCompanyName() {
        return this.companyName;
    }

}
