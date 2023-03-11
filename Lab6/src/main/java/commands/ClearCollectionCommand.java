package commands;

import collectionManagement.CollectionManager;
import storedClasses.HumanBeing;

import java.util.Map;

/**
 * The class clears the collection.
 * The class uses the CollectionManager to access the data.
 * The class extends the abstract class "Command"
 */
public class ClearCollectionCommand extends CommandTemplate {

    /**
     Constructor for ClearCollectionCommand class.
     @param collectionManager the manager of the collection.
     */
    public ClearCollectionCommand(CollectionManager collectionManager) {
        super(collectionManager);
    }

    /**
     * Method that clears the collection.
     */
    @Override
    public void execute() {
        Map<Long, HumanBeing> data = getCollectionManager().getCollection();
        data.clear();
    }
}
