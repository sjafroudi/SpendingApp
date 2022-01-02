package model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TransactionTest {
    private Transaction testTransaction;

    @BeforeEach
    void runBefore() {
        testTransaction = new Transaction(4.55, "05/10/21");
    }

    @Test
    void testTransactionConstructor() { // inspired by BankTeller
        assertEquals(4.55, testTransaction.getPrice());
        assertEquals("05/10/21", testTransaction.getDate());
    }

}
