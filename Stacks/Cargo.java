//Shahrizod Bobojonov 114137520 Recitation 1

/**
 * This class represents a piece of cargo that cab be stored in a stack, either
 * on the ship or on the dock (wherever CargoStack is used). It stores the name,
 * weight, and strength of the cargo as a String, double, and enum CargoStrength,
 * respectively and is initialized with these values with no setters, making
 * objects of this class immutable
 */
public class Cargo {
    private String name;
    private double weight;
    private CargoStrength strength;

    /**
     * Constructor for a Cargo object with the parameters to create the object
     * with, also throws an IllegalArgumentException if the user tries creating
     * an object with a null name or weight <= 0
     *
     * @param initName
     *      the name of the cargo (used when searching for cargo on the ship)
     * @param initWeight
     *      the weight of the cargo (used when determining if the ship can hold it)
     * @param initStrength
     *      the strength of the cargo (used to see if cargo is stackable)
     * @throws IllegalArgumentException
     *      when the name in the constructor is null or the weight is <= 0
     */
    public Cargo(String initName, double initWeight, CargoStrength initStrength) throws IllegalArgumentException {
        if (initName == null || initWeight <= 0) throw new IllegalArgumentException();
        name = initName;
        weight = initWeight;
        strength = initStrength;
    }

    /**
     * Getter method for the name of the Cargo object
     *
     * @return
     *      the String value of the name field of the Cargo object
     */
    public String getName() {
        return name;
    }

    /**
     * Getter method for the weight of the Cargo object
     *
     * @return
     *      the double value of the weight field of the Cargo object
     */
    public double getWeight() {
        return weight;
    }

    /**
     * Getter method for the strength of the Cargo object
     *
     * @return
     *      the CargoStrength enum of the strength field of the Cargo object
     */
    public CargoStrength getStrength() {
        return strength;
    }

    /**
     * Override <CODE>toString()</CODE> method to represent the Cargo object
     * in the preferred way of just the first initial, used particularly when
     * printing the cargo on the ship and dock
     *
     * @return
     *      the string value to represent the Cargo object with
     */
    @Override
    public String toString() {
        if (strength == CargoStrength.FRAGILE) {
            return "F";
        } else if (strength == CargoStrength.MODERATE) {
            return "M";
        } else {
            return "S";
        }
    }
}
