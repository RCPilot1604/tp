package tutorlink.logic.result;

public class CommandResult {

    private static final String LINE_SEPARATOR = "\n\t";

    public final String message;

    public CommandResult(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }
}
