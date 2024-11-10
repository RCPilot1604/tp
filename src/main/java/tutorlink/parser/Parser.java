package tutorlink.parser;

import tutorlink.command.AddStudentCommand;
import tutorlink.command.Command;
import tutorlink.command.DeleteComponentCommand;
import tutorlink.command.DeleteGradeCommand;
import tutorlink.command.DeleteStudentCommand;
import tutorlink.command.ExitCommand;
import tutorlink.command.FindStudentCommand;
import tutorlink.command.InvalidCommand;
import tutorlink.command.ListComponentCommand;
import tutorlink.command.ListGradeCommand;
import tutorlink.command.ListStudentCommand;
import tutorlink.command.AddGradeCommand;
import tutorlink.command.AddComponentCommand;
import tutorlink.command.UpdateComponentCommand;

import java.util.HashMap;
import java.util.logging.Logger;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {
    private static final Logger LOGGER = Logger.getLogger(Parser.class.getName());

    private String extractCommandWord(String input) {
        String[] words = input.trim().split("\\s+");
        return words[0]; //return the first word
    }


    public Command getCommand(String line) {
        String commandWord = extractCommandWord(line);

        switch (commandWord.toLowerCase()) {
        case AddStudentCommand.COMMAND_WORD:
            return new AddStudentCommand(); // Calls delete command handling method

        case DeleteStudentCommand.COMMAND_WORD:
            return new DeleteStudentCommand(); // Calls add command handling method

        case FindStudentCommand.COMMAND_WORD:
            return new FindStudentCommand(); // Lists all students

        case ListStudentCommand.COMMAND_WORD:
            return new ListStudentCommand(); // Lists all students

        case AddGradeCommand.COMMAND_WORD:
            return new AddGradeCommand();

        case DeleteGradeCommand.COMMAND_WORD:
            return new DeleteGradeCommand();

        case AddComponentCommand.COMMAND_WORD:
            return new AddComponentCommand();

        case DeleteComponentCommand.COMMAND_WORD:
            return new DeleteComponentCommand();

        case ListComponentCommand.COMMAND_WORD:
            return new ListComponentCommand();

        case ListGradeCommand.COMMAND_WORD:
            return new ListGradeCommand();

        case UpdateComponentCommand.COMMAND_WORD:
            return new UpdateComponentCommand();

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand(); // Lists all students

        default:
            return new InvalidCommand();
        }

    }


    /**
     * Extracts command arguments from the input string based on the given argument prefixes.
     *
     * @param argumentPrefixes an array of valid argument prefixes (e.g., "n/", "i/")
     * @param line the user input containing command arguments
     * @return a HashMap where keys are the prefixes (e.g., "n/", "i/") and the values are the corresponding arguments
     */
    public HashMap<String, String> getArguments(String[] argumentPrefixes, String line) {
        HashMap<String, String> arguments = new HashMap<>();

        if (argumentPrefixes == null) {
            return arguments;
        }

        // Build regex pattern dynamically from the provided argument prefixes
        StringBuilder regexBuilder = new StringBuilder("(?i)(");
        for (String prefix : argumentPrefixes) {
            regexBuilder.append(Pattern.quote(prefix)).append("|");
        }
        regexBuilder.setLength(regexBuilder.length() - 1); // Remove the last "|"
        // Match argument that could have spaces, but ends before another prefix or end of the string
        regexBuilder.append(")([^\\s].*?)(?=(\\s+[^\\s]+/|$))");

        String regex = regexBuilder.toString();
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(line);

        // Iterate through all found tags and arguments
        while (matcher.find()) {
            String tag = matcher.group(1); // Group 1 is the tag (e.g., n/, i/, etc.)
            String argument = matcher.group(2).trim(); // Group 2 is the argument after the tag
            arguments.put(tag, argument); // Store the tag and argument
        }

        return arguments;
    }
}
