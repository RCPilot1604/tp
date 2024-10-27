package tutorlink;

import tutorlink.model.AppState;
import tutorlink.logic.command.Command;
import tutorlink.model.exceptions.TutorLinkException;
import tutorlink.logic.result.CommandResult;
import tutorlink.ui.Ui;
import tutorlink.logic.parser.Parser;

import java.io.IOException;
import java.util.HashMap;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.util.logging.FileHandler;

/**
 * Represents the main class containing the entry point for the TutorLink application
 */
public class TutorLink {
    private static final Ui ui = new Ui();
    private static final Logger LOGGER = Logger.getLogger(TutorLink.class.getName());

    /**
     * Main entry-point for TutorLink
     */
    public static void main(String[] args) {

        setUpLogger();
        LOGGER.log(Level.INFO, "Test log message successful");

        ui.displayWelcomeMessage();
        while (true) {
            ui.runApp();
        }
    }

    private static void setUpLogger() {
        LogManager.getLogManager().reset();
        Logger rootLogger = Logger.getLogger("");
        rootLogger.setLevel(Level.WARNING);
        ConsoleHandler ch = new ConsoleHandler();
        ch.setLevel(Level.INFO);
        rootLogger.addHandler(ch);

        try {
            FileHandler fh = new FileHandler("myLogger.log");
            fh.setLevel(Level.FINE);
            rootLogger.addHandler(fh);
        } catch (IOException e) {
            //ignore
            rootLogger.log(Level.SEVERE, "File logger not working", e);
        }
    }
}
