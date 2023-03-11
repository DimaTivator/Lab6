package commands;

import collectionManagement.CollectionManager;
import collectionManagement.CollectionPrinter;
import collectionManagement.CollectionSaver;
import dataStructures.Pair;
import exceptions.ScriptsRecursionException;
import exceptions.commandExceptions.InvalidArgumentsException;
import io.fileIO.out.HumanBeingXMLWriter;
import io.humanBeingInput.CarObjectConsoleReader;
import io.humanBeingInput.CarObjectFileReader;
import io.humanBeingInput.HumanBeingObjectConsoleReader;
import io.humanBeingInput.HumanBeingObjectFileReader;
import storedClasses.Car;
import storedClasses.HumanBeing;

import java.util.*;

/**
 * The `CommandsExecutor` class is responsible for executing commands.
 * It uses two objects of the `CollectionPrinter` and `CollectionManager` classes.
 */
public class CommandsExecutor {

    private boolean collectionChanged = false;

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

    private final ArrayList<String> humanBeingCommandsList = new ArrayList<>();

    private final ArrayList<String> lineArgCommands = new ArrayList<>();

    private final Map<String, Command> commandsTable = new HashMap<>();

    private void fillCommandLists() {
        humanBeingCommandsList.add("insert");
        humanBeingCommandsList.add("update");
        humanBeingCommandsList.add("remove_lower");
        humanBeingCommandsList.add("replace_if_greater");

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

    /**
     * Returns a `HumanBeing` object read from the console.
     *
     * @return the `HumanBeing` object read from the console
     */
    private HumanBeing getHumanBeingFromConsole() {
        HumanBeingObjectConsoleReader humanBeingObjectReader = new HumanBeingObjectConsoleReader();
        return humanBeingObjectReader.readHumanBeingFromConsole();
    }

    /**
     * This method reads a `HumanBeing` object from a file and returns it.
     *
     * @param fileScanner the `Scanner` instance that reads the file
     * @return the `HumanBeing` object read from the file
     */
    private HumanBeing getHumanBeingFromFile(Scanner fileScanner) throws Exception {
        HumanBeingObjectFileReader humanBeingObjectFileReader = new HumanBeingObjectFileReader(fileScanner);
        return humanBeingObjectFileReader.readHumanBeingFromFile();
    }

    /**
     Executes the given command.

     @param commandPair a Pair of command name and its arguments
     @param stream source of the input data ("console" or "file")
     @param scanner the Scanner object to read input from
     @throws Exception if an error occurs during the execution of the command
     */
    public void execute(Pair<String, String[]> commandPair, String stream, Scanner scanner) throws Exception {
        String commandName = commandPair.getFirst();
        String[] args = commandPair.getSecond();

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
        }

        else if (commandName.equals("filter_less_than_car")) {

            if (args.length != 0) {
                throw new InvalidArgumentsException("Something wrong with command arguments :(\n" +
                        "Please check that you do not enter any arguments in the same line with the command");
            }
            try {
                Car car;

                if (stream.equals("console")) {
                    CarObjectConsoleReader carObjectReader = new CarObjectConsoleReader();
                    car = carObjectReader.readObjectFromConsole();
                } else {
                    CarObjectFileReader carObjectFileReader = new CarObjectFileReader(scanner);
                    car = carObjectFileReader.readData();
                }

                command.setValue(car);

            } catch (Exception e) {
                System.out.println(e.getMessage());
                throw new InvalidArgumentsException();
            }

            command.execute();
        }
        else if (commandName.equals("save")) {

            if (args.length == 0) {
                command.setArg("humanbeing.xml");
                collectionChanged = false;
            } else if (args.length == 1) {
                command.setArg(args[0]);
                collectionChanged = false;
            } else {
                throw new InvalidArgumentsException();
            }

            command.execute();
        }
        else if (lineArgCommands.contains(commandName)) {
            if (args.length != 1) {
                throw new InvalidArgumentsException("Something wrong with command arguments :(\n" +
                        "Please check that you do not enter any arguments in the same line with the command");
            }

            try {
                command.setArg(args[0]);
                if (humanBeingCommandsList.contains(commandName)) {
                    if (stream.equals("console")) {
                        command.setValue(getHumanBeingFromConsole());
                    } else {
                        command.setValue(getHumanBeingFromFile(scanner));
                    }
                }
                command.execute();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

        }
        else if (humanBeingCommandsList.contains(commandName)) {
            if (stream.equals("console")) {
                command.setValue(getHumanBeingFromConsole());
            } else {
                command.setValue(getHumanBeingFromFile(scanner));
            }
            command.execute();
        }
        else {
            command.execute();
        }


        Map<Long, HumanBeing> dataAfterCommand = collectionManager.getCollection();

        boolean equals = (dataAfterCommand.size() == dataBeforeCommand.size());
        for (Long key : dataAfterCommand.keySet()) {
            if (!dataBeforeCommand.containsKey(key) || !dataBeforeCommand.get(key).equals(dataAfterCommand.get(key))) {
                equals = false;
            }
        }

        collectionChanged = equals;

        ExitCommand exitCommand = (ExitCommand) commandsTable.get("exit");
        exitCommand.setCollectionChanged(collectionChanged);

        // save collection
        CollectionSaver collectionSaver = new CollectionSaver(collectionManager, new HumanBeingXMLWriter());
        collectionSaver.saveCollection();
    }
}
