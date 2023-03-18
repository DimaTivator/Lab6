package server;

import commonModule.auxiliaryClasses.ConsoleColors;
import commonModule.collectionClasses.HumanBeing;
import commonModule.commands.CommandWithResponse;
import commonModule.dataStructures.Pair;
import commonModule.io.consoleIO.CommandParser;
import commonModule.io.fileIO.in.HumanBeingXMLParser;
import commonModule.io.fileIO.in.Parser;
import server.collectionManagement.CollectionManager;
import server.collectionManagement.CollectionPrinter;
import commonModule.dataStructures.Request;
import commonModule.dataStructures.Response;
import commonModule.io.consoleIO.ConfirmationReader;
import commonModule.commands.CommandsExecutor;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        int port = 0;

        if (args.length == 1) {
            try {
                port = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                System.out.println(ConsoleColors.RED + "Port must be a number. Please try again" + ConsoleColors.RESET);
                System.exit(0);
            }
        } else {
            System.out.println(ConsoleColors.RED + "You should enter port in program arguments" + ConsoleColors.RESET);
            System.exit(0);
        }

        CollectionManager collectionManager = new CollectionManager();
        CollectionPrinter collectionPrinter = new CollectionPrinter();
        CommandsExecutor commandsExecutor = new CommandsExecutor(collectionManager, collectionPrinter);
        CommandParser commandParser = new CommandParser();

        ServerCommandExecutor serverCommandExecutor = new ServerCommandExecutor(collectionManager);

        Scanner scanner = new Scanner(System.in);
        ConfirmationReader confirmationReader = new ConfirmationReader();

        String clientsDataPath = "src/main/java/server/clientsData/data.xml";

        serverCommandExecutor.setClientsDataPath(clientsDataPath);

        Parser<LinkedHashMap<Long, HumanBeing>> humanBeingXMLParser = new HumanBeingXMLParser();
        collectionManager.setCollection(humanBeingXMLParser.parseData(clientsDataPath));

        try {

            server.NetworkProvider networkProvider = new server.NetworkProvider(port);

            while (true) {

                Request request = networkProvider.receive();

                // check is there any input in System.in
                try {

                    if (System.in.available() > 0) {

                        Pair<String, String[]> commandPair = commandParser.getCommand(scanner);

                        serverCommandExecutor.execute(commandPair);
                    }

                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }

                // if now the request is null then go to the next iteration
                if (request == null) {
                    continue;
                }

                Response response = null;

                try {

                    commandsExecutor.execute((CommandWithResponse) request.getCommand());

                    response = commandsExecutor.getCommandResponse();

                } catch (Exception e) {
                    response = new Response("Exception", null, e.getMessage());

                } finally {
                    networkProvider.send(response, request.getHost());
                    collectionManager.save(clientsDataPath);
                }

            }

        } catch (IOException e) {
            System.out.println(ConsoleColors.RED + "This port in busy. Try another port" + ConsoleColors.RESET);

        } catch (NullPointerException ignored) {}

          catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}

