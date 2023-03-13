package commonModule.dataStructures;

import java.io.Serializable;
import java.net.SocketAddress;

public class Request implements Serializable {

    private String commandName;

    private String[] commandArgs;

    private Serializable commandObject;

    private SocketAddress host;

    public Request(String commandName, String[] commandArgs) {
        this.commandName = commandName;
        this.commandArgs = commandArgs;
    }

    public Request(String commandName, String[] commandArgs, Serializable commandObject) {
        this.commandName = commandName;
        this.commandArgs = commandArgs;
        this.commandObject = commandObject;
    }


    public String getCommandName() {
        return commandName;
    }

    public void setCommandName(String commandName) {
        this.commandName = commandName;
    }


    public String[] getCommandArgs() {
        return commandArgs;
    }

    public void setCommandArgs(String[] commandArgs) {
        this.commandArgs = commandArgs;
    }


    public Serializable getCommandObject() {
        return commandObject;
    }

    public void setCommandObject(Serializable commandObject) {
        this.commandObject = commandObject;
    }


    public SocketAddress getHost() {
        return host;
    }

    public void setHost(SocketAddress host) {
        this.host = host;
    }
}
