package Part;

/**
 * Parts.InHouse.Java (For Parts.InHouse Parts)
 */

public class InHouse extends Part {
    private int machineId;

    /** Constructor */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id,name,price,stock,min,max);
        this.machineId = machineId;
    }
    /**
     * Set the part's machine Id
     */
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }
    /**
     * Return the part's machine Id
     */
    public int getMachineId() {
        return machineId;
    }
}
