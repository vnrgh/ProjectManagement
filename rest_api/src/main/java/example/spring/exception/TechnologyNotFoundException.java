package example.spring.exception;

public class TechnologyNotFoundException extends RuntimeException {
    public TechnologyNotFoundException(String message) {
        super(message);
    }
}
