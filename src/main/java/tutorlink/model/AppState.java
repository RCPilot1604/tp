package tutorlink.model;

import tutorlink.model.lists.ComponentList;
import tutorlink.model.lists.GradeList;
import tutorlink.model.lists.StudentList;

public class AppState {
    public static StudentList students = new StudentList();
    public static GradeList grades = new GradeList();
    public static ComponentList components = new ComponentList();

    public AppState() {
    }
}
