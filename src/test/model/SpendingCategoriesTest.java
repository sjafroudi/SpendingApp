package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SpendingCategoriesTest {

    private SpendingCategories spendingCategories;
    private List<SpendingCategory> categories;
    private SpendingCategory category;
    private double balance = 0.00;

    @BeforeEach
    public void runBefore() {
        spendingCategories = new SpendingCategories("Sara's Spending App");
        categories = new ArrayList<>();
        category = new SpendingCategory("Starbucks", balance);
    }

    @Test
    public void testConstructor() {
        assertEquals("Sara's Spending App", spendingCategories.getName());
    }

    @Test
    public void testEmptyCategoryList() {
        assertEquals(0, spendingCategories.numOfSpendingCategories());
    }

    @Test
    public void testAddCategory() {
        spendingCategories.addSpendingCategory(category);
        categories.add(category);
        assertEquals(categories, spendingCategories.getCategories());
        assertEquals(1, spendingCategories.numOfSpendingCategories());
    }

    @Test
    public void testAddAnotherCategory() {
        spendingCategories.addSpendingCategory(category);
        categories.add(category);
        SpendingCategory anotherCategory = new SpendingCategory("Toilet Paper", balance);
        spendingCategories.addSpendingCategory(anotherCategory);
        categories.add(anotherCategory);
        assertEquals(categories, spendingCategories.getCategories());
        assertEquals(2, spendingCategories.numOfSpendingCategories());
    }

    @Test
    public void testDeleteCategory() {
        spendingCategories.addSpendingCategory(category);
        categories.add(category);
        SpendingCategory anotherCategory = new SpendingCategory("Toilet Paper", balance);
        spendingCategories.addSpendingCategory(anotherCategory);
        categories.add(anotherCategory);
        assertEquals(categories, spendingCategories.getCategories());
        spendingCategories.removeSpendingCategory(anotherCategory.getCategory());
        assertEquals(1, spendingCategories.numOfSpendingCategories());
    }

}
