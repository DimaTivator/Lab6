package client;

import commonModule.dataStructures.Request;
import commonModule.dataStructures.Response;
import commonModule.dataStructures.Triplet;
import commonModule.io.consoleIO.CommandParser;

import java.io.Serializable;
import java.net.InetAddress;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        // TODO reading server ip and port and parsing it

        String commandName = null;
        String[] commandArgs = null;
        Request request = null;
        Response response;

        try {

            NetworkProvider networkProvider = new NetworkProvider(InetAddress.getLocalHost().getHostAddress(), 6799);
            Scanner scanner = new Scanner(System.in);
            CommandParser commandParser = new CommandParser();

            System.out.println("If you want to see the list of available commands, enter 'help'");

            while (true) {

                try {

                    Triplet<String, String[], Object> parsedInput = commandParser.readObjectFromConsole();
                    commandName = parsedInput.getFirst();
                    commandArgs = parsedInput.getSecond();

                    request = new Request(commandName, commandArgs, (Serializable) parsedInput.getThird());

                    networkProvider.send(request);

                    response = networkProvider.receive();

                    System.out.println(response.getOutput());

                } catch (NoSuchElementException e) {
                    break;
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
