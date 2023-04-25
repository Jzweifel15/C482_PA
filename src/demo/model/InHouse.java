package demo.model;

/**
 * @author Justin R. Zweifel
 */
public class InHouse extends Part {

    private int machineId;

    // Constructor
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }

    /**
     * Change/set the machineId
     * @param machineId the new ID for the instance to be set to
     */
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }

    /**
     * Get the machineID for the specific instance
     * @return the ID of the specific instance
     */
    public int getMachineId() {
        return this.machineId;
    }

    @Override
    public String toString() {
        return "ID=" + this.getId() + ", Name=" + this.getName() + ", Price=" + this.getPrice() +
                ", Stock=" + this.getStock() + ", Min=" + this.getMin() + ", Max=" + this.getMax() +
                ", MachineID=" + this.getMachineId();
    }

}
