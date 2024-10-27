package tutorlink.model;

public class Commons {

    public static final String ERROR_NULL = "Error! Null parameter passed!";

    //@@author RCPilot1604
    //Student
    public static final String ADD_STUDENT_SUCCESS = "Student %s (%s) added successfully!";
    public static final String ERROR_DUPLICATE_STUDENT =
            "Error! There is more than 1 student with the Matric Number, %s!";
    public static final String ERROR_STUDENT_BOTH_NULL = "Error! Both parameters passed are null!";
    public static final String DELETE_STUDENT_SUCCESS = "Student %s successfully deleted";
    public static final String LIST_STUDENT_SUCCESS = "Here are your students:";

    //@@author yeekian
    //Grade
    public static final String DELETE_GRADE_SUCCESS = "Grade: Component %s for student %s successfully deleted";
    public static final String ADD_GRADE_SUCCESS = "%s grade added successfully to %s for %s!";
    public static final String ERROR_INVALID_SCORE =
            "Error! Score must be double that is more than or equal to 0, and not exceed the max score!";

    //@@author TrungBui32
    //Component
    public static final String DELETE_COMPONENT_SUCCESS = "Component %s successfully deleted";
    public static final String ERROR_COMPONENT_NOT_FOUND = "Error! Component (Name %s) not found";
    public static final String ERROR_DUPLICATE_COMPONENT = "Error! Component (Name %s) already exists in the list!";

    //@@author RCPilot1604
    //Invalid
    public static final String ERROR_INVALID_COMMAND = "Error! Invalid command given!";

    //Exit
    public static final String EXIT = "Goodbye! See you soon!";
}
