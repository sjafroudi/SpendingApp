package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;

import java.text.DecimalFormat;

// Represents a category of spending
public class SpendingCategory implements Writable {
    private String category;
    private double balance;
    private ArrayList<Transaction> transactions;

    // EFFECTS: Constructs a spending category with a name, running balance, and an empty
    //          list of transactions
    public SpendingCategory(String categoryName, double runningBalance) {
        this.category = categoryName;
        this.balance = runningBalance;
        transactions = new ArrayList<>();
    }

    public String getCategory() {
        return category;
    }

    public double getBalance() {
        return balance;
    }

    public ArrayList<Transaction> getTransactions() {
        return transactions;
    }

    // EFFECTS: returns the number of transactions
    public int numOfTransactions() {
        return transactions.size();
    }

    // MODIFIES: this
    // EFFECTS: adds transaction to spending category
    public void addTransaction(Transaction t) {
        if (t.getPrice() <= 0) {
            System.out.println("Invalid amount entered. Transaction must be greater than zero dollars.");
        } else {
            EventLog.getInstance().logEvent(new Event(t + " added to Transactions."));
            transactions.add(t);
        }
    }

    // MODIFIES: this
    // EFFECTS: removes transaction from spending category
    public void removeTransaction(Integer i) { // inspired by WorkRoom
        Transaction tr = transactions.get(i -  1);
        balance -= tr.getPrice();
        transactions.remove(tr);
        EventLog.getInstance().logEvent(new Event("Transaction" + tr + " removed"));
    }

    // REQUIRES: amount is >= 0.00
    // MODIFIES: this
    // EFFECTS: adds input amount to the chosen spending category
    public double addToBalance(double amount) {
        if (amount <= 0) {
            balance = 0.00 + balance;
            EventLog.getInstance().logEvent(new Event(balance + " added to balance."));
        } else {
            balance = amount + balance;
            EventLog.getInstance().logEvent(new Event(balance + " added to balance."));
        }
        return balance;
    }

    // EFFECTS: transcribes each category to a single string
    @Override
    public String toString() {
        DecimalFormat priceStr = new DecimalFormat("0.00#");
        return "Category: " + category + " " + priceStr.format(balance);
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("category", category);
        json.put("balance", balance);
        json.put("transactions", transactionsToJson());
        return json;
    }

    // EFFECTS: returns a list of transactions as a json array
    private JSONArray transactionsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Transaction t : transactions) {
            jsonArray.put(t.toJson());
        }

        return jsonArray;
    }
}

