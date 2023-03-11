package commands;

import collectionManagement.CollectionManager;
import exceptions.commandExceptions.InvalidArgumentsException;
import storedClasses.HumanBeing;

import java.util.Map;

/**
 * The ReplaceIfGreaterCommand class extends the Command class.
 * This class is used to replace the value in the collection associated with a specified key with the specified value
 * if the value is greater than the current value.
 */
public class ReplaceIfGreaterCommand extends CommandTemplate {

    /**
     * Constructs a ReplaceIfGreaterCommand object with a CollectionManager object, a key, and a value.
     *
     * @param collectionManager The CollectionManager object that the command operates on.
     */
    public ReplaceIfGreaterCommand(CollectionManager collectionManager) {
        super(collectionManager);
    }

    @Override
    public void setArg(String arg) throws InvalidArgumentsException {
        try {
            Long key = Long.parseLong(arg);
            super.setArg(String.valueOf(key));
        } catch (NumberFormatException e) {
            throw new InvalidArgumentsException("The key must be a number! Please Try to enter a command again");
        }
    }

    /**
     * Replaces the value in the collection associated with the specified key with the specified value
     * if the value is greater than the current value.
     */
    @Override
    public void execute() {
        Map<Long, HumanBeing> data = getCollectionManager().getCollection();
        Long key = Long.parseLong(getArg());
        HumanBeing value = (HumanBeing) getValue();

        if (value.compareTo(data.get(key)) > 0) {
            data.put(key, value);
        }
    }
}

