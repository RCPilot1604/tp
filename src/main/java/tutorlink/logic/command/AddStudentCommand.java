package tutorlink.logic.command;

import java.util.HashMap;

import tutorlink.model.AppState;
import tutorlink.model.Commons;
import tutorlink.model.exceptions.IllegalValueException;
import tutorlink.model.exceptions.TutorLinkException;
import tutorlink.logic.result.CommandResult;

public class AddStudentCommand extends Command {

    public static final String[] ARGUMENT_PREFIXES = {"i/", "n/"};
    public static final String COMMAND_WORD = "add_student";

    @Override
    public CommandResult execute(AppState appState, HashMap<String, String> hashmap) throws TutorLinkException {
        String matricNumber = hashmap.get(ARGUMENT_PREFIXES[0]);
        String name = hashmap.get(ARGUMENT_PREFIXES[1]);
        if (matricNumber == null || name == null) {
            throw new IllegalValueException(Commons.ERROR_NULL);
        }
        appState.students.addStudent(matricNumber, name);
        return new CommandResult(String.format(Commons.ADD_STUDENT_SUCCESS, name, matricNumber));
    }

    @Override
    public String[] getArgumentPrefixes() {
        return ARGUMENT_PREFIXES;
    }
}
