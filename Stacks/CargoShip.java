//Shahrizod Bobojonov 114137520 Recitation 1

import java.util.EmptyStackException; //possibly thrown when popping cargo

/**
 * This class represents a Cargo ship filled with stacks of cargo which is stored
 * in an array, and it has maxHeight and maxWeight limitations provided when
 * constructing. It also has a shipWeight field to track the weight of the ship
 * to make sure that it doesn't exceed the maxWeight allowed
 */
public class CargoShip {
    private CargoStack[] stacks;
    private int maxHeight;
    private double maxWeight;
    private double shipWeight;

    /**
     * Constructor for a CargoShip object, initializing the CargoStack array (stacks)
     * based on the number of stacks to be used on the ship, as well as setting
     * the max height and weight of the ship using the given parameters
     *
     * @param numStacks
     *      the size of the CargoStack array to initialize / number of stacks on ship
     * @param initMaxHeight
     *      the maximum number of cargo allowed on one stack
     * @param initMaxWeight
     *      the maximum total weight allowed on the ship
     */
    public CargoShip(int numStacks, int initMaxHeight, double initMaxWeight) {
        if (numStacks <= 0 || initMaxHeight <= 0 || initMaxWeight <= 0) throw new IllegalArgumentException();
        stacks = new CargoStack[numStacks];
        for (int i = 0; i < numStacks; i++) {
            stacks[i] = new CargoStack();     //NullPointerException if not initialized
        }
        maxHeight = initMaxHeight;
        maxWeight = initMaxWeight;
        shipWeight = 0;
    }

    /**
     * Method to push cargo onto a particular stack
     *
     * @param cargo
     *      the cargo to push on the stack
     * @param stack
     *      the index of the stack to push onto, starts from 1
     * @throws FullStackException
     *      when the stack being pushed to is already at maxHeight
     * @throws ShipOverweightException
     *      when the ship will exceed maxWeight after adding this Cargo object
     * @throws CargoStrengthException
     *      when the cargo being pushed to can't be stacked on top of whatever
     *      is already at the top of the stack due to the CargoStrength limits
     *      given by the assignment
     */
    public void pushCargo(Cargo cargo, int stack) throws FullStackException, ShipOverweightException, CargoStrengthException {
        if (cargo == null || stack < 1 || stack > stacks.length) throw new IllegalArgumentException();
        if (stacks[stack-1].size() == maxHeight) throw new FullStackException("Stack is full");
        if (cargo.getWeight()+shipWeight>maxWeight) throw new ShipOverweightException("The ship is going to sink!");
        if (!stacks[stack-1].isStackable(cargo)) throw new CargoStrengthException("Stack not strong enough");

        stacks[stack-1].push(cargo);
        shipWeight+=cargo.getWeight();
    }

    /**
     * Method to pop cargo from a particular stack
     *
     * @param stack
     *      the index of the stack to pop from, starts from 1
     * @return
     *      the cargo that was popped from the stack
     * @throws EmptyStackException
     *      when the stack being popped from is already empty
     */
    public Cargo popCargo(int stack) throws EmptyStackException {
        if (stack < 1 || stack > stacks.length) throw new IllegalArgumentException();
        if (stacks[stack-1].isEmpty()) throw new EmptyStackException();

        Cargo c = stacks[stack-1].pop();
        shipWeight-=c.getWeight();
        return c;
    }

    /**
     * Method to see what is at the top of the stack, used to see if any
     * exceptions will be thrown when attempting to push, as if an exception
     * is thrown when pushing, the Cargo object will then be lost
     *
     * @param stack
     *      the stack to check what's on top
     * @return
     *      the Cargo object that's on top of the requested stack
     */
    public Cargo peekCargo(int stack) {
        if (stack < 1 || stack > stacks.length) throw new IllegalArgumentException();
        if (stacks[stack-1].isEmpty()) throw new EmptyStackException();

        return stacks[stack-1].peek();
    }

    /**
     * Method to search for Cargo objects on the ship based on their name
     * and then print out the results in a tabular format
     *
     * @param name
     *      the name of the Cargo object(s) to search for
     * @throws IllegalArgumentException
     *      when the name provided is null
     */
    public void findAndPrint(String name) throws IllegalArgumentException {
        if (name == null) throw new IllegalArgumentException();
        int numMatches = 0;
        int totalWeight = 0;
        System.out.printf("\n%-7s %-7s %-8s %-10s", " Stack", " Depth", " Weight", " Strength");
        System.out.print("\n" + "=".repeat(7) + "+" + "=".repeat(7) + "+" +"=".repeat(8) + "+" + "=".repeat(10));
        for (int i = 0; i < stacks.length; i++) {
            CargoStack temp = new CargoStack();
            int ogStackSize = stacks[i].size();
            for(int j = 0; j < ogStackSize; j++) {
               Cargo c = stacks[i].pop();
               if (c.getName().equals(name)) {
                   System.out.printf("\n%7s|%7s|%8s|%10s", //centerPrint() helper method used heavily
                           centerPrint(7, Integer.toString(i+1)),
                           centerPrint(7, Integer.toString(j)),
                           centerPrint(8, Double.toString(c.getWeight())),
                           centerPrint(10, c.getStrength().toString()));
                   numMatches++;
                   totalWeight+=c.getWeight();
               }
               temp.push(c);
            }
            while (!temp.isEmpty()) {
                stacks[i].push(temp.pop());
            }
        }
        System.out.println("\n");
        System.out.println("Total Count: " + numMatches);
        System.out.println("Total Weight: " + totalWeight);

        if (numMatches == 0) {
            System.out.println("\nCargo " + name + " could not be found on the ship");
        }
    }

    /**
     * Getter method used to get the stacks of the ship (used when printing)
     *
     * @return
     *      the CargoStack array of all the Cargo on the ship
     */
    public CargoStack[] getStacks() {
        return stacks;
    }


    /**
     * Getter method for the weight of the ship (used when printing)
     *
     * @return
     *      the value of the shipWeight field, the weight of the ship
     */
    public double getWeight() {
        return shipWeight;
    }

    /**
     * Getter method for the max weight of the ship (used when printing)
     *
     * @return
     *      the value of the maxWeight field, the maximum weight the ship can hold
     */
    public double getMaxWeight() {
        return maxWeight;
    }

    /**
     * Helper method to center a string for printing by adding the appropriate
     * number of spaces on each side of the string based on the given width of
     * the area the string will be printed in and returning the result, used
     * for centering values when printing the tabular representation of the
     * results of the <CODE>findAndPrint()</CODE> method
     *
     * @param width
     *      the width of the field to center the string in
     * @param s
     *      the String to center
     * @return
     *      the centered String with spaces on each side
     */
    public String centerPrint(int width, String s) {
        int numSpaces = (width - s.length())/2;
        return " ".repeat(numSpaces) + s + " ".repeat(numSpaces);
    }
}