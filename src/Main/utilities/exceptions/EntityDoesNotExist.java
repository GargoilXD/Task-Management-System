package Main.utilities.exceptions;

public class EntityDoesNotExist extends RuntimeException {
    public EntityDoesNotExist(String message) {
        super(message);
    }
}
