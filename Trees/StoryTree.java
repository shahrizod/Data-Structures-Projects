//Shahrizod Bobojonov 114137520 Recitation 1

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.zip.DataFormatException;

/**
 * This class is the ternary tree data structure representing the whole story
 * and multiple paths it has. It uses StoryTreeNode objects to populate the tree
 * and has a dummy root to be used to then populate the tree, as well as a cursor
 * to point to the active StoryTreeNode, which is then used when making/editing
 * games. It also uses the GameState enum to represent the current state of the
 * game in the state variable.
 */
public class StoryTree {
    private StoryTreeNode root;
    private StoryTreeNode cursor;
    private GameState state;

    /**
     * Default constructor for the StoryTree object which initializes the root,
     * cursor, and state of the StoryTree instance.
     */
    public StoryTree() {
        root = new StoryTreeNode("root", "root", "Hello, welcome to Zork!");
        cursor = root;
        state = GameState.GAME_NOT_OVER;
    }

    /**
     * Takes in the name of the file which is then read and used to construct
     * a StoryTree object. It assumes a preorder traversal of the tree and uses
     * the position given for each line to determine where the node should go
     * recursively and then placing the node with the attributes given in the
     * correct position. If the file provided doesn't exist, it says so and then
     * creates an empty tree with a real root and initializes its option and
     * message to N/A.
     *
     * @param filename
     *      the name of the file to read in
     * @return
     *      the StoryTree object created from the file or an empty one if no file
     * @throws IllegalArgumentException
     *      when the filename is null or blank
     * @throws DataFormatException
     *      when any line in the file isn't formatted as expected
     */
    public static StoryTree readTree(String filename) throws IllegalArgumentException, DataFormatException {
        if (filename == null || filename.isBlank()) throw new IllegalArgumentException();
        StoryTree tree = new StoryTree();
        try {
            Scanner fileIn = new Scanner(new File(filename));
            System.out.println("\nFile loaded!\n");
            while (fileIn.hasNextLine()) {
                String[] line = fileIn.nextLine().split("\\|");
                if (line.length != 3 || !line[0].trim().startsWith("1")) throw new DataFormatException();
                String position = line[0].trim();
                String option = line[1].trim();
                String message = line[2].trim();
                if (position.equals("1")) {
                    tree.root.setLeftChild(new StoryTreeNode(position, option, message));
                    continue;
                }
                StoryTreeNode n = tree.treeBuild(position, tree.root.getLeftChild());
                if (position.endsWith("-1"))
                    n.setLeftChild(new StoryTreeNode(position, option, message));
                else if (position.endsWith("-2"))
                    n.setMiddleChild(new StoryTreeNode(position, option, message));
                else
                    n.setRightChild(new StoryTreeNode(position, option, message));
            }
            fileIn.close();
        } catch(FileNotFoundException fnfe) {
            System.out.println("\nThis file does not exist\n");
            tree.root.setLeftChild(new StoryTreeNode("1", "N/A", "N/A"));
        }
        return tree;
    }

    /**
     * Helper method used to recursively find the parent of where a node should go
     *
     * @param position
     *      the position of the node, where it will end up going
     * @param n
     *      the StoryTreeNode object the recursive loop starts with
     * @return
     *      a StoryTreeNode object representing the parent of the node location
     */
    public StoryTreeNode treeBuild(String position, StoryTreeNode n) {
        if (n == root.getLeftChild())
            position = position.substring(1, Math.max(position.length(), position.length()-2));
        if (position.length()==2) return n;
        if (position.startsWith("-1")) {
            return treeBuild(position.substring(2), n.getLeftChild());
        } else if (position.startsWith("-2")) {
            return treeBuild(position.substring(2), n.getMiddleChild());
        } else {
            return treeBuild(position.substring(2), n.getRightChild());
        }
    }

    /**
     * Helper method used to recursively write each node to a file
     *
     * @param fileOut
     *      a PrintWriter object used to write the tree nodes to a file
     * @param n
     *      the StoryTreeNode to start each recursion with
     */
    public void saveRecursion(PrintWriter fileOut, StoryTreeNode n) {
        if (!n.getPosition().equals("root"))
            fileOut.write(n.getPosition() + " | " + n.getOption() + " | " + n.getMessage()+"\n");
        if (n.getLeftChild() != null) {
            saveRecursion(fileOut, n.getLeftChild());
            if (n.getMiddleChild() != null) {
                saveRecursion(fileOut, n.getMiddleChild());
                if (n.getRightChild() != null)
                    saveRecursion(fileOut, n.getRightChild());
            }
        }
    }

