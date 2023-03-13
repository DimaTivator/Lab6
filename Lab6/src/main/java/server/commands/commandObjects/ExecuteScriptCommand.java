package server.commands.commandObjects;

import commonModule.collectionManagement.CollectionManager;
import commonModule.collectionManagement.CollectionPrinter;
import commonModule.collectionManagement.ScriptExecutor;
import commonModule.dataStructures.Response;
import server.commands.CommandTemplate;
import server.commands.CommandWithResponse;
import server.commands.CommandsExecutor;

/**
 Class that executes the commands from a script file.
 */
public class ExecuteScriptCommand extends CommandTemplate implements CommandWithResponse {

    private final CommandsExecutor commandsExecutor;

    /**
     Constructor with collection manager, collection printer, and file path arguments.
     @param collectionManager Collection manager that holds the collection.
     @param collectionPrinter Collection printer for printing collection information.
     */
    public ExecuteScriptCommand(CollectionManager collectionManager, CollectionPrinter collectionPrinter, CommandsExecutor commandsExecutor) {
        super(collectionManager, collectionPrinter);
        this.commandsExecutor = commandsExecutor;
    }

    /**
     Executes the script commands from the file.
     @throws Exception If there are any errors while executing the script.
     */
    @Override
    public void execute() throws Exception {
        ScriptExecutor scriptExecutor = new ScriptExecutor();
        scriptExecutor.executeScript(getArgs()[0], commandsExecutor);
    }

    @Override
    public Response getCommandResponse() {
        return new Response("execute_script", getArgs(), "Done!");
    }
}
