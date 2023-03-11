import auxiliaryClasses.ConsoleColors;
import collectionManagement.CollectionManager;
import collectionManagement.CollectionPrinter;
import collectionManagement.CollectionSaver;
import commands.CommandsExecutor;
import io.consoleIO.CommandParser;
import io.consoleIO.ConfirmationReader;
import io.fileIO.in.HumanBeingXMLParser;
import io.fileIO.in.Parser;
import io.fileIO.out.HumanBeingXMLWriter;
import storedClasses.HumanBeing;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Main class that contains the main method for starting the program.
 */
public class Main {
    /**
     * Main method that starts the program. It creates objects for managing the collection,
     * printing the collection, parsing XML data, executing commands, reading user input, and parsing commands.
     * The method also contains an infinite loop that reads and executes user commands until the program is terminated.
     * @param args - Arguments passed to the main method.
     */
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println(ConsoleColors.RED + "You should enter a file path to the xml file with collection!" +
                    ConsoleColors.RESET);
            System.exit(0);
        }

        CollectionManager collectionManager = new CollectionManager();
        CollectionPrinter collectionPrinter = new CollectionPrinter();

        CollectionSaver collectionSaver = new CollectionSaver(collectionManager, new HumanBeingXMLWriter());

        Parser<LinkedHashMap<Long, HumanBeing>> humanBeingXMLParser = new HumanBeingXMLParser();
        // collectionManager.setCollection(humanBeingXMLParser.parseDataToHashMap("src/dataFiles/data.xml"));

        File savedCollection = new File("/tmp/s367054Lab5Saves/.save.xml");

        if (savedCollection.exists()) {

            System.out.println("At the last launch, the program completed its work unscheduled.\n" +
                    "Do you want to download the previous data?");

            ConfirmationReader confirmationReader = new ConfirmationReader();
            String confirmation = confirmationReader.readObjectFromConsole();
            if (confirmation.equals("Y")) {
                collectionManager.setCollection(humanBeingXMLParser.parseData("/tmp/s367054Lab5Saves/.save.xml"));
            } else {
                collectionManager.setCollection(humanBeingXMLParser.parseData(args[0]));
            }

        } else {
            collectionManager.setCollection(humanBeingXMLParser.parseData(args[0]));
        }

        // delete saves
        savedCollection.delete();

        CommandsExecutor commandsExecutor = new CommandsExecutor(collectionManager, collectionPrinter);
        Scanner scanner = new Scanner(System.in);
        CommandParser commandParser = new CommandParser();

        System.out.println("If you want to see the list of available commands, enter 'help'");

        while (true) {
            try {
                commandsExecutor.execute(commandParser.readObjectFromConsole(), "console", scanner);
            } catch (NoSuchElementException e) {
                break;
            } catch (Exception e) {
                collectionSaver.saveCollection();
                System.out.println(e.getMessage());
            }
        }
    }
}
