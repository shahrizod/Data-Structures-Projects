//Shahrizod Bobojonov, 114137520, Recitation 1

/**
 * Wrapper for the slide object to act as a node in the doubly linked list,
 * takes in the Slide object to wrap in the data variable and then prev
 * and next variables which will reference the previous and next nodes
 * in the linked list, which are also SlideListNode objects
 *
 * @author Shahrizod Bobojonov
 */
public class SlideListNode {
    private Slide data;
    private SlideListNode prev;
    private SlideListNode next;

    /**
     * Arg constructor for the SlideListNode, with the only argument being a
     * Slide object which will be set to the data variable, while the prev and
     * next variables are initially set to null
     *
     * @param initData
     *      a Slide object that the node will wrap and store in data variable
     * @throws IllegalArgumentException
     *      when the Slide object is null
     */
    public SlideListNode(Slide initData) throws IllegalArgumentException {
        if (initData == null) throw new IllegalArgumentException();
        this.data = initData;
        this.prev = null;
        this.next = null;
    }

    /**
     * Accessor method for the data variable, which will return the Slide object
     * that the node contains
     *
     * @return
     *      the data variable which is the Slide object that the node is wrapping
     */
    public Slide getData() {
        return data;
    }

    /**
     * Mutator method for the data variable, which will allow us to change the
     * Slide object that the node is wrapping
     *
     * @param newData
     *      the new Slide object to set the data variable to
     * @throws IllegalArgumentException
     *      when the given Slide object is null
     */
    public void setData(Slide newData) throws IllegalArgumentException {
        if (newData == null) throw new IllegalArgumentException();
        this.data = newData;
    }

    /**
     * Accessor method for the prev variable, representing the previous node
     * in the linked list
     *
     * @return
     *      a SlideListNode object reference to the previous object in the list
     */
    public SlideListNode getPrev() {
        return prev;
    }

    /**
     * Mutator method for the prev variable, allowing us to manipulate the order
     * of the linked list by changing the previous and next object references
     *
     * @param newPrev
     *      the new SlideListNode object to refer to as the previous object
     */
    public void setPrev(SlideListNode newPrev) {
        this.prev = newPrev;
    }

    /**
     * Accessor method for the next variable, representing the next node in the
     * linked list
     *
     * @return
     *      a SlideListNode object reference to the next object in the list
     */
    public SlideListNode getNext() {
        return next;
    }

    /**
     * Mutator method for the next variable, allowing us to manipulate the order
     * of the linked list by changing the previous and next object references
     *
     * @param newNext
     *      the new SlideListNode object to refer to as the next object
     */
    public void setNext(SlideListNode newNext) {
        this.next = newNext;
    }
}
