package tutorlink.lists;

import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


import tutorlink.exceptions.DuplicateGradeException;
import tutorlink.exceptions.GradeNotFoundException;
import tutorlink.exceptions.IncompleteGradesException;
import tutorlink.exceptions.TutorLinkException;
import tutorlink.grade.Grade;

/**
 * Represents a list of grades.
 */
public class GradeList {
    private static final String ERROR_DUPLICATE_GRADE_ON_ADD =
            "Error! Grade for component %s for student %s already exists in the list!";
    private static final String ERROR_NO_GRADE_FOUND =
            "Error! Grade for component %s for student %s does not exist in the list!";
    private static final String ERROR_PERCENTAGE_SCORE_NO_GRADES = "No Grades";
    private static final String ERROR_PERCENTAGE_SCORE_IP = "IP";

    private ArrayList<Grade> gradeArrayList;

    public GradeList() {
        this.gradeArrayList = new ArrayList<>();
    }

    public GradeList(ArrayList<Grade> gradeArrayList) {
        this.gradeArrayList = gradeArrayList;
    }

    public void deleteGrade(String matricNumber, String componentDescription) throws GradeNotFoundException {
        matricNumber = matricNumber.toUpperCase();
        for (Grade grade : gradeArrayList) {
            if (grade.getStudent().getMatricNumber().equals(matricNumber.toUpperCase())
                    && grade.getComponent().getName().toUpperCase().equals(componentDescription.toUpperCase())) {
                gradeArrayList.remove(grade);
                return;
            }
        }
        throw new GradeNotFoundException(String.format(ERROR_NO_GRADE_FOUND, componentDescription, matricNumber));
    }

    public void deleteGradesByMatric(String matricNumber) {
        matricNumber = matricNumber.toUpperCase();
        ArrayList<Grade> gradesToDelete = new ArrayList<>();
        for (Grade grade : gradeArrayList) {
            if (grade.getStudent().getMatricNumber().equals(matricNumber)) {
                gradesToDelete.add(grade);
            }
        }
        gradeArrayList.removeAll(gradesToDelete);
    }

    public void deleteGradesByComponent(String componentDescription) {
        componentDescription = componentDescription.toUpperCase();
        ArrayList<Grade> gradesToDelete = new ArrayList<>();
        for (Grade grade : gradeArrayList) {
            if (grade.getComponent().getName().toUpperCase().equals(componentDescription.toUpperCase())) {
                gradesToDelete.add(grade);
            }
        }
        gradeArrayList.removeAll(gradesToDelete);
    }

    public void addGrade(Grade grade) throws DuplicateGradeException {
        for (Grade gradeToCompare : gradeArrayList) {
            if (grade.equals(gradeToCompare)) {
                throw new DuplicateGradeException(
                        String.format(ERROR_DUPLICATE_GRADE_ON_ADD,
                                grade.getComponentName(), grade.getStudentMatricNumber()));
            }
        }
        gradeArrayList.add(grade);
    }

    @Override
    public String toString() {
        return IntStream.range(0, gradeArrayList.size())
                .mapToObj(i -> (i + 1) + ": " + gradeArrayList.get(i)) // 1-based index
                .collect(Collectors.joining("\n\t"));
    }

    public GradeList findGrade(String matricNumber, String componentDescription) throws TutorLinkException {
        GradeList filteredList = new GradeList();
        filteredList.gradeArrayList = gradeArrayList
                .stream()
                .filter(grade -> grade.getStudent().getMatricNumber().equals(matricNumber.toUpperCase())
                        && grade.getComponent().getName().toUpperCase().equals(componentDescription.toUpperCase()))
                .collect(Collectors.toCollection(ArrayList::new));
        if (filteredList.gradeArrayList.isEmpty()) {
            throw new GradeNotFoundException(String.format(ERROR_NO_GRADE_FOUND, componentDescription, matricNumber));
        }
        return filteredList;
    }
    //@@author

    public double calculateStudentPercentageScore(String matricNumber, ComponentList componentList) {
        ArrayList<Grade> studentGrades = gradeArrayList
                .stream()
                .filter(grade -> grade.getStudent().getMatricNumber().equals(matricNumber.toUpperCase()))
                .collect(Collectors.toCollection(ArrayList::new));

        if (studentGrades.isEmpty()) {
            throw new IncompleteGradesException(ERROR_PERCENTAGE_SCORE_NO_GRADES);
        }

        //Compute the total weight of all grades tagged to this student
        int totalWeighting = studentGrades
                .stream()
                .mapToInt(c -> c.getComponent().getWeight())
                .sum();
        if(totalWeighting < componentList.getTotalWeighting()) {
            throw new IncompleteGradesException(ERROR_PERCENTAGE_SCORE_IP);
        }
        return studentGrades
                .stream()
                .mapToDouble(grade ->
                        grade.getScore()
                        / grade.getComponent().getMaxScore()
                        * grade.getComponent().getWeight()
                )
                .sum() / totalWeighting * 100;
    }

    public ArrayList<Grade> getGradeArrayList() {
        return gradeArrayList;
    }

}
