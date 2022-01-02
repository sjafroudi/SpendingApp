package persistence;

import model.SpendingCategories;
import model.SpendingCategory;
import model.Transaction;

import org.json.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

// Represents a JSON reader which returns data from a .json file
public class JsonReader {
    private String input;

    // EFFECTS: constructs json reader to draw data from source file
    public JsonReader(String input) { // inspired by JsonReader from JsonSerializationDemo
        this.input = input;
    }

    // EFFECTS: reads spending category data from a file and returns it;
    // throws IOException if an error occurs reading data from file
    public SpendingCategories read() throws IOException { // inspired by JsonReader from JsonSerializationDemo
        String jsonData = readFile(input);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseSpendingCategories(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String input) throws IOException { // inspired by JsonReader from JsonSerializationDemo
        StringBuilder contentBuilder = new StringBuilder();
        try (Stream<String> stream = Files.lines(Paths.get(input), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }
        return contentBuilder.toString();
    }

    // EFFECTS: parses spending categories from JSON object and returns it
    private SpendingCategories parseSpendingCategories(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        SpendingCategories sc = new SpendingCategories(name);
        addCategories(sc, jsonObject);
        return sc;
    }

    // MODIFIES: SpendingCategories
    // EFFECTS: parses categories from JSON object and adds them to spending categories object
    private void addCategories(SpendingCategories sc, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("spendingCategories");
        for (Object json : jsonArray) {
            JSONObject nextCategory = (JSONObject) json;
            addCategory(sc, nextCategory);
        }
    }


    // MODIFIES: SpendingCategories
    // EFFECTS: parses each spending category from JSON object and adds it to spending categories
    private void addCategory(SpendingCategories sc, JSONObject jsonObject) {
        String name = jsonObject.getString("category");
        Double balance = jsonObject.getDouble("balance");
        SpendingCategory spendingCategory = new SpendingCategory(name, balance);
        sc.addSpendingCategory(spendingCategory);
        JSONArray jsonArray = jsonObject.getJSONArray("transactions");
        for (Object json : jsonArray) {
            JSONObject nextCategory = (JSONObject) json;
            addTransactionToCategory(spendingCategory, nextCategory);
        }
    }

    // MODIFIES: SpendingCategory
    // EFFECTS: parses transactions from json object and adds them to spending categories
    private void addTransactionToCategory(SpendingCategory s, JSONObject jsonObject) {
        String date = jsonObject.getString("date");
        Double amount = jsonObject.getDouble("amount");
        Transaction transaction = new Transaction(amount, date);
        s.addTransaction(transaction);
    }
}


