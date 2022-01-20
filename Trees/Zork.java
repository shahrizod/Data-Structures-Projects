//Shahrizod Bobojonov 114137520 Recitation 1

import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.zip.DataFormatException;

/**
 * This class allows us to play and edit the game of Zork. It reads in user input
 * to get a file and reads it into a tree using the readTree() method from
 * StoryTree and then continually prompts the user if they want to edit, play,
 * or quit the game, executing the appropriate methods to do so.
 */
public class Zork {
    //made a static stdin so every method can easily access it
    private static Scanner stdin = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Hello and welcome to Zork!\n");
        System.out.print("Please enter the filename: ");
        String filename = stdin.nextLine();
        while (filename == null || filename.isBlank()) {
            System.out.print("Invalid file name! Please try again: ");
            filename = stdin.nextLine();
        }
        if (!filename.contains(".txt")) {
            filename+=".txt";
            System.out.println("Assuming you forgot to add .txt and adding for you");
        }
        System.out.println("\nLoading game from file...");
        StoryTree tree;
        try {
            tree = StoryTree.readTree(filename);
        } catch (DataFormatException e) {
            System.out.println("Incorrect format detected, using empty tree...");
            tree = new StoryTree();
        }
        while (true) {
            System.out.print("Would you like to edit (E), play (P), or quit (Q)?: ");
            String choice = stdin.nextLine();
            if (choice.equalsIgnoreCase("E")) {
                editTree(tree);
            } else if (choice.equalsIgnoreCase("P")) {
                playTree(tree);
                stdin.nextLine(); //clear buffer
            } else if (choice.equalsIgnoreCase("Q")){
                System.out.println("\nGame being saved to " + filename + "...\n");
                StoryTree.saveTree(filename, tree);
                System.out.println("Program terminating normally.");
                break;
            } else {
                System.out.print("\nInvalid choice, please try again: ");
            }
        }
    }

    /**
     * This method continuously prompts the user to select options from a menu
     * that allow the user to edit the game in different ways
     *
     * @param tree
     *      the StoryTree object to edit
     */
    public static void editTree(StoryTree tree) {
        //need to initialize root to real root for editing instead of dummy root
        try {
            tree.selectChild("1");
        } catch (InvalidArgumentException | NodeNotPresentException e) {
            System.out.println("No tree available, creating new one");
        }

        while (true) {
            System.out.print("\nZork Editor:\n" +
                                "\tV: View the cursor's position, option, and message.\n" +
                                "\tS: Select a child of this cursor (options are 1, 2, and 3).\n" +
                                "\tO: Set the option of the cursor.\n" +
                                "\tM: Set the message of the cursor.\n" +
                                "\tA: Add a child StoryNode to the cursor.\n" +
                                "\tD: Delete one of the cursor's children and all its descendants.\n" +
                                "\tR: Move the cursor to the root of the tree.\n" +
                                "\tP: Return to Parent\n" +
                                "\tQ: Quit editing and return to main menu.\n" +
                                "\nPlease select an option: ");
            String choice = stdin.nextLine();
            System.out.println();
            if (choice.equalsIgnoreCase("V")) {
                System.out.println("Position: " + tree.getCursorPosition() +
                                    "\nOption: " + tree.getCursorOption() +
                                    "\nMessage: " + tree.getCursorMessage());
            } else if (choice.equalsIgnoreCase("S")) {
                String[][] ops = tree.getOptions();
                if (ops == null) continue;
                System.out.print("Please select a child: [");
                for (int i=0; i < ops.length; i++) {
                    System.out.print(ops[i][0].substring(tree.getCursorPosition().length()+1));
                    if (i != ops.length-1) {
                        System.out.print(",");
                    } else {
                        System.out.print("] ");
                    }
                }
                String inChoice = stdin.nextLine();
                try {
                    tree.selectChild(tree.getCursorPosition() + "-" + inChoice);
                    System.out.println("Position changed to " + tree.getCursorPosition());
                } catch (NodeNotPresentException | InvalidArgumentException e) {
                    System.out.println("\nInvalid choice, returning to menu");
                }
            } else if (choice.equalsIgnoreCase("O")) {
                System.out.print("Please enter a new option: ");
                tree.setCursorOption(stdin.nextLine());
                System.out.println("Option set.");
            } else if (choice.equalsIgnoreCase("M")) {
                System.out.print("Please enter a new message: ");
                tree.setCursorMessage(stdin.nextLine());
                System.out.println("Message set.");
            } else if (choice.equalsIgnoreCase("A")) {
                System.out.print("Enter an option: ");
                String option = stdin.nextLine();
                System.out.print("Enter a message: ");
                String message = stdin.nextLine();
                try {
                    tree.addChild(option, message);
                    System.out.println("Child added");
                } catch (InvalidArgumentException e) {
                    System.out.println("Invalid argument given.");
                } catch (TreeFullException e) {
                    System.out.println("Can't add any children here, no spots left.");
                }
            } else if (choice.equalsIgnoreCase("D")) {
                String[][] ops = tree.getOptions();
                if (ops == null) continue;
                System.out.print("Please select a child: [");
                for (int i=0; i < ops.length; i++) {
                    System.out.print(ops[i][0].substring(tree.getCursorPosition().length()+1));
                    if (i != ops.length-1) {
                        System.out.print(",");
                    } else {
                        System.out.print("] ");
                    }
                }
                String inChoice = stdin.nextLine();
                try {
                    tree.removeChild(tree.getCursorPosition()+"-"+inChoice);
                    System.out.println("Subtree deleted.");
                } catch (NodeNotPresentException e) {
                    System.out.println("\nInvalid choice, returning to menu");
                }
            } else if (choice.equalsIgnoreCase("R")) {
                tree.resetCursor();
                try {
                    tree.selectChild("1");
                    System.out.println("Cursor moved to root.");
                } catch (InvalidArgumentException | NodeNotPresentException e) {
                    e.printStackTrace();
                }
            } else if (choice.equalsIgnoreCase("P")) {
                tree.returnToParent();
            } else if (choice.equalsIgnoreCase("Q")) {
                tree.resetCursor();
                break;
            } else {
                System.out.print("Invalid choice, please try again\n");
            }
        }
    }

    /**
     * This method allows the user to play through the game, prompting the user
     * at each node on how they want to proceed and then selecting the correct
     * the next node to progress through the game until the state of the game
     * is changed from GAME_NOT_OVER, signifying we reached either a winning
     * or losing node
     *
     * @param tree
     *      the StoryTree object to play
     */
    public static void playTree(StoryTree tree) {
        System.out.println();
        System.out.println(tree.getOptions()[0][1] + "\n");
        try {
            tree.selectChild("1");
        } catch (InvalidArgumentException | NodeNotPresentException e) {
            System.out.println("\nInvalid input given\n");
        }
        System.out.println(tree.getCursorMessage());
        while (tree.getGameState() == GameState.GAME_NOT_OVER) {
            String[][] options = tree.getOptions();
            for (int i = 0; i < options.length; i++) {
                if (options[i][0] == null) break;
                System.out.println((i+1) + ") " + options[i][1]);
            }
            System.out.print("Please make a choice: ");
            int optionChoice;
            try {
                optionChoice = stdin.nextInt();
                while (optionChoice < 1 || optionChoice > options.length) {
                    System.out.print("Incorrect input, please make a choice: ");
                    optionChoice = stdin.nextInt();
                }
            } catch (InputMismatchException e) {
                System.out.println("\nINVALID INPUT!\n");
                stdin.nextLine(); //clear buffer
                continue;
            }
            System.out.println();
            try {
                tree.selectChild(options[optionChoice - 1][0]);
            } catch (InvalidArgumentException | NodeNotPresentException e) {
                e.printStackTrace();
                continue;
            }
            System.out.println(tree.getCursorMessage());
        }
        System.out.println("\nThanks for playing.\n");
        tree.resetCursor(); //reset game for next time
    }
}
