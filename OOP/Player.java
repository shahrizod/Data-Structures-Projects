//Shahrizod Bobojonov, 114137520, Recitation 1

/**
 * This class represents a Player that has a name and statistics representing
 * their number of hits and errors.
 * @author Shahrizod Bobojonov
 */
public class Player {
    private String name;
    private int numHits;
    private int numErrors;

    /**
     * This is a Constructor used to create a new Player object with the specified parameters
     *
     * @param name
     *      name of the player to construct the object
     * @param numHits
     *      number of hits the player has made
     * @param numErrors
     *      number of errors the player has made
     */
    public Player(String name, int numHits, int numErrors) {
        if (numHits < 0 | numErrors < 0) throw new IllegalArgumentException();
        this.name = name;
        this.numHits = numHits;
        this.numErrors = numErrors;
    }

    /**
     * Accessor method for a Player object's name
     *
     * @return
     *      the name of the player
     */
    public String getName() {
        return name;
    }

    /**
     * Mutator method that sets the Player object's name to the given parameter
     *
     * @param name
     *      the new value to set the name field to
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Accessor method for the number of hits statistic of the Player object
     *
     * @return numHits
     *      the number of hits statistic belonging to the Player object
     */
    public int getNumHits() {
        return numHits;
    }

    /**
     * Mutator method for the number of hits statistic of the Player object
     *
     * @param numHits
     *      the new value to set the number of hits statistic of the Player object to
     * @throws IllegalArgumentException
     *      if the new value of the number of hits statistic is less than 0
     */
    public void setNumHits(int numHits) throws IllegalArgumentException {
        if (numHits < 0) throw new IllegalArgumentException();
        this.numHits = numHits;
    }

    /**
     * Accessor method for the number of errors statistic of the Player object
     *
     * @return numErrors
     *      the number of errors statistic belonging to the Player object
     */
    public int getNumErrors() {
        return numErrors;
    }

    /**
     * Mutator method for the number of errors statistic of the Player object
     *
     * @param numErrors
     *      the new value to set the number of errors statistic of the Player object to
     * @throws IllegalArgumentException
     *      if the new value of the number of errors statistic is less than 0
     */
    public void setNumErrors(int numErrors) throws IllegalArgumentException {
        if (numErrors < 0) throw new IllegalArgumentException();
        this.numErrors = numErrors;
    }

    /**
     * Override <CODE>clone()</CODE> method to deep-copy Player object and return it
     *
     * @return
     *      new Object that is a copy of the Player object that the method is
     *      called from; must be typecast before use
     */
    @Override
    public Object clone() {
        return new Player(this.name, this.numHits, this.numErrors);
    }

    /**
     * Override <CODE>equals(obj)</CODE> method to check whether an Object has
     * equivalent data to the Player object
     *
     * @param obj
     *      Object to check if is equal to Player object method is called from
     * @return
     *      boolean value representing whether the passed Object has the same
     *      data as the Player object the method is called from
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Player) {
            Player p = (Player) obj;
            return this.name.equals(p.name) && this.numHits == p.numHits && this.numErrors == p.numErrors;
        }
        return false;
    }

    /**
     * Override <CODE>toString()</CODE> method specifying how Player object
     * should be represented when printed
     *
     * @return
     *      String representation of the Player object to be used when Player
     *      object is printed
     */
    @Override
    public String toString() {
        return name + " - " + numHits + " hits, " + numErrors + " errors";
    }
}