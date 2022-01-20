//Shahrizod Bobojonov 114137520 Recitation 1

import java.util.Scanner; //to take in user input

/**
 * This class stores the main method and simulate method which brings together
 * everything from the other classes to create the simulation. The main method
 * takes in inputs, either from the commandline at execution or through Scanner
 * once the program is started. It then processes all the inputs to make sure
 * they are in the valid range before passing it to simulate which will construct
 * the necessary objects and run the whole simulation, iterating timesteps and
 * printing along the way. If the main method finds any inputs that don't fall
 * within the correct range, it continuously prompts the user to enter new values
 * until they fall within the valid ranges.
 *
 * @author Shahrizod Bobojonov
 */
public class IntersectionSimulator {
    public static void main(String[] args) {
        int simTime;
        double prob;
        int numRoads;
        String[] names;
        int[] times;
        System.out.println("Welcome to IntersectionSimulator 2021\n");
        if (args.length>1) {
            simTime = Integer.parseInt(args[0]);
            prob = Double.parseDouble(args[1]);
            numRoads = Integer.parseInt(args[2]);
            names = new String[numRoads];
            times = new int[numRoads];
            for (int i = 0; i < numRoads; i++) {
                names[i] = args[3 + i];
                times[i] = Integer.parseInt(args[3 + numRoads + i]);
            }
            if (simTime < 1 || prob <= 0 || prob > 1 || numRoads < 2 || numRoads > 4) {
                System.out.println("=".repeat(36));
                System.out.println("Invalid arguments provided, goodbye!");
                System.out.println("=".repeat(36));
                System.exit(-1);
            }
        } else {
            Scanner stdin = new Scanner(System.in);
            System.out.print("Input the simulation time: ");
            simTime = stdin.nextInt();
            while (simTime < 1) {
                System.out.println("Invalid simulation time.");
                System.out.print("Input the simulation time: ");
                simTime = stdin.nextInt();
            }
            System.out.print("Input the arrival probability: ");
            prob = stdin.nextDouble();
            while (prob <= 0 || prob > 1) {
                System.out.println("Invalid probability.");
                System.out.print("Input the arrival probability: ");
                prob = stdin.nextDouble();
            }
            System.out.print("Input number of streets: ");
            numRoads = stdin.nextInt();
            while (numRoads < 2) {
                System.out.println("Invalid number.");
                System.out.print("Input number of streets: ");
                numRoads = stdin.nextInt();
            }
            names = new String[numRoads];
            times = new int[numRoads];
            stdin.nextLine(); //clear Scanner buffer
            for (int i = 0; i < numRoads; i++) {
                System.out.print("Input Street " + (i + 1) + " name: ");
                String tempName = stdin.nextLine();
                while (tempName.equals("")) {
                    System.out.println("Empty string detected.");
                    System.out.print("Input Street " + (i + 1) + " name: ");
                    tempName = stdin.nextLine();
                }
                for (int j = 0; j < i; j++) {
                    while (names[j].equals(tempName)) {
                        System.out.print("Duplicate Detected.");
                        System.out.print("Input Street " + (i + 1) + " name: ");
                        tempName = stdin.nextLine();
                    }
                }
                names[i] = tempName;
            }
            for (int i = 0; i < numRoads; i++) {
                System.out.print("Input max green time for " + names[i] + ": ");
                int tempTime = stdin.nextInt();
                while (tempTime <= 1) {
                    System.out.println("Max time too low.");
                    System.out.print("Input max green time for " + names[i] + ": ");
                    tempTime = stdin.nextInt();
                }
                times[i] = tempTime;
                stdin.nextLine(); //clear buffer
            }
        }
        System.out.println("\nStarting simulation...");
        try {
            simulate(simTime, prob, names, times);
        } catch (IllegalArgumentException e) {
            System.out.println("=".repeat(36));
            System.out.println("Invalid arguments provided, goodbye!");
            System.out.println("=".repeat(36));
        }
    }

