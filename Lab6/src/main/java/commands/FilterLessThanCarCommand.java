package commands;

import auxiliaryClasses.ConsoleColors;
import collectionManagement.CollectionManager;
import exceptions.EmptyCollectionException;
import storedClasses.Car;
import storedClasses.HumanBeing;

import java.util.Map;

/**
 * The FilterLessThanCarCommand class implements a command to filter the elements of the collection
 * whose car field value is less than the specified one.
 */
public class FilterLessThanCarCommand extends CommandTemplate {

    /**
     * Creates a new instance of the FilterLessThanCarCommand class.
     * @param collectionManager the CollectionManager instance to manage the collection.
     */
    public FilterLessThanCarCommand(CollectionManager collectionManager) {
        super(collectionManager);
    }

    /**
     * Executes the command to filter the elements of the collection
     * whose car field value is less than the specified one.
     * @throws EmptyCollectionException if the collection is empty.
     */
    @Override
    public void execute() throws EmptyCollectionException {
        Map<Long, HumanBeing> data = getCollectionManager().getCollection();
        Car car = (Car) getValue();

        if (data.isEmpty()) {
            throw new EmptyCollectionException();
        }

        boolean found = false;
        for (HumanBeing value : data.values()) {
            if (value.getCar().compareTo(car) < 0) {
                System.out.println(value.getCar());
                found = true;
            }
        }

        if (!found) {
            System.out.println(ConsoleColors.RED +
                    "There are no objects whose car field value is less than the specified one" + ConsoleColors.RESET);
        }
    }
}
