package tutorlink.model.lists;

import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


import tutorlink.model.component.Component;
import tutorlink.model.exceptions.DuplicateGradeException;
import tutorlink.model.exceptions.GradeNotFoundException;
import tutorlink.model.exceptions.StudentNotFoundException;
import tutorlink.model.exceptions.TutorLinkException;
import tutorlink.model.exceptions.IncompleteGradesException;
import tutorlink.model.grade.Grade;

/**
 * Represents a list of grades.
 */
public class GradeList {
    private static final String ERROR_DUPLICATE_GRADE_ON_ADD = "Error! Grade (%s, %s) already exists in the list!";
    private static final String ERROR_NO_GRADE_FOUND = "Error! Grade (%s, %s) does not exist in the list!";


    private ArrayList<Grade> gradeArrayList;

    public GradeList() {
        this.gradeArrayList = new ArrayList<>();
    }

    public void deleteGrade(String matricNumber, String componentDescription) throws GradeNotFoundException {
        matricNumber = matricNumber.toUpperCase();
        componentDescription = componentDescription.toLowerCase();
        for (Grade grade : gradeArrayList) {
            if (grade.getStudent().getMatricNumber().equals(matricNumber)
                    && grade.getComponent().getName().equals(componentDescription)) {
                gradeArrayList.remove(grade);
                return;
            }
        }
        throw new GradeNotFoundException(String.format(ERROR_NO_GRADE_FOUND, matricNumber, componentDescription));
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
        componentDescription = componentDescription.toLowerCase();
        ArrayList<Grade> gradesToDelete = new ArrayList<>();
        for (Grade grade : gradeArrayList) {
            if (grade.getComponent().getName().equals(componentDescription)) {
                gradesToDelete.add(grade);
            }
        }
        gradeArrayList.removeAll(gradesToDelete);
    }

    public void addGrade(Grade grade) throws DuplicateGradeException {
        for (Grade gradeToCompare : gradeArrayList) {
            if (grade.equals(gradeToCompare)) {
                throw new DuplicateGradeException(ERROR_DUPLICATE_GRADE_ON_ADD);
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
                        && grade.getComponent().getName().equals(componentDescription.toLowerCase()))
                .collect(Collectors.toCollection(ArrayList::new));
        if (filteredList.gradeArrayList.isEmpty()) {
            throw new GradeNotFoundException(String.format(ERROR_NO_GRADE_FOUND, matricNumber, componentDescription));
        }
        return filteredList;
    }

    public double calculateStudentGPA(String matricNumber, ComponentList componentList)
            throws IncompleteGradesException {
        ArrayList<Grade> studentGrades = gradeArrayList
                .stream()
                .filter(grade -> grade.getStudent().getMatricNumber().equals(matricNumber.toUpperCase()))
                .collect(Collectors.toCollection(ArrayList::new));

        ArrayList<Component> allComponents = componentList.findAllComponents();

        if (studentGrades.isEmpty()) {
            throw new StudentNotFoundException("No grades found for student: " + matricNumber);
        }

        boolean hasAllComponents = allComponents.stream()
                .allMatch(component -> studentGrades.stream()
                        .anyMatch(grade -> grade.getComponent().equals(component)));

        if (!hasAllComponents) {
            throw new IncompleteGradesException(
                    "Cannot calculate GPA: Student " + matricNumber + " is missing grades for some components");
        }

        return studentGrades
                .stream()
                .mapToDouble(grade -> grade.getScore() * grade.getComponent().getWeight())
                .sum();
    }

    public ArrayList<Grade> getGradeArrayList() {
        return gradeArrayList;
    }
}
