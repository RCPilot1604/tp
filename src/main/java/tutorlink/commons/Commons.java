package tutorlink.commons;

public class Commons {

    public static final String ERROR_NULL = "Error! Null parameter passed!";

    //@@author RCPilot1604

    //Input Validation
    public static final String MATRIC_NUMBER_REGEX = "A\\d{7}[A-Z]";
    public static final String ERROR_ILLEGAL_MATRIC_NUMBER = "Error! Ensure matric " +
            "number is of the form A\\d{7}[A-Z] (case insensitive)";
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
    public static final String ADD_GRADE_SUCCESS = "Score of %s added successfully to %s for %s!";
    public static final String ERROR_INVALID_SCORE =
            "Error! Score must be double that is more than or equal to 0, and not exceed the max score!";

    //@@author TrungBui32
    //Component
    public static final int MAX_WEIGHT = 100;
    public static final String ADD_COMPONENT_SUCCESS =
            "Component %s of weight %s%%, with max score %s added successfully!";
    public static final String DELETE_COMPONENT_SUCCESS = "Component %s successfully deleted";
    public static final String ERROR_COMPONENT_NOT_FOUND = "Error! Component (Name %s) not found";
    public static final String ERROR_DUPLICATE_COMPONENT = "Error! Component (Name %s) already exists in the list!";
    public static final String ERROR_MULTIPLE_QUERY_RESULT = "Error! Multiple query results for keyword: %s found " +
            "in list!";
    public static final String ERROR_INVALID_WEIGHTAGE = "Error! Weightage must be integer that is between 0 and 100!";
    public static final String ERROR_INVALID_MAX_SCORE =
            "Error! Max Score must be double that is more than or equal to 0!";
    //@@author RCPilot1604
    public static final String ERROR_INVALID_TOTAL_WEIGHTING = "Error! Total weighting must add up to 100%%.\n" +
            "Current weighting (after addition): %s%%";

    //Invalid
    public static final String ERROR_INVALID_COMMAND = "Error! Invalid command given!";

    public static final String ERROR_FILESTORAGE_EXCEPTION = "Error! File storage exception encountered: ";

    //Exit
    public static final String EXIT = "Goodbye! See you soon!";
}
