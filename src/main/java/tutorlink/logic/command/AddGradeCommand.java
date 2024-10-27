package tutorlink.logic.command;

import tutorlink.model.AppState;
import tutorlink.model.Commons;
import tutorlink.model.component.Component;
import tutorlink.model.exceptions.ComponentNotFoundException;
import tutorlink.model.exceptions.DuplicateComponentException;
import tutorlink.model.exceptions.DuplicateMatricNumberException;
import tutorlink.model.exceptions.IllegalValueException;
import tutorlink.model.exceptions.StudentNotFoundException;
import tutorlink.model.exceptions.TutorLinkException;
import tutorlink.model.grade.Grade;
import tutorlink.model.lists.ComponentList;
import tutorlink.model.lists.StudentList;
import tutorlink.logic.result.CommandResult;
import tutorlink.model.student.Student;
import java.util.HashMap;
import static tutorlink.model.lists.StudentList.STUDENT_NOT_FOUND;

public class AddGradeCommand extends Command {
    public static final String[] ARGUMENT_PREFIXES = {"i/", "c/", "s/"};
    public static final String COMMAND_WORD = "add_grade";

    @Override
    public CommandResult execute(AppState appstate, HashMap<String, String> hashmap) throws TutorLinkException {
        String matricNumber = hashmap.get(ARGUMENT_PREFIXES[0]);
        String componentDescription = hashmap.get(ARGUMENT_PREFIXES[1]);
        String scoreNumber = hashmap.get(ARGUMENT_PREFIXES[2]);
        if (matricNumber == null || componentDescription == null || scoreNumber == null) {
            throw new IllegalValueException(Commons.ERROR_NULL);
        }

        //Get component object using String componentDescription
        ComponentList componentFilteredList = appstate.components.findComponent(componentDescription.toUpperCase());
        Component component;
        if (componentFilteredList.size() == 1) {
            component = componentFilteredList.getComponentArrayList().get(0);
        } else if (componentFilteredList.size() == 0) {
            throw new ComponentNotFoundException(String.format(Commons.ERROR_COMPONENT_NOT_FOUND,
                    componentDescription));
        } else {
            String errorMessage = String.format(Commons.ERROR_DUPLICATE_COMPONENT, componentDescription);
            throw new DuplicateComponentException(errorMessage);
        }

        assert component != null : "Component object should not be null after this point";

        //Get Student student object using String matricNumber
        StudentList studentFilteredList = appstate.students.findStudentByMatricNumber(matricNumber);

        Student student;
        if (studentFilteredList.size() == 1) {
            student = studentFilteredList.getStudentArrayList().get(0);
        } else if (studentFilteredList.size() == 0) {
            throw new StudentNotFoundException(String.format(STUDENT_NOT_FOUND, matricNumber));
        } else {
            String errorMessage = String.format(Commons.ERROR_DUPLICATE_STUDENT, matricNumber);
            throw new DuplicateMatricNumberException(errorMessage);
        }

        assert student != null : "Student object should not be null after this point";

        //Convert scoreNumber to double
        try {
            double score = Double.parseDouble(scoreNumber);

            if (score < 0.0 || score >= component.getMaxScore()) {
                throw new IllegalValueException(Commons.ERROR_INVALID_SCORE);
            }

            //create a new grade object
            Grade grade = new Grade(component, student, score);

            appstate.grades.addGrade(grade);
        } catch (NumberFormatException e) {
            throw new IllegalValueException(Commons.ERROR_INVALID_SCORE);

        }

        return new CommandResult(String.format(Commons.ADD_GRADE_SUCCESS, scoreNumber, componentDescription,
                matricNumber));
    }

    @Override
    public String[] getArgumentPrefixes() {
        return ARGUMENT_PREFIXES;
    }
}