    /**
     * Saves the given tree to a file by recursively traversing the tree
     * and writing each node to the file in the specified format
     *
     * @param filename
     *      the name of the file to write to
     * @param tree
     *      the tree to save to the file
     * @throws IllegalArgumentException
     *      when the filename parameter is null or blank or the given tree is null
     */
    public static void saveTree(String filename, StoryTree tree) throws IllegalArgumentException {
        if (filename == null || filename.isBlank() || tree == null) throw new IllegalArgumentException();
        if (filename.contains(".txt")) filename = filename.substring(0,filename.length()-4);
        try {
            PrintWriter fileOut = new PrintWriter(filename+".txt");
            tree.saveRecursion(fileOut, tree.root);
            fileOut.close();
            System.out.println("Save successful!");
        } catch (FileNotFoundException fnfe) {
            System.out.println("File couldn't be written");
        }
    }

    /**
     * Accessor method for the state of the StoryTree object
     *
     * @return
     *      a GameState enum stored in state
     */
    public GameState getGameState() {
        return state;
    }

    /**
     * Accessor method for the position of the node stored in cursor
     *
     * @return
     *      a String value representing the position of cursor
     */
    public String getCursorPosition() {
        return cursor.getPosition();
    }

    /**
     * Accessor method for the message of the node stored in the cursor
     *
     * @return
     *      a String value representing the message of cursor
     */
    public String getCursorMessage() {
        return cursor.getMessage();
    }

    /**
     * Accessor method for the option of the node stored in the cursor
     *
     * @return
     *      a String value representing the option of cursor
     */
    public String getCursorOption() {
        return cursor.getOption();
    }

    /**
     * Gets the options of all the children of the cursor node and returns a
     * 2D array of the position and option of each child. Used to view the
     * potential options to move forward in the game
     *
     * @return
     *      a 2-dimensional String array with each row representing a child and
     *      the first column representing the position while the second represents
     *      the option of each child
     */
    public String[][] getOptions() {
        if (cursor.isLeaf()) {
            System.out.println("Cursor is a leaf, so there are no options");
            return null;
        }
        String[][] options;
        if (cursor.getRightChild() != null)
            options = new String[3][2];
        else if (cursor.getMiddleChild() != null)
            options = new String[2][2];
        else
            options = new String[1][2];

        options[0][0] = cursor.getLeftChild().getPosition();
        options[0][1] = cursor.getLeftChild().getOption();
        if (cursor.getMiddleChild() != null) {
            options[1][0] = cursor.getMiddleChild().getPosition();
            options[1][1] = cursor.getMiddleChild().getOption();
            if (cursor.getRightChild() != null) {
                options[2][0] = cursor.getRightChild().getPosition();
                options[2][1] = cursor.getRightChild().getOption();
            }
        }
        return options;
    }

    /**
     * Mutator method for the message of the node stored in cursor
     *
     * @param message
     *      the new message to change the cursor message to
     */
    public void setCursorMessage(String message) {
        cursor.setMessage(message);
    }

    /**
     * Mutator method for the option of the node stored in cursor
     *
     * @param option
     *      the new option to change the cursor option to
     */
    public void setCursorOption(String option) {
        cursor.setOption(option);
    }

    /**
     * Moves the cursor back to the dummy root of the tree and resets the state
     * of the tree to not over because this method is used after playing or
     * editing the story, so this makes it simple to reset the game for the next
     * time it's played or edited, instead of making a mutator for state.
     */
    public void resetCursor() {
        cursor = root;
        state = GameState.GAME_NOT_OVER;
    }

