//@@author RCPilot1604
package tutorlink.command;

import java.util.HashMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tutorlink.appstate.AppState;
import tutorlink.commons.Commons;
import tutorlink.exceptions.IllegalValueException;
import tutorlink.exceptions.StudentNotFoundException;
import tutorlink.result.CommandResult;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

public class FindStudentCommandTest {
    private FindStudentCommand findCommand;
    private AddStudentCommand addCommand;
    private final AppState appState = new AppState();
    private HashMap<String, String> arguments;
    private CommandResult result;

    @BeforeEach
    void setup() {
        findCommand = new FindStudentCommand();
        addCommand = new AddStudentCommand();
        arguments = new HashMap<>();
        arguments.put("i/","A1234567X");
        arguments.put("n/", "John");
        result = addCommand.execute(appState, arguments);
        assertNotNull(result);
        arguments.put("i/","A2234567X");
        arguments.put("n/", "Jon");
        result = addCommand.execute(appState, arguments);
        assertNotNull(result);
    }

    @Test
    void execute_matric_one() {
        arguments.clear();
        arguments.put("i/","A1234567X");
        CommandResult result = findCommand.execute(appState,arguments);
        assertEquals(result.toString(), "\t1: John (matric no: A1234567X, percentage score: No Grades)");
    }

    @Test
    void execute_matric_none() {
        arguments.clear();
        arguments.put("i/","A1111111X");
        try {
            CommandResult result = findCommand.execute(appState, arguments);
        } catch (StudentNotFoundException e) {
            assertEquals(e.getMessage(), "No students with matricNumber " + "A1111111X" + " found");
        } catch (Exception e) {
            fail("Expected: StudentNotFoundException, Actual: " + e.getMessage());
        }
    }

    @Test
    void execute_name_two() {
        HashMap<String, String> arguments = new HashMap<>();
        arguments.put("n/","Jo");
        CommandResult result = findCommand.execute(appState,arguments);
        assertEquals(result.toString(), "\t1: John (matric no: A1234567X, percentage score: No Grades)" + "\n\t"
                + "2: Jon (matric no: A2234567X, percentage score: No Grades)");
    }

    @Test
    void execute_find_bothNull() {
        HashMap<String, String> arguments = new HashMap<>();
        try {
            CommandResult result = findCommand.execute(appState, arguments);
        } catch (IllegalValueException e) {
            assertEquals(e.getMessage(), Commons.ERROR_STUDENT_BOTH_NULL);
        } catch (Exception e) {
            fail("Expected: IllegalValueException, Actual: " + e.getMessage());
        }
    }

}
//@author
