package commands;

import exceptions.commandExceptions.InvalidArgumentsException;

public interface Command {

    void execute() throws Exception;

    String getArg();

    void setArg(String arg) throws InvalidArgumentsException;

    Object getValue();

    void setValue(Object value);
}
