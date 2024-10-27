package tutorlink.component;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tutorlink.model.component.Assignment;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AssignmentTest {
    private Assignment assignment;

    @BeforeEach
    void setup() {
        assignment = new Assignment("Homework", 30.0, 0.2);
    }

    @Test
    void constructor_validAssignment_success() {
        assertEquals("homework", assignment.getName());
        assertEquals(30.0, assignment.getMaxScore());
        assertEquals(0.2, assignment.getWeight());
    }
}

