package commands;

import auxiliaryClasses.ConsoleColors;
import collectionManagement.CollectionManager;
import exceptions.EmptyCollectionException;
import exceptions.commandExceptions.InvalidArgumentsException;
import storedClasses.HumanBeing;

import java.util.Map;

/**
 * CountLessThanImpactSpeedCommand class is a concrete implementation of the Command abstract class.
 * It counts the number of elements in the collection whose `impactSpeed` is less than the specified `impactSpeed`.
 */
public class CountLessThanImpactSpeedCommand extends CommandTemplate {


    /**
     * Constructs a CountLessThanImpactSpeedCommand with the specified CollectionManager and impact speed.
     *
     * @param collectionManager the CollectionManager to be used
     */
    public CountLessThanImpactSpeedCommand(CollectionManager collectionManager) {
        super(collectionManager);
    }

    @Override
    public void setArg(String arg) throws InvalidArgumentsException {
        try {
            double key = Double.parseDouble(arg);
            super.setArg(String.valueOf(key));
        } catch (NumberFormatException e) {
            throw new InvalidArgumentsException("The key must be a number! Please Try to enter a command again");
        }
    }

    /**
     * Executes the command, counting the number of elements in the collection whose `impactSpeed` is less than the specified `impactSpeed`.
     *
     * @throws EmptyCollectionException if the collection is empty
     */
    @Override
    public void execute() throws EmptyCollectionException {
        CollectionManager collectionManager = getCollectionManager();
        Map<Long, HumanBeing> data = collectionManager.getCollection();
        double impactSpeed = Double.parseDouble(getArg());

        if (data.isEmpty()) {
            throw new EmptyCollectionException();
        }

        int counter = 0;
        for (HumanBeing value : data.values()) {
            if (value.getImpactSpeed() < impactSpeed) {
                counter++;
            }
        }

        System.out.println("The number of objects whose impactSpeed field value is less than the specified one is " +
                ConsoleColors.GREEN + counter + ConsoleColors.RESET);
    }
}
