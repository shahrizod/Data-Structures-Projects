//Shahrizod Bobojonov 114137520 Recitation 1

/**
 * This class creates Vehicle objects to be put into the queue aka the lanes
 * at the lights. Each vehicle has a serialId and timeArrived attached to it
 * as well as the class itself having a static serialCounter which allows us
 * to iterate the serialId for each vehicle
 */
public class Vehicle {
    private static int serialCounter = 0;
    private int serialId;
    private int timeArrived;

    /**
     * Constructor for a Vehicle object that takes an int value representing
     * the timeArrived as its only parameter. Typically, this timeArrived is
     * the value of the timeStep in the Intersection class. If the given
     * timeArrived is <= 0, then an error is thrown
     *
     * @param initTimeArrived
     *      the time that the car arrived at the light
     * @throws IllegalArgumentException
     *      if the given parameter for timeArrived is <= 0
     */
    public Vehicle(int initTimeArrived) throws IllegalArgumentException {
        if (initTimeArrived <= 0) throw new IllegalArgumentException();
        serialCounter++;
        serialId = serialCounter;
        timeArrived = initTimeArrived;
    }

    /**
     * Accessor method for serialId of vehicle
     *
     * @return
     *      serialId of Vehicle object
     */
    public int getSerialId() {
        return serialId;
    }

    /**
     * Accessor method for timeArrived of vehicle, used to determine wait time
     * in IntersectionSimulator
     *
     * @return
     *      timeArrived of Vehicle object
     */
    public int getTimeArrived() {
        return timeArrived;
    }

    /**
     * When printing a vehicle, it's printed in the format [serialId] with 0
     * padding making it 3 digits long
     *
     * @return
     *      a String representing the Vehicle as its formatted serialId
     */
    @Override
    public String toString() {
        return "[" + String.format("%03d",serialId) + "]";
    }
}
