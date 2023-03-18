package server;

import commonModule.dataStructures.Pair;
import commonModule.exceptions.commandExceptions.InvalidArgumentsException;
import commonModule.io.consoleIO.ConfirmationReader;
import server.collectionManagement.CollectionManager;

public class ServerCommandExecutor {

    private CollectionManager collectionManager;
    private final ConfirmationReader confirmationReader;

    private String clientsDataPath;


    {
        confirmationReader = new ConfirmationReader();
        clientsDataPath = "src/main/java/server/clientsData/data.xml";
    }


    public ServerCommandExecutor(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    public void setCollectionManager(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    public void setClientsDataPath(String path) {
        this.clientsDataPath = path;
    }


    public void execute(Pair<String, String[]> command) throws InvalidArgumentsException {

        String commandName = command.getFirst();
        String[] commandArgs = command.getSecond();

        if (commandName.equals("exit")) {

            if (commandArgs.length != 0) {
                throw new InvalidArgumentsException("Command exit doesn't take any arguments!\n");
            }

            String confirmation = confirmationReader.readObjectFromConsole();
            if (confirmation.equals("Y")) {
                System.exit(1);
            }
        } else if (commandName.equals("save")) {

            if (commandArgs.length == 0) {
                collectionManager.save(clientsDataPath);
            } else if (commandArgs.length == 1) {
                collectionManager.save(commandArgs[0]);
            }
            else {
                throw new InvalidArgumentsException("Command save takes 0 or 1 arguments!\n");
            }
        }
    }
}
