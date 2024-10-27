//@@author yeekian
package tutorlink.command;

import org.junit.jupiter.api.Test;
import tutorlink.logic.command.AddGradeCommand;
import tutorlink.logic.command.AddStudentCommand;
import tutorlink.logic.command.Command;
import tutorlink.logic.command.DeleteGradeCommand;
import tutorlink.model.AppState;
import tutorlink.model.component.Exam;
import tutorlink.model.lists.GradeList;
import tutorlink.logic.parser.Parser;
import tutorlink.logic.result.CommandResult;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class DeleteGradeCommandTest {

    private final String successMessage = "%s grade added successfully to %s for %s!";

    @Test
    void addGrade_allArgumentsExam_successful() {
        AppState appState = new AppState();
        Parser parser = new Parser();
        HashMap<String, String> arguments = new HashMap<>();
        Command command = new DeleteGradeCommand();

        String componentName = "Exam under test";

        GradeList gradeList = addGradeForTest(parser, appState, componentName);
        assertEquals(1, gradeList.getGradeArrayList().size());


        arguments.put("i/","A1234567X");
        arguments.put("c/", componentName);

        command.execute(appState, arguments);
        assertEquals(0, gradeList.getGradeArrayList().size());
    }

    private GradeList addGradeForTest(Parser parser, AppState appState, String componentName) {
        //Create student
        String line = "add_student i/A1234567X n/John Doe";
        Command addStudentCommand = new AddStudentCommand();
        String[] argumentPrefixes = addStudentCommand.getArgumentPrefixes();
        HashMap<String, String> arguments = parser.getArguments(argumentPrefixes, line);
        CommandResult result = addStudentCommand.execute(appState, arguments);
        assertNotNull(result);
        assertEquals("Student John Doe (A1234567X) added successfully!", result.toString());
        assertEquals(appState.students.getStudentArrayList().size(), 1);

        //Create component
        String examName = componentName;
        double examMaxScore = 100.0;
        double examWeight = 50.0;
        Exam exam = new Exam(examName,examMaxScore, examWeight);

        appState.components.addComponent(exam);

        //Add grade
        HashMap<String, String> gradeArguments = new HashMap<>();

        String matricNumber = "A1234567X";
        String componentDescription = componentName;
        String scoreNumber = "75.0";
        gradeArguments.put("i/",matricNumber);
        gradeArguments.put("c/", componentDescription);
        gradeArguments.put("s/", scoreNumber);

        Command addGradeCommand = new AddGradeCommand();
        CommandResult gradeResult = addGradeCommand.execute(appState, gradeArguments);

        //Test grade added
        assertNotNull(gradeResult);
        assertEquals(String.format(successMessage, scoreNumber, componentDescription, matricNumber),
                gradeResult.toString());

        return appState.grades;
    }
}
//@@author
