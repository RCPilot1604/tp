package tutorlink.logic.command;

import java.util.HashMap;

import tutorlink.model.Commons;
import tutorlink.model.exceptions.IllegalValueException;
import tutorlink.model.exceptions.TutorLinkException;
import tutorlink.model.lists.StudentList;
import tutorlink.logic.result.CommandResult;
import tutorlink.model.AppState;

public class FindStudentCommand extends Command {

    public static final String[] ARGUMENT_PREFIXES = {"i/", "n/"};
    public static final String COMMAND_WORD = "find_student";

    @Override
    public CommandResult execute(AppState appstate, HashMap<String, String> hashmap) throws TutorLinkException {
        String matricNumber = hashmap.get(ARGUMENT_PREFIXES[0]);
        String name = hashmap.get(ARGUMENT_PREFIXES[1]);
        StudentList students;
        if (name == null && matricNumber == null) {
            throw new IllegalValueException(Commons.ERROR_STUDENT_BOTH_NULL);
        }
        if (hashmap.containsKey(ARGUMENT_PREFIXES[0])) {
            students = appstate.students.findStudentByMatricNumber(matricNumber);
            assert students.getStudentArrayList().size() <= 1; //there should only be 1 unique matric number
        } else {
            students = appstate.students.findStudentByName(name);
        }
        return new CommandResult(students.toString());
    }

    @Override
    public String[] getArgumentPrefixes() {
        return ARGUMENT_PREFIXES;
    }
}
