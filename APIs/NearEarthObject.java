//Shahrizod Bobojonov 114137520 Recitation 1

import java.util.Date;

/**
 * This class represents a Near Earth Object as provided by the NASA API
 * and contains many relevant statistics of the NEO, such as its name,
 * magnitude, diameter, and others.
 */
public class NearEarthObject {
    private int referenceId;
    private String name;
    private double absoluteMagnitude;
    private double averageDiameter;
    private boolean isDangerous;
    private Date closestApproachDate;
    private double missDistance;
    private String orbitingBody;

    /**
     * Constructor for an NEO object using several arguments which are provided
     * by the NASA API
     *
     * @param referenceId
     *      integer value representing the unique ID of the NEO
     * @param name
     *      String value representing the name of the NEO
     * @param absoluteMagnitude
     *      double value representing the absolute brightness of the NEO
     * @param minDiameter
     *      double value representing the minimum diameter of the NEO in kilometers
     * @param maxDiameter
     *      double value representing the maximum diameter of the NEO in kilometers
     * @param isDangerous
     *      boolean value representing if the NEO poses a threat or not
     * @param closestDateTimeStamp
     *      long value representing the Unix timestamp of a date of closest approach
     * @param missDistance
     *      double value representing the distance that the NEO missed Earth by on the provided closestDateTimestamp
     * @param orbitingBody
     *      String value representing what the NEO was orbiting
     */
    public NearEarthObject(int referenceId, String name, double absoluteMagnitude, double minDiameter, double maxDiameter,
                           boolean isDangerous, long closestDateTimeStamp, double missDistance, String orbitingBody) {
        this.referenceId = referenceId;
        this.name = name;
        this.absoluteMagnitude = absoluteMagnitude;
        this.averageDiameter = (minDiameter + maxDiameter)/2;
        this.isDangerous = isDangerous;
        this.closestApproachDate = new Date(closestDateTimeStamp);
        this.missDistance = missDistance;
        this.orbitingBody = orbitingBody;
    }

    /**
     * Accessor method for the referenceId field
     *
     * @return
     *      the referenceId field of the object
     */
    public int getReferenceId() {
        return referenceId;
    }

    /**
     * Mutator method for the referenceId field
     *
     * @param referenceId
     *      the new referenceId to change the field to
     */
    public void setReferenceId(int referenceId) {
        this.referenceId = referenceId;
    }

    /**
     * Accessor method for the name field
     *
     * @return
     *      the name field of the object
     */
    public String getName() {
        return name;
    }

    /**
     * Mutator method for the name field
     *
     * @param name
     *      the new name to change the field to
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Accessor method for the absoluteMagnitude field
     *
     * @return
     *      the absoluteMagnitude field of the object
     */
    public double getAbsoluteMagnitude() {
        return absoluteMagnitude;
    }

    /**
     * Mutator method for the absoluteMagnitude field
     *
     * @param absoluteMagnitude
     *      the new absoluteMagnitude to change the field to
     */
    public void setAbsoluteMagnitude(double absoluteMagnitude) {
        this.absoluteMagnitude = absoluteMagnitude;
    }

    /**
     * Accessor method for the averageDiameter field
     *
     * @return
     *      the averageDiameter field of the object
     */
    public double getAverageDiameter() {
        return averageDiameter;
    }

    /**
     * Mutator method for the averageDiameter field
     *
     * @param averageDiameter
     *      the new averageDiameter to change the field to
     */
    public void setAverageDiameter(double averageDiameter) {
        this.averageDiameter = averageDiameter;
    }

    /**
     * Accessor method for the isDangerous field
     *
     * @return
     *      the isDangerous field of the object
     */
    public boolean isDangerous() {
        return isDangerous;
    }

    /**
     * Mutator method for the isDangerous field
     *
     * @param dangerous
     *      the new isDangerous to change the field to
     */
    public void setDangerous(boolean dangerous) {
        isDangerous = dangerous;
    }

    /**
     * Accessor method for the closestApproachDate field
     *
     * @return
     *      the closestApproachDate field of the object
     */
    public Date getClosestApproachDate() {
        return closestApproachDate;
    }

    /**
     * Mutator method for the closestApproachDate field
     *
     * @param closestApproachDate
     *      the new closestApproachDate to change the field to
     */
    public void setClosestApproachDate(Date closestApproachDate) {
        this.closestApproachDate = closestApproachDate;
    }

    /**
     * Accessor method for the missDistance field
     *
     * @return
     *      the missDistance field of the object
     */
    public double getMissDistance() {
        return missDistance;
    }

    /**
     * Mutator method for the missDistance field
     *
     * @param missDistance
     *      the new missDistance to change the object's field to
     */
    public void setMissDistance(double missDistance) {
        this.missDistance = missDistance;
    }

    /**
     * Accessor method for the orbitingBody field
     *
     * @return
     *      orbitingBody field of the object
     */
    public String getOrbitingBody() {
        return orbitingBody;
    }

    /**
     * Mutator method for the orbitingBody field
     *
     * @param orbitingBody
     *      new orbitingBody to change the object's field to
     */
    public void setOrbitingBody(String orbitingBody) {
        this.orbitingBody = orbitingBody;
    }

}
