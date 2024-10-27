package tutorlink.logic.command;

import java.util.HashMap;

import tutorlink.model.AppState;
import tutorlink.model.Commons;
import tutorlink.model.exceptions.DuplicateMatricNumberException;
import tutorlink.model.exceptions.IllegalValueException;
import tutorlink.model.exceptions.StudentNotFoundException;
import tutorlink.model.exceptions.TutorLinkException;
import tutorlink.model.lists.StudentList;
import tutorlink.logic.result.CommandResult;
import static tutorlink.model.lists.StudentList.STUDENT_NOT_FOUND;

public class DeleteGradeCommand extends Command {

    public static final String[] ARGUMENT_PREFIXES = {"i/", "c/"};
    public static final String COMMAND_WORD = "delete_grade";
    @Override
    public CommandResult execute(AppState appState, HashMap<String, String> hashmap) throws TutorLinkException {
        String matricNumber = hashmap.get(ARGUMENT_PREFIXES[0]);
        String componentDescription = hashmap.get(ARGUMENT_PREFIXES[1]);

        if (matricNumber == null || componentDescription == null) {
            throw new IllegalValueException(Commons.ERROR_NULL);
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
        return new CommandResult(String.format(Commons.DELETE_GRADE_SUCCESS, componentDescription, matricNumber));
    }

    @Override
    public String[] getArgumentPrefixes() {
        return ARGUMENT_PREFIXES;
    }
}
