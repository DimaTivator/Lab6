package server.commands.commandObjects;

import commonModule.collectionManagement.CollectionManager;
import commonModule.dataStructures.Response;
import commonModule.exceptions.commandExceptions.InvalidArgumentsException;
import commonModule.collectionClasses.HumanBeing;
import server.commands.CommandTemplate;
import server.commands.CommandWithResponse;

import java.util.Map;

/**
 * InsertCommand is a class that extends the Command class and provides an implementation for
 * inserting an element into the collection.
 */
public class InsertCommand extends CommandTemplate implements CommandWithResponse {

    /**
     * Constructor for the InsertCommand class
     *
     * @param collectionManager the CollectionManager instance to get the collection from
     */
    public InsertCommand(CollectionManager collectionManager) {
        super(collectionManager);
    }

    @Override
    public void setArgs(String[] args) throws InvalidArgumentsException {
        Long key;
        try {
            key = Long.parseLong(args[0]);
            super.setArgs(new String[]{ String.valueOf(key) });
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
        data.put(Long.parseLong(getArgs()[0]), (HumanBeing) getValue());
    }

    @Override
    public Response getCommandResponse() {
        return new Response("insert", getArgs(), "Done!");
    }
}