    /**
     * Selects a child of the cursor based on the provided String parameter, position.
     * Also updates the state of the game if the selected child is a winning or
     * losing node so that any method using state to determine when the game is
     * over is aware and acts appropriately
     *
     * @param position
     *      position of the child to move the cursor to
     * @throws InvalidArgumentException
     *      when the position given is null or blank
     * @throws NodeNotPresentException
     *      when the node is not found
     */
    public void selectChild(String position) throws InvalidArgumentException, NodeNotPresentException {
        if (position == null || position.isBlank()) throw new InvalidArgumentException("Given position invalid");
        if (cursor.getLeftChild() != null && cursor.getLeftChild().getPosition().equals(position))
            cursor = cursor.getLeftChild();
        else if (cursor.getMiddleChild() != null && cursor.getMiddleChild().getPosition().equals(position))
            cursor = cursor.getMiddleChild();
        else if (cursor.getRightChild() != null && cursor.getRightChild().getPosition().equals(position))
            cursor = cursor.getRightChild();
        else throw new NodeNotPresentException("Node doesn't exist");
        if (cursor.isWinningNode()) {
            state = GameState.GAME_OVER_WIN;
        } else if (cursor.isLosingNode()) {
            state = GameState.GAME_OVER_LOSE;
        }
    }

    /**
     * Adds a StoryTreeNode to the leftmost available child spot by finding the
     * position and then creating a StoryTreeNode using the given option, position,
     * and message, and then setting it to the appropriate child spot
     *
     * @param option
     *      the option of the new StoryTreeNode to add
     * @param message
     *      the message of the new StoryTreeNode to add
     * @throws InvalidArgumentException
     *      when the option and message given are null or blank
     * @throws TreeFullException
     *      when there are no empty child spots of the cursor to add the child to
     */
    public void addChild(String option, String message) throws InvalidArgumentException, TreeFullException {
        if (option==null || option.isBlank() || message==null || message.isBlank())
            throw new InvalidArgumentException("Invalid parameters provided");
        if (cursor.isFull()) throw new TreeFullException("The node has no empty child spots");
        if (cursor.getLeftChild() == null) {
            cursor.setLeftChild(new StoryTreeNode(cursor.getPosition()+"-1", option, message));
        } else if (cursor.getMiddleChild() == null) {
            cursor.setMiddleChild(new StoryTreeNode(cursor.getPosition()+"-2", option, message));
        } else {
            cursor.setRightChild(new StoryTreeNode(cursor.getPosition()+"-3", option, message));
        }
    }

    /**
     * Removes the child of the cursor indicated by position and shifts the
     * other children so that the tree is left-aligned. Also changes the
     * position of the shifted nodes and their descendants.
     *
     * @param position
     *      the full position of the child to remove
     * @return
     *      the StoryTreeNode that was removed
     * @throws NodeNotPresentException
     *      when the node to be removed can't be found
     */
    public StoryTreeNode removeChild(String position) throws NodeNotPresentException {
        if (cursor.getRightChild() != null && cursor.getRightChild().getPosition().equals(position)) {
            StoryTreeNode removedChild = cursor.getRightChild();
            cursor.setRightChild(null);
            return removedChild;
        } else if (cursor.getMiddleChild() != null && cursor.getMiddleChild().getPosition().equals(position)) {
            StoryTreeNode removedChild = cursor.getMiddleChild();
            cursor.setMiddleChild(cursor.getRightChild());
            cursor.setRightChild(null);
            if (cursor.getMiddleChild() != null)
                cursor.getMiddleChild().setPosition(cursor.getPosition()+"-2");
            return removedChild;
        } else if (cursor.getLeftChild() != null && cursor.getLeftChild().getPosition().equals(position)) {
            StoryTreeNode removedChild = cursor.getLeftChild();
            if (cursor.getMiddleChild() == null && cursor.getRightChild() == null) {
                cursor.setLeftChild(null);
            } else if (cursor.getRightChild() == null) {
                cursor.setLeftChild(cursor.getMiddleChild());
                cursor.setMiddleChild(null);
                cursor.getLeftChild().setPosition(cursor.getPosition()+"-1");
            } else {
                cursor.setLeftChild(cursor.getMiddleChild());
                cursor.setMiddleChild(cursor.getRightChild());
                cursor.setRightChild(null);
                cursor.getLeftChild().setPosition(cursor.getPosition() + "-1");
                cursor.getMiddleChild().setPosition(cursor.getPosition() + "-2");
            }
            return removedChild;
        }
        else throw new NodeNotPresentException("Node not found");
    }

    /**
     * Uses the previous recursion method created for building the tree
     * to also get the parent of the cursor and move the cursor there.
     */
    public void returnToParent() {
        if (cursor == root.getLeftChild()) {
            System.out.println("Can't go up any further!");
        } else {
            cursor = treeBuild(cursor.getPosition(), root.getLeftChild());
            System.out.println("Moved cursor to parent");
        }
    }
}
