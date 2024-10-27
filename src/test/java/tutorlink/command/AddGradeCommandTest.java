//@@author yeekian
package tutorlink.command;

import org.junit.jupiter.api.Test;
import tutorlink.logic.command.AddGradeCommand;
import tutorlink.logic.command.AddStudentCommand;
import tutorlink.logic.command.Command;
import tutorlink.model.AppState;
import tutorlink.model.Commons;
import tutorlink.model.component.Assignment;
import tutorlink.model.component.ClassParticipation;
import tutorlink.model.component.Exam;
import tutorlink.model.exceptions.ComponentNotFoundException;
import tutorlink.model.exceptions.DuplicateGradeException;
import tutorlink.model.exceptions.IllegalValueException;
import tutorlink.model.exceptions.StudentNotFoundException;
import tutorlink.model.grade.Grade;
import tutorlink.model.lists.GradeList;
import tutorlink.logic.parser.Parser;
import tutorlink.logic.result.CommandResult;


import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class AddGradeCommandTest {
    @Test
    void addGrade_allArgumentsExam_successful() {
        AppState appState = new AppState();
        Parser parser = new Parser();

        //Create student
        String line = "add_student i/A1234567X n/John Doe";
        Command addStudentCommand = new AddStudentCommand();
        String[] argumentPrefixes = addStudentCommand.getArgumentPrefixes();
        HashMap<String, String>  arguments = parser.getArguments(argumentPrefixes, line);
        CommandResult result = addStudentCommand.execute(appState, arguments);
        assertNotNull(result);
        assertEquals("Student John Doe (A1234567X) added successfully!", result.toString());
        assertEquals(appState.students.getStudentArrayList().size(), 1);

        //Create component
        String examName = "Exam Under Test";
        double examMaxScore = 100.0;
        double examWeight = 50.0;
        Exam exam = new Exam(examName,examMaxScore, examWeight);

        appState.components.addComponent(exam);

        //Add grade
        HashMap<String, String> gradeArguments = new HashMap<>();

        String matricNumber = "A1234567X";
        String componentDescription = "Exam Under Test";
        String scoreNumber = "75.0";
        gradeArguments.put("i/",matricNumber);
        gradeArguments.put("c/", componentDescription);
        gradeArguments.put("s/", scoreNumber);

        Command addGradeCommand = new AddGradeCommand();
        CommandResult gradeResult = addGradeCommand.execute(appState, gradeArguments);

        //Test grade added
        assertNotNull(gradeResult);
        assertEquals(String.format(Commons.ADD_GRADE_SUCCESS, scoreNumber, componentDescription, matricNumber),
                gradeResult.toString());
    }

    @Test
    void addGrade_allArgumentsAssignment_successful() {
        AppState appState = new AppState();
        Parser parser = new Parser();

        //Create student
        String line = "add_student i/A1234567X n/John Doe";
        Command addStudentCommand = new AddStudentCommand();
        String[] argumentPrefixes = addStudentCommand.getArgumentPrefixes();
        HashMap<String, String>  arguments = parser.getArguments(argumentPrefixes, line);
        CommandResult result = addStudentCommand.execute(appState, arguments);
        assertNotNull(result);
        assertEquals("Student John Doe (A1234567X) added successfully!", result.toString());
        assertEquals(appState.students.getStudentArrayList().size(), 1);

        //Create component
        String assignmentName = "Assignment Under Test";
        double assignmentMaxScore = 100.0;
        double assignmentWeight = 50.0;
        Assignment assignment = new Assignment(assignmentName,assignmentMaxScore, assignmentWeight);

        appState.components.addComponent(assignment);

        //Add grade
        HashMap<String, String> gradeArguments = new HashMap<>();

        String matricNumber = "A1234567X";
        String componentDescription = "Assignment Under Test";
        String scoreNumber = "75.0";
        gradeArguments.put("i/",matricNumber);
        gradeArguments.put("c/", componentDescription);
        gradeArguments.put("s/", scoreNumber);

        Command addGradeCommand = new AddGradeCommand();
        CommandResult gradeResult = addGradeCommand.execute(appState, gradeArguments);

        //Test grade added

        assertNotNull(gradeResult);
        assertEquals(String.format(Commons.ADD_GRADE_SUCCESS, scoreNumber, componentDescription, matricNumber),
                gradeResult.toString());
    }

    @Test
    void addGrade_allArgumentsClassParticipation_successful() {
        AppState appState = new AppState();
        Parser parser = new Parser();

        //Create student
        String line = "add_student i/A1234567X n/John Doe";
        Command addStudentCommand = new AddStudentCommand();
        String[] argumentPrefixes = addStudentCommand.getArgumentPrefixes();
        HashMap<String, String>  arguments = parser.getArguments(argumentPrefixes, line);
        CommandResult result = addStudentCommand.execute(appState, arguments);
        assertNotNull(result);
        assertEquals("Student John Doe (A1234567X) added successfully!", result.toString());
        assertEquals(appState.students.getStudentArrayList().size(), 1);

        //Create component
        String classPartName = "Class Participation Under Test";
        double classPartMaxScore = 100.0;
        double classPartWeight = 50.0;
        ClassParticipation classPart = new ClassParticipation(classPartName,classPartMaxScore, classPartWeight);

        appState.components.addComponent(classPart);

        //Add grade
        HashMap<String, String> gradeArguments = new HashMap<>();

        String matricNumber = "A1234567X";
        String componentDescription = "Class Participation Under Test";
        String scoreNumber = "75.0";
        gradeArguments.put("i/",matricNumber);
        gradeArguments.put("c/", componentDescription);
        gradeArguments.put("s/", scoreNumber);

        Command addGradeCommand = new AddGradeCommand();
        CommandResult gradeResult = addGradeCommand.execute(appState, gradeArguments);

        //Test grade added
        assertNotNull(gradeResult);
        assertEquals(String.format(Commons.ADD_GRADE_SUCCESS, scoreNumber, componentDescription, matricNumber),
                gradeResult.toString());
    }

    @Test
    void addGrade_missingComponentDescriptionExam_illegalValueExceptionThrown() {
        AppState appState = new AppState();
        Parser parser = new Parser();

        //Create student
        String line = "add_student i/A1234567X n/John Doe";
        Command addStudentCommand = new AddStudentCommand();
        String[] argumentPrefixes = addStudentCommand.getArgumentPrefixes();
        HashMap<String, String>  arguments = parser.getArguments(argumentPrefixes, line);
        CommandResult result = addStudentCommand.execute(appState, arguments);
        assertNotNull(result);
        assertEquals("Student John Doe (A1234567X) added successfully!", result.toString());
        assertEquals(appState.students.getStudentArrayList().size(), 1);

        //Create component
        String assignmentName = "Assignment Under Test";
        double assignmentMaxScore = 100.0;
        double assignmentWeight = 50.0;
        Assignment assignment = new Assignment(assignmentName,assignmentMaxScore, assignmentWeight);

        appState.components.addComponent(assignment);

        //Add grade
        HashMap<String, String> gradeArguments = new HashMap<>();

        String matricNumber = "A1234567X";
        String componentDescription = "Assignment Under Test";
        String scoreNumber = "75.0";
        gradeArguments.put("i/",matricNumber);
        gradeArguments.put("s/", scoreNumber);

        Command addGradeCommand = new AddGradeCommand();

        //Test grade added
        assertThrows(IllegalValueException.class, () -> {
            addGradeCommand.execute(appState, gradeArguments);
        });
    }

    @Test
    void addGrade_nonDoubleScoreExam_illegalValueExceptionThrown() {
        AppState appState = new AppState();
        Parser parser = new Parser();

        //Create student
        String line = "add_student i/A1234567X n/John Doe";
        Command addStudentCommand = new AddStudentCommand();
        String[] argumentPrefixes = addStudentCommand.getArgumentPrefixes();
        HashMap<String, String>  arguments = parser.getArguments(argumentPrefixes, line);
        CommandResult result = addStudentCommand.execute(appState, arguments);
        assertNotNull(result);
        assertEquals("Student John Doe (A1234567X) added successfully!", result.toString());
        assertEquals(appState.students.getStudentArrayList().size(), 1);

        //Create component
        String examName = "Exam Under Test";
        double examMaxScore = 100.0;
        double examWeight = 50.0;
        Exam exam = new Exam(examName,examMaxScore, examWeight);

        appState.components.addComponent(exam);

        //Add grade
        HashMap<String, String> gradeArguments = new HashMap<>();

        String matricNumber = "A1234567X";
        String componentDescription = "Exam Under Test";
        String scoreNumber = "Non-double String";
        gradeArguments.put("i/",matricNumber);
        gradeArguments.put("c/", componentDescription);
        gradeArguments.put("s/", scoreNumber);

        Command addGradeCommand = new AddGradeCommand();

        assertThrows(IllegalValueException.class, () -> addGradeCommand.execute(appState, gradeArguments));
    }

    @Test
    void addGrade_scoreMoreThanMax_throwIllegalValueException() {
        AppState appState = new AppState();
        Parser parser = new Parser();

        //Create student
        String line = "add_student i/A1234567X n/John Doe";
        Command addStudentCommand = new AddStudentCommand();
        String[] argumentPrefixes = addStudentCommand.getArgumentPrefixes();
        HashMap<String, String>  arguments = parser.getArguments(argumentPrefixes, line);
        CommandResult result = addStudentCommand.execute(appState, arguments);
        assertNotNull(result);
        assertEquals("Student John Doe (A1234567X) added successfully!", result.toString());
        assertEquals(appState.students.getStudentArrayList().size(), 1);

        //Create component
        String examName = "Exam Under Test";
        double examMaxScore = 100.0;
        double examWeight = 50.0;
        Exam exam = new Exam(examName,examMaxScore, examWeight);

        appState.components.addComponent(exam);

        //Add grade
        HashMap<String, String> gradeArguments = new HashMap<>();

        String matricNumber = "A1234567X";
        String componentDescription = "Exam Under Test";
        String scoreNumber = "-50.0";
        gradeArguments.put("i/",matricNumber);
        gradeArguments.put("c/", componentDescription);
        gradeArguments.put("s/", scoreNumber);

        Command addGradeCommand = new AddGradeCommand();

        assertThrows(IllegalValueException.class, () -> addGradeCommand.execute(appState, gradeArguments));
    }

    @Test
    void addGrade_scoreNegative_throwIllegalValueException() {
        AppState appState = new AppState();
        Parser parser = new Parser();

        //Create student
        String line = "add_student i/A1234567X n/John Doe";
        Command addStudentCommand = new AddStudentCommand();
        String[] argumentPrefixes = addStudentCommand.getArgumentPrefixes();
        HashMap<String, String>  arguments = parser.getArguments(argumentPrefixes, line);
        CommandResult result = addStudentCommand.execute(appState, arguments);
        assertNotNull(result);
        assertEquals("Student John Doe (A1234567X) added successfully!", result.toString());
        assertEquals(appState.students.getStudentArrayList().size(), 1);

        //Create component
        String examName = "Exam Under Test";
        double examMaxScore = 100.0;
        double examWeight = 50.0;
        Exam exam = new Exam(examName,examMaxScore, examWeight);

        appState.components.addComponent(exam);

        //Add grade
        HashMap<String, String> gradeArguments = new HashMap<>();

        String matricNumber = "A1234567X";
        String componentDescription = "Exam Under Test";
        String scoreNumber = "-50.0";
        gradeArguments.put("i/",matricNumber);
        gradeArguments.put("c/", componentDescription);
        gradeArguments.put("s/", scoreNumber);

        Command addGradeCommand = new AddGradeCommand();

        assertThrows(IllegalValueException.class, () -> addGradeCommand.execute(appState, gradeArguments));
    }

    @Test
    void addGrade_componentNotFound_throwXXXXException() {
        AppState appState = new AppState();
        Parser parser = new Parser();

        //Create student
        String line = "add_student i/A1234567X n/John Doe";
        Command addStudentCommand = new AddStudentCommand();
        String[] argumentPrefixes = addStudentCommand.getArgumentPrefixes();
        HashMap<String, String>  arguments = parser.getArguments(argumentPrefixes, line);
        CommandResult result = addStudentCommand.execute(appState, arguments);
        assertNotNull(result);
        assertEquals("Student John Doe (A1234567X) added successfully!", result.toString());
        assertEquals(appState.students.getStudentArrayList().size(), 1);

        //No component created

        //Add grade
        HashMap<String, String> gradeArguments = new HashMap<>();

        String matricNumber = "A1234567X";
        String componentDescription = "Exam Under Test";
        String scoreNumber = "75.0";
        gradeArguments.put("i/",matricNumber);
        gradeArguments.put("c/", componentDescription);
        gradeArguments.put("s/", scoreNumber);

        Command addGradeCommand = new AddGradeCommand();

        assertThrows(ComponentNotFoundException.class, () -> addGradeCommand.execute(appState, gradeArguments));
    }

    @Test
    void addGrade_studentNotFoundExam_throwStudentNotFoundException() {
        AppState appState = new AppState();

        //No student created

        //Create component
        String assignmentName = "Assignment Under Test";
        double assignmentMaxScore = 100.0;
        double assignmentWeight = 50.0;
        Assignment assignment = new Assignment(assignmentName,assignmentMaxScore, assignmentWeight);

        appState.components.addComponent(assignment);

        //Add grade
        HashMap<String, String> gradeArguments = new HashMap<>();

        String matricNumber = "A1234567X";
        String componentDescription = "Assignment Under Test";
        String scoreNumber = "75.0";
        gradeArguments.put("i/",matricNumber);
        gradeArguments.put("c/", componentDescription);
        gradeArguments.put("s/", scoreNumber);

        Command addGradeCommand = new AddGradeCommand();

        assertThrows(StudentNotFoundException.class, () -> addGradeCommand.execute(appState, gradeArguments));
    }

    @Test
    void addGrade_duplicateGradeExamDiffScores_throwDuplicateGradeException() {
        AppState appState = new AppState();
        Parser parser = new Parser();

        //Create student
        String line = "add_student i/A1234567X n/John Doe";
        Command addStudentCommand = new AddStudentCommand();
        String[] argumentPrefixes = addStudentCommand.getArgumentPrefixes();
        HashMap<String, String>  arguments = parser.getArguments(argumentPrefixes, line);
        CommandResult result = addStudentCommand.execute(appState, arguments);
        assertNotNull(result);
        assertEquals("Student John Doe (A1234567X) added successfully!", result.toString());
        assertEquals(appState.students.getStudentArrayList().size(), 1);

        //Create Exam component
        String examName = "Test";
        double examMaxScore = 100.0;
        double examWeight = 50.0;
        Exam exam = new Exam(examName,examMaxScore, examWeight);

        appState.components.addComponent(exam);

        //Add exam grade
        HashMap<String, String> gradeArguments = new HashMap<>();

        String matricNumber = "A1234567X";
        String componentDescription = "Test";
        String originalScoreNumber = "75.0";
        gradeArguments.put("i/",matricNumber);
        gradeArguments.put("c/", componentDescription);
        gradeArguments.put("s/", originalScoreNumber);

        Command addGradeCommand = new AddGradeCommand();
        CommandResult gradeResult = addGradeCommand.execute(appState, gradeArguments);

        //Test grade added
        assertNotNull(gradeResult);
        assertEquals(String.format(Commons.ADD_GRADE_SUCCESS, originalScoreNumber, componentDescription, matricNumber),
                gradeResult.toString());

        //Add repeat exam grade
        HashMap<String, String> assignmentArguments = new HashMap<>();

        matricNumber = "A1234567X";
        componentDescription = "Test";
        String changedScoreNumber = "10.0";
        gradeArguments.put("i/",matricNumber);
        gradeArguments.put("c/", componentDescription);
        gradeArguments.put("s/", changedScoreNumber);

        assertThrows(DuplicateGradeException.class, () -> addGradeCommand.execute(appState, gradeArguments));

        GradeList findGradeList = appState.grades.findGrade(matricNumber, componentDescription);
        assertEquals(1, findGradeList.getGradeArrayList().size());
        Grade finalGrade = findGradeList.getGradeArrayList().get(0);
        assertEquals(Double.parseDouble(originalScoreNumber),finalGrade.getScore());
    }
}
//@@author
