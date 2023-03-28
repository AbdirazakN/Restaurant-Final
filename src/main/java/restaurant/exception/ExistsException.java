package restaurant.exception;
public class ExistsException extends RuntimeException{
    public ExistsException() {
        super();
    }

    public ExistsException(String message) {
        super(message);
    }
}
