//Shahrizod Bobojonov 114137520 Recitation 1

import java.util.Scanner;

/**
 * This class ties the other classes together so that the user can interact
 * with them. It prompts them for whether they want to add a page to the
 * database, sort it and if so by what column, as well as print the table
 * representing the database, or quitting the program entirely
 */
public class NeoViewer {
    public static void main(String[] args) {
        System.out.println("Welcome to NEO Viewer");
        Scanner stdin = new Scanner(System.in);
        NeoDatabase data = new NeoDatabase();
        while(true) {
            System.out.print("\nOption Menu:\n" +
                    "\tA) Add a page to the database\n" +
                    "\tS) Sort the database\n" +
                    "\tP) Print the database as a table\n" +
                    "\tQ) Quit\n" +
                    "\nSelect a menu option: ");
            String answer = stdin.nextLine();
            if (answer.equalsIgnoreCase("A")) {
                System.out.print("Enter the page to load: ");
                int pageNum = stdin.nextInt();
                try {
                    data.addAll(data.buildQueryURL(pageNum));
                    System.out.println("Page loaded successfully!");
                } catch (IllegalArgumentException e) {
                    System.out.println("Invalid page number, returning to main menu...");
                }
                stdin.nextLine();
            } else if (answer.equalsIgnoreCase("S")) {
                if (data.isEmpty()) {
                    System.out.println("Database is empty, returning to main menu...");
                    continue;
                }
                System.out.print("\nR) Sort by referenceID" +
                        "\nD) Sort by Diameter" +
                        "\nA) Sort by Approach Date" +
                        "\nM) Sort by Miss Distance" +
                        "\n\nSelect a menu option: ");
                String sortChoice = stdin.nextLine();
                if (sortChoice.equalsIgnoreCase("R")) {
                    data.sort(new ReferenceIDComparator());
                    System.out.println("Table sorted by reference ID");
                } else if (sortChoice.equalsIgnoreCase("D")) {
                    data.sort(new DiameterComparator());
                    System.out.println("Table sorted by diameter");
                } else if (sortChoice.equalsIgnoreCase("A")) {
                    data.sort(new ApproachDateComparator());
                    System.out.println("Table sorted by approach date");
                } else if (sortChoice.equalsIgnoreCase("M")) {
                    data.sort(new MissDistanceComparator());
                    System.out.println("Table sorted by miss distance");
                } else {
                    System.out.println("Invalid input, returning to main menu...");
                }
            } else if (answer.equalsIgnoreCase("P")) {
                data.printTable();
            } else if (answer.equalsIgnoreCase("Q")) {
                System.out.println("Terminating program, goodbye...");
                break;
            } else {
                System.out.println("Invalid input, please try again!");
            }
        }
    }
}
