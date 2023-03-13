package server.commands;

import commonModule.exceptions.commandExceptions.InvalidArgumentsException;

public interface Command {

    void execute() throws Exception;

    String[] getArgs();

    void setArgs(String[] args) throws InvalidArgumentsException;

    Object getValue();

    void setValue(Object value);
}
