//Shahrizod Bobojonov 114137520 Recitation 1

/**
 * This class represents a two way road that goes into the intersection. It
 * has its own name, maximum green time, left signal green time, and keeps
 * track of its light value as well as a VehicleQueue array which represents
 * the lanes of the road. The lanes of the road are tracked using ways and lanes
 * which are represented using integer constants.
 */
public class TwoWayRoad {
    public static final int FORWARD_WAY = 0;
    public static final int BACKWARD_WAY = 1;
    public static final int NUM_WAYS = 2;
    public static final int LEFT_LANE = 0;
    public static final int MIDDLE_LANE = 1;
    public static final int RIGHT_LANE = 2;
    public static final int NUM_LANES = 3;
    private String name;
    private int greenTime;
    private int leftSignalGreenTime;
    private VehicleQueue[][] lanes = new VehicleQueue[NUM_WAYS][NUM_LANES];
    private LightValue lightValue;
    private boolean keepLeft = false;

    /**
     * Constructor for the TwoWayRoad that takes in a String for the name of
     * the road and an int for the greenTime field. It also initializes the
     * leftSignalGreenTime using a formula respective to greenTime and NUM_LANES,
     * as well as the lanes array using new VehicleQueue objects.
     *
     * @param initName
     *      the name of the road
     * @param initGreenTime
     *      the max time the light should stay green/left signal
     * @throws IllegalArgumentException
     *      if the provided greenTime is <= 0 or the name is null
     */
    public TwoWayRoad(String initName, int initGreenTime) throws IllegalArgumentException {
        if (initGreenTime <= 0 || initName == null) throw new IllegalArgumentException();
        name = initName;
        greenTime = initGreenTime;
        leftSignalGreenTime = (int)((1.0 / NUM_LANES) * greenTime);
        lightValue = LightValue.RED;
        for (int i = 0; i < NUM_WAYS; i++) {
            for (int j = 0; j < NUM_LANES; j++) {
                lanes[i][j] = new VehicleQueue();
            }
        }
    }

    /**
     * Given the current value of the timer in the Intersection class, it
     * determines the correct value of the signal and dequeues the appropriate
     * vehicles to a Vehicle object array
     *
     * @param timerVal
     *      the current value of the timer, used to determine lightValue
     * @return
     *      an array of Vehicle objects that were dequeued
     * @throws IllegalArgumentException
     *      if the provided timerVal is <= 0
     */
    public Vehicle[] proceed(int timerVal) throws IllegalArgumentException {
        if (timerVal <= 0) throw new IllegalArgumentException();
        Vehicle[] vehicles = null;
        if (timerVal <= leftSignalGreenTime || keepLeft) {
            lightValue = LightValue.LEFT_SIGNAL;
            if (isLaneEmpty(FORWARD_WAY, LEFT_LANE) && isLaneEmpty(BACKWARD_WAY, LEFT_LANE)) {
                return null;
            } else if (isLaneEmpty(FORWARD_WAY, LEFT_LANE)) {
                vehicles = new Vehicle[]{lanes[BACKWARD_WAY][LEFT_LANE].dequeue()};
            } else if (isLaneEmpty(BACKWARD_WAY, LEFT_LANE)) {
                vehicles = new Vehicle[]{lanes[FORWARD_WAY][LEFT_LANE].dequeue()};
            } else {
                vehicles = new Vehicle[]{lanes[FORWARD_WAY][LEFT_LANE].dequeue(), lanes[BACKWARD_WAY][LEFT_LANE].dequeue()};
            }
        } else {
            lightValue = LightValue.GREEN;
            vehicles = new Vehicle[(NUM_LANES-1)*NUM_WAYS];
            for (int i = 0; i < (NUM_LANES-1)*NUM_WAYS; i++) {
                if (i/2==0) {
                    if (!lanes[FORWARD_WAY][i+1].isEmpty())
                        vehicles[i] = lanes[FORWARD_WAY][i+1].dequeue();
                } else {
                    if (!lanes[BACKWARD_WAY][i-1].isEmpty())
                        vehicles[i] = lanes[BACKWARD_WAY][i-1].dequeue();
                }
            }
        }
        return vehicles;
    }

    /**
     * Enqueues a given vehicle onto the appropriate lane, which is provided in
     * the parameters. If all the preconditions are met, then it just calls the
     * enqueue() method for the VehicleQueue object of the appropriate lane
     *
     * @param wayIndex
     *      the way of the lane, either FORWARD_WAY or BACKWARD_WAY
     * @param laneIndex
     *      the lane of the way, either LEFT_LANE, MIDDLE_LANE, or RIGHT_LANE
     * @param vehicle
     *      the Vehicle object to enqueue onto the given way and lane of the road
     * @throws IllegalArgumentException
     *      if the wayIndex or laneIndex is outside the valid ranges or if the
     *      given vehicle is null
     */
    public void enqueueVehicle(int wayIndex, int laneIndex, Vehicle vehicle) throws IllegalArgumentException {
        if (wayIndex > 1 || wayIndex < 0 || laneIndex < 0 || laneIndex > 2 || vehicle==null)
            throw new IllegalArgumentException();
        lanes[wayIndex][laneIndex].enqueue(vehicle);
    }

