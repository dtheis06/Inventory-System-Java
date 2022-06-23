package Part;

/**
 * Outsourced.Java (For Outsourced Parts)
 */

public class Outsourced extends Part {
    private String companyName;

    /** Constructor */
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id,name,price,stock,min,max);
        this.companyName = companyName;
    }
    /**
     * Set the name of the company that makes the part.
     */
    public void setCompanyName(String name) {
        this.companyName = name;
    }
    /**
     * Return the name of the company who makes the part
     */
    public String getCompanyName() {
        return companyName;
    }
}
