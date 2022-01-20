//Shahrizod Bobojonov 114137520 Recitation 1

import java.util.EmptyStackException; //thrown when trying to pop from empty stack
import java.util.InputMismatchException; //Scanner exception when incorrect input is given
import java.util.Scanner; //Scanner class needed to get user input

/**
 * Test method for all the methods created in the Cargo, CargoStack, and CargoShip
 * classes. Creates a CargoShip based on user input and then provides a menu
 * of options to choose from to interact with the CargoShip/CargoStack/Cargo
 * objects created.
 */
public class ShipLoader {
    public static void main(String[] args) {
        Scanner stdin = new Scanner(System.in);
        boolean isOngoing = true;
        System.out.println("Welcome to ShipLoader!");
        CargoShip ship = shipBuilder(stdin);
        System.out.println("\nCargo ship created.\n" +
                            "Pulling ship in to dock...\n" +
                            "Cargo ship ready to be loaded.");
        CargoStack dock = new CargoStack();
        stdin.nextLine(); //clear input
        while (isOngoing) {
            System.out.print("\nPlease select an option: \n" +
                    "C) Create new cargo \n" +
                    "L) Load cargo from dock \n" +
                    "U) Unload cargo from ship \n" +
                    "M) Move cargo between stacks \n" +
                    "K) Clear dock \n" +
                    "P) Print ship stacks \n" +
                    "S) Search for cargo \n" +
                    "Q) Quit \n\n" +
                    "Select a menu option: ");
            try {
                String strChoice = stdin.nextLine(); //clear existing input
                if (strChoice.length() != 1) throw new IllegalArgumentException();
                char choice = Character.toUpperCase(strChoice.charAt(0));

                if (choice == 'C') {
                    System.out.println();
                    System.out.print("Enter the name of the cargo: ");
                    String name = stdin.nextLine();
                    System.out.print("Enter the weight of the cargo: ");
                    double weight = stdin.nextDouble();
                    stdin.nextLine(); //clear input
                    System.out.print("Enter the container strength (F/M/S): ");
                    String strStrength = stdin.nextLine();
                    if (strStrength.length() != 1) throw new IllegalArgumentException();
                    char charStrength = Character.toUpperCase(strStrength.charAt(0));
                    CargoStrength strength;
                    if (charStrength == 'F') {
                        strength = CargoStrength.FRAGILE;
                    } else if (charStrength == 'M') {
                        strength = CargoStrength.MODERATE;
                    } else if (charStrength == 'S') {
                        strength = CargoStrength.STURDY;
                    } else {
                        throw new IllegalArgumentException();
                    }
                    Cargo c = new Cargo(name, weight, strength);
                    if (dock.isStackable(c)) {
                        dock.push(c);
                    } else {
                        throw new CargoStrengthException("Not strong enough");
                    }
                    System.out.println("Cargo '" + name + "' pushed onto the dock.");
                    printEverything(ship, dock);

                } else if (choice == 'L') {
                    if (dock.isEmpty()) throw new EmptyStackException();
                    System.out.print("\nSelect the load destination stack index: ");
                    int dstIndex = stdin.nextInt();
                    stdin.nextLine(); //clear input
                    Cargo c = dock.peek();
                    ship.pushCargo(c, dstIndex); //might throw ShipOverweightException
                    dock.pop(); //pop after to make sure that no exceptions were thrown
                    System.out.println("Cargo '" + c.getName() + "' moved from dock to stack " + dstIndex);
                    printEverything(ship, dock);
                } else if (choice == 'U') {
                    System.out.print("\nSelect the unload source stack index: ");
                    int srcIndex = stdin.nextInt();
                    stdin.nextLine(); //clear input
                    Cargo c = ship.peekCargo(srcIndex);
                    if (!dock.isStackable(c)) throw new CargoStrengthException("");
                    dock.push(c);
                    ship.popCargo(srcIndex);
                    System.out.println("Cargo '" + c.getName() + "' moved from stack " + srcIndex + " to the dock");
                    printEverything(ship, dock);
                } else if (choice == 'M') {
                    System.out.print("\nSelect the source stack index: ");
                    int srcIndex = stdin.nextInt();
                    stdin.nextLine(); //clear input
                    Cargo c = ship.peekCargo(srcIndex);
                    System.out.print("Select the destination stack index: ");
                    int dstIndex = stdin.nextInt();
                    stdin.nextLine(); //clear input
                    ship.pushCargo(c, dstIndex);
                    ship.popCargo(srcIndex);
                    System.out.println("Cargo '" + c.getName() + "' moved from stack " +  srcIndex + " to stack " + dstIndex);
                    printEverything(ship, dock);
                } else if (choice == 'K') {
                    dock = new CargoStack();
                    System.out.println("\nDock cleared.");
                    printEverything(ship, dock);
                } else if (choice == 'P') {
                    printEverything(ship, dock);
                } else if (choice == 'S') {
                    System.out.print("Enter the name of the cargo: ");
                    String cargoName = stdin.nextLine();
                    ship.findAndPrint(cargoName);
                } else if (choice == 'Q') {
                    isOngoing = false;
                } else {
                    throw new IllegalArgumentException();
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid input!");
            } catch (InputMismatchException e) {
                System.out.println("Invalid input type");
            } catch (FullStackException e) {
                System.out.println("Can't load this, the stack's too tall!");
            } catch (EmptyStackException e) {
                System.out.println("Stack is empty");
            } catch (ShipOverweightException e) {
                System.out.println("Can't load this, the ship's going to sink!");
            } catch (CargoStrengthException e) {
                System.out.println("Can't load this, it'll crush the cargo underneath");
            }
        }
        System.out.println("Terminating program, goodbye!");
    }

    /**
     * Method to build a CargoShip object, continually asking for new parameters
     * until a ship is built that meets the parameters (returns a CargoShip object)
     *
     * @param stdin
     *      the Scanner object created in the main method to use everywhere
     * @return
     *      the CargoShip object that was eventually created
     * @throws IllegalArgumentException
     *      when the number of stacks, maximum height, or maximum weight provided
     *      by the user is <= 0
     */
    public static CargoShip shipBuilder(Scanner stdin) throws IllegalArgumentException {
        while (true) {
            System.out.println("\nCargo Ship Parameters\n" + "-".repeat(50));
            try {
                System.out.print("Number of stacks: ");
                int numStacks = stdin.nextInt();
                if (numStacks <= 0) throw new IllegalArgumentException();
                System.out.print("Maximum height of stacks: ");
                int maxHeight = stdin.nextInt();
                if (maxHeight <= 0) throw new IllegalArgumentException();
                System.out.print("Maximum total cargo weight: ");
                double maxWeight = stdin.nextDouble();
                if (maxWeight <= 0) throw new IllegalArgumentException();
                return new CargoShip(numStacks, maxHeight, maxWeight);
            } catch (IllegalArgumentException e) {
                System.out.println("That's not a valid input, please try again!");
            }
        }
    }

    /**
     * Method to print the stacks on the ship and dock side by side with the
     * weight of the ship out of the max weight allowed also shown underneath
     *
     * @param ship
     *      the CargoShip object whose stacks should be printed
     * @param dock
     *      the dock object whose stack should be printed
     */
    public static void printEverything(CargoShip ship, CargoStack dock) {
        CargoStack[] stacks = ship.getStacks();
        CargoStack[] tempStacks = new CargoStack[stacks.length+1]; //temporary stack array to use to traverse stack and print
        System.out.println();
        int maxStack = 0;
        for (int i = 0; i < stacks.length; i++) {
            if (stacks[i].size() > maxStack) {
                maxStack = stacks[i].size();
            }
            tempStacks[i] = new CargoStack();
        }
        tempStacks[stacks.length] = new CargoStack(); //temporary dock stack
        maxStack = Math.max(dock.size(), maxStack);
        String out = "";
        for (int j = maxStack; j > 0; j--) {
            out+="   ";
            for (int i = 0; i < stacks.length+1; i++) {
                CargoStack currStack;
                if (i == stacks.length) {
                    currStack = dock;
                    out+=" ".repeat(12);
                } else {
                    currStack = stacks[i];
                }
                if (currStack.size() < j) {
                    out += " ".repeat(5);
                } else {
                    Cargo c = currStack.pop();
                    out += "  " + c.toString() + "  ";
                    tempStacks[i].push(c);
                }
                out+=" ";
            }
            out+="\n";
        }
        out+="\\=|";
        for (int i = 0; i < stacks.length; i++) {
            while (!tempStacks[i].isEmpty()) {
                stacks[i].push(tempStacks[i].pop());
            }
            out+=("=".repeat(5)+"|");
        }
        while (!tempStacks[stacks.length].isEmpty()) {
            dock.push(tempStacks[stacks.length].pop());
        }
        out+="=/   Dock: |=====|\n \\   ";
        for (int i = 0; i < stacks.length; i++) {
            if (i != stacks.length-1) {
                if (i+1>=10) { //to account for double digit numbers offsetting print
                    out += (i + 1) + " ".repeat(4);
                } else {
                    out += (i + 1) + " ".repeat(5);
                }
            } else {
                if (i+1>=10) { //to account for double digit numbers offsetting print
                    out += (i + 1) + "  /";
                } else {
                    out += (i + 1) + "   /";
                }
            }
        }
        out+=" ".repeat(10)+"|=====|\n  \\" + "-".repeat(stacks.length*5+stacks.length-1) + "/" + " ".repeat(11)+"|=====|";
        System.out.println(out);
        System.out.println("\nTotal Weight: " + ship.getWeight() + " / " + ship.getMaxWeight());
    }
}
