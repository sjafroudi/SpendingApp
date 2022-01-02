package persistence;

import model.Event;
import model.EventLog;
import model.SpendingCategories;
import org.json.JSONObject;

import java.io.*;

// Represents a JSON writer which saves data to a file
public class JsonWriter {
    private static final int TAB = 4; // inspired by JsonWriter from JsonSerializationDemo
    private PrintWriter writer; // inspired by JsonWriter from JsonSerializationDemo
    private String output;

    // EFFECTS: constructs writer to copy data to destination file
    public JsonWriter(String output) { // inspired by JsonWriter from JsonSerializationDemo
        this.output = output;
    }

    // MODIFIES: this
    // EFFECTS: opens this; throws FileNotFoundException if output file cannot be found
    public void open() throws FileNotFoundException { // inspired by JsonWriter from JsonSerializationDemo
        writer = new PrintWriter(new File(output));
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of spending categories to file
    public void write(SpendingCategories sc) { // inspired by JsonWriter from JsonSerializationDemo
        JSONObject json = sc.toJson();
        saveToFile(json.toString(TAB));
    }

    // MODIFIES: this
    // EFFECTS: closes this
    public void close() { // inspired by JsonWriter from JsonSerializationDemo
        writer.close();
    }

    // MODIFIES: this
    // EFFECTS: writes string to file
    private void saveToFile(String json) { // inspired by JsonWriter from JsonSerializationDemo
        writer.print(json);
    }


}
