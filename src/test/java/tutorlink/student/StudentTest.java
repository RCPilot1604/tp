//@@author RCPilot1604
package tutorlink.student;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;


public class StudentTest {
    private Student student1;
    private Student student2;

    @BeforeEach
    void setup() {
        student1 = new Student("A1234567B", "John Doe");
        student2 = new Student("A7654321B", "Jane Smith");
    }

    @Test
    void testConstructor() {
        assertEquals("John Doe", student1.getName());
        assertEquals("A1234567B", student1.getMatricNumber());
    }

    @Test
    void testToString() {
        assertEquals("John Doe (matric no: A1234567B)", student1.toString());
        assertEquals("Jane Smith (matric no: A7654321B)", student2.toString());
    }

    @Test
    void testSameObject() {
        // Test if the same object returns true
        assertEquals(student1, student1);
    }

    @Test
    void diffObj_sameMatric() {
        // Test with another object having the same matric number
        Student student3 = new Student("A1234567B", "Johnny");
        assertEquals(student1, student3);
    }

    @Test
    void diffObj_differentMatric() {
        // Test with another object with a different matric number
        assertNotEquals(student1, student2);
    }

    @Test
    void equals_nullObject() {
        // Test with null
        assertNotEquals(null, student1);
    }

    @Test
    void equals_differentClass() {
        // Test with an object of a different class
        String notAStudent = "Not a Student";
        assertNotEquals(student1, notAStudent);
    }
}
//@@author
