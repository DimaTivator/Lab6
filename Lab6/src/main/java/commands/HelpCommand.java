package commands;

import auxiliaryClasses.ConsoleColors;
import collectionManagement.CollectionManager;
import collectionManagement.CollectionPrinter;

import java.util.Map;

/**
 * The HelpCommand class implements the behavior for the "help" command.
 * This command is used to display the available commands in the system and their descriptions.
 */
public class HelpCommand extends CommandTemplate {

    /**
     Constructs a HelpCommand object.
     @param collectionPrinter the object that helps with printing the collection data
     */
    public HelpCommand(CollectionPrinter collectionPrinter) {
        super(collectionPrinter);
    }

    /**
     This method displays the available commands in the system and their descriptions.
     */
    @Override
    public void execute() {
        CollectionPrinter collectionPrinter = getCollectionPrinter();
        Map<String, String> commands = collectionPrinter.getCommands();

        commands.forEach((key, value) -> {
            System.out.print(ConsoleColors.GREEN);
            for (int i = 0; i < key.length(); i++) {
                char letter = key.charAt(i);
                if (letter == '<') {
                    System.out.print(letter + ConsoleColors.PURPLE);
                } else if (letter == '>') {
                    System.out.print(ConsoleColors.GREEN + letter);
                } else {
                    System.out.print(letter);
                }
            }
            System.out.println(ConsoleColors.RESET + ": " + value);
        });
        System.out.println();
    }
}
