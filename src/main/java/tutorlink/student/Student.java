package tutorlink.student;

public class Student {
    private String matricNumber;
    private String name;
    private double percentageScore;

    public Student(String matricNumber, String name) {
        this.name = name;
        this.matricNumber = matricNumber.toUpperCase();
        this.percentageScore = 0.0;
    }

    public Student(String matricNumber, String name, double gpa) {
        this.name = name;
        this.matricNumber = matricNumber.toUpperCase();
        this.percentageScore = gpa;
    }

    public String getName() {
        return name;
    }

    public String getMatricNumber() {
        return matricNumber;
    }

    public double getPercentageScore() {
        return percentageScore;
    }

    public void setPercentageScore(double percentageScore) {
        this.percentageScore = percentageScore;
    }

    /**
     * Returns a string representation of the object, including the name and matriculation number.
     *
     * This method overrides the default {@code toString} method to provide a custom string format
     * that combines the object's name and matriculation number in the format:
     * {@code "name (matric no: matricNumber)"}.
     *
     * Note: We need to keep this for GradeList.toString()
     * @return a string representing the object in the format "name (matric no: matricNumber)"
     */
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
