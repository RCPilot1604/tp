package tutorlink.ui;

import tutorlink.logic.Logic;
import tutorlink.model.AppState;
import tutorlink.model.exceptions.TutorLinkException;
import tutorlink.logic.result.CommandResult;

import java.util.Scanner;

public class Ui {
    private Scanner in = new Scanner(System.in);
    private AppState appState;
    private Logic logic;
    private final String logo = "___________      __               .____    .__        __\n"
            + "\\__    ___/_ ___/  |_  ___________|    |   |__| ____ |  | __\n"
            + "  |    | |  |  \\   __\\/  _ \\_  __ \\    |   |  |/    \\|  |/ /\n"
            + "  |    | |  |  /|  | (  <_> )  | \\/    |___|  |   |  \\    <\n"
            + "  |____| |____/ |__|  \\____/|__|  |_______ \\__|___|  /__|_ \\\n"
            + "                                          \\/       \\/     \\/\n";
    private final String halfBreakLine =
            "-------------------------";
    private final String fullBreakLine =
            "-------------------------------------------------------------";

    public Ui() {
        appState = new AppState();
        logic = new Logic();
    }

    public void runApp() {
        //@@author ThienDuc
        try {
            String line = getUserInput();
            CommandResult result = logic.execute(appState, line);
            displayResult(result);
        } catch (TutorLinkException e) {
            displayException(e);
        }
        //@@author
    }
    public String getUserInput() {
        return in.nextLine();
    }

    public void displayWelcomeMessage() {
        System.out.println(logo);
        System.out.println(fullBreakLine);
        System.out.println("Hello! I'm TutorLink\nWhat can I do for you?");
        System.out.println(fullBreakLine);
    }

    public void displayResult(CommandResult result) {
        System.out.println(halfBreakLine + "   Result   " + halfBreakLine);
        System.out.println(result.toString());
        System.out.println(fullBreakLine);
    }

    public void displayException(TutorLinkException error) {
        System.out.println(halfBreakLine + "   Error   " + halfBreakLine);
        System.out.println(error.getMessage());
        System.out.println(fullBreakLine);
    }
}
