package tutorlink.component;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tutorlink.model.component.Exam;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ExamTest {
    private Exam exam;

    @BeforeEach
    void setup() {
        exam = new Exam("Midterm", 100.0, 0.4);
    }

    @Test
    void constructor_validExam_success() {
        assertEquals("midterm", exam.getName());
        assertEquals(100.0, exam.getMaxScore());
        assertEquals(0.4, exam.getWeight());
    }
}
