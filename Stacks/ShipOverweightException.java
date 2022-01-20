//Shahrizod Bobojonov 114137520 Recitation 1

/**
 * Custom exception thrown when the cargo being pushed will make the ship overweight
 */
public class ShipOverweightException extends Exception {
    public ShipOverweightException(String message) {
        super(message);
    }
}
