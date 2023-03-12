package io.consoleIO;

import auxiliaryClasses.ConsoleColors;
import dataStructures.Pair;
import dataStructures.Triplet;
import exceptions.InvalidInputException;
import exceptions.commandExceptions.NoSuchCommandException;
import io.humanBeingInput.CarObjectConsoleReader;
import io.humanBeingInput.CarObjectFileReader;
import io.humanBeingInput.HumanBeingObjectConsoleReader;
import io.humanBeingInput.HumanBeingObjectFileReader;

import java.util.*;

/**
 * The {@code CommandParser} class is a console reader that is used to parse commands and arguments from the console or
 * a file.
 */
public class CommandParser extends ConsoleReader<Triplet<String, String[], Object>> {

    /**
     * The {@code commandsList} ArrayList stores a list of supported commands.
     */
    private static final ArrayList<String> commandsList = new ArrayList<>() {{
        add("help");
        add("info");
        add("show");
        add("insert");
        add("update");
        add("remove_key");
        add("clear");
        add("save");
        add("save");
        add("execute_script");
        add("exit");
        add("remove_lower");
        add("replace_if_greater");
        add("remove_greater_key");
        add("count_less_than_impact_speed");
        add("filter_less_than_car");
        add("print_unique_mood");
    }};

    private static final ArrayList<String> humanBeingCommandsList = new ArrayList<>() {{
        add("insert");
        add("update");
        add("remove_lower");
        add("replace_if_greater");
    }};

    public static ArrayList<String> getHumanBeingCommandsList() {
        return humanBeingCommandsList;
    }

    /**
     * Returns the {@code commandsList}.
     *
     * @return an ArrayList of supported commands
     */
    public static ArrayList<String> getCommandsList() {
        return commandsList;
    }


    /**
     * Reads a command and its arguments from the console and returns them as a Pair of command and arguments.
     *
     * @return a Pair of command and arguments
     * @throws NoSuchCommandException if the entered command is not found in the list of supported commands
     */
    @Override
    public Triplet<String, String[], Object> readObjectFromConsole() throws NoSuchCommandException, InvalidInputException {
        Scanner scanner = getConsoleScanner();
        System.out.print(ConsoleColors.BLUE_BRIGHT + "Enter a command: " + ConsoleColors.RESET);

        Pair<String, String[]> input = getCommand(scanner);
        String commandName = input.getFirst();
        String[] args = input.getSecond();
        Object object = null;

        if (humanBeingCommandsList.contains(commandName)) {
            HumanBeingObjectConsoleReader humanBeingObjectConsoleReader = new HumanBeingObjectConsoleReader();
            object = humanBeingObjectConsoleReader.readHumanBeingFromConsole();
        }

        if (commandName.equals("filter_less_than_car")) {
            CarObjectConsoleReader carObjectConsoleReader = new CarObjectConsoleReader();
            object = carObjectConsoleReader.readObjectFromConsole();
        }

        return new Triplet<>(commandName, args, object);
    }


    private Pair<String, String[]> getCommand(Scanner scanner) throws NoSuchCommandException {
        List<String> line = Arrays.stream(scanner.nextLine().strip().replaceAll(" +", " ").split(" ")).toList();

        // System.out.println(line);

        if (!commandsList.contains(line.get(0)) && !line.get(0).equals("")) {
            throw new NoSuchCommandException(String.format("Can't find command %s in commands list\n", line.get(0)) +
                    "Please try to enter command again\n");
        }

        String command = line.get(0);
        String[] args = line.subList(1, line.size()).toArray(new String[0]);

        return new Pair<>(command, args);
    }

    /**
     * Reads a command and its arguments from the file and returns them as a Pair of command and arguments.
     *
     * @return a Pair of command and arguments
     * @throws NoSuchCommandException if the entered command is not found in the list of supported commands
     */
    public Triplet<String, String[], Object> parseCommandFromFile(Scanner fileScanner) throws Exception {

        Pair<String, String[]> input = getCommand(fileScanner);
        String commandName = input.getFirst();
        String[] args = input.getSecond();
        Object object = null;

        if (humanBeingCommandsList.contains(commandName)) {
            HumanBeingObjectFileReader humanBeingObjectFileReader = new HumanBeingObjectFileReader(fileScanner);
            object = humanBeingObjectFileReader.readHumanBeingFromFile();
        }

        if (commandName.equals("filter_less_than_car")) {
            CarObjectFileReader carObjectFileReader = new CarObjectFileReader(fileScanner);
            object = carObjectFileReader.readData();
        }

        return new Triplet<>(commandName, args, object);
    }
}
