package commands;

import collectionManagement.CollectionManager;
import exceptions.EmptyCollectionException;
import storedClasses.HumanBeing;

import java.util.Map;

/**
 * Class representing the ShowCommand, which extends the Command class.
 */
public class ShowCommand extends CommandTemplate {

    /**
     * Constructor that takes in a CollectionManager object.
     * @param collectionManager the CollectionManager object.
     */
    public ShowCommand(CollectionManager collectionManager) {
        super(collectionManager);
    }

    /**
     * Overrides the execute method to show the data of the collection.
     * Throws an EmptyCollectionException if the collection is empty.
     * @throws EmptyCollectionException if the collection is empty.
     */
    @Override
    public void execute() throws EmptyCollectionException {
        Map<Long, HumanBeing> data = getCollectionManager().getCollection();

        if (data.isEmpty()) {
            throw new EmptyCollectionException();
        }

        data.forEach((key, value) -> System.out.println(key + ":\n" + value));
    }
}
