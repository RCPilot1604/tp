package tutorlink.command;

import java.util.HashMap;

import java.util.stream.Collectors;
import java.util.stream.IntStream;
import tutorlink.commons.Commons;
import tutorlink.exceptions.IllegalValueException;
import tutorlink.exceptions.IncompleteGradesException;
import tutorlink.exceptions.TutorLinkException;
import tutorlink.lists.StudentList;
import tutorlink.result.CommandResult;
import tutorlink.appstate.AppState;
import tutorlink.student.Student;

public class FindStudentCommand extends Command {
    private static final String TO_STRING_DELIMITER = "\n";
    public static final String[] ARGUMENT_PREFIXES = {"i/", "n/"};
    public static final String COMMAND_WORD = "find_student";

    private String printStudent(Student student, AppState appState) {
        try {
            double percentageScore = appState.grades.calculateStudentPercentageScore(student.getMatricNumber(),
                    appState.components);
            return student.getName() + " (matric no: " + student.getMatricNumber() + ", percentage score: " +
                    String.format("%.2f", percentageScore) + ")";
        } catch (IncompleteGradesException e) {
            return student.getName() + " (matric no: " + student.getMatricNumber() + ", percentage score: " +
                    String.format("%s", e.getMessage()) + ")";
        }

    }

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
        String result = IntStream.range(0, students.getStudentArrayList().size())
                .mapToObj(i -> ("\t" + (i + 1)) + ": " +
                        printStudent(students.getStudentArrayList().get(i), appstate)) // 1-based index
                .collect(Collectors.joining(TO_STRING_DELIMITER));
        return new CommandResult(result);
    }

    @Override
    public String[] getArgumentPrefixes() {
        return ARGUMENT_PREFIXES;
    }
}
