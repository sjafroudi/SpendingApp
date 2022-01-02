package persistence;

import model.SpendingCategories;
import model.SpendingCategory;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonReaderTest extends JsonTest { // inspired by JsonReaderTest from JsonSerializationDemo

    @Test
    void testReaderNonExistentFile() { // inspired by JsonReaderTest
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            SpendingCategories sc = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptySpendingCategories() { // inspired by JsonReaderTest
        JsonReader reader = new JsonReader("./data/testReaderEmptySpendingCategories.json");
        try {
            SpendingCategories sc = reader.read();
            assertEquals("Sara Jafroudi", sc.getName());
            assertEquals(0, sc.numOfSpendingCategories());
        } catch (IOException e) {
            fail("Nothing to read");
        }
    }

    @Test
    void testReaderGeneralSpendingCategories() { // inspired by JsonReaderTest
        JsonReader reader = new JsonReader("./data/testReaderSpendingCategories.json");
        try {
            SpendingCategories sc = reader.read();
            assertEquals("Sara Jafroudi", sc.getName());
            List<SpendingCategory> categories = sc.getCategories();
            assertEquals(2, categories.size());
            checkCategory("Starbucks", 0.00, categories.get(0));
            checkCategory("Gas", 0.00, categories.get(1));
        } catch (IOException e) {
            fail("Could not read data from file");
        }
    }

}
