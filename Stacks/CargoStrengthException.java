//Shahrizod Bobojonov 114137520 Recitation 1

/**
 * Custom exception thrown when trying to stack cargo that is not stackable
 */
public class CargoStrengthException extends Exception {
    public CargoStrengthException(String message) {
        super(message);
    }
}
