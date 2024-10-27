//@@author yeekian
package tutorlink.parser;

import org.junit.jupiter.api.Test;
import tutorlink.logic.command.AddStudentCommand;
import tutorlink.logic.command.Command;
import tutorlink.logic.command.DeleteStudentCommand;
import tutorlink.logic.command.ExitCommand;
import tutorlink.logic.command.FindStudentCommand;
import tutorlink.logic.command.InvalidCommand;
import tutorlink.logic.command.ListStudentCommand;


import java.util.HashMap;
import tutorlink.logic.parser.Parser;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ParserTest {

    @Test
    void  getCommand_addStudentCommand_addStudentCommandReturned() {

        Parser parser = new Parser();
        String input = "add_student i/A1234567X n/John";
        Command actualCommand = parser.getCommand(input);

        assertEquals(AddStudentCommand.class, actualCommand.getClass());
    }

    @Test
    void  getCommand_deleteStudentCommand_deleteStudentCommandReturned() {
        Parser parser = new Parser();
        String input = "delete_student i/A1234567X";
        Command actualCommand = parser.getCommand(input);

        assertEquals(DeleteStudentCommand.class, actualCommand.getClass());
    }

    @Test
    void  getCommand_exitCommand_exitCommandReturned() {
        Parser parser = new Parser();
        String input = "bye";
        Command actualCommand = parser.getCommand(input);

        assertEquals(ExitCommand.class, actualCommand.getClass());
    }

    @Test
    void  getCommand_listStudentCommand_listStudentCommandReturned() {
        Parser parser = new Parser();
        String input = "list_student";
        Command actualCommand = parser.getCommand(input);

        assertEquals(ListStudentCommand.class, actualCommand.getClass());
    }

    @Test
    void  getCommand_findStudentCommand_findStudentCommandReturned() {
        Parser parser = new Parser();
        String input = "find_student i/A1234567X n/John Doe";
        Command actualCommand = parser.getCommand(input);

        assertEquals(FindStudentCommand.class, actualCommand.getClass());
    }

    @Test
    void  getCommand_invalidCommand_invalidCommandReturned() {
        Parser parser = new Parser();
        String input = "test_input";
        Command actualCommand = parser.getCommand(input);

        assertEquals(InvalidCommand.class, actualCommand.getClass());
    }

    @Test
    void  getArguments_addStudentCommand_addStudentCommandHashMapReturned() {
        Parser parser = new Parser();
        String line = "add_student i/A1234567X n/John Doe";

        Command currentCommand = new AddStudentCommand();
        String[] argumentPrefixes = currentCommand.getArgumentPrefixes();

        HashMap<String, String>  arguments = parser.getArguments(argumentPrefixes, line);

        assertEquals(2, arguments.size());
        assertEquals("A1234567X", arguments.get("i/")); // Check matriculation number
        assertEquals("John Doe", arguments.get("n/")); // Check
    }

    @Test
    void  getArguments_exitCommandNoArgumentPrefix_exitCommandHashMapReturned() {
        Parser parser = new Parser();
        String line = "bye";

        Command currentCommand = new ExitCommand();
        String[] argumentPrefixes = currentCommand.getArgumentPrefixes();

        HashMap<String, String>  arguments = parser.getArguments(argumentPrefixes, line);

        assertEquals(0, arguments.size());
    }

    @Test
    void  getArguments_addStudentCommandExtraArguments_ignoreExtraTag() {
        Parser parser = new Parser();
        String line = "add_student i/A1234567X n/John Doe t/extraTag";

        Command currentCommand = new AddStudentCommand();
        String[] argumentPrefixes = currentCommand.getArgumentPrefixes();

        HashMap<String, String>  arguments = parser.getArguments(argumentPrefixes, line);

        assertEquals(2, arguments.size());
        assertEquals("A1234567X", arguments.get("i/")); // Check matriculation number
        assertEquals("John Doe", arguments.get("n/")); // Check
    }

    @Test
    void  getArguments_addStudentCommandSwappedArguments_addStudentCommandHashMapReturned() {
        Parser parser = new Parser();
        String line = "add_student n/John Doe i/A1234567X ";

        Command currentCommand = new AddStudentCommand();
        String[] argumentPrefixes = currentCommand.getArgumentPrefixes();

        HashMap<String, String>  arguments = parser.getArguments(argumentPrefixes, line);

        assertEquals(2, arguments.size());
        assertEquals("A1234567X", arguments.get("i/")); // Check matriculation number
        assertEquals("John Doe", arguments.get("n/")); // Check
    }

    @Test
    void  getArguments_addStudentCommandMissingArguments_hashMapWithOnlyGivenArgumentsReturned() {
        Parser parser = new Parser();
        String line = "add_student n/John Doe";

        Command currentCommand = new AddStudentCommand();
        String[] argumentPrefixes = currentCommand.getArgumentPrefixes();

        HashMap<String, String>  arguments = parser.getArguments(argumentPrefixes, line);

        assertEquals(1, arguments.size());
        assertEquals("John Doe", arguments.get("n/")); // Check
    }

    @Test
    void  getArguments_addStudentCommandInvalidArguments_emptyHashMapReturned() {
        Parser parser = new Parser();
        String line = "add_student j/test";

        Command currentCommand = new AddStudentCommand();
        String[] argumentPrefixes = currentCommand.getArgumentPrefixes();

        HashMap<String, String>  arguments = parser.getArguments(argumentPrefixes, line);

        assertEquals(0, arguments.size());
    }
}
//@@author
