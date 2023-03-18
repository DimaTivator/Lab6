package server.collectionManagement;

import commonModule.auxiliaryClasses.ConsoleColors;
import commonModule.collectionClasses.HumanBeing;
import commonModule.io.fileIO.out.HumanBeingXMLWriter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * CollectionManager is a class that stores a collection of {@link HumanBeing} objects.
 * The class is responsible for managing the collection, adding, updating, removing and replacing elements.
 * It also provides methods for removing elements by key, removing elements with a greater key, and clearing the collection.
 * The class is designed to use the command pattern to execute actions on the collection.
 */

public class CollectionManager {

    /**
     * The creation date of the collection.
     */
    private final java.time.LocalDate creationDate;

    private HumanBeingXMLWriter humanBeingXMLWriter;

    /**
     * The collection of {@link HumanBeing} objects.
     */
    private Map<Long, HumanBeing> data = new LinkedHashMap<>();

    /**
     * Constructs a new CollectionManager and sets the creation date to the current date.
     */
    public CollectionManager() {
        creationDate = java.time.LocalDate.now();
        humanBeingXMLWriter = new HumanBeingXMLWriter();
    }

    /**
     * Gets the creation date of the collection.
     *
     * @return the creation date of the collection.
     */
    public java.time.LocalDate getCreationDate() {
        return creationDate;
    }

    /**
     * Gets the collection of {@link HumanBeing} objects.
     *
     * @return the collection of {@link HumanBeing} objects.
     */
    public Map<Long, HumanBeing> getCollection() {
        return data;
    }

    /**
     * Sets the collection of {@link HumanBeing} objects.
     *
     * @param collection the collection to set.
     */
    public void setCollection(LinkedHashMap<Long, HumanBeing> collection) {
        data = collection;
    }


    public void save(String filePath) {

        System.out.println("LOLOOLOOLOOL");
        Path path = Paths.get(filePath);

        if (!Files.exists(path)) {
            try {
                Files.createDirectories(path.getParent()); // Create directories if they don't exist
                Files.createFile(path); // Create the file

                humanBeingXMLWriter.writeData(data, filePath);

                System.out.println(ConsoleColors.PURPLE + "Done!" + ConsoleColors.RESET);
            } catch (IOException e) {
                System.out.println(ConsoleColors.RED + "Some troubles with creating file to save data :(" + ConsoleColors.RESET);
            }
        }
    }
}
