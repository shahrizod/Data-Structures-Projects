//Shahrizod Bobojonov, 114137520, Recitation 1

/**
 * This class represents a slide and so it has all the necessary data for one,
 * such as a title, a String array of the bullet points, the duration of the
 * slide as well as the number of bullets the slide has in order to easily get
 * the size of the bullet array / number of bullet points on the slide in O(1)
 *
 * @author Shahrizod Bobojonov
 */
public class Slide {

    private static final int MAX_BULLETS = 5;
    private String title;
    private String[] bullets;
    private double duration;
    private int numBullets;

    /**
     * This is a no-arg constructor that creates an empty slide whose values
     * are then set by mutator methods afterwards. It creates a Slide object
     * with the title as null, instantiates the bullets String array with the
     * max number of bullets allowed on the slide, and sets the duration and
     * number of bullets on the slide to 0 initially
     */
    public Slide() {
        title = null;
        bullets = new String[MAX_BULLETS];
        duration = 0;
        numBullets = 0;
    }

    /**
     * Accessor method for the MAX_BULLETS variable, which will allow
     * us to find the capacity of the bullet array even outside the class
     *
     * @return
     *      the value of MAX_BULLETS, the size of the bullet array, an integer
     */
    public int getCapacity() {
        return MAX_BULLETS;
    }

    /**
     * Accessor method for title variable, representing the title of the slide
     *
     * @return
     *      the value of title, a String
     */
    public String getTitle() {
        return title;
    }

    /**
     * Mutator method for the title, allowing us to change the title after
     * creating the object, title can be empty string, but not null
     *
     * @param newTitle
     *      the new String to set the title to, cannot be null
     * @throws IllegalArgumentException
     *      when the new String given in the parameter is null
     */
    public void setTitle(String newTitle) throws IllegalArgumentException {
        if (newTitle == null) throw new IllegalArgumentException();
        this.title = newTitle;
    }

    /**
     * Accessor method for the duration variable, representing the duration
     * of the slide
     *
     * @return
     *      the value of duration, a double
     */
    public double getDuration() {
        return duration;
    }

    /**
     * Mutator method for the duration variable, allowing us to change the value
     * of duration after creating the object
     *
     * @param newDuration
     *      the new value of duration to set the variable to, can't be <= 0
     * @throws IllegalArgumentException
     *      when the new duration value given in the parameter is <= 0
     */
    public void setDuration(double newDuration) throws IllegalArgumentException {
        if (newDuration <= 0) throw new IllegalArgumentException();
        this.duration = newDuration;
    }

    /**
     * Special method for deleting the duration by setting it to -1, since the
     * regular <CODE>setDuration()</CODE> method does not allow us to use
     * values <= 0
     */
    public void deleteDuration() {
        duration = -1;
    }

    /**
     * Accessor method for the size of the bullet array, which is counted
     * using the numBullets variable in order to achieve O(1) efficiency
     *
     * @return
     *      the value of numBullets, an integer
     */
    public int getNumBullets() {
        return numBullets;
    }

    /**
     * Accessor method for the value of a bullet within the bullet array, found
     * given an index in the parameter
     *
     * @param i
     *      the index of the bullet needed, can be from 1 to the capacity of
     *      the bullet array, MAX_BULLETS
     * @return
     *      the value of the bullet at the given index, a String
     * @throws IllegalArgumentException
     *      when the index is < 1 or > MAX_BULLETS, the capacity of the array
     */
    public String getBullet(int i) throws IllegalArgumentException {
        if (i < 1 || i > MAX_BULLETS) throw new IllegalArgumentException();
        return bullets[i-1];
    }

    /**
     * Mutator method for bullets in the bullet array, given a String for the
     * new value of the bullet as well as an integer representing the index of
     * the bullet we want to change
     *
     * @param bullet
     *      the new value of the bullet to set a certain bullet in the array to
     * @param i
     *      the index of the bullet in the array to change, starting from 1
     * @throws IllegalArgumentException
     *      when the bullet is < 1 or > MAX_BULLETS, the capacity of the array
     */
    public void setBullet(String bullet, int i) throws IllegalArgumentException {
        if (i<1 || i>MAX_BULLETS) throw new IllegalArgumentException();
        if (bullet == null) {
            if (i==numBullets) {
                bullets[i-1] = null;
            } else {
                for (int x = i - 1; x < numBullets - 1; x++) {
                    bullets[x] = bullets[x + 1];
                    if (x==numBullets-2) bullets[x+1] = null;
                }
            }
            numBullets--;
        } else {
            if (bullets[i-1] == null) {
                numBullets++;
            }
            bullets[i-1] = bullet;
        }
    }

    /**
     * Override <CODE>toString()</CODE> method which returns the Slide object's
     * values in a certain format when printed
     *
     * @return
     *      a String representation of the Slide object's values
     */
    @Override
    public String toString() {
        if (duration==-1) return String.format("%-26s%-12s%-12d", title, "N/A", numBullets);
        return String.format("%-26s%-12.2f%-12d", title, duration, numBullets);
    }
}
