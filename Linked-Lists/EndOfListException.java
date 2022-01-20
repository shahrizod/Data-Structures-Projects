//Shahrizod Bobojonov, 114137520, Recitation 1

/**
 * Custom exception for when the cursor reaches the end of the list and can't
 * go any further, either front or back
 */
public class EndOfListException extends Exception {
    public EndOfListException(String message) {
        super(message);
    }
}
