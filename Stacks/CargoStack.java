//Shahrizod Bobojonov 114137520 Recitation 1

import java.util.Stack; //import java Stack class

/**
 * CargoStack class which extends the java Stack class and adds some new
 * variables and methods needed for the parameters of the assignment, such as
 * a custom <CODE>size()</CODE> method to avoid using the inherited Vector
 * method, as well as an <CODE>isStackable()</CODE> method to check whether cargo
 * can be placed on top of the stack without crushing the cargo underneath based
 * on provided rules
 */
public class CargoStack extends Stack<Cargo>{
    private int numCargo;

    /**
     * Constructor for a CargoStack object, calls the Stack class constructor
     * and then initializes the numCargo variable to 0;
     */
    public CargoStack() {
        super();
        numCargo = 0;
    }

    /**
     * Overrides <CODE>push()</CODE> method to also increment the numCargo
     * variable so it can be used when calling the <CODE>size()</CODE> method.
     * Also uses the inherited <CODE>push()</CODE> method from the Stack object
     * and returns that object because that's the method signature of the method
     * being overridden
     *
     * @param c
     *      the Cargo object to push onto the stack
     * @return
     *      the Cargo object that was pushed
     */
    @Override
    public Cargo push(Cargo c) {
        super.push(c);
        numCargo++;
        return c;
    }

    /**
     * Overrides <CODE>pop()</CODE> method to also decrement the numCargo
     * variable so it can be used when calling the <CODE>size()</CODE> method.
     * Also uses the inherited <CODE>pop()</CODE> method from the Stack
     * class and then returns the Cargo object it popped
     *
     * @return
     *      the Cargo object being popped
     *
     */
    @Override
    public Cargo pop() {
        Cargo c = super.pop();
        numCargo--;
        return c;
    }

    /**
     * Overrides <CODE>size()</CODE> method inherited from Vector class because
     * Vector class is not allowed to be used for this assignment. Instead, an
     * O(1) implementation using the numCargo variable is used.
     *
     * @return
     *      the value of numCargo, which tracks the size of the stack by
     *      incrementing/decrementing when pushing/popping
     */
    @Override
    public int size() {
        return numCargo;
    }

    /**
     * Overrides <CODE>empty()</CODE> method inherited from Stack class to be
     * executed using <Code>isEmpty()</Code> instead and just calling the method
     * within it
     *
     * @return
     *      boolean value representing if the stack is empty or not
     */
    @Override
    public boolean isEmpty() {
        return empty();
    }

    /**
     * Helper method to quickly figure out whether a Cargo object can be put
     * on top of the stack without crushing the Cargo object underneath, based
     * on the provided set of rules
     *
     * @param c
     *      Cargo object to check if it's able to be pushed on top of the stack
     * @return
     *      boolean value representing whether the Cargo object can be pushed
     */
    public boolean isStackable(Cargo c) {
        if (numCargo == 0) return true;
        if (peek().getStrength() == CargoStrength.FRAGILE) {
            return c.getStrength() != CargoStrength.MODERATE && c.getStrength() != CargoStrength.STURDY;
        } else if (peek().getStrength() == CargoStrength.MODERATE) {
            return c.getStrength() != CargoStrength.STURDY;
        }
        return true;
    }
}
