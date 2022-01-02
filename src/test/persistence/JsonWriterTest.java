package persistence;

import model.SpendingCategories;
import model.SpendingCategory;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonWriterTest extends JsonTest {
    @Test
    void testWriterInvalidFile() { // inspired by JsonWriterTest from JsonSerializationDemo
        try {
            SpendingCategories sc = new SpendingCategories("Sara Jafroudi");
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptySpendingCategories() { // inspired by JsonWriterTest
        try {
            SpendingCategories sc = new SpendingCategories("Sara Jafroudi");
            JsonWriter writer = new JsonWriter("./data/testWriterEmptySpendingCategories.json");
            writer.open();
            writer.write(sc);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptySpendingCategories.json");
            sc = reader.read();
            assertEquals("Sara Jafroudi", sc.getName());
            assertEquals(0, sc.numOfSpendingCategories());
        } catch (IOException e) {
            fail("Exception was not expected");
        }
    }

    @Test
    void testWriterGeneralSpendingCategories() { // inspired by JsonWriterTest
        try {
            SpendingCategories sc = new SpendingCategories("Sara Jafroudi");
            sc.addSpendingCategory(new SpendingCategory("School", 0.00));
            sc.addSpendingCategory(new SpendingCategory("Transit", 0.00));
            JsonWriter writer = new JsonWriter("./data/testWriterSpendingCategories.json");
            writer.open();
            writer.write(sc);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterSpendingCategories.json");
            sc = reader.read();
            assertEquals("Sara Jafroudi", sc.getName());
            List<SpendingCategory> categories = sc.getCategories();
            assertEquals(2, categories.size());
            checkCategory("School", 0.00, categories.get(0));
            checkCategory("Transit", 0.00, categories.get(1));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
