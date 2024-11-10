package tutorlink.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tutorlink.appstate.AppState;
import tutorlink.component.Component;
import tutorlink.exceptions.TutorLinkException;
import tutorlink.grade.Grade;
import tutorlink.student.Student;

import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class UpdateComponentCommandTest {
    private UpdateComponentCommand command;
    private AppState appState;
    private Component targetDummy;

    @BeforeEach
    void setUp() {
        command = new UpdateComponentCommand();
        createMockAppState();
    }

    @Test
    void normal_both_arguments() {
        HashMap<String, String> args = new HashMap<>();
        args.put("c/", "finals");
        args.put("m/", "30");
        args.put("w/", "30");

        assertDoesNotThrow(() -> command.execute(appState, args));
        assertEquals(targetDummy.getMaxScore(), 30);
        assertEquals(targetDummy.getWeight(), 30);
    }

    @Test
    void normal_only_weight() {
        HashMap<String, String> args = new HashMap<>();
        args.put("c/", "finals");
        args.put("w/", "30");

        assertDoesNotThrow(() -> command.execute(appState, args));
        assertEquals(targetDummy.getMaxScore(), 40);
        assertEquals(targetDummy.getWeight(), 30);
    }

    @Test
    void normal_only_mark() {
        HashMap<String, String> args = new HashMap<>();
        args.put("c/", "finals");
        args.put("m/", "30");

        assertDoesNotThrow(() -> command.execute(appState, args));
        assertEquals(targetDummy.getMaxScore(), 30);
        assertEquals(targetDummy.getWeight(), 40);
    }

    @Test
    void normal_updated_score() {
        HashMap<String, String> args = new HashMap<>();
        args.put("c/", "finals");
        args.put("m/", "30");

        assertDoesNotThrow(() -> command.execute(appState, args));
        assertEquals(targetDummy.getMaxScore(), 30);
        assertEquals(targetDummy.getWeight(), 40);
        for (Grade grade : appState.grades.getGradeArrayList()) {
            if (grade.getComponent().equals(targetDummy)) {
                assertEquals(grade.getScore(), 30);
            }
        }
    }


    @Test
    void error_no_args() {
        HashMap<String, String> args = new HashMap<>();

        assertThrows(TutorLinkException.class, () -> command.execute(appState, args));
        assertEquals(targetDummy.getMaxScore(), 40);
        assertEquals(targetDummy.getWeight(), 40);
    }

    @Test
    void error_exceed_weight() {
        HashMap<String, String> args = new HashMap<>();
        args.put("c/", "finals");
        args.put("w/", "70");

        assertThrows(TutorLinkException.class, () -> command.execute(appState, args));
        assertEquals(targetDummy.getMaxScore(), 40);
        assertEquals(targetDummy.getWeight(), 40);
    }

    @Test
    void error_negative_weight() {
        HashMap<String, String> args = new HashMap<>();
        args.put("c/", "finals");
        args.put("w/", "-30");

        assertThrows(TutorLinkException.class, () -> command.execute(appState, args));
        assertEquals(targetDummy.getMaxScore(), 40);
        assertEquals(targetDummy.getWeight(), 40);
    }

    @Test
    void error_weight_over100() {
        HashMap<String, String> args = new HashMap<>();
        args.put("c/", "finals");
        args.put("w/", "101");

        assertThrows(TutorLinkException.class, () -> command.execute(appState, args));
        assertEquals(targetDummy.getMaxScore(), 40);
        assertEquals(targetDummy.getWeight(), 40);
    }

    @Test
    void error_nan_weight() {
        HashMap<String, String> args = new HashMap<>();
        args.put("c/", "finals");
        args.put("w/", "abc");

        assertThrows(TutorLinkException.class, () -> command.execute(appState, args));
        assertEquals(targetDummy.getMaxScore(), 40);
        assertEquals(targetDummy.getWeight(), 40);
    }

    @Test
    void error_mark_negative() {
        HashMap<String, String> args = new HashMap<>();
        args.put("c/", "finals");
        args.put("m/", "-1");

        assertThrows(TutorLinkException.class, () -> command.execute(appState, args));
        assertEquals(targetDummy.getMaxScore(), 40);
        assertEquals(targetDummy.getWeight(), 40);
    }


    @Test
    void error_nan_mark() {
        HashMap<String, String> args = new HashMap<>();
        args.put("c/", "finals");
        args.put("m/", "abc");

        assertThrows(TutorLinkException.class, () -> command.execute(appState, args));
        assertEquals(targetDummy.getMaxScore(), 40);
        assertEquals(targetDummy.getWeight(), 40);
    }


    private void createMockAppState() {
        appState = new AppState();
        targetDummy = new Component("finals", 40.0, 40);
        appState.components.addComponent(targetDummy);
        appState.components.addComponent(new Component("iP", 20.0, 30));
        appState.components.addComponent(new Component("lectures", 10.0, 10));
        appState.students.addStudent("A1234567X", "John Smith");
        appState.students.addStudent("A2345678A", "John Doe");
        appState.students.addStudent("A3456789E", "Alan Smith");
        List<Student> stuList = appState.students.getStudentArrayList();
        List<Component> comList = appState.components.getComponentArrayList();
        for (Student student : stuList) {
            for (Component component : comList) {
                Grade newGrade = new Grade(component, student, component.getMaxScore());
                appState.grades.addGrade(newGrade);
            }
        }
    }
}
