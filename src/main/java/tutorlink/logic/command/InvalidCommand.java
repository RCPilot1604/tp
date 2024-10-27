package tutorlink.logic.command;

import tutorlink.model.AppState;
import tutorlink.model.Commons;
import tutorlink.model.exceptions.InvalidCommandException;
import tutorlink.model.exceptions.TutorLinkException;
import tutorlink.logic.result.CommandResult;

import java.util.HashMap;

public class InvalidCommand extends Command {

    @Override
    public CommandResult execute(AppState appState, HashMap<String, String> hashMap)  throws TutorLinkException {
        throw new InvalidCommandException(Commons.ERROR_INVALID_COMMAND);
    }

    @Override
    public String[] getArgumentPrefixes() {
        return null;
    }
}
