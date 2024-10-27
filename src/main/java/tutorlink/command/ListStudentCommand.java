package tutorlink.command;

import java.util.HashMap;

import tutorlink.appstate.AppState;
import tutorlink.commons.Commons;
import tutorlink.result.CommandResult;

public class ListStudentCommand extends Command {

    public static final String COMMAND_WORD = "list_student";


    @Override
    public CommandResult execute(AppState appState, HashMap<String, String> hashMap) {
        if (appState.students.size() < 1) {
            return new CommandResult(Commons.ERROR_NO_STUDENTS);
        }
        return new CommandResult(Commons.LIST_STUDENT_SUCCESS + "\n" + appState.students.toString());
    }

    @Override
    public String[] getArgumentPrefixes() {
        return null;
    }
}
