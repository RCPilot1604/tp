package tutorlink.command;

import tutorlink.appstate.AppState;
import tutorlink.commons.Commons;
import tutorlink.component.Component;
import tutorlink.exceptions.ComponentNotFoundException;
import tutorlink.exceptions.IllegalValueException;
import tutorlink.exceptions.InvalidWeightingException;
import tutorlink.exceptions.TutorLinkException;
import tutorlink.lists.ComponentList;
import tutorlink.result.CommandResult;

import java.util.HashMap;

public class UpdateComponentCommand extends Command {
    public static final String[] ARGUMENT_PREFIXES = {"c/", "w/", "m/"};
    public static final String COMMAND_WORD = "update_component";

    @Override
    public CommandResult execute(AppState appState, HashMap<String, String> hashmap) throws TutorLinkException {
        String name = hashmap.get(ARGUMENT_PREFIXES[0]);
        String cWeight = hashmap.get(ARGUMENT_PREFIXES[1]);
        String cMark = hashmap.get(ARGUMENT_PREFIXES[2]);
        if (name == null || (cWeight == null && cMark == null)) {
            StringBuilder sb = new StringBuilder();
            sb.append("Error!");
            if(name != null){
                sb.append(" n/ is required!");
            }
            if(cWeight != null && cMark != null){
                sb.append(" either w/ or m/ or both are required!");
            }
            throw new IllegalValueException(sb.toString());
        }
        Integer weight = null;
        Double mark = null;
        try {
            if (cWeight != null) {
                weight = Integer.parseInt(cWeight);
            }
            if (cMark != null) {
                mark = Double.parseDouble(cMark);
            }
        } catch (NumberFormatException e) {
            throw new IllegalValueException("Error! weight is not an integer or mark is not a real positive number");
        }
        if (mark != null && (mark.isNaN() || mark.isInfinite() || mark < 0)) {
            throw new IllegalValueException("Error! Mark is not a real positive number");
        }

        if (weight != null && (weight < 0 || weight > 100)) {
            throw new IllegalValueException("Error! weight must be between 0 and 100");
        }

        ComponentList componentsToEdit = appState.components.findComponent(name);

        if (componentsToEdit.size() == 0) {
            throw new ComponentNotFoundException(String.format(Commons.ERROR_COMPONENT_NOT_FOUND, name));
        }

        Component component = componentsToEdit.getComponentArrayList().get(0);

        int oldWeight = component.getWeight();
        if (weight != null) {
            component.setWeight(weight);
            int totalWeight = appState.components.getTotalWeighting();
            if ((totalWeight) > 100) {
                component.setWeight(oldWeight);
                throw new InvalidWeightingException(String.format(Commons.ERROR_INVALID_TOTAL_WEIGHTING,
                        totalWeight));
            }
        }

        if (mark != null) {
            component.setMaxScore(mark);
        }

        return new CommandResult("Here is the updated component\n" + component.toString());
    }

    @Override
    public String[] getArgumentPrefixes() {
        return ARGUMENT_PREFIXES;
    }
}
