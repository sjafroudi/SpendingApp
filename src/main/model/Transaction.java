package model;

import org.json.JSONObject;
import persistence.Writable;

import java.text.DecimalFormat;

// Represents a single transaction
public class Transaction implements Writable {
    private double price;
    private String date;

    // EFFECTS: constructs a transaction with an amount (CAD) and date (e.g., 10/29/21)
    public Transaction(double amount, String date) {
        this.price = amount;
        this.date = date;
    }

    // EFFECTS: returns amount (CAD) of transaction
    public double getPrice() {
        return price;
    }

    // EFFECTS: returns the date of the transaction
    public String getDate() {
        return date;
    }

    // EFFECTS: transcribes each transaction to a single string
    @Override
    public String toString() {
        DecimalFormat priceStr = new DecimalFormat("0.00#");
        return "Price: " + priceStr.format(price) + " Date: " + date;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("amount", price);
        json.put("date", date);
        return json;
    }
}
