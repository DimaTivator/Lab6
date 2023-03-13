package commonModule.collectionManagement;

import server.commands.CommandsExecutor;
import commonModule.exceptions.ScriptsRecursionException;
import commonModule.exceptions.commandExceptions.NoSuchCommandException;
import commonModule.io.consoleIO.CommandParser;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * The {@code ScriptExecutor} class provides the functionality to execute commands from a script file.
 */
public class ScriptExecutor {

    /**
     * Executes the commands from the script file located at the given file path.
     * @param filePath the path to the script file.
     */
    public void executeScript(String filePath, CommandsExecutor commandsExecutor) throws FileNotFoundException {
        File file = new File(filePath);
        Scanner fileScanner = new Scanner(file);

        CommandParser commandParser = new CommandParser();

        while (true) {
            try {
                commandsExecutor.execute(commandParser.parseCommandFromFile(fileScanner));
            } catch (NoSuchCommandException e) {
                System.out.println(e.getMessage());
            } catch (ScriptsRecursionException e) {
                System.out.println(e.getMessage());
                break;
            } catch (Exception e) {
                break;
            }
        }
    }
}
