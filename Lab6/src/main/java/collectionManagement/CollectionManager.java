package collectionManagement;

import commands.*;
import storedClasses.HumanBeing;

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

    /**
     * The collection of {@link HumanBeing} objects.
     */
    private Map<Long, HumanBeing> data = new LinkedHashMap<>();

    /**
     * Constructs a new CollectionManager and sets the creation date to the current date.
     */
    public CollectionManager() {
        creationDate = java.time.LocalDate.now();
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
}
