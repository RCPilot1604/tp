package tutorlink.command;

import java.util.HashMap;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import tutorlink.appstate.AppState;
import tutorlink.commons.Commons;
import tutorlink.exceptions.DuplicateMatricNumberException;
import tutorlink.exceptions.IllegalValueException;
import tutorlink.exceptions.IncompleteGradesException;
import tutorlink.exceptions.StudentNotFoundException;
import tutorlink.exceptions.TutorLinkException;
import tutorlink.lists.StudentList;
import tutorlink.result.CommandResult;
import tutorlink.student.Student;

import static tutorlink.lists.StudentList.STUDENT_NOT_FOUND;

public class DeleteGradeCommand extends Command {

    public static final String[] ARGUMENT_PREFIXES = {"i/", "c/"};
    public static final String COMMAND_WORD = "delete_grade";
    private static final Logger logger = Logger.getLogger(DeleteComponentCommand.class.getName());

    @Override
    public CommandResult execute(AppState appState, HashMap<String, String> hashmap) throws TutorLinkException {
        String matricNumber = hashmap.get(ARGUMENT_PREFIXES[0]);
        String componentDescription = hashmap.get(ARGUMENT_PREFIXES[1]);

        if (matricNumber == null || componentDescription == null) {
            throw new IllegalValueException(Commons.ERROR_NULL);
        }
        matricNumber = matricNumber.toUpperCase();
        Pattern pattern = Pattern.compile(Commons.MATRIC_NUMBER_REGEX);
        Matcher matcher = pattern.matcher(matricNumber);
        if (!matcher.find()) {
            throw new IllegalValueException(Commons.ERROR_ILLEGAL_MATRIC_NUMBER);
        }
        //Check number of students with matricNumber
        StudentList filteredList = appState.students.findStudentByMatricNumber(matricNumber);

        if (filteredList.size() == 0) {
            throw new StudentNotFoundException(String.format(STUDENT_NOT_FOUND, matricNumber));
        } else if (filteredList.size() > 1) {
            String errorMessage = String.format(Commons.ERROR_DUPLICATE_STUDENT, matricNumber);
            throw new DuplicateMatricNumberException(errorMessage);
        }

        //Attempt to delete grade
        appState.grades.deleteGrade(matricNumber, componentDescription);

        // Update student GPA
        Student student = filteredList.getStudentArrayList().get(0);
        try {
            double percentageScore = appState.grades.calculateStudentPercentageScore(matricNumber,
                    appState.components);
            student.setPercentageScore(percentageScore);
        } catch (IncompleteGradesException e) {
            logger.info(e.getMessage());
        }

        return new CommandResult(String.format(Commons.DELETE_GRADE_SUCCESS, componentDescription, matricNumber));
    }

    @Override
    public String[] getArgumentPrefixes() {
        return ARGUMENT_PREFIXES;
    }
}
