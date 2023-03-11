package commands;

import collectionManagement.CollectionManager;
import collectionManagement.CollectionPrinter;
import collectionManagement.ScriptExecutor;

/**
 Class that executes the commands from a script file.
 */
public class ExecuteScriptCommand extends CommandTemplate {

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
        ScriptExecutor scriptExecutor = new ScriptExecutor(getCollectionManager(), getCollectionPrinter());
        scriptExecutor.executeScript(getArg(), commandsExecutor);
    }
}
