package tutorlink.command;

import java.util.HashMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tutorlink.appstate.AppState;
import tutorlink.commons.Commons;
import tutorlink.component.Component;
import tutorlink.exceptions.ComponentNotFoundException;
import tutorlink.exceptions.IllegalValueException;
import tutorlink.grade.Grade;
import tutorlink.result.CommandResult;
import tutorlink.student.Student;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

public class DeleteComponentCommandTest {
    private AppState appState;
    private DeleteComponentCommand command = new DeleteComponentCommand();
    private HashMap<String,String> arguments;
    private CommandResult result;
    @BeforeEach
    void setup() {
        appState = new AppState();
        appState.components.addComponent(new Component("finals", 40.0, 40));
        appState.components.addComponent(new Component("iP", 20.0, 10));
        appState.components.addComponent(new Component("lectures", 10.0, 10));
    }

    @Test
    void deleteComponent_success() {
        arguments = new HashMap<>();
        arguments.put("c/", "finals");
        int initialWeighting = appState.components.getTotalWeighting();
        result = command.execute(appState, arguments);
        assertNotNull(result);
        assertEquals(appState.components.size(), 2);
        assertEquals(appState.components.getTotalWeighting(), initialWeighting - 40);
    }

    @Test
    void deleteComponent_notFound_fail() {
        arguments = new HashMap<>();
        arguments.put("c/", "midterms");
        int initialWeighting = appState.components.getTotalWeighting();
        try {
            command.execute(appState, arguments);
        } catch (ComponentNotFoundException e) {
            assertEquals(e.getMessage(), "Error! Component midterms does not exist in the list!");
        } catch (Exception e) {
            fail("Expected: ComponentNotFoundException, actual: " + e.getMessage());
        } finally {
            assertEquals(appState.components.getTotalWeighting(), initialWeighting);
        }
    }

    @Test
    void deleteComponent_emptyParam_fail() {
        arguments = new HashMap<>();
        int initialWeighting = appState.components.getTotalWeighting();
        try {
            command.execute(appState, arguments);
        } catch (IllegalValueException e) {
            assertEquals(e.getMessage(), Commons.ERROR_NULL);
        } catch (Exception e) {
            fail("Expected: ComponentNotFoundException, actual: " + e.getMessage());
        } finally {
            assertEquals(appState.components.getTotalWeighting(), initialWeighting);
        }
    }

    @Test
    void deleteComponent_onSuccess_checkAllGradesDeleted() {
        appState.students.addStudent("A0276007H", "Ethan");
        appState.students.addStudent("A1234567X", "John");
        appState.grades.addGrade(new Grade(appState.components.getComponentArrayList().get(0),
                appState.students.getStudentArrayList().get(0), 30));
        appState.grades.addGrade(new Grade(appState.components.getComponentArrayList().get(1),
                appState.students.getStudentArrayList().get(0), 20));
        appState.grades.addGrade(new Grade(appState.components.getComponentArrayList().get(2),
                appState.students.getStudentArrayList().get(0), 8.5));
        assertEquals(appState.grades.getGradeArrayList().size(), 3);
        appState.grades.addGrade(new Grade(appState.components.getComponentArrayList().get(0),
                appState.students.getStudentArrayList().get(1), 30));
        assertEquals(appState.grades.getGradeArrayList().size(), 4);
        arguments = new HashMap<>();
        arguments.put("c/", "finals");
        command.execute(appState, arguments);
        assertEquals(appState.grades.getGradeArrayList().size(), 2);
    }
}
