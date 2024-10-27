package tutorlink.logic;

import java.util.HashMap;
import tutorlink.logic.command.Command;
import tutorlink.logic.parser.Parser;
import tutorlink.logic.result.CommandResult;
import tutorlink.model.AppState;

public class Logic {
    private static final Parser parser = new Parser();
    private static Logic singleton = null;
    private Logic() {

    }

    public static Logic getInstance() {
        if(singleton == null) {
            singleton = new Logic();
        }
        return singleton;
    }

    public CommandResult execute(AppState appState, String line) {
        //@@author yeekian
        Command currentCommand = parser.getCommand(line);
        HashMap<String, String> arguments = parser.getArguments(currentCommand.getArgumentPrefixes(), line);
        CommandResult res = currentCommand.execute(appState, arguments);
        //@@author
        return res;
    }

}
