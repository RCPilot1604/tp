package tutorlink.command;

import java.util.logging.Logger;
import tutorlink.appstate.AppState;
import tutorlink.exceptions.IncompleteGradesException;
import tutorlink.grade.Grade;
import tutorlink.result.CommandResult;
import tutorlink.exceptions.StudentNotFoundException;
import tutorlink.student.Student;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.Comparator;

public class ListGradeCommand extends Command {

    public static final String COMMAND_WORD = "list_grade";
    public static final String[] ARGUMENT_PREFIXES = {"i/"};
    private static final String MESSAGE_NO_GRADES = "No grades have been recorded yet.";
    private static final String MESSAGE_STUDENT_NOT_FOUND = "No grades found for student with matriculation number %s.";
    private static final Logger logger = Logger.getLogger(DeleteComponentCommand.class.getName());

    @Override
    public CommandResult execute(AppState appState, HashMap<String, String> hashMap) throws StudentNotFoundException {
        ArrayList<Grade> grades = appState.grades.getGradeArrayList();

        if (grades.isEmpty()) {
            return new CommandResult(MESSAGE_NO_GRADES);
        }

        String matricNumber = hashMap.get("i/");

        // If a specific student is requested
        if (matricNumber != null) {
            matricNumber = matricNumber.toUpperCase();
            String finalMatricNumber = matricNumber;
            Student student = appState.students.findStudentByMatricNumber(matricNumber)
                    .getStudentArrayList().stream().findFirst()
                    .orElseThrow(() -> new StudentNotFoundException(
                            String.format(MESSAGE_STUDENT_NOT_FOUND, finalMatricNumber)));

            ArrayList<Grade> studentGrades = grades.stream()
                    .filter(grade -> grade.getStudent().getMatricNumber().equals(finalMatricNumber))
                    .collect(Collectors.toCollection(ArrayList::new));

            if (studentGrades.isEmpty()) {
                return new CommandResult(String.format(MESSAGE_STUDENT_NOT_FOUND, matricNumber));
            }

            return generateStudentGradeReport(student, studentGrades, appState);
        }

        // Otherwise, display all students' grades
        return generateAllGradesReport(grades, appState);
    }

    private CommandResult generateStudentGradeReport(Student student, ArrayList<Grade> studentGrades,
                                                     AppState appState) {
        StringBuilder output = new StringBuilder(
                String.format("Grades for %s (%s):\n", student.getName(), student.getMatricNumber()));

        // Sort grades by component name and display each with numbering
        int gradeIndex = 1;
        for (Grade grade : studentGrades.stream()
                .sorted(Comparator.comparing(g -> g.getComponent().getName()))
                .collect(Collectors.toList())) {
            output.append(
                    String.format("%d: %-15s: %.2f / %.2f\n",
                            gradeIndex++,
                            grade.getComponent().getName(),
                            grade.getScore(),
                            grade.getComponent().getMaxScore()
                    ));
        }

        // Calculate and display the GPA (final grade)
        try {
            double percentageScore = appState.grades.calculateStudentPercentageScore(student.getMatricNumber(),
                    appState.components);
            output.append(String.format("\nFinal score: %.2f%%\n", percentageScore));
        } catch (IncompleteGradesException e) {
            logger.info(e.getMessage());
        }

        return new CommandResult(output.toString());
    }

    private CommandResult generateAllGradesReport(ArrayList<Grade> grades, AppState appState) {
        StringBuilder output = new StringBuilder("List of All Grades:\n\n");

        Map<String, ArrayList<Grade>> gradesByStudent = grades.stream()
                .collect(Collectors.groupingBy(
                        grade -> grade.getStudent().getMatricNumber(),
                        TreeMap::new,
                        Collectors.toCollection(ArrayList::new)
                ));

        int studentIndex = 1;
        for (Map.Entry<String, ArrayList<Grade>> entry : gradesByStudent.entrySet()) {
            Student student = entry.getValue().get(0).getStudent();
            output.append(
                    String.format("%d: %s (%s):\n", studentIndex++, student.getName(), student.getMatricNumber()));

            // Grade numbering for each student's grades
            int gradeIndex = 1;
            for (Grade grade : entry.getValue().stream()
                    .sorted(Comparator.comparing(g -> g.getComponent().getName()))
                    .collect(Collectors.toList())) {
                output.append(
                        String.format("   %d. %-15s: %.2f\n", gradeIndex++,
                                grade.getComponent().getName(), grade.getScore()));
            }
            try {
                // Calculate and display the GPA for the student
                double percentageScore = appState.grades.calculateStudentPercentageScore(student.getMatricNumber(),
                        appState.components);
                output.append(String.format("   Final Percentage Score: %.2f%%\n\n", percentageScore));
            } catch (IncompleteGradesException e) {
                logger.info(e.getMessage());
            }
        }

        return new CommandResult(output.toString());
    }

    @Override
    public String[] getArgumentPrefixes() {
        return ARGUMENT_PREFIXES;
    }
}

