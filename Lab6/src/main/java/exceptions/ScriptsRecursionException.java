package exceptions;

import auxiliaryClasses.ConsoleColors;

public class ScriptsRecursionException extends Exception {

    public ScriptsRecursionException(String message) {
        super(ConsoleColors.RED + message + ConsoleColors.RESET);
    }
}