    /**
     * This method runs the whole simulation by taking in the parameters we
     * get and check for in the main method and then initializing the
     * BooleanSource, TwoWayRoad, and Intersection objects. It then initializes
     * the relevant stats to 0 and tracks them along the way. From there, it
     * enqueues cars until simulationTime is reached (which is found by tracking
     * the number of timeSteps) and executing timeStep, printing, and changing
     * the lights after. Once simulationTime is reached, cars stop arriving, and
     * the program continues until no cars are left in any lanes
     *
     * @param simulationTime
     *      the number of timesteps vehicles should still be arriving for
     * @param arrivalProbability
     *      the probability of a vehicle arriving per lane
     * @param roadNames
     *      the names of each road, used to create TwoWayRoad objects and printing
     * @param maxGreenTimes
     *      the amount of the time the light should be green for, for each road
     * @throws IllegalArgumentException
     *      if the constructors receive an input that is out of the valid range
     */
    public static void simulate(int simulationTime, double arrivalProbability, String[] roadNames, int[] maxGreenTimes) {
        TwoWayRoad[] roads = new TwoWayRoad[roadNames.length];
        for (int i = 0; i < roadNames.length; i++) {
            roads[i] = new TwoWayRoad(roadNames[i], maxGreenTimes[i]);
        }
        Intersection intersection = new Intersection(roads);
        BooleanSource gen = new BooleanSource(arrivalProbability);
        int carsWaiting = 0;
        int carsPassed = 0;
        int waitTime = 0;
        int maxWait = 0;
        double avgWait = 0;
        while ((Intersection.timeStepCount) <= simulationTime) {
            String arriving = "\n\tARRIVING CARS:";
            int numGen = 0;
            for (int i = 0; i < roadNames.length * TwoWayRoad.NUM_WAYS * TwoWayRoad.NUM_LANES; i++) {
                if (numGen >= 6) break;
                if (gen.occurs()) {
                    numGen++;
                    carsWaiting++;
                    Vehicle newCar = new Vehicle(Intersection.timeStepCount);
                    arriving += "\n\t\tCar" + newCar + " entered " + roadNames[i/6] + ", going " +
                            (((i / 3) - (2*(i / 6))) == TwoWayRoad.FORWARD_WAY ? "FORWARD" : "BACKWARD") +
                            " in " + (i%3 == TwoWayRoad.LEFT_LANE ? "LEFT" :
                            (i%3 == TwoWayRoad.RIGHT_LANE ? "RIGHT" : "MIDDLE")) +
                            " lane.";
                    intersection.enqueueVehicle(i / 6, (i / 3) - (2*(i / 6)), i % 3, newCar);
                }
            }
            Vehicle[] re = intersection.timeStep();
            System.out.println(arriving);
            System.out.println("\n\tPASSING CARS:");
            try {
                for (int i = 0; i < re.length; i++) {
                    if (re[i] != null) {
                        System.out.println("\t\tCar" + re[i] + " passes through. Wait time of " +
                                (Intersection.timeStepCount - 1 - re[i].getTimeArrived()) + ".");

                        carsWaiting--;
                        carsPassed++;
                        waitTime += Intersection.timeStepCount - 1 - re[i].getTimeArrived();
                        if ((Intersection.timeStepCount - 1 - re[i].getTimeArrived()) > maxWait)
                            maxWait = Intersection.timeStepCount - 1 - re[i].getTimeArrived();
                        avgWait = (double)waitTime / (double)carsPassed;
                    }
                }
            } catch (NullPointerException e) {
                System.out.println("\n\t\tNo cars passed");
            }
            intersection.display();
            System.out.println("\n\tSTATISTICS:" +
                    String.format("%-30s%15s","\n\t\tCars currently waiting: ", carsWaiting + " cars") +
                    String.format("%-30s%15s", "\n\t\tTotal cars passed: ", carsPassed + " cars") +
                    String.format("%-30s%15s", "\n\t\tTotal wait time: ", waitTime + " turns") +
                    String.format("%-30s%15s", "\n\t\tAverage wait time: ", String.format("%.2f",avgWait) + " turns")
            );

            intersection.checkLights();
        }
        while (!intersection.isEmpty()) {
            Vehicle[] re = intersection.timeStep();
            System.out.println("\n\tPASSING CARS:");
            try {
                for (Vehicle vehicle : re) {
                    if (vehicle != null) {
                        System.out.println("\t\tCar" + vehicle + " passes through. Wait time of " +
                                (Intersection.timeStepCount - 1 - vehicle.getTimeArrived()) + ".");

                        carsWaiting--;
                        carsPassed++;
                        waitTime += Intersection.timeStepCount - 1 - vehicle.getTimeArrived();
                        if ((Intersection.timeStepCount - 1 - vehicle.getTimeArrived()) > maxWait)
                            maxWait = Intersection.timeStepCount - 1 - vehicle.getTimeArrived();
                        avgWait = (double) waitTime / (double) carsPassed;
                    }
                }
            } catch (NullPointerException e) { //when intersection returns null instead of vehicle array
                System.out.println("\n\t\tNo cars passed");
            }
            intersection.display();
            System.out.println("\n\tSTATISTICS:" +
                    String.format("%-30s%15s","\n\t\tCars currently waiting: ", carsWaiting + " cars") +
                    String.format("%-30s%15s", "\n\t\tTotal cars passed: ", carsPassed + " cars") +
                    String.format("%-30s%15s", "\n\t\tTotal wait time: ", waitTime + " turns") +
                    String.format("%-30s%15s", "\n\t\tAverage wait time: ", String.format("%.2f",avgWait) + " turns")
            );
            intersection.checkLights();
        }
        System.out.println("\n\n" + "#".repeat(80) + "\n" + "#".repeat(80) + "\n" + "#".repeat(80) +
                            "\n\nSIMULATION SUMMARY:\n" +
                            String.format("%-22s%15s", "\n\tTotal Time: ", (Intersection.timeStepCount-1) + " steps") +
                            String.format("%-22s%15s", "\n\tTotal Vehicles: ", carsPassed + " vehicles") +
                            String.format("%-22s%15s", "\n\tLongest wait time: ", maxWait + " turns") +
                            String.format("%-22s%15s", "\n\tTotal wait time: ", waitTime + " turns") +
                            String.format("%-22s%15s", "\n\tAverage wait time: ", String.format("%.2f",avgWait) + " turns") +
                "\n\nEnd simulation.");
    }
}
