package tutorlink.model.student;

public class Student {
    private String matricNumber;
    private String name;
    private double gpa;

    public Student(String matricNumber, String name) {
        this.name = name;
        this.matricNumber = matricNumber.toUpperCase();
    }

    public String getName() {
        return name;
    }

    public String getMatricNumber() {
        return matricNumber;
    }

    @Override
    public String toString() {
        return this.name + " (matric no: " + this.matricNumber + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Student)) {
            return false;
        }
        Student s = (Student) obj;
        return this.matricNumber.equals(s.getMatricNumber());
    }
}
