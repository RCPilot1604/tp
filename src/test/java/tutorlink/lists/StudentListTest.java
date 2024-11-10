//@@author RCPilot1604
package tutorlink.lists;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tutorlink.exceptions.DuplicateMatricNumberException;
import tutorlink.exceptions.StudentNotFoundException;
import tutorlink.student.Student;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class StudentListTest {

    private StudentList studentList;

    @BeforeEach
    void setUp() {
        studentList = new StudentList();
    }

    @Test
    void add_success() throws DuplicateMatricNumberException {
        studentList.addStudent("A1234567B", "John Doe");
        ArrayList<Student> students = studentList.getStudentArrayList();
        assertEquals(1, students.size());
        assertEquals("John Doe", students.get(0).getName());
    }

    @Test
    void add_duplicateMatricNumberException() throws DuplicateMatricNumberException {
        studentList.addStudent("A1234567B", "John Doe");
        assertThrows(DuplicateMatricNumberException.class, () -> studentList.addStudent("A1234567B", "Jane Smith"));
    }

    @Test
    void delete_success() throws DuplicateMatricNumberException {
        studentList.addStudent("A1234567B", "John Doe");
        studentList.deleteStudent("A1234567B");
        assertTrue(studentList.getStudentArrayList().isEmpty());
    }

    @Test
    void delete_failure() {
        try {
            studentList.deleteStudent("A1234567B");
        } catch (StudentNotFoundException e) {
            assertEquals(e.getMessage(), "Error! Student (Matric Number A1234567B) not found");
        }
    }

    @Test
    void find_matric_success() throws Exception {
        studentList.addStudent("A1234567B", "John Doe");
        studentList.addStudent("A7654321B", "Jane Smith");

        StudentList result = studentList.findStudentByMatricNumber("A1234567B");
        assertEquals(1, result.getStudentArrayList().size());
        assertEquals("John Doe", result.getStudentArrayList().get(0).getName());
    }

    @Test
    void find_matric_notFound() {
        assertThrows(StudentNotFoundException.class, () -> studentList.findStudentByMatricNumber("A9999999B"));
    }

    @Test
    void find_name_success() throws Exception {
        studentList.addStudent("A1234567B", "John Doe");
        studentList.addStudent("A7654321B", "Jane Smith");

        StudentList result = studentList.findStudentByName("Jane");
        assertEquals(1, result.getStudentArrayList().size());
        assertEquals("Jane Smith", result.getStudentArrayList().get(0).getName());
    }

    @Test
    void find_name_notFound() {
        assertThrows(StudentNotFoundException.class, () -> studentList.findStudentByName("Nonexistent Name"));
    }
}
//@@author
