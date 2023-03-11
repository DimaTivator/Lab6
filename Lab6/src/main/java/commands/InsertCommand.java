package commands;

import collectionManagement.CollectionManager;
import exceptions.commandExceptions.InvalidArgumentsException;
import storedClasses.HumanBeing;

import java.util.Map;

/**
 * InsertCommand is a class that extends the Command class and provides an implementation for
 * inserting an element into the collection.
 */
public class InsertCommand extends CommandTemplate {

    /**
     * Constructor for the InsertCommand class
     *
     * @param collectionManager the CollectionManager instance to get the collection from
     */
    public InsertCommand(CollectionManager collectionManager) {
        super(collectionManager);
    }

    @Override
    public void setArg(String arg) throws InvalidArgumentsException {
        Long key;
        try {
            key = Long.parseLong(arg);
            super.setArg(String.valueOf(key));
        } catch (NumberFormatException e) {
            throw new InvalidArgumentsException("The key must be a number! Please Try to enter a command again");
        }

        if (getCollectionManager().getCollection().containsKey(key)) {
            throw new InvalidArgumentsException("Collection already contains this key!\nPlease try to enter a command again");
        }
    }

    /**
     * The method overrides the execute method from the Command class and provides an implementation
     * to insert the element into the collection.
     */
    @Override
    public void execute() throws InvalidArgumentsException {
        Map<Long, HumanBeing> data = getCollectionManager().getCollection();
        data.put(Long.parseLong(getArg()), (HumanBeing) getValue());
    }
}
