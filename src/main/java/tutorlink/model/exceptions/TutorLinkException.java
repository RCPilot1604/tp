package tutorlink.model.exceptions;

public abstract class TutorLinkException extends RuntimeException {
    public TutorLinkException(String message) {
        super(message);
    }
}
