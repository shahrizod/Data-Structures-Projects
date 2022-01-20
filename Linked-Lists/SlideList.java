//Shahrizod Bobojonov, 114137520, Recitation 1

/**
 * This class is a doubly linked list that represents the presentation. It has
 * a head node, a tail node, and a cursor node represented using SlideListNode
 * objects and stored in head, tail, and cursor, respectively. It also has
 * numSlides, totalDuration, totalBullets, and cursorNum variables, all in order to track
 * and return the number of slides, the total duration of the presentation, and
 * the location of the cursor, respectively, in O(1)
 *
 * @author Shahrizod Bobojonov
 */
public class SlideList {
    private SlideListNode head;
    private SlideListNode tail;
    private SlideListNode cursor;
    private int numSlides;
    private double totalDuration;
    private int totalBullets;
    private int cursorNum;

    /**
     * A no-arg constructor for a SlideList object initializing the empty
     * presentation with a head, tail, and cursor all set to null, while
     * numSlides, totalDuration, totalBullets, and cursorNum are initialized to 0
     */
    public SlideList() {
        head = null;
        tail = null;
        cursor = null;

        numSlides = 0;
        totalDuration = 0;
        totalBullets = 0;
        cursorNum = 0;
    }

    /**
     * Accessor method for the size of the presentation, represented by the
     * numSlides variable of the object
     *
     * @return
     *      the numSlides variable of the presentation, representing size
     */
    public int size() {
        return numSlides;
    }

    /**
     * Accessor method for the duration of the presentation, represented by
     * the totalDuration variable of the object
     *
     * @return
     *      the totalDuration variable of the presentation
     */
    public double duration() {
        return totalDuration;
    }

    /**
     * Changes the duration of the presentation by a given amount, used when
     * editing a slide
     *
     * @param d
     *      a double value to change the totalDuration variable by
     */
    public void updateDuration(double d) {
        totalDuration+=d;
    }

    /**
     * Accessor method for the total number of bullets, represented by the
     * totalBullets variable of the object
     *
     * @return
     *      the totalBullets variable of the presentation
     */
    public int numBullets() {
        return totalBullets;
    }

    /**
     * Changes the total number of bullets in the presentation by a given
     * amount, used when editing a slide
     *
     * @param b
     *      an int value to change the totalBullets variable by
     */
    public void updateNumBullets(int b) {
        totalBullets+=b;
    }

    /**
     * A method that returns the Slide object contained within the SlideListNode
     * object the cursor is pointing to
     *
     * @return
     *      a Slide object which is the slide that the cursor is pointing to
     */
    public Slide getCursorSlide() {
        if (cursor == null) return null;
        return cursor.getData();
    }

    /**
     * Moves the cursor back to the head of the doubly linked list
     */
    public void resetCursorToHead() {
        cursor = head;
        cursorNum = 1;
    }

    /**
     * Moves the cursor forward one slide by changing its reference to the
     * node after it, and throws an error if its at the tail of the list
     *
     * @throws EndOfListException
     *      when the cursor is already at the tail
     */
    public void cursorForward() throws EndOfListException {
        if (cursor == tail) throw new EndOfListException("Reached end of list!");
        cursor = cursor.getNext();
        cursorNum++;
    }

    /**
     * Moves the cursor back one slide by changing its reference to the previous
     * node, also throws an error if its at the head of the list
     *
     * @throws EndOfListException
     *      when the cursor is already at the head
     */
    public void cursorBackward() throws EndOfListException {
        if (cursor == head) throw new EndOfListException("Reached beginning of list!");
        cursor = cursor.getPrev();
        cursorNum--;
    }

    /**
     * Inserts a new Slide object right before where the cursor currently is,
     * and then moves the cursor to that new slide
     *
     * @param newSlide
     *      the Slide object to insert into the presentation before the cursor
     * @throws IllegalArgumentException
     *      when the Slide object given is null
     */
    public void insertBeforeCursor(Slide newSlide) throws IllegalArgumentException {
        if (newSlide == null) throw new IllegalArgumentException();
        addSlideVals(newSlide);
        SlideListNode insert = new SlideListNode(newSlide);
        if (cursor == null) {
            head = insert;
            tail = insert;
            cursorNum = 1;
        } else if (cursor.getPrev() == null) {
            insert.setNext(head);
            head.setPrev(insert);
            head = insert;
        } else {
            insert.setNext(cursor);
            insert.setPrev(cursor.getPrev());
            cursor.getPrev().setNext(insert);
            cursor.setPrev(insert);
        }
        cursor = insert; //needs to happen no matter what
    }

