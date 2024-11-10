//@@author yeekian
package tutorlink.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tutorlink.appstate.AppState;
import tutorlink.commons.Commons;
import tutorlink.exceptions.DuplicateComponentException;
import tutorlink.exceptions.IllegalValueException;
import tutorlink.exceptions.InvalidWeightingException;
import tutorlink.result.CommandResult;


import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AddComponentCommandTest {
    private AppState appState;
    private HashMap<String, String> arguments;
    private AddComponentCommand command;

    @BeforeEach
    void setup() {
        appState = new AppState();
        arguments = new HashMap<>();
        command = new AddComponentCommand();
    }

    @Test
    void execute_validArguments_componentAddedSuccessfully() {
        arguments.put("c/", "Quiz 1");
        arguments.put("w/", "30");
        arguments.put("m/", "100");
        int initialWeighting = appState.components.getTotalWeighting();
        CommandResult result = command.execute(appState, arguments);
        assertNotNull(result);
        assertEquals("Component Quiz 1 of weight 30%, with max score 100 added successfully!", result.toString());
        assertEquals(1, appState.components.getComponentArrayList().size());
        assertEquals(initialWeighting + 30, appState.components.getTotalWeighting());
    }

    @Test
    void execute_nullArguments_throwsIllegalValueException() {
        int initialWeighting = appState.components.getTotalWeighting();
        IllegalValueException exception = assertThrows(IllegalValueException.class, () -> {
            command.execute(appState, arguments);
        });
        assertEquals("Error! Null parameter passed!", exception.getMessage());
        assertEquals(initialWeighting, appState.components.getTotalWeighting());
    }

    @Test
    void execute_missingWeightageArgument_throwsIllegalValueException() {
        arguments.put("c/", "Quiz 1");
        arguments.put("m/", "100");
        int initialWeighting = appState.components.getTotalWeighting();
        IllegalValueException exception = assertThrows(IllegalValueException.class, () -> {
            command.execute(appState, arguments);
        });
        assertEquals("Error! Null parameter passed!", exception.getMessage());
        assertEquals(initialWeighting, appState.components.getTotalWeighting());
    }

    @Test
    void execute_extraArgument_componentAddedSuccessfully() {
        arguments.put("c/", "Quiz 1");
        arguments.put("w/", "50");
        arguments.put("m/", "100");
        arguments.put("extra/", "extra value");
        int initialWeighting = appState.components.getTotalWeighting();
        CommandResult result = command.execute(appState, arguments);
        assertNotNull(result);
        assertEquals("Component Quiz 1 of weight 50%, with max score 100 added successfully!", result.toString());
        assertEquals(1, appState.components.getComponentArrayList().size());
        assertEquals(initialWeighting + 50, appState.components.getTotalWeighting());
    }

    @Test
    void execute_weightageOutOfRange_throwsIllegalValueException() {
        arguments.put("c/", "Quiz 1");
        arguments.put("w/", "1.5");
        arguments.put("m/", "100");
        int initialWeighting = appState.components.getTotalWeighting();
        IllegalValueException exception = assertThrows(IllegalValueException.class, () -> {
            command.execute(appState, arguments);
        });
        assertEquals(Commons.ERROR_INVALID_WEIGHTAGE, exception.getMessage());
        assertEquals(initialWeighting, appState.components.getTotalWeighting());
    }

    @Test
    void execute_negativeWeighting_throwsIllegalValueException() {
        arguments.put("c/", "Quiz 1");
        arguments.put("w/", "-1");
        arguments.put("m/", "100");
        int initialWeighting = appState.components.getTotalWeighting();
        IllegalValueException exception = assertThrows(IllegalValueException.class, () -> {
            command.execute(appState, arguments);
        });
        assertEquals(Commons.ERROR_INVALID_WEIGHTAGE, exception.getMessage());
        assertEquals(initialWeighting, appState.components.getTotalWeighting());
    }

    @Test
    void execute_negativeMaxScore_throwsIllegalValueException() {
        arguments.put("c/", "Quiz 1");
        arguments.put("w/", "0.4");
        arguments.put("m/", "-10");
        int initialWeighting = appState.components.getTotalWeighting();
        IllegalValueException exception = assertThrows(IllegalValueException.class, () -> {
            command.execute(appState, arguments);
        });
        assertEquals(Commons.ERROR_INVALID_WEIGHTAGE, exception.getMessage());
        assertEquals(initialWeighting, appState.components.getTotalWeighting());
    }

    @Test
    void execute_weightageNotInt_throwsIllegalValueException() {
        arguments.put("c/", "Quiz 1");
        arguments.put("w/", "one");
        arguments.put("m/", "100");
        int initialWeighting = appState.components.getTotalWeighting();
        IllegalValueException exception = assertThrows(IllegalValueException.class, () -> {
            command.execute(appState, arguments);
        });
        assertEquals(Commons.ERROR_INVALID_WEIGHTAGE, exception.getMessage());
        assertEquals(initialWeighting, appState.components.getTotalWeighting());
    }

    @Test
    void execute_maxScoreNotDouble_throwsIllegalValueException() {
        arguments.put("c/", "Quiz 1");
        arguments.put("w/", "0.4");
        arguments.put("m/", "hundred");
        int initialWeighting = appState.components.getTotalWeighting();
        IllegalValueException exception = assertThrows(IllegalValueException.class, () -> {
            command.execute(appState, arguments);
        });
        assertEquals(Commons.ERROR_INVALID_WEIGHTAGE, exception.getMessage());
        assertEquals(initialWeighting, appState.components.getTotalWeighting());
    }

    @Test
    void execute_maxWeightageExceeded_throwsInvalidWeightingException() {
        arguments.put("c/", "Quiz 1");
        arguments.put("w/", "100");
        arguments.put("m/", "100");

        CommandResult result = command.execute(appState, arguments);
        assertNotNull(result);

        int initialWeighting = appState.components.getTotalWeighting();

        arguments.clear();
        arguments.put("c/", "Quiz 2");
        arguments.put("w/", "1");
        arguments.put("m/", "100");

        InvalidWeightingException exception = assertThrows(InvalidWeightingException.class, () -> {
            command.execute(appState, arguments);
        });
        assertEquals(String.format(Commons.ERROR_INVALID_TOTAL_WEIGHTING, appState.components.getTotalWeighting() + 1),
                exception.getMessage());
        assertEquals(initialWeighting, appState.components.getTotalWeighting());
    }

    @Test
    void execute_duplicateComponents_totalWeightingUnchanged() {
        arguments.put("c/", "Quiz 1");
        arguments.put("w/", "20");
        arguments.put("m/", "100");

        assertEquals(0, appState.components.getTotalWeighting());

        command.execute(appState, arguments);

        assertEquals(20, appState.components.getTotalWeighting());

        DuplicateComponentException exception = assertThrows(DuplicateComponentException.class, () -> {
            command.execute(appState, arguments);
        });

        assertEquals(20, appState.components.getTotalWeighting());
    }

    @Test
    void execute_negativeWeighting_totalWeightingUnchanged() {
        arguments.put("c/", "Quiz 1");
        arguments.put("w/", "20");
        arguments.put("m/", "100");

        assertEquals(0, appState.components.getTotalWeighting());
        command.execute(appState, arguments);
        assertEquals(20, appState.components.getTotalWeighting());

        arguments.clear();
        arguments.put("c/", "Quiz 2");
        arguments.put("w/", "-20");
        arguments.put("m/", "100");
        IllegalValueException exception = assertThrows(IllegalValueException.class, () -> {
            command.execute(appState, arguments);
        });
        assertEquals(Commons.ERROR_INVALID_WEIGHTAGE, exception.getMessage());
        assertEquals(20, appState.components.getTotalWeighting());
    }
}
//@@author