    /**
     * Checks whether a given lane for a given way is empty by running the
     * isEmpty() method on the VehicleQueue stored for that lane
     *
     * @param wayIndex
     *      the way of the lane, either FORWARD_WAY or BACKWARD_WAY
     * @param laneIndex
     *      the lane of the way, either LEFT_LANE, MIDDLE_LANE, or RIGHT_LANE
     * @return
     *      a boolean value representing if the lane is empty or not *
     * @throws IllegalArgumentException
     *      if the wayIndex or laneIndex is outside the valid ranges
     */
    public boolean isLaneEmpty(int wayIndex, int laneIndex) throws IllegalArgumentException {
        if (wayIndex > 1 || wayIndex < 0 || laneIndex < 0 || laneIndex > 2)
            throw new IllegalArgumentException();
        return lanes[wayIndex][laneIndex].isEmpty();
    }

    /**
     * Changes the light to the provided LightValue enum parameter
     *
     * @param lightVal
     *      the LightValue enum to change the light of the object to
     */
    public void changeLight(LightValue lightVal) {
        lightValue = lightVal;
    }

    /**
     * Changes whether the light should keep the left signal on or not instead
     * of the regular green light
     *
     * @param val
     *      a boolean value indicating whether the left signal has priority
     */
    public void setKeepLeft(boolean val) {
        keepLeft = val;
    }

    /**
     * Tells the calling method (checkLights) if the left lane is empty when
     * the left signal should be on so that it can preempt and switch roads
     *
     * @param timerVal
     *      the value of the countdownTimer
     * @return
     *      whether the light should be on but the left lanes are empty
     */
    public boolean leftEmptySwitch(int timerVal) {
        return ((timerVal <= leftSignalGreenTime || keepLeft) && lanes[FORWARD_WAY][LEFT_LANE].isEmpty() && lanes[BACKWARD_WAY][LEFT_LANE].isEmpty());
    }

    /**
     * Accessor method for the greenTime of the road
     *
     * @return
     *      the greenTime field of the TwoWayRoad object
     */
    public int getGreenTime() {
        return greenTime;
    }

    /**
     * Accessor method for the time that the signal changes to left signal
     *
     * @return
     *      the leftSignalGreenTime field of the TwoWayRoad object
     */
    public int getLeftSignalGreenTime() {
        return leftSignalGreenTime;
    }

    /**
     * Accessor method for the current light value
     *
     * @return
     *      the lightValue field of the TwoWayRoad object
     */
    public LightValue getLightValue() {
        return lightValue;
    }

    /**
     * Accessor method for the name of the road
     *
     * @return
     *      the name field of the TwoWayRoad object
     */
    public String getName() {
        return name;
    }

    /**
     * Checks each lane for each way to see if it's empty and if so returns true
     *
     * @return
     *      if the TwoWayRoad is empty
     */
    public boolean isEmpty() {
        for (int i = 0; i < NUM_WAYS; i++) {
            for (int j = 0; j < NUM_LANES; j++) {
                if (!lanes[i][j].isEmpty()) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Helper method for the toString() method which returns the queue as a String
     * either forwards or backwards depending on the way
     *
     * @param wayIndex
     *      the way of the lane, either FORWARD_WAY or BACKWARD_WAY
     * @param laneIndex
     *      the lane of the way, either LEFT_LANE, MIDDLE_LANE, or RIGHT_LANE
     * @return
     *      a String appropriately representing the VehicleQueue of the lane
     */
    public String qString(int wayIndex, int laneIndex) {
        String q = "";
        for (int i = 0; i < lanes[wayIndex][laneIndex].size(); i++) {
            if (wayIndex == FORWARD_WAY) {
                q+=lanes[wayIndex][laneIndex].get(lanes[wayIndex][laneIndex].size()-1-i);
            } else {
                q+=lanes[wayIndex][laneIndex].get(i);
            }
        }
        return q;
    }

    /**
     * Overrides the toString() method and prints the name of the road followed
     * by a simple view of the road and the cars in each lane for each way
     *
     * @return
     *      a String object representing the name of the road followed by the
     *      road and the vehicles in each lane in an easy-to-view format
     */
    @Override
    public String toString() {
        String answer = "\n" + name + ":\n" +
                String.format("%30s%15s%-30s", "FORWARD", " ".repeat(15), "BACKWARD") + "\n" +
                "=".repeat(30) + " ".repeat(15) + "=".repeat(30) + "\n";
        for (int i = 0; i < NUM_LANES; i++) {
            String firstQ = qString(0, i);
            String mid = "";
            switch (i) {
                case LEFT_LANE:
                    mid += " [L] " + (lightValue==LightValue.LEFT_SIGNAL ? " ":"x") +
                            "   " + (lightValue==LightValue.GREEN ? " ":"x") + " [R] ";
                    break;
                case MIDDLE_LANE:
                    mid += " [M] " + (lightValue==LightValue.GREEN ? " ":"x") +
                            "   " + (lightValue==LightValue.GREEN ? " ":"x") + " [M] ";
                    break;
                case RIGHT_LANE:
                    mid += " [R] " + (lightValue==LightValue.GREEN ? " ":"x") +
                            "   " + (lightValue==LightValue.LEFT_SIGNAL ? " ":"x") + " [L] ";
                    break;
            }
            String secondQ = qString(1, NUM_LANES-i-1);
            answer += String.format("%30s%15s%-30s", firstQ, mid, secondQ);
            answer += "\n";
            if (i==NUM_LANES-1)
                answer += "=".repeat(30) + " ".repeat(15) + "=".repeat(30);
            else
                answer += "-".repeat(30) + " ".repeat(15) + "-".repeat(30) + "\n";
        }
        answer = answer.indent(4);
        return answer;
    }


}
