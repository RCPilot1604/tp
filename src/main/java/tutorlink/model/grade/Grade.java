package tutorlink.model.grade;

import tutorlink.model.component.Component;
import tutorlink.model.student.Student;

public class Grade {
    private double score;
    private Component component;
    private Student student;

    public Grade(Component component, Student student, double score) {
        this.component = component;
        this.student = student;
        this.score = score;
    }

    public Student getStudent() {
        return student;
    }

    public Component getComponent() {
        return component;
    }

    public double getScore() {
        return score;
    }

    public boolean isSameSubmission(Object obj) {
        if (obj instanceof Grade grade) {
            return this.component.equals(grade.component)
                    && this.student.equals(grade.student);
        }
        return false;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Grade grade) {
            return this.component.equals(grade.component)
                    && this.student.equals(grade.student);
        }
        return false;
    }

    @Override
    public String toString() {
        return "Student: " + this.student + ", Component: " + this.component
                + ", Score: " + this.score;
    }
}
