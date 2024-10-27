# Developer Guide

## Acknowledgements

{list here sources of all reused/adapted ideas, code, documentation, and third-party libraries -- include links to the
original source as well}

## Design & implementation

### Architecture

The high-level design of TutorLink is as depicted in the following **Architecture Diagram**:

**Main Components of the Architecture**

<code>TutorLink</code>: Main class that serves as the main entry point of the application and
the overall coordination of interactions between the various classes.

- At app launch, TutorLink initializes the required packages (Ui, Logic, AppState, Storage)
- On app shutdown, it shuts down the components and invokes cleanup methods.

The key classes providing functionality to TutorLink are:

1. <code>AppState</code>: Stores global variables/resources required by TutorLink at run time.
2. <code>Ui</code>: Collects data (via Strings sent via CLI) from the user and relays information to the user (via
   printing back to the CLI).
3. <code>Logic</code>: Interprets the raw data from the user, applies data validation, generates and executes 
required opreations, and handles necessary exceptions.
4. <code>Storage</code>: Serves as long-term storage of data to be retained even after TutorLink is shut down.

#### Typical Architecture-Level Interaction

The sequence diagram below depicts a typical user interaction with <code>TutorLink</code>:



### AppState 
//Content goes here

### Attendance 
//Content goes here

### Command 
//Content goes here

### Component
//Content goes here

### Exceptions
//Content goes here

### Grade
//Content goes here

### Result
//Content goes here

### Student
//Content goes here

### Ui
//Content goes here
//contains Ui and Parser classes

## Product Scope

### Target User Profile

The target users for **TutorLink** are professors at NUS who manage small, single-staffed classes. These professors
typically have strong technical expertise but are often overwhelmed by time-consuming administrative tasks that detract
from their ability to focus on teaching and curriculum development.

#### Key Characteristics:

- **Time-Constrained**: Professors have limited time for lesson preparation due to the administrative burden of managing
  grades, assignments, and attendance.
- **Technologically Savvy**: While proficient in using educational platforms, they often find existing tools slow,
  overly complex, or requiring constant internet connectivity.
- **Desire for Simplicity**: They prefer tools that are easy to use, automate repetitive tasks, and function offline,
  allowing them to streamline administrative work without unnecessary complexity.

The target user values efficiency, reliability, and simplicity, seeking a solution that reduces administrative workload
and enables them to focus more on the core aspects of teaching.

### Value Proposition

**TutorLink** solves the problem of administrative overload by automating routine tasks such as managing assignments,
and monitoring student performance. Professors often struggle with time-consuming admin work that takes away from their
primary focus: teaching and preparing lessons.

By offering an offline, lightweight solution that simplifies these processes, TutorLink helps professors:

- Save time by automating tedious administrative tasks.
- Access important information quickly, without needing an internet connection.
- Focus on teaching and lesson development instead of being bogged down by admin work.

In contrast to bloated systems, TutorLink is designed to be fast, simple, and effective—freeing up valuable time and
enhancing teaching efficiency.

## User Stories

| Version | As a ... | I want to ...             | So that I can ...                                           |
|---------|----------|---------------------------|-------------------------------------------------------------|
| v1.0    | new user | see usage instructions    | refer to them when I forget how to use the application      |
| v2.0    | user     | find a to-do item by name | locate a to-do without having to go through the entire list |

## Non-Functional Requirements

{Give non-functional requirements}

1. Performance: The system should respond to any command within a few seconds
2. Reliability: The system should not lose any user data even if it crashes unexpectedly
3. Usability: A new user should be able to use basic features without confusion
4. Compatibility: The system should work on any mainstream OS (Windows, macOS, Linux) that has Java 11 or above
   installed
5. Maintainability: The system should be able to export all data in a human-readable format for backup purposes

## Glossary

* *glossary item* - Definition

## Instructions for manual testing

{Give instructions on how to do a manual product testing e.g., how to load sample data to be used for testing}

## Implementation

This section describes some noteworthy details on how certain features are implemented.

### Add/delete grade feature

#### Implementation

The `AddGradeCommand` or `DeleteGradeCommand` is responsible for adding or deleting a grade to a student for a specified
component within the TutorLink application, respectively. It validates the inputs to ensure that they meet the required
criteria before performing the addition, thus maintaining data integrity.
The grades are added to GradeList as a Grade object.

Additional, it implements

#### Key Operations

- **AddGradeCommand#execute(AppState appstate, HashMap<String, String> hashmap)**: Executes the command to add a grade
  to a student. It performs the following steps:
    1. Retrieves and validates the matric number, component description, and score from the `hashmap`.
    2. Checks for the existence of the specified component and student.
    3. Validates the score to ensure it is within the acceptable range.
    4. Creates a new `Grade` object and adds it to the `GradeList` within the `AppState`.


- **DeleteGradeCommand#execute(AppState appstate, HashMap<String, String> hashmap)**: Executes the command to delete a
  grade to a student. It performs the following steps:
    1. Retrieves and validates the matric number, and component description from the `hashmap`.
    2. Checks for the existence of the specified component and student.
    4. Deletes the `Grade` object from the `GradeList` within the `AppState`.

#### Example Usage Scenario

Step 1: The user launches the application and has already added students and graded components into TutorLink

Step 2: The user executes ________ . The add_grade command is parsed through the parser, causing a AddGradeCommand
object to be created. A hashmap containing all the input parameters are also returned

Step 3: The AddGradeCommand is then executed in the TutorLink class file. The execution takes in the current appState
and the hashmap of arguments generated in step 2.

Step 4: During execution, the command extracts the matricNumber, componentDescription, and scoreNumber from the hashmap
as String objects. A check is done to ensure that all arguments are not null, else an IllegalValueException is thrown

Step 5: As grades are added to a component, the component is found using the componentDescription if it is equal to the
name of the component.

Step 6: The corresponding student object will also have to be found, using the matricNumber.
Note that exceptions will be thrown should there be components or students that are missing or duplicated.

Step 7: The String scoreNumber is then converted to a double as score. An exception will be thrown when score is
negative, more than the maximum score of the component, or the input is not suitable for double conversion.

Step 8: Using the score double, and student and component objects, a grade object is created

Step 9: The newly created grade object is then added to the gradelist.

<Insert Sequence diagram here, todo>

### Storage feature

#### Implementation

The `Storage` class is responsible for the automatic loading and saving of list data to and from `.txt` files,
so that data will be retained between runs of the application.

#### Example Usage Scenario

Step 1: The user launches the application. During startup, the `main` method calls constructors for `Storage` objects
for each of `StudentList`, `ComponentList` and `Gradelist`.

Step 2: The predefined filepaths are passed into the constructor. The directory and file are created if they do not
currently exist.

<Insert Sequence diagram here, todo>
