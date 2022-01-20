//Shahrizod Bobojonov, 114137520, Recitation 1

import java.util.InputMismatchException; //needed to catch when wrong input put in
import java.util.Scanner; //import Scanner in order to read user input

/**
 * This class represents a TeamManager which takes in the Team and Player classes
 * and provides a way to execute the various methods in the Team and Player
 * classes in a menu format. The number of teams it's able to manage is represented
 * by the constant MAX_TEAMS and each Team is stored in an array of size MAX_TEAMS.
 * There is also a teamNum variable in order to keep track of the active team
 * to perform menu operations on. Also created a final Scanner object in the
 * class so that the menu is able to access user input.
 */
public class TeamManager {
    public static final Scanner stdin = new Scanner(System.in);
    public static final int MAX_TEAMS = 5;
    private static final Team[] teams = new Team[MAX_TEAMS];
    private static int teamNum = 1;
    public static void main(String[] args) {
        teams[0] = new Team();
        System.out.println("Welcome to TeamManager! \n\nTeam 1 is currently selected");
        boolean endProgram = false;
        while (!endProgram) {
            endProgram = menu(teams[teamNum-1]);
        }
    }

    /**
     * Method that prints the menu as well as deals with corresponding options
     * when called. Called by main method over and over until menu option Q is
     * chosen which indicates that the program can quit. Since the menu takes
     * care of all the options as well, not just prints them, it catches
     * exceptions such as InputMismatchException, FullTeamException, and
     * IllegalArgumentException thrown by Team/Player/Scanner objects and prints
     * an error message, then returns to the main menu by returning false,
     * indicating that the program should not end yet.
     *
     * @param t
     *      takes a Team object when called in order to easily access methods
     *      without using teams[teamNum-1] every time.
     * @return
     *      a boolean value representing whether the program should end or not,
     *      only returns true when menu option Q is chosen.
     */
    public static boolean menu(Team t) {
        System.out.print("\nPlease select an option: \n" +
                        "A)  Add Player. \n" +
                        "G)  Get Player stats. \n" +
                        "L)  Get leader in a stat. \n" +
                        "R)  Remove a player. \n" +
                        "P)  Print all players. \n" +
                        "S)  Size of team. \n" +
                        "T)  Select team \n" +
                        "C)  Clone team \n" +
                        "E)  Team equals \n" +
                        "U)  Update stat. \n" +
                        "Q)  Quit. \n" +
                "Select a menu option: ");
        String sChoice = stdin.next();
        stdin.nextLine(); //Needed to clear Scanner buffer
        System.out.println();
        try {
            if (sChoice.length() > 1) {
                System.out.println("Invalid input, returning to main menu");
                return false;
            }
            char choice = Character.toUpperCase(sChoice.charAt(0));
            if (choice == 'A') {
                System.out.print("Enter the player's name: ");
                String name = stdin.nextLine();
                System.out.print("Enter the number of hits: ");
                int numHits = stdin.nextInt();
                System.out.print("Enter the number of errors: ");
                int numErrors = stdin.nextInt();
                System.out.print("Enter the position: ");
                int position = stdin.nextInt();
                try {
                    t.addPlayer(new Player(name, numHits, numErrors), position);
                } catch (FullTeamException e) {
                    System.out.println("Team is full, returning to main menu");
                } catch (IllegalArgumentException e) {
                    System.out.println("Invalid position to add the new player, returning to main menu");
                }
            } else if (choice == 'G') {
                System.out.print("Enter the position: ");
                int position = stdin.nextInt();
                try {
                    System.out.println("The player at position " + position + " is " + t.getPlayer(position));
                } catch (IllegalArgumentException e) {
                    System.out.println("No player in position, returning to main menu");
                }
            } else if (choice == 'L') {
                if (t == null || t.size() == 0) {
                    System.out.println("Team has no players");
                    return false;
                }
                System.out.print("Enter the stat (hits/errors): ");
                String stat = stdin.next().toLowerCase(); //to remove case sensitivity when printing
                try {
                    System.out.println("The current leader in " + stat + " is " + t.getLeader(stat));
                } catch (IllegalArgumentException e) {
                    System.out.println("No such statistic, returning to main menu");
                }
            } else if (choice == 'R') {
                System.out.print("Enter position of player: ");
                int position = stdin.nextInt();
                try {
                    t.removePlayer(position);
                } catch (IllegalArgumentException e) {
                    System.out.println("No player in position to remove, returning to main menu");
                }
            } else if (choice == 'P') {
                System.out.print("Select team index: ");
                int teamChoice = stdin.nextInt();
                if (teamChoice < 1 || teamChoice > MAX_TEAMS) {
                    System.out.println("Team index out of bounds, returning to main menu");
                    return false;
                }
                if (teams[teamChoice-1] == null || teams[teamChoice-1].size() == 0) {
                    System.out.println("Team has no players");
                    return false;
                }
                teams[teamChoice - 1].printAllPlayers();
            } else if (choice == 'S') {
                System.out.println("There are " + t.size() + " player(s) in the current team.");
            } else if (choice == 'T') {
                System.out.print("Enter team index to select: ");
                int teamChoice = stdin.nextInt();
                if (teamChoice < 1 || teamChoice > MAX_TEAMS) {
                    System.out.println("Team index is out of bounds, returning to main menu");
                    return false;
                }

                if (teams[teamChoice-1] == null)
                    teams[teamChoice-1] = new Team();

                teamNum = teamChoice;
                System.out.println("Team " + teamNum + " has been selected");
            } else if (choice == 'C') {
                System.out.print("Select team to clone from: ");
                int cloneFrom = stdin.nextInt();
                System.out.print("Select team to clone to: ");
                int cloneTo = stdin.nextInt();
                if (cloneFrom < 1 || cloneFrom > MAX_TEAMS || cloneTo < 1 || cloneTo > MAX_TEAMS) {
                    System.out.println("Team choice out of bounds, returning to main menu");
                    return false;
                }

                if (teams[cloneFrom-1] == null)
                    teams[cloneFrom-1] = new Team();
                if (teams[cloneTo-1] == null)
                    teams[cloneTo-1] = new Team();

                teams[cloneTo - 1] = (Team) teams[cloneFrom - 1].clone();
                System.out.println("Team " + cloneFrom + " cloned to Team " + cloneTo);
            } else if (choice == 'E') {
                System.out.print("Select first team index: ");
                int firstTeam = stdin.nextInt();
                System.out.print("Select second team index: ");
                int secondTeam = stdin.nextInt();
                if (firstTeam < 1 || firstTeam > MAX_TEAMS || secondTeam < 1 || secondTeam > MAX_TEAMS) {
                    System.out.println("Team choice out of bounds, returning to main menu");
                    return false;
                }

                if (teams[firstTeam-1] == null)
                    teams[firstTeam-1] = new Team();
                if (teams[secondTeam-1] == null)
                    teams[secondTeam-1] = new Team();

                if (teams[firstTeam - 1].equals(teams[secondTeam - 1])) {
                    System.out.println("These teams are equal");
                } else {
                    System.out.println("These teams are not equal");
                }
            } else if (choice == 'U') {
                System.out.print("Enter name of player to update: ");
                String name = stdin.nextLine();
                boolean match = false;
                int position = 1;
                for (int i = 1; i <= t.size(); i++) {
                    if (t.getPlayer(i).getName().equalsIgnoreCase(name)) {
                        match = true;
                        position = i;
                        break;
                    }
                }
                if (!match) {
                    System.out.println("Player not found, returning to main menu");
                    return false;
                }
                System.out.print("Enter stat to update (hits/errors): ");
                String stat = stdin.next();
                if (!(stat.equals("hits") || stat.equals("errors"))) {
                    System.out.println("No such statistic, returning to main menu");
                    return false;
                }
                System.out.print("Enter new number of " + stat + ": ");
                int newNum = stdin.nextInt();
                if (stat.equals("hits"))
                    t.getPlayer(position).setNumHits(newNum);
                else
                    t.getPlayer(position).setNumErrors(newNum);
                System.out.println("Player stats updated");
            } else if (choice == 'Q') {
                System.out.println("Program terminating normally...");
                return true;
            } else {
                System.out.println("Invalid input, returning to main menu");
            }
        } catch (InputMismatchException | IllegalArgumentException e) {
            System.out.println("Invalid input, returning to main menu");
            stdin.nextLine(); //needed to clear Scanner buffer
        }
        return false;
    }
}