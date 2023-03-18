package commonModule.commands;

import commonModule.commands.commandObjects.*;
import server.collectionManagement.CollectionManager;
import server.collectionManagement.CollectionPrinter;
import server.collectionManagement.CollectionSaver;
import commonModule.dataStructures.Response;
import commonModule.dataStructures.Triplet;
import commonModule.exceptions.ScriptsRecursionException;
import commonModule.exceptions.commandExceptions.InvalidArgumentsException;
import commonModule.io.consoleIO.CommandParser;
import commonModule.io.fileIO.out.HumanBeingXMLWriter;
import commonModule.collectionClasses.HumanBeing;

import java.util.*;

/**
 * The `CommandsExecutor` class is responsible for executing commands.
 * It uses two objects of the `CollectionPrinter` and `CollectionManager` classes.
 */
public class CommandsExecutor {

    private Response response;


    /**
     * The `collectionPrinter` object is used for printing various information about the collection.
     */
    private final CollectionPrinter collectionPrinter;

    /**
     * The `collectionManager` object is used for managing the collection.
     */
    private final CollectionManager collectionManager;

//    private final ArrayList<String> objectCommandsList = new ArrayList<>();
//
//    private final ArrayList<String> lineArgCommands = new ArrayList<>();
//
//    private final Map<String, Command> commandsTable = new HashMap<>();
//
//    private void fillCommandLists() {
//
//        ArrayList<String> humanBeingCommandsList = CommandParser.getHumanBeingCommandsList();
//        objectCommandsList.addAll(humanBeingCommandsList);
//        objectCommandsList.add("filter_less_than_car");
//
//        lineArgCommands.add("remove_key");
//        lineArgCommands.add("execute_script");
//        lineArgCommands.add("remove_greater_key");
//        lineArgCommands.add("count_less_than_impact_speed");
//        lineArgCommands.add("insert");
//        lineArgCommands.add("update");
//        lineArgCommands.add("replace_if_greater");
//
//        commandsTable.put("help", new HelpCommand(collectionPrinter));
//        commandsTable.put("info", new InfoCommand(collectionManager, collectionPrinter));
//        commandsTable.put("show", new ShowCommand(collectionManager));
//        commandsTable.put("insert", new InsertCommand(collectionManager));
//        commandsTable.put("update", new UpdateCommand(collectionManager));
//        commandsTable.put("remove_key", new RemoveKeyCommand(collectionManager));
//        commandsTable.put("clear", new ClearCollectionCommand(collectionManager));
//        commandsTable.put("save", new SaveCollectionToFileCommand(collectionManager));
//        commandsTable.put("execute_script", new ExecuteScriptCommand(collectionManager, collectionPrinter, this));
//        commandsTable.put("remove_lower", new RemoveLowerCommand(collectionManager));
//        commandsTable.put("replace_if_greater", new ReplaceIfGreaterCommand(collectionManager));
//        commandsTable.put("remove_greater_key", new RemoveGreaterKeyCommand(collectionManager));
//        commandsTable.put("count_less_than_impact_speed", new CountLessThanImpactSpeedCommand(collectionManager));
//        commandsTable.put("filter_less_than_car", new FilterLessThanCarCommand(collectionManager));
//        commandsTable.put("print_unique_mood", new PrintUniqueMoodCommand(collectionManager));
//        commandsTable.put("exit", new ExitCommand());
//    }


    /**
     * Constructs a `CommandsExecutor` object with the given `collectionManager` and `collectionPrinter`.
     *
     * @param collectionManager the `CollectionManager` object used for managing the collection
     * @param collectionPrinter the `CollectionPrinter` object used for printing information about the collection
     */
    public CommandsExecutor(CollectionManager collectionManager, CollectionPrinter collectionPrinter) {
        this.collectionManager = collectionManager;
        this.collectionPrinter = collectionPrinter;
        // fillCommandLists();
    }

    private boolean checkCollectionChanges(Map<Long, HumanBeing> collectionBeforeCommand, Map<Long, HumanBeing> collectionAfterCommand) {
        boolean equals = (collectionAfterCommand.size() == collectionBeforeCommand.size());
        for (Long key : collectionAfterCommand.keySet()) {
            if (!collectionBeforeCommand.containsKey(key) || !collectionBeforeCommand.get(key).equals(collectionAfterCommand.get(key))) {
                equals = false;
            }
        }
        return equals;
    }

    /**
     Executes the given command.

     @throws Exception if an error occurs during the execution of the command
     */
    public void execute(CommandWithResponse command) throws Exception {

        command.setCollectionManager(collectionManager);
        command.setCollectionPrinter(collectionPrinter);

        command.execute();

        response = command.getCommandResponse();

    }

    public Response getCommandResponse() {
        return response;
    }
}
