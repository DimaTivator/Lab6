package server;

import commonModule.auxiliaryClasses.ConsoleColors;
import commonModule.collectionManagement.CollectionManager;
import commonModule.collectionManagement.CollectionPrinter;
import commonModule.dataStructures.Request;
import commonModule.dataStructures.Response;
import commonModule.dataStructures.Triplet;
import server.commands.CommandsExecutor;

import java.io.IOException;
import java.net.InetAddress;

public class Main {

    public static void main(String[] args) {

        CollectionManager collectionManager = new CollectionManager();
        CollectionPrinter collectionPrinter = new CollectionPrinter();
        CommandsExecutor commandsExecutor = new CommandsExecutor(collectionManager, collectionPrinter);

        try {

            server.NetworkProvider networkProvider = new server.NetworkProvider(6799);

            while (true) {
                Request request;
                InetAddress host;


                request = networkProvider.receive();

//                System.out.println(request.getCommandName());
//                System.out.println(Arrays.toString(request.getCommandArgs()));
//                System.out.println(request.getCommandObject());

                Response response = null;

                try {

                    commandsExecutor.execute(new Triplet<>(
                            request.getCommandName(),
                            request.getCommandArgs(),
                            request.getCommandObject()
                            )
                    );

                    response = commandsExecutor.getCommandResponse();

                } catch (Exception e) {
                    response = new Response("Exception", null, e.getMessage());
                } finally {
                    networkProvider.send(response, request.getHost());
                }

//                System.out.println(response.getCommand());
//                System.out.println(response.getOutput());
//                System.out.println(response.getObject());

            }
        } catch (IOException e) {
            System.out.println(ConsoleColors.RED + "This port in busy. Try another port" + ConsoleColors.RESET);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
