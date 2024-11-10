package tutorlink.command;

import java.util.HashMap;

import java.util.stream.Collectors;
import java.util.stream.IntStream;
import tutorlink.appstate.AppState;
import tutorlink.exceptions.IncompleteGradesException;
import tutorlink.result.CommandResult;
import tutorlink.student.Student;

public class ListStudentCommand extends Command {

    private static final String TO_STRING_DELIMITER = "\n";

    public static final String COMMAND_WORD = "list_student";

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
    public CommandResult execute(AppState appState, HashMap<String, String> hashMap) {
        String result = IntStream.range(0, appState.students.getStudentArrayList().size())
                .mapToObj(i -> ("\t" + (i + 1)) + ": " +
                        printStudent(appState.students.getStudentArrayList().get(i), appState)) // 1-based index
                                .collect(Collectors.joining(TO_STRING_DELIMITER));

        return new CommandResult(result);
    }

    @Override
    public String[] getArgumentPrefixes() {
        return null;
    }
}
