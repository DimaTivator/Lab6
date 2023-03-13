package server.commands.commandObjects;

import commonModule.auxiliaryClasses.ConsoleColors;
import commonModule.collectionManagement.CollectionManager;
import commonModule.dataStructures.Response;
import commonModule.exceptions.EmptyCollectionException;
import commonModule.collectionClasses.Car;
import commonModule.collectionClasses.HumanBeing;
import server.commands.CommandTemplate;
import server.commands.CommandWithResponse;

import java.util.Map;

/**
 * The FilterLessThanCarCommand class implements a command to filter the elements of the collection
 * whose car field value is less than the specified one.
 */
public class FilterLessThanCarCommand extends CommandTemplate implements CommandWithResponse {

    private StringBuilder output;

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
                output.append(value.getCar()).append("\n");
                // System.out.println(value.getCar());
                found = true;
            }
        }

        if (!found) {
            output.append(ConsoleColors.RED +
                    "There are no objects whose car field value is less than the specified one" + ConsoleColors.RESET).append("\n");
//            System.out.println(ConsoleColors.RED +
//                    "There are no objects whose car field value is less than the specified one" + ConsoleColors.RESET);
        }
    }

    @Override
    public Response getCommandResponse() {
        return new Response("filter_less_than_car", getArgs(), output.toString());
    }
}
