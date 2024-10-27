//@@author RCPilot1604
package tutorlink.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tutorlink.logic.command.AddStudentCommand;
import tutorlink.logic.command.DeleteStudentCommand;
import tutorlink.model.AppState;
import tutorlink.model.Commons;
import tutorlink.model.component.Assignment;
import tutorlink.model.exceptions.IllegalValueException;
import tutorlink.model.exceptions.StudentNotFoundException;
import tutorlink.model.grade.Grade;
import tutorlink.logic.result.CommandResult;
import java.util.HashMap;
import tutorlink.model.student.Student;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

public class DeleteStudentCommandTest {
    private DeleteStudentCommand deleteCommand = new DeleteStudentCommand();
    private AddStudentCommand addCommand = new AddStudentCommand();
    private AppState appState;
    private HashMap<String, String> arguments = new HashMap<>();
    private CommandResult result;

    @BeforeEach
    void setup() {
        appState = new AppState();
        appState.students.addStudent("A1234567X", "John Doe");
        appState.students.addStudent("A7654321B", "Jane Smith");
        appState.students.addStudent("A2468102C", "Arthur Mueller");

        appState.grades.addGrade(new Grade(new Assignment("Take Home Quiz 1", 10.0,
                0.2), new Student("A1234567X", "John Doe"), 7.0));
        appState.grades.addGrade(new Grade(new Assignment("Take Home Quiz 2", 10.0,
                0.2), new Student("A1234567X", "John Doe"), 9.0));
        appState.grades.addGrade(new Grade(new Assignment("Take Home Quiz 1", 10.0,
                0.2), new Student("A7654321B", "Jane Smith"), 9.0));
    }

    @Test
    void execute_delete_success() {
        arguments.clear();
        arguments.put("i/","A1234567X");
        result = deleteCommand.execute(appState, arguments);
        assertNotNull(result);
        assertEquals(result.message, "Student A1234567X successfully deleted");
        assertEquals(appState.students.getStudentArrayList().size(), 2);
        assertEquals(appState.grades.getGradeArrayList().size(), 1);
        arguments.clear();
        arguments.put("i/","A7654321B");
        result = deleteCommand.execute(appState, arguments);
        assertNotNull(result);
        assertEquals(result.message, "Student A7654321B successfully deleted");
        assertEquals(appState.students.getStudentArrayList().size(), 1);
        assertEquals(appState.grades.getGradeArrayList().size(), 0);
    }

    @Test
    void execute_delete_matricNull() {
        arguments.clear();
        arguments.put("n/","John Doe");
        try {
            result = deleteCommand.execute(appState, arguments);
        } catch (IllegalValueException e) {
            assertEquals(e.getMessage(), Commons.ERROR_NULL);
        } catch (Exception e) {
            fail("Expected: IllegalValueException, Actual: " + e.getMessage());
        }
    }

    @Test
    void execute_delete_studentNotFound() {
        arguments.clear();
        arguments.put("i/","A9999999X");
        try {
            result = deleteCommand.execute(appState, arguments);
        } catch (StudentNotFoundException e) {
            assertEquals(e.getMessage(), String.format("Error! Student (Matric Number A9999999X) not found"));
        }
    }
}
//@@author
