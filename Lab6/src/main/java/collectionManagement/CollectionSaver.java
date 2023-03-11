package collectionManagement;

import collectionManagement.CollectionManager;
import io.fileIO.out.HumanBeingXMLWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


public class CollectionSaver {

    private final CollectionManager collectionManager;
    private final HumanBeingXMLWriter humanBeingXMLWriter;

    public CollectionSaver(CollectionManager collectionManager, HumanBeingXMLWriter humanBeingXMLWriter) {
        this.collectionManager = collectionManager;
        this.humanBeingXMLWriter = humanBeingXMLWriter;
    }

    public void saveCollection() {
//        File collectionSaveFile = new File("/tmp/.save.xml");
//
        // Write data to the file
//        humanBeingXMLWriter.writeData(collectionManager.getCollection(), "/tmp/.save.xml");

        String dirPath = "/tmp/s367054Lab5Saves";
        String filePath = "/tmp/s367054Lab5Saves/.save.xml";

        File dir = new File(dirPath);
        File file = new File(filePath);

        try {
            // Create the directory
            if (!dir.exists()) {
                dir.mkdir();
            }

            // Create the file
            if (!file.exists()) {
                file.createNewFile();
            }

            // write data to the file
            humanBeingXMLWriter.writeData(collectionManager.getCollection(), "/tmp/s367054Lab5Saves/.save.xml");

        } catch (IOException e) {
            System.out.println(e.getMessage());;
        }
    }
}
