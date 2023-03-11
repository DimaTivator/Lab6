package commands;

import collectionManagement.CollectionManager;
import exceptions.commandExceptions.InvalidArgumentsException;
import storedClasses.HumanBeing;

import java.util.Map;

/**
 The UpdateCommand class updates the value of an existing key in the collection.
 */
public class UpdateCommand extends CommandTemplate {

    /**
     * Constructs a new UpdateCommand instance.
     * @param collectionManager The CollectionManager to execute the command on.
     */
    public UpdateCommand(CollectionManager collectionManager) {
        super(collectionManager);
    }

    @Override
    public void setArg(String arg) throws InvalidArgumentsException {
        Long id;
        try {
            id = Long.parseLong(arg);
            super.setArg(String.valueOf(id));
        } catch (NumberFormatException e) {
            throw new InvalidArgumentsException("The key must be a number! Please Try to enter a command again");
        }

        Map <Long, HumanBeing> data = getCollectionManager().getCollection();
        boolean containsId = false;
        for (Long key : data.keySet()) {
            if (id.equals(data.get(key).getId())) {
                containsId = true;
                break;
            }
        }

        if (!containsId) {
            throw new InvalidArgumentsException("Can't find the entered id in collection:(\nPlease try to enter the command again");
        }
    }

    /**
     * Executes the UpdateCommand. This method updates the value of an existing key in the collection.
     */
    @Override
    public void execute() throws InvalidArgumentsException {
        Map<Long, HumanBeing> data = getCollectionManager().getCollection();
        Long id = Long.parseLong(getArg());
        HumanBeing newValue = (HumanBeing) getValue();

        for (Long key : data.keySet()) {
            if (id.equals(data.get(key).getId())) {
                data.put(key, newValue);
                break;
            }
        }
    }
}
