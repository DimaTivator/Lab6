package server.commands.commandObjects;

import commonModule.collectionManagement.CollectionManager;
import commonModule.collectionClasses.HumanBeing;
import commonModule.dataStructures.Response;
import server.commands.CommandTemplate;
import server.commands.CommandWithResponse;

import java.util.Map;

/**
 * The class clears the collection.
 * The class uses the CollectionManager to access the data.
 * The class extends the abstract class "Command"
 */
public class ClearCollectionCommand extends CommandTemplate implements CommandWithResponse {

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

    @Override
    public Response getCommandResponse() {
        return new Response("clear", getArgs(), "Done!");
    }
}
