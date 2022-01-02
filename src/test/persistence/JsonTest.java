package persistence;

import model.SpendingCategory;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest { // inspired by JsonTest from JsonSerializationDemo
    protected void checkCategory(String name, double balance, SpendingCategory sc) {
        assertEquals(name, sc.getCategory());
        assertEquals(balance, sc.getBalance());

    }

}
