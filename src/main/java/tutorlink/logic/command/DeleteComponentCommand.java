package tutorlink.logic.command;

import java.util.HashMap;
import tutorlink.model.AppState;
import tutorlink.model.Commons;
import tutorlink.model.exceptions.ComponentNotFoundException;
import tutorlink.model.exceptions.DuplicateComponentException;
import tutorlink.model.exceptions.IllegalValueException;
import tutorlink.model.exceptions.TutorLinkException;
import tutorlink.model.lists.ComponentList;
import tutorlink.logic.result.CommandResult;

public class DeleteComponentCommand extends Command{
    public static final String[] ARGUMENT_PREFIXES = {"n/"};
    public static final String COMMAND_WORD = "delete_component";

    @Override
    public CommandResult execute(AppState appState, HashMap<String, String> hashmap) throws TutorLinkException {
        String componentName = hashmap.get(ARGUMENT_PREFIXES[0]);
        if(componentName == null) {
            throw new IllegalValueException(Commons.ERROR_NULL);
        }
        ComponentList componentsToDelete = appState.components.findComponent(componentName);
        if(componentsToDelete.size() == 0) {
            throw new ComponentNotFoundException(String.format(Commons.ERROR_COMPONENT_NOT_FOUND, componentName));
        } else if (componentsToDelete.size() > 1) {
            throw new DuplicateComponentException(Commons.ERROR_DUPLICATE_COMPONENT);
        }
        appState.components.deleteComponent(componentsToDelete.getComponentArrayList().get(0));
        return new CommandResult(String.format(Commons.DELETE_COMPONENT_SUCCESS, componentName));
    }

    @Override
    public String[] getArgumentPrefixes() {
        return ARGUMENT_PREFIXES;
    }
}
