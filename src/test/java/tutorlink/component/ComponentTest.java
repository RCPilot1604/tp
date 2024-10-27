package tutorlink.component;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import tutorlink.model.component.Assignment;
import tutorlink.model.component.ClassParticipation;
import tutorlink.model.component.Component;
import tutorlink.model.component.Exam;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class ComponentTest {
    private Component assignment;
    private Component exam;
    private Component participation;

    @BeforeEach
    void setup() {
        assignment = new Assignment("Assignment 1", 50.0, 0.3);
        exam = new Exam("Final Exam", 100.0, 0.4);
        participation = new ClassParticipation("Class Participation", 20.0, 0.1);
    }

    @Test
    void constructor_validInputs_success() {
        assertEquals("assignment 1", assignment.getName());
        assertEquals(50.0, assignment.getMaxScore());
        assertEquals(0.3, assignment.getWeight());
    }

    @Test
    void equals_sameComponent_returnsTrue() {
        Component duplicateAssignment = new Assignment("Assignment 1", 50.0, 0.3);
        assertEquals(assignment, duplicateAssignment);
    }

    @Test
    void equals_differentComponent_returnsFalse() {
        Component differentAssignment = new Assignment("Assignment 2", 50.0, 0.3);
        assertNotEquals(assignment, differentAssignment);
        assertNotEquals(assignment, exam);
        assertNotEquals(assignment, null);
        assertNotEquals(assignment, "not a component");
    }
}
