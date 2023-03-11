package commands;

import collectionManagement.CollectionManager;
import exceptions.EmptyCollectionException;
import storedClasses.HumanBeing;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * The RemoveLowerCommand class extends the Command class.
 * This class is used to remove all key-value pairs in the collection where the value is less than the specified human being.
 */
public class RemoveLowerCommand extends CommandTemplate {

    /**
     * Constructs a RemoveLowerCommand object with a CollectionManager object and a human being.
     *
     * @param collectionManager The CollectionManager object that the command operates on.
     */
    public RemoveLowerCommand(CollectionManager collectionManager) {
        super(collectionManager);
    }

    /**
     * Removes all key-value pairs in the collection where the value is less than the specified human being.
     * Throws an EmptyCollectionException if the collection is empty.
     *
     * @throws EmptyCollectionException If the collection is empty.
     */
    @Override
    public void execute() throws EmptyCollectionException {
        Map<Long, HumanBeing> data = getCollectionManager().getCollection();
        HumanBeing humanBeing = (HumanBeing) getValue();

        if (data.isEmpty()) {
            throw new EmptyCollectionException();
        }

        /*
         * A set to store the keys of the key-value pairs where the value is less than the specified human being.
         */
        Set<Long> keySet = new HashSet<>();

        data.forEach((key, value) -> {
            if (value.compareTo(humanBeing) < 0) {
                keySet.add(key);
            }
        });

        keySet.forEach(data::remove);
    }
}