    /**
     * Appends a new Slide object to the tail of the presentation, making that
     * the new tail in the process
     *
     * @param newSlide
     *      the Slide object to add to the tail of the presentation
     * @throws IllegalArgumentException
     *      when the Slide object given is null
     */
    public void appendToTail(Slide newSlide) throws IllegalArgumentException {
        if (newSlide == null) throw new IllegalArgumentException();
        addSlideVals(newSlide);
        SlideListNode insert = new SlideListNode(newSlide);
        if (tail == null) {
            head = insert;
            cursor = insert;
            cursorNum = numSlides;
        } else {
            insert.setPrev(tail);
            tail.setNext(insert);
        }
        tail = insert; //needs to happen no matter what
    }

    /**
     * Removes the slide that's referenced by the cursor and moving the cursor
     * back one slide if it's not the head and one forward if it is
     *
     * @return
     *      the Slide object that was removed from the presentation
     * @throws EndOfListException
     *      when there's nothing at the cursor, indicating that the list is empty
     */
    public Slide removeCursor() throws EndOfListException {
        if (cursor == null) throw new EndOfListException("Nothing at cursor");
        Slide deleted = cursor.getData();
        removeSlideVals(deleted);
        if (head==tail) {
            head=tail=null;
            cursorNum--;
            return deleted;
        }
        if (cursor == head) {
            cursorForward();
            head = cursor;
            cursor.getPrev().setNext(null);
            cursor.setPrev(null);
            resetCursorToHead();
            return deleted;
        }
        if (cursor == tail) {
            cursorBackward();
            tail = cursor;
            cursor.getNext().setPrev(null);
            cursor.setNext(null);
            return deleted;
        }
        cursorBackward();
        cursor.getNext().setPrev(null);
        cursor.getNext().getNext().setPrev(cursor);
        cursor.setNext(cursor.getNext().getNext());
        cursor.getNext().setNext(null);
        return deleted;
    }

    /**
     * Method used when adding a slide in order to easily add the values of the
     * slide to the total values of the presentation
     *
     * @param slide
     *      the Slide object whose values need to be added to the totals
     */
    public void addSlideVals(Slide slide) {
        numSlides++;
        totalBullets += slide.getNumBullets();
        totalDuration += slide.getDuration();
    }

    /**
     * Method used when removing a slide in order to easily remove the values
     * of the slide from the total values of the presentation
     *
     * @param slide
     *      the Slide object whose values need to be removed from the totals
     */
    public void removeSlideVals(Slide slide) {
        numSlides--;
        totalBullets -= slide.getNumBullets();
        if (slide.getDuration() != -1) {
            totalDuration -= slide.getDuration();
        }
    }

    /**
     * Accessor method for cursorNum, returning the current index of the cursor
     *
     * @return
     *      the value of cursorNum, representing current index of cursor
     */
    public int getCursorNum() {
        return cursorNum;
    }

    /**
     * Changes the cursor to the given index
     *
     * @param cursorNum
     *      what index the cursor should move to
     * @throws IllegalArgumentException
     *      when the given cursorNum is < 1 or > numSlides, indicating its not
     *      a valid value to move the cursor to, unless cursor is null, meaning
     *      the list is empty. Also when cursor is null and cursorNum is not 0
     */
    public void setCursorNum(int cursorNum) throws IllegalArgumentException {
        if ((cursorNum < 1 || cursorNum > numSlides) && cursor != null) throw new IllegalArgumentException();
        if (cursor == null && cursorNum != 0) throw new IllegalArgumentException(); //if list is empty only valid cursorNum is 0
        this.cursorNum = cursorNum;
        resetCursorToHead();
        for (int i=1; i < cursorNum; i++) {
            try {
                cursorForward();
            } catch (EndOfListException e) { //this error should not occur, but catching just in case
                e.printStackTrace();
            }
        }
    }
}
