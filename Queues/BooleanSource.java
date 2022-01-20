//Shahrizod Bobojonov 114137520 Recitation 1

/**
 * This is the class used to figure out if a Vehicle is added to each lane or not
 * per timestep. It uses Math.random() and a provided probability to randomly
 * generate numbers and determines if it meets the given probability.
 */
public class BooleanSource {
    private double probability;

    /**
     * Constructor for BooleanSource object that takes in a value in the range
     * (0,1] and then initializes the probability field to that value. Throws
     * IllegalArgumentException if given probability is out of range
     *
     * @param initProbability
     *      user provided probability to check if met
     * @throws IllegalArgumentException
     *      if the given probability is <= 0 or >1
     */
    public BooleanSource(double initProbability) throws IllegalArgumentException {
        if (initProbability <= 0 || initProbability > 1) throw new IllegalArgumentException();
        probability = initProbability;
    }

    /**
     * Uses Math.random() and a given probability to determine whether something
     * happens or not, returns a boolean value indicating that
     *
     * @return
     *      boolean value indicating if probability is met, used to determine
     *      if Vehicle object enqueued
     */
    public boolean occurs() {
        return Math.random()<=probability;
    }
}
