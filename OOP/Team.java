//Shahrizod Bobojonov, 114137520, Recitation 1

/**
 * This class represents a Team that has a constant representing the maximum
 * number of players allowed on the team, an array storing Player objects
 * representing the roster of the team, and a variable to store the size of the team
 * @author Shahrizod Bobojonov
 */
public class Team {
    public static final int MAX_PLAYERS = 40;
    private final Player[] players;
    private int numPlayers;

    /**
     * No argument Constructor for Team object that initializes empty Player
     * array with the max size indicated by the constant defined earlier in the
     * class and initializes the size of the team to 0.
     */
    public Team() {
        players = new Player[MAX_PLAYERS];
        numPlayers = 0;
    }

    /**
     * Override <CODE>clone()</CODE> method to deep-copy Team object and return it
     *
     * @return
     *      Returns deep-copy of Team object that must be typecast before use.
     */
    public Object clone() {
        Team answer = new Team();
        for (int i = 0; i < numPlayers; i++) {
            answer.players[i] = (Player) players[i].clone();
        }
        answer.numPlayers = this.numPlayers;
        return answer;
    }

    /**
     * Override <CODE>equals(obj)</CODE> method to check whether an Object has
     * equivalent data to the Team object
     *
     * @param obj
     *      Object to check if equal to Team object method is called from
     * @return
     *      boolean value representing if the two objects are equal or not
     */
    public boolean equals(Object obj) {
        if (obj instanceof Team) {
            Team t = (Team) obj;
            if (this.numPlayers == t.numPlayers) {
                for (int i = 0; i < numPlayers; i++) {
                    if (!(this.players[i].equals(t.players[i]))) {
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }

    /**
     * Accessor method for MAX_PlAYERS, or players.length, indicating max capacity
     *
     * @return
     *      value of players.length, which is the maximum number of players the
     *      array can hold
     */
    public int getCapacity() {
        return players.length;
    }

    /**
     * Accessor method for numPlayers, tracking the size of the team
     *
     * @return
     *      number of players on the team at the time of method call, tracked
     *      by numPlayers throughout class
     */
    public int size() {
        return numPlayers;
    }

    /**
     * Adds player to the team using given parameters of Player object and
     * position in which to insert player. Position must be between 1 and the
     * number of players already on the team + 1, meaning that there can be no
     * empty spaces between players. If player is added to a point with players
     * to its right, all the other players are shifted to accommodate the new
     * player as well
     *
     * @param p
     *      Player object to be added to the Team
     * @param position
     *      position in which to add the Player at, limited from 1 to the number
     *      of players on the team + 1, as well as the max capacity of the team
     * @throws FullTeamException
     *      when the number of players on the team will exceed the max capacity
     *      of the team if the player is added
     * @throws IllegalArgumentException
     *      when the position in the parameter is out of the bounds of 1 to the
     *      number of players on the team + 1
     */
    public void addPlayer(Player p, int position) throws FullTeamException, IllegalArgumentException {
        if (position < 1 || position > numPlayers + 1) throw new IllegalArgumentException();
        if (numPlayers >= MAX_PLAYERS) throw new FullTeamException("Team is already full!");
        for (int i = numPlayers; i > position - 1; i--) {
            players[i] = players[i - 1];
        }
        players[position - 1] = p;
        numPlayers++;
        System.out.println("Player added: " + p);
    }

    /**
     * Removes the player at the given position on the Team and shifts players
     * accordingly. Player position must be greater than and equal to 0 and less
     * than or equal to the number of players on the team in order to ensure that
     * there is actually a player to remove.
     *
     * @param position
     *      tells method which player to remove and is restricted in that there
     *      must be a player in that position, so it needs to be greater than or
     *      equal to 1 and less than or equal to the number of players on the
     *      Team
     * @throws IllegalArgumentException
     *      when the position given is out of the bounds
     */
    public void removePlayer(int position) throws IllegalArgumentException {
        if (position > numPlayers || position < 1) throw new IllegalArgumentException();
        for (int i = position - 1; i < numPlayers-1; i++) {
            players[i] = players[i + 1];
        }
        players[numPlayers-1] = null;
        numPlayers--;
        System.out.println("Player at position " + position + " removed\n");

    }

    /**
     * Returns the Player at the given position on the Team with their name and
     * stats. Also throws an exception when the position given is out of bounds
     *
     * @param position
     *      the position of the player on the Team which should be returned
     * @return
     *      the Player object at the specified position
     * @throws IllegalArgumentException
     *      when the position is less than 1 or greater than the number of
     *      players on the team, meaning there is no player there
     */
    public Player getPlayer(int position) throws IllegalArgumentException {
        if (position < 1 || position > numPlayers) {
            throw new IllegalArgumentException();
        }
        return players[position - 1];
    }

    /**
     * Returns the Player object with the best hits/errors on the Team based on
     * the stat parameter passed to it. If hits, it returns the Player object
     * with the highest number of hits on the Team, and if errors, then it
     * returns the Player object with the lowest number of errors on the Team
     *
     * @param stat
     *      the stat in which to find the best performing player in, which means
     *      the player with the highest hits or the lowest errors depending on
     *      what stat is passed. Only valid arguments are "hits" or "errors",
     *      not case-sensitive.
     * @return
     *      the player with the best hits/errors
     * @throws IllegalArgumentException
     *      when the stat parameter was neither hits nor errors
     */
    public Player getLeader(String stat) throws IllegalArgumentException {
        stat  = stat.toLowerCase(); //removes case sensitivity
        if (!(stat.equals("hits") || stat.equals("errors"))) {
            throw new IllegalArgumentException();
        }
        Player leader = players[0];
        if (stat.equals("hits")) {
            for (int i = 1; i < numPlayers; i++) {
                if (players[i].getNumHits() > leader.getNumHits()) {
                    leader = players[i];
                }
            }
        } else {
            for (int i = 1; i < numPlayers; i++) {
                if (players[i].getNumErrors() < leader.getNumErrors()) {
                    leader = players[i];
                }
            }
        }
        return leader;
    }

    /**
     * Calls <CODE>toString()</CODE> method for the object and prints
     */
    public void printAllPlayers() {
        System.out.println(this);
    }

    /**
     * Override <CODE>toString()</CODE> method specifying how Team object should
     * be represented when printed
     *
     * @return
     *      String representation of the team in tabular form with player#, name,
     *      hits, and errors as the heading and then a loop to concatenate values
     *      of each player afterwards
     */
    @Override
    public String toString() {
        String result = "\nPlayer#     Name                     Hits        Errors      \n";
        result+="-".repeat(61)+"\n";
        for (int i = 0; i < numPlayers; i++) {
            result+=String.format("%-12d%-25s%-12d%-12d\n", i + 1,
                    players[i].getName(), players[i].getNumHits(), players[i].getNumErrors());
        }
        return result;
    }
}