//Shahrizod Bobojonov 114137520 Recitation 1

import java.util.LinkedList; //use LinkedList to implement the Queue

/**
 * VehicleQueue class to use for each lane of road, creating used LinkedList API
 */
public class VehicleQueue extends LinkedList<Vehicle> {

    /**
     * Constructor for the queue which just calls the LinkedList constructor
     */
    VehicleQueue() {
        super();
    }

    /**
     * Uses the super LinkedList add method to easily enqueue by adding
     * to the head of the LinkedList
     *
     * @param v
     *      the Vehicle object to add to the LinkedList and therefore enqueue
     */
    void enqueue(Vehicle v) {
        super.add(v);
    }

    /**
     * Uses the super LinkedList remove method to easily dequeue by removing
     * from the tail of the LinkedList
     *
     * @return
     *      the Vehicle object removed from the queue
     */
    Vehicle dequeue() {
        return super.remove();
    }

    //also inherits size() and isEmpty() methods from LinkedList
}
