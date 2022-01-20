//Shahrizod Bobojonov 114137520 Recitation 1

/**
 * Implements the Comparator interface so that the class can be used to compare
 * objects based on their missDistance
 */
public class MissDistanceComparator implements java.util.Comparator<NearEarthObject>{
    /**
     * Overrides the Comparator compare() method to compare by the missDistance
     *
     * @param o1
     *      first object to use for comparison
     * @param o2
     *      second object to use for comparison
     * @return
     *      0 if they're equal, -1 if the missDistance of o1 is less than o2,
     *      and 1 if the missDistance of o1 is greater than o2
     */
    @Override
    public int compare(NearEarthObject o1, NearEarthObject o2) {
        return Double.compare(o1.getMissDistance(), o2.getMissDistance());
    }
}
