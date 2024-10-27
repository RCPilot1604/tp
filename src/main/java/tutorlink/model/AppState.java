package tutorlink.model;

import tutorlink.model.lists.ComponentList;
import tutorlink.model.lists.GradeList;
import tutorlink.model.lists.StudentList;

public class AppState {
    public StudentList students = new StudentList();
    public GradeList grades = new GradeList();
    public ComponentList components = new ComponentList();

    public AppState() {
    }
}
