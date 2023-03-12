package commands;

import collectionManagement.CollectionManager;
import collectionManagement.CollectionPrinter;
import collectionManagement.CollectionSaver;
import dataStructures.Triplet;
import exceptions.ScriptsRecursionException;
import exceptions.commandExceptions.InvalidArgumentsException;
import io.consoleIO.CommandParser;
import io.fileIO.out.HumanBeingXMLWriter;
import io.humanBeingInput.HumanBeingObjectConsoleReader;
import io.humanBeingInput.HumanBeingObjectFileReader;
import collectionClasses.HumanBeing;

import java.util.*;

/**
 * The `CommandsExecutor` class is responsible for executing commands.
 * It uses two objects of the `CollectionPrinter` and `CollectionManager` classes.
 */
public class CommandsExecutor {

    /**
     * The `collectionPrinter` object is used for printing various information about the collection.
     */
    private final CollectionPrinter collectionPrinter;

    /**
     * The `collectionManager` object is used for managing the collection.
     */
    private final CollectionManager collectionManager;

    /**
     * The set with used files in the command `execute_script`
     */
    private final Set<String> used_scripts = new HashSet<>();

    private final ArrayList<String> objectCommandsList = new ArrayList<>();

    private final ArrayList<String> lineArgCommands = new ArrayList<>();

    private final Map<String, Command> commandsTable = new HashMap<>();

    private void fillCommandLists() {

        ArrayList<String> humanBeingCommandsList = CommandParser.getHumanBeingCommandsList();
        objectCommandsList.addAll(humanBeingCommandsList);
        objectCommandsList.add("filter_less_than_car");

        lineArgCommands.add("remove_key");
        lineArgCommands.add("execute_script");
        lineArgCommands.add("remove_greater_key");
        lineArgCommands.add("count_less_than_impact_speed");
        lineArgCommands.add("insert");
        lineArgCommands.add("update");
        lineArgCommands.add("replace_if_greater");

        commandsTable.put("help", new HelpCommand(collectionPrinter));
        commandsTable.put("info", new InfoCommand(collectionManager, collectionPrinter));
        commandsTable.put("show", new ShowCommand(collectionManager));
        commandsTable.put("insert", new InsertCommand(collectionManager));
        commandsTable.put("update", new UpdateCommand(collectionManager));
        commandsTable.put("remove_key", new RemoveKeyCommand(collectionManager));
        commandsTable.put("clear", new ClearCollectionCommand(collectionManager));
        commandsTable.put("save", new SaveCollectionToFileCommand(collectionManager));
        commandsTable.put("execute_script", new ExecuteScriptCommand(collectionManager, collectionPrinter, this));
        commandsTable.put("remove_lower", new RemoveLowerCommand(collectionManager));
        commandsTable.put("replace_if_greater", new ReplaceIfGreaterCommand(collectionManager));
        commandsTable.put("remove_greater_key", new RemoveGreaterKeyCommand(collectionManager));
        commandsTable.put("count_less_than_impact_speed", new CountLessThanImpactSpeedCommand(collectionManager));
        commandsTable.put("filter_less_than_car", new FilterLessThanCarCommand(collectionManager));
        commandsTable.put("print_unique_mood", new PrintUniqueMoodCommand(collectionManager));
        commandsTable.put("exit", new ExitCommand());
    }

    /**
     * Constructs a `CommandsExecutor` object with the given `collectionManager` and `collectionPrinter`.
     *
     * @param collectionManager the `CollectionManager` object used for managing the collection
     * @param collectionPrinter the `CollectionPrinter` object used for printing information about the collection
     */
    public CommandsExecutor(CollectionManager collectionManager, CollectionPrinter collectionPrinter) {
        this.collectionManager = collectionManager;
        this.collectionPrinter = collectionPrinter;
        fillCommandLists();
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

     @param commandTriplet a Triplet of command name and its arguments
     @throws Exception if an error occurs during the execution of the command
     */
    public void execute(Triplet<String, String[], Object> commandTriplet) throws Exception {
        String commandName = commandTriplet.getFirst();
        String[] args = commandTriplet.getSecond();
        Object object = commandTriplet.getThird();

        Map<Long, HumanBeing> dataBeforeCommand = collectionManager.getCollection();

        Command command = commandsTable.get(commandName);

        if (commandName.equals("execute_script")) {
            if (args.length != 1) {
                throw new InvalidArgumentsException();
            }

            if (used_scripts.contains(args[0])) {
                throw new ScriptsRecursionException("You should not call command `execute_script` recursively!");
            }

            used_scripts.add(args[0]);

            command.setArg(args[0]);

            command.execute();

            used_scripts.clear();

        } else {
            if (lineArgCommands.contains(commandName)) {
                if (args.length == 0) {
                    throw new InvalidArgumentsException("Something wrong with command arguments :(\n" +
                            "Please check that you do not enter any arguments in the same line with the command");
                }
                command.setArg(args[0]);
            }
            if (objectCommandsList.contains(commandName)) {
                command.setValue(object);
            }

            command.execute();
        }


        // check if there are changes in collection values
        Map<Long, HumanBeing> dataAfterCommand = collectionManager.getCollection();
        boolean collectionChanged = checkCollectionChanges(dataBeforeCommand, dataAfterCommand);

        // setting flag to exitCommand if there are changes
        ExitCommand exitCommand = (ExitCommand) commandsTable.get("exit");
        exitCommand.setCollectionChanged(collectionChanged);

        // save collection
        CollectionSaver collectionSaver = new CollectionSaver(collectionManager, new HumanBeingXMLWriter());
        collectionSaver.saveCollection();
    }
}
