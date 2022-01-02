package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

// Represents a collection of spending categories
public class SpendingCategories implements Iterable<SpendingCategory>, Writable {
    String name;
    private List<SpendingCategory> categories;

    // EFFECTS: constructs an aggregate of spending categories with the name of the account owner
    public SpendingCategories(String name) {
        this.name = name;
        categories = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public List<SpendingCategory> getCategories() {
        return categories;
    }

    // EFFECTS: returns the number of spending categories
    public int numOfSpendingCategories() {
        return categories.size();
    }

    // MODIFIES: this
    // EFFECTS: adds category to spending categories
    public void addSpendingCategory(SpendingCategory sc) { // inspired by WorkRoom
        EventLog.getInstance().logEvent(new Event(sc + " added to categories."));
        categories.add(sc);
    }

    // MODIFIES: this
    // EFFECTS: removes category from spending categories
    public void removeSpendingCategory(String name) { // inspired by WorkRoom
        categories.removeIf(s -> Objects.equals(s.getCategory(), name));
        EventLog.getInstance().logEvent(new Event(name + " added to categories."));
    }

    @Override
    public JSONObject toJson() { // inspired by WorkRoom
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("spendingCategories", categoriesToJson());
        return json;
    }

    // EFFECTS: returns spending categories as a JSON Array
    private JSONArray categoriesToJson() { // inspired by WorkRoom
        JSONArray jsonArray = new JSONArray();

        for (SpendingCategory c : categories) {
            jsonArray.put(c.toJson());
        }

        return jsonArray;
    }

    @Override
    public Iterator<SpendingCategory> iterator() {
        return categories.iterator();
    }

    public void remove(int index) {
        categories.remove(index);
    }

    public SpendingCategory getCategoryAtIndex(int index) {
        return categories.get(index);
    }

}
