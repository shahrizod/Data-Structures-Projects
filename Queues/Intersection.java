//Shahrizod Bobojonov 114137520 Recitation 1

/**
 *  This class represents the Intersection, combining multiple TwoWayRoad objects
 *  to create it, and switching between the roads based on the logic given regarding
 *  the countdownTimer and lightValues of each road
 */
public class Intersection {
    private final int MAX_ROADS = 4;
    private TwoWayRoad[] roads;
    private int lightIndex;
    private int countdownTimer;
    public static int timeStepCount = 1; //easy to universally get the # of timeSteps

    /**
     * The constructor for the Intersection class which takes in an array of
     * TwoWayRoad objects to initialize the roads field to, as well as
     * initializing the lightIndex and countdownTimer to the first road
     *
     * @param initRoads
     *      an array of initialized TwoWayRoad objects
     * @throws IllegalArgumentException
     *      if the parameter is null or has a length greater than MAX_ROADS or
     *      if any value in the array is null
     */
    public Intersection(TwoWayRoad[] initRoads) throws IllegalArgumentException {
        if (initRoads == null || initRoads.length > MAX_ROADS) throw new IllegalArgumentException();
        for (TwoWayRoad initRoad : initRoads) {
            if (initRoad == null) throw new IllegalArgumentException();
        }
        roads = initRoads;
        lightIndex = 0;
        countdownTimer = roads[lightIndex].getGreenTime();
    }

    /**
     * Runs a single timeStep iteration, dequeueing the appropriate vehicles,
     * changing the light if the countdownTimer is at 0, and printing the status
     * of the intersection before iterating the countdownTimer and timeStepCount
     * and returning a Vehicle array of the dequeued vehicles
     *
     * @return
     *      an array of Vehicle objects that were dequeued
     */
    public Vehicle[] timeStep() {
        if (countdownTimer == 0) {
            roads[lightIndex].changeLight(LightValue.RED);
            roads[lightIndex].setKeepLeft(false);
            lightIndex = ++lightIndex%roads.length;
            countdownTimer = roads[lightIndex].getGreenTime();
        }
        Vehicle[] re = roads[lightIndex].proceed(countdownTimer);
        System.out.println("\n" + "#".repeat(80) + "\n\nTime Step: " + timeStepCount + "\n\n\t" +
                (roads[lightIndex].getLightValue() == LightValue.GREEN ? "Green" : "Left Signal") +
                " Light for " + roads[lightIndex].getName() + ".\n\tTimer = " +
                countdownTimer);
        countdownTimer--;
        timeStepCount++;
        return re;
    }

    /**
     * Enqueues a given Vehicle object to the given way/lane of the given road
     *
     * @param roadIndex
     *      the index of the road to enqueue the vehicle to
     * @param wayIndex
     *      the way of the lane, either FORWARD_WAY or BACKWARD_WAY
     * @param laneIndex
     *      the lane of the way, either LEFT_LANE, MIDDLE_LANE, or RIGHT_LANE
     * @param vehicle
     *      the vehicle to enqueue to the correct way/lane of the road
     * @throws IllegalArgumentException
     *      if the vehicle is null or the conditions for any of the indices
     *      are not met, such as being >0 or greater than the available number
     */
    public void enqueueVehicle(int roadIndex, int wayIndex, int laneIndex, Vehicle vehicle) throws IllegalArgumentException {
        if (vehicle == null || roadIndex < 0 || roadIndex >= roads.length ||
                wayIndex < 0 || wayIndex >= TwoWayRoad.NUM_WAYS ||
                laneIndex < 0 || laneIndex >= TwoWayRoad.NUM_LANES)
            throw new IllegalArgumentException();
        roads[roadIndex].enqueueVehicle(wayIndex, laneIndex, vehicle);
    }

    /**
     * Prints each TwoWayRoad object stored in the roads array with a space
     * in between them
     */
    public void display() {
        for (TwoWayRoad road : roads) {
            System.out.println();
            System.out.println(road);
        }
    }

    /**
     * Helper method to check if any preemptive conditions for changing the
     * light are met so that the lights, index, and other values can be set
     * to the correct value before beginning the next timeStep
     */
    public void checkLights() {
        if (roads[lightIndex].isEmpty() || roads[lightIndex].leftEmptySwitch(countdownTimer)) {
            roads[lightIndex].changeLight(LightValue.RED);
            roads[lightIndex].setKeepLeft(false);
            lightIndex = ++lightIndex%roads.length;
            countdownTimer = roads[lightIndex].getGreenTime();
        }
        if (roads[lightIndex].isLaneEmpty(TwoWayRoad.FORWARD_WAY,TwoWayRoad.MIDDLE_LANE)
                && roads[lightIndex].isLaneEmpty(TwoWayRoad.FORWARD_WAY,TwoWayRoad.RIGHT_LANE)
                && roads[lightIndex].isLaneEmpty(TwoWayRoad.BACKWARD_WAY,TwoWayRoad.MIDDLE_LANE)
                && roads[lightIndex].isLaneEmpty(TwoWayRoad.BACKWARD_WAY,TwoWayRoad.RIGHT_LANE))
            roads[lightIndex].setKeepLeft(true);
    }

    /**
     * Checks whether the intersection is empty by checking all the roads in
     * the intersection using the TwoWayRoad isEmpty() method
     *
     * @return
     *      a boolean value representing if the whole intersection has no vehicles
     */
    public boolean isEmpty() {
        for (int i = 0; i < roads.length; i++) {
            if (!roads[i].isEmpty()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Accessor method for the number of roads in the intersection
     *
     * @return
     *      the length of the roads array
     */
    public int getNumRoads() {
        return roads.length;
    }

    /**
     * Accessor method for the index of the active road
     *
     * @return
     *      the lightIndex field of the Intersection object
     */
    public int getLightIndex() {
        return lightIndex;
    }

    /**
     * Accessor method for the countdown timer
     *
     * @return
     *      the countdownTimer field of the Intersection object
     */
    public int getCountdownTimer() {
        return countdownTimer;
    }

    /**
     * Accessor method for the current light value of the active road
     *
     * @return
     *      the lightValue field for the active road
     */
    public LightValue getCurrentLightValue() {
        return roads[lightIndex].getLightValue();
    }
}
