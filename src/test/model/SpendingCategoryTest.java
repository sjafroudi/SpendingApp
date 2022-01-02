package model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class SpendingCategoryTest {
    SpendingCategory gasCategoryTest;
    private List<Transaction> transactions;

    @BeforeEach
    void runBefore() {
        gasCategoryTest = new SpendingCategory("Gas", 0.00);
        transactions = new ArrayList<>();
    }

    @Test
    void testCategoryConstructor() {
        assertEquals("Gas", gasCategoryTest.getCategory());
        assertEquals(0.00, gasCategoryTest.getBalance());
    }

    @Test
    void testAddZeroDollars() {
        gasCategoryTest.addToBalance(0.00);
        assertEquals(0.00, gasCategoryTest.getBalance());
    }

    @Test
    void testAddTransaction() {
        Transaction gas1 = new Transaction(35.00, "05/10/21");
        gasCategoryTest.addTransaction(gas1);
        gasCategoryTest.addToBalance(gas1.getPrice());
        assertEquals(35.00, gasCategoryTest.getBalance());
    }

    @Test
    void testAddAnotherTransaction() {
        Transaction gas1 = new Transaction(35.00, "05/10/21");
        gasCategoryTest.addTransaction(gas1);
        gasCategoryTest.addToBalance(gas1.getPrice());
        Transaction gas2 = new Transaction(25.00, "08/10/21");
        gasCategoryTest.addTransaction(gas2);
        gasCategoryTest.addToBalance(gas2.getPrice());
        assertEquals(60.00, gasCategoryTest.getBalance());
    }

    @Test
    void testRemoveTransaction() {
        Transaction gas1 = new Transaction(35.00, "05/10/21");
        gasCategoryTest.addTransaction(gas1);
        gasCategoryTest.addToBalance(gas1.getPrice());
        Transaction gas2 = new Transaction(25.00, "08/10/21");
        gasCategoryTest.addTransaction(gas2);
        gasCategoryTest.addToBalance(gas2.getPrice());
        gasCategoryTest.removeTransaction(1);
        assertEquals(25.00, gasCategoryTest.getBalance());
    }

    @Test
    void testAddNegativeAmount() {
        Transaction gas3 = new Transaction(-10.00, "08/10/21");
        gasCategoryTest.addTransaction(gas3);
        gasCategoryTest.addToBalance(gas3.getPrice());
        assertEquals(0.00, gasCategoryTest.getBalance());
    }

    @Test
    void testGetTransactions() {
        Transaction gas1 = new Transaction(35.00, "05/10/21");
        transactions.add(gas1);
        gasCategoryTest.addTransaction(gas1);
        Transaction gas2 = new Transaction(25.00, "08/10/21");
        transactions.add(gas2);
        gasCategoryTest.addTransaction(gas2);
        assertEquals(transactions, gasCategoryTest.getTransactions());
        assertEquals(2, gasCategoryTest.numOfTransactions());

    }

}
