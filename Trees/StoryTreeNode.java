//Shahrizod Bobojonov 114137520 Recitation 1

/**
 * Class representing a node in the tree, and has a position, an option, a message,
 * as well as three spots for possible children of the tree to go to. Each node
 * essentially represents a decision point in the game, causing different paths
 * to be taken based on which node is selected.
 */
public class StoryTreeNode {
    public static final String WIN_MESSAGE = "YOU WIN";
    public static final String LOSE_MESSAGE = "YOU LOSE";
    private String position;
    private String option;
    private String message;
    private StoryTreeNode leftChild;
    private StoryTreeNode middleChild;
    private StoryTreeNode rightChild;

    /**
     * Default constructor for StoryTreeNode that initializes String variables
     * as empty and children as null, allowing these values to be set later
     * using mutator methods
     */
    public StoryTreeNode() {
        position = "";
        option = "";
        message = "";
    }

    /**
     * Arg constructor for StoryTreeNode allowing variables to be initialized upon
     * construction rather than later through mutators
     *
     * @param position
     *      the position of the node in the tree
     * @param option
     *      the option stored in the node (how the user chooses this node over others)
     * @param message
     *      the message given if this node is selected
     */
    public StoryTreeNode(String position, String option, String message) {
        this.position = position;
        this.option = option;
        this.message = message;
    }

    /**
     * Checks if the node has any children, and since the tree is left-aligned,
     * we only check if the leftChild is null to see if it's a leaf
     *
     * @return
     *      boolean value representing if the node is a leaf or not
     */
    public boolean isLeaf() {
        return (leftChild == null);
    }

    /**
     * Checks if the node is a leaf and has the win message inside of its message
     *
     * @return
     *      boolean value representing if the aforementioned conditions are met
     */
    public boolean isWinningNode() {
        return (isLeaf() && message.contains(WIN_MESSAGE));
    }

    /**
     * Checks if the node is a leaf and doesn't have the win message inside of
     * its message. Note that it doesn't have to have the lose message, just needs
     * to not have the win message.
     *
     * @return
     *      boolean value representing if the aforementioned conditions are met
     */
    public boolean isLosingNode() {
        return (isLeaf() && !message.contains(WIN_MESSAGE));
    }

    /**
     * Checks if there are any empty child spots to possibly add to
     *
     * @return
     *      if there are no empty child spots for the node
     */
    public boolean isFull() {
        return (rightChild != null);
    }

    //GETTERS AND SETTERS:

    /**
     * Accessor method for the position
     *
     * @return
     *      the position of the node
     */
    public String getPosition() {
        return position;
    }

    /**
     * Mutator method for the position, also recursively changes the positions
     * of all of the descendants
     *
     * @param position
     *      the new position to change the node's position to
     */
    public void setPosition(String position) {
        this.position = position;
        if (isLeaf()) return;
        if (leftChild!=null) {
            leftChild.setPosition(position + "-1");
            if (middleChild != null) {
                middleChild.setPosition(position + "-2");
                if (rightChild != null) {
                    rightChild.setPosition(position + "-3");
                }
            }
        }
    }

    /**
     * Accessor method for the option variable
     *
     * @return
     *      the option variable of the node
     */
    public String getOption() {
        return option;
    }

    /**
     * Mutator method for the option variable
     *
     * @param option
     *      the new option to change the node's option to
     */
    public void setOption(String option) {
        this.option = option;
    }

    /**
     * Accessor method for the message variable
     *
     * @return
     *      the message variable of the node
     */
    public String getMessage() {
        return message;
    }

    /**
     * Mutator method for the message variable
     *
     * @param message
     *      the new message to change the node's message to
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Accessor method for the left child of the node
     *
     * @return
     *      the left child of the node
     */
    public StoryTreeNode getLeftChild() {
        return leftChild;
    }

    /**
     * Mutator method for the left child of the node
     *
     * @param leftChild
     *      the new leftChild node to change the node's leftChild to
     */
    public void setLeftChild(StoryTreeNode leftChild) {
        this.leftChild = leftChild;
    }

    /**
     * Accessor method for the middle child of the node
     *
     * @return
     *      the middle child of the node
     */
    public StoryTreeNode getMiddleChild() {
        return middleChild;
    }

    /**
     * Mutator method for the middle child of the node
     *
     * @param middleChild
     *      the new middleChild node to change the node's middleChild to
     */
    public void setMiddleChild(StoryTreeNode middleChild) {
        this.middleChild = middleChild;
    }

    /**
     * Accessor method for the right child of the node
     *
     * @return
     *      the right child of the node
     */
    public StoryTreeNode getRightChild() {
        return rightChild;
    }

    /**
     * Mutator method for the right child of the node
     *
     * @param rightChild
     *      the new rightChild node to change the node's rightChild to
     */
    public void setRightChild(StoryTreeNode rightChild) {
        this.rightChild = rightChild;
    }
}