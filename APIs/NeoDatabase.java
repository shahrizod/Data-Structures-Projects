//Shahrizod Bobojonov 114137520 Recitation 1

import big.data.DataSource;
import big.data.DataSourceException;

import java.text.SimpleDateFormat; //to convert the date into desired format
import java.util.ArrayList;
import java.util.Comparator;

/**
 * This class represents a database of NEOs using ArrayList by implementing the
 * NASA API and creating an NEO for each one returned based on a page number.
 */
public class NeoDatabase{
    public static final String API_KEY = "UaNHtrWh7ynlKcBbfGRDFgIPcUj4DFQYjKK967ul";
    public static final String API_ROOT = "https://api.nasa.gov/neo/rest/v1/neo/browse?";
    private ArrayList<NearEarthObject> objects; //represents the database

    /**
     * No-arg constructor which initializes the ArrayList representing the
     * database of NEOs
     */
    public NeoDatabase() {
        objects = new ArrayList<NearEarthObject>();
    }

    /**
     * Creates the query URL used to obtain JSON data which is later parsed
     * for the database. Takes the root, page number and API keys and combines
     * them to form the right query URL
     *
     * @param pageNumber
     *      the desired page number to obtain from the API
     * @return
     *      a String representing the query URL
     * @throws IllegalArgumentException
     *      when the page number is out of the bounds of 0 and 715
     */
    public String buildQueryURL(int pageNumber) throws IllegalArgumentException {
        if (pageNumber < 0 || pageNumber > 715) throw new IllegalArgumentException();
        return API_ROOT + "page=" + pageNumber + "&api_key=" + API_KEY;
    }

    /**
     * Takes in a query URL which is created using the buildQueryURL() method
     * and runs it through the API, parses the JSON data and adds NEO objects
     * to the ArrayList representing the database.
     *
     * @param queryURL
     *      the query URL to be passed to the API in order to get the data
     * @throws IllegalArgumentException
     *      when the queryURL is null or invalid
     */
    public void addAll(String queryURL) throws IllegalArgumentException{
        if (queryURL == null) throw new IllegalArgumentException();
        try {
            DataSource ds = DataSource.connectJSON(queryURL);
            ds.load();
            objects.addAll(ds.fetchList("NearEarthObject",
                    "near_earth_objects/neo_reference_id",
                    "near_earth_objects/name",
                    "near_earth_objects/absolute_magnitude_h",
                    "near_earth_objects/estimated_diameter/kilometers/estimated_diameter_min",
                    "near_earth_objects/estimated_diameter/kilometers/estimated_diameter_max",
                    "near_earth_objects/is_potentially_hazardous_asteroid",
                    "near_earth_objects/close_approach_data/epoch_date_close_approach",
                    "near_earth_objects/close_approach_data/miss_distance/kilometers",
                    "near_earth_objects/close_approach_data/orbiting_body"));
            objects.sort(new ReferenceIDComparator());
        } catch (DataSourceException | NullPointerException e) {
            System.out.println("\nError parsing data!\n");
            throw new IllegalArgumentException();
        }
    }

    /**
     * Sorts the database aka ArrayList based on the given comparator which
     * determines what attribute to sort the ArrayList by, such as referenceId,
     * diameter, closestApproachDate, or missDistance
     *
     * @param comp
     *      the Comparator object to use in order to determine what to sort by
     * @throws IllegalArgumentException
     *      when the given Comparator object is null
     */
    public void sort(Comparator<NearEarthObject> comp) throws IllegalArgumentException {
        if (comp == null) throw new IllegalArgumentException();
        objects.sort(comp);
    }

    /**
     * Prints the database in an easy-to-read tabular format
     */
    public void printTable() {
        System.out.println("     ID   |      Name    | Mag.  | Diameter | Danger | Close Date | Miss Distance | Orbits");
        System.out.println("=".repeat(92));
        if (objects == null) return;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd-yyyy");

        for (NearEarthObject value : objects) {
            System.out.printf(" %8d   %12s   %5.2f   %8.2f   %6b   %10s   %13.2f   %7s ", value.getReferenceId(),
                    "(" + value.getName().split("\\(")[1], value.getAbsoluteMagnitude(),
                    value.getAverageDiameter(), value.isDangerous(),
                    simpleDateFormat.format(value.getClosestApproachDate()),
                    value.getMissDistance(), value.getOrbitingBody());
            System.out.println();
        }
        System.out.println();
    }

    /**
     * Tells whether the database is empty or not
     *
     * @return
     *      boolean value representing if the database is empty or not
     */
    public boolean isEmpty() {
        return objects.isEmpty();
    }
}
