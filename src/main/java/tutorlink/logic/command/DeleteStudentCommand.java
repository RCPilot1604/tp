package tutorlink.logic.command;

import java.util.HashMap;

import tutorlink.model.AppState;
import tutorlink.model.Commons;
import tutorlink.model.exceptions.IllegalValueException;
import tutorlink.model.exceptions.TutorLinkException;
import tutorlink.logic.result.CommandResult;

public class DeleteStudentCommand extends Command {

    public static final String[] ARGUMENT_PREFIXES = {"i/"};
    public static final String COMMAND_WORD = "delete_student";

    @Override
    public CommandResult execute(AppState appState, HashMap<String, String> hashmap) throws TutorLinkException {
        String matricNumber = hashmap.get(ARGUMENT_PREFIXES[0]);
        if (matricNumber == null) {
            throw new IllegalValueException(Commons.ERROR_NULL);
        }
        appState.students.deleteStudent(matricNumber);
        appState.grades.deleteGradesByMatric(matricNumber);
        return new CommandResult(String.format(Commons.DELETE_STUDENT_SUCCESS, matricNumber));
    }

    @Override
    public String[] getArgumentPrefixes() {
        return ARGUMENT_PREFIXES;
    }
}
