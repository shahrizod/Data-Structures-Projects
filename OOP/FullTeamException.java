//Shahrizod Bobojonov, 114137520, Recitation 1

/**
 * Custom exception to be used when the team is full, typically called when
 * attempting to add players
 */
public class FullTeamException extends Exception {
    public FullTeamException(String message) {
        super(message);
    }
}
