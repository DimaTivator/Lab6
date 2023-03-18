package client;

import commonModule.auxiliaryClasses.ConsoleColors;
import commonModule.commands.Command;
import commonModule.dataStructures.Request;
import commonModule.dataStructures.Response;
import commonModule.dataStructures.Triplet;
import commonModule.exceptions.commandExceptions.InvalidArgumentsException;
import commonModule.io.consoleIO.CommandParser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        InetAddress address = null;
        int port = 0;

        if (args.length == 2) {

            try {
                address = InetAddress.getByName(args[0]);
            } catch (UnknownHostException e) {
                System.out.println("Unknown host. Please try again");
            }

            try {
                port = Integer.parseInt(args[1]);
            } catch (NumberFormatException e) {
                System.out.println(ConsoleColors.RED + "Port must be a number. Please try again" + ConsoleColors.RESET);
                System.exit(0);
            }

        } else {
            System.out.println(ConsoleColors.RED + "You should enter server ip and port in program arguments" + ConsoleColors.RESET);
            System.exit(0);
        }


        String commandName;
        String[] commandArgs;
        Request request;
        Response response;

        try {

            NetworkProvider networkProvider = new NetworkProvider(address.getHostAddress(), port);
            CommandParser commandParser = new CommandParser();
            ScriptExecutor scriptExecutor = new ScriptExecutor();

            System.out.println("If you want to see the list of available commands, enter 'help'");

            while (true) {

                try {

                    Triplet<String, String[], Object> parsedInput = commandParser.readObjectFromConsole();
                    commandName = parsedInput.getFirst();
                    commandArgs = parsedInput.getSecond();
                    Object object = parsedInput.getThird();

                    if (commandName.equals("exit")) {
                        if (commandArgs.length == 0) {
                            System.out.println(ConsoleColors.PURPLE + "Bye!" + ConsoleColors.RESET);
                            System.exit(0);
                        } else {
                            throw new InvalidArgumentsException("Command exit doesn't take any arguments!");
                        }
                    }

                    else if (commandName.equals("execute_script")) {
                        if (commandArgs.length == 1) {
                            scriptExecutor.executeScript(commandArgs[0], networkProvider);
                        } else {
                            throw new InvalidArgumentsException("Command execute_script take only one argument - path to the script");
                        }
                    }

                    else {

                        Command command = commandParser.pack(parsedInput);

                        request = new Request(command);
                        networkProvider.send(request);
                        response = networkProvider.receive();

                        if (response == null) {
                            System.out.println(ConsoleColors.RED + "Server is down :(\nPlease try again later" + ConsoleColors.RESET);
                        } else {
                            System.out.println(response.getOutput());
                        }
                    }

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
