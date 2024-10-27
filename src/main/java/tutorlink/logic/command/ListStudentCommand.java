package tutorlink.logic.command;

import java.util.HashMap;

import tutorlink.model.AppState;
import tutorlink.logic.result.CommandResult;

public class ListStudentCommand extends Command {

    public static final String COMMAND_WORD = "list_student";


    @Override
    public CommandResult execute(AppState appState, HashMap<String, String> hashMap) {
        return new CommandResult(appState.students.toString());
    }

    @Override
    public String[] getArgumentPrefixes() {
        return null;
    }
}
