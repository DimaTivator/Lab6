package commonModule.commands.commandObjects;

import commonModule.commands.CommandTemplate;
import server.collectionManagement.CollectionManager;
import commonModule.dataStructures.Response;
import commonModule.exceptions.commandExceptions.InvalidArgumentsException;
import commonModule.collectionClasses.HumanBeing;
import commonModule.commands.CommandWithResponse;

import java.util.Collections;
import java.util.Map;

/**
 The UpdateCommand class updates the value of an existing key in the collection.
 */
public class UpdateCommand extends CommandTemplate implements CommandWithResponse {

    /**
     * Constructs a new UpdateCommand instance.
     * @param collectionManager The CollectionManager to execute the command on.
     */
    public UpdateCommand(CollectionManager collectionManager) {
        super(collectionManager);
    }

    public UpdateCommand() {}

    @Override
    public void setArgs(String[] args) throws InvalidArgumentsException {
        Long id;
        try {
            id = Long.parseLong(args[0]);
            super.setArgs(new String[]{ String.valueOf(id) });
        } catch (NumberFormatException e) {
            throw new InvalidArgumentsException("The key must be a number! Please Try to enter a command again");
        }
    }

    /**
     * Executes the UpdateCommand. This method updates the value of an existing key in the collection.
     */
    @Override
    public void execute() throws InvalidArgumentsException {
        Map<Long, HumanBeing> data = getCollectionManager().getCollection();
        Long id = Long.parseLong(getArgs()[0]);
        HumanBeing newValue = (HumanBeing) getValue();

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

        for (Long key : data.keySet()) {
            if (id.equals(data.get(key).getId())) {
                newValue.setId(data.get(key).getId());
                data.put(key, newValue);
                break;
            }
        }

        getCollectionManager().sort();
    }

    @Override
    public Response getCommandResponse() {
        return new Response("update", getArgs(), "Done!");
    }
}
