package commands;

import auxiliaryClasses.ConsoleColors;
import collectionManagement.CollectionManager;
import collectionManagement.CollectionPrinter;

/**
 * InfoCommand is a class that provides information about the collection,
 * such as the type of the collection, the date of initialization, and the number of elements in the collection.
 */
public class InfoCommand extends CommandTemplate {

    /**
     * Constructs a new InfoCommand with the specified CollectionManager and CollectionPrinter.
     * @param collectionManager the CollectionManager that manages the collection
     * @param collectionPrinter the CollectionPrinter that provides the functionality to print the collection
     */
    public InfoCommand(CollectionManager collectionManager, CollectionPrinter collectionPrinter) {
        super(collectionManager, collectionPrinter);
    }

    /**
     * Prints the information about the collection, including the type of the collection, the date of initialization,
     * and the number of elements in the collection.
     */
    @Override
    public void execute() {

        CollectionManager collectionManager = getCollectionManager();

        System.out.println(ConsoleColors.GREEN + "Collection type: " + ConsoleColors.RESET +
                collectionManager.getCollection().getClass());

        System.out.println(ConsoleColors.GREEN + "Date of initialization: " + ConsoleColors.RESET +
                collectionManager.getCreationDate());

        System.out.println(ConsoleColors.GREEN + "Number of elements in collection: " + ConsoleColors.RESET +
                collectionManager.getCollection().size());

        System.out.println();
    }
}
