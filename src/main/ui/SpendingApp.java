package ui;

import model.Event;
import model.EventLog;
import model.SpendingCategories;
import model.SpendingCategory;
import model.Transaction;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.*;

// Represents the SpendingCategories application
public class SpendingApp extends JFrame implements ListSelectionListener {

    private JButton loadButton;
    private JButton newButton;
    private JButton newCategory;
    private JButton delCategory;
    private JButton addOrRemoveTr;
    private JButton viewSpending;
    private JButton save;
    private JButton exit;
    private JButton back;
    private JPanel loadFilePanel;
    private JPanel newFilePanel;
    private JPanel mainMenu;
    private JFrame frame;

    private JPanel createNewCategory;
    private JButton addCategoryToListBtn;
    private JTextField newCategoryInputText;
    private JLabel newCategoryTextFieldLabel;

    private JPanel viewCategoriesPanel;
    private JScrollPane viewCategoriesScrollPane;
    private JList categoriesList;
    private JButton backFromList;
    private JButton backFromAddTransaction;
    private DefaultListModel<SpendingCategory> listModel;

    private JButton del;
    private JButton addTransactionBtn;
    private JButton removeTransactionBtn;
    private JPanel addOrRemovePanel;

    private JTextField transactionAmount;
    private JTextField dateField;
    private JLabel dateLabel;
    private JLabel amountLabel;
    private JPanel addTransactionPanel;
    private JButton saveTransactionBtn;
    private JButton remove;

    private JPanel removeTransactionPanel;
    private JButton removeTransaction;
    private JComboBox<SpendingCategory> transactionCombo;
    private JList<Transaction> transactionsList;
    private JScrollPane transactionScrollPane;
    private DefaultComboBoxModel<SpendingCategory> comboBoxModel;
    private DefaultListModel<Transaction> transactionsListModel;

    private GridBagConstraints gridbag;

    private static final String JSON_DATA = "./data/SpendingAppData.json";
    SpendingCategories spendingCategories;
    SpendingCategory sc;
    JsonWriter jsonWriter;
    JsonReader jsonReader;

    private UtilDateModel model;
    private JDatePanelImpl datePanel;
    private JDatePickerImpl datePicker;

    private void appFrame() { // inspired by components-BorderDemoProject

        frame = new JFrame("Spending App");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setMinimumSize(new Dimension(850, 300));
        frame.pack();
        frame.setVisible(true);
        frame.setLayout(new GridLayout());

        loadFilePanel = new JPanel();
        newFilePanel = new JPanel();
        loadButton = new JButton();
        newButton = new JButton();
        frame.getContentPane();

        mainFrame();
    }

    private void mainFrame() {
        frame.getContentPane();
        loadFilePanel.setLayout(new GridBagLayout());
        newFilePanel.setLayout(new GridBagLayout());

        loadButton.setText("Load Existing Data");
        newButton.setText("Start New App");

        addButtons();

        loadButton.addMouseListener(new MouseAdapter() {
            public void mouseReleased(MouseEvent evt) {
                loadData();
                removeButtons();
            }
        });

        newButton.addMouseListener(new MouseAdapter() {
            public void mouseReleased(MouseEvent evt) {
                newApp();
                removeButtons();
            }
        });
    }

    private void addButtons() {
        loadFilePanel.add(loadButton);
        newFilePanel.add(newButton);
        frame.add(loadFilePanel);
        frame.add(newFilePanel);
        loadFilePanel.revalidate();
        newFilePanel.revalidate();
        loadFilePanel.repaint();
        newFilePanel.repaint();
        frame.getContentPane();
    }

    // EFFECTS: loads data and opens main menu
    private void loadData() {
        loadWorkRoom();
        removeButtons();
        menu();
        frame.getContentPane();
    }

    // EFFECTS: leads to main menu without loading data
    private void newApp() {
        removeButtons();
        menu();
    }

    // EFFECTS: removes buttons from default panel
    private void removeButtons() {
        loadFilePanel.remove(loadButton);
        newFilePanel.remove(newButton);
        frame.remove(loadFilePanel);
        frame.remove(newFilePanel);
        frame.getContentPane();
    }

    // EFFECTS: opens main menu
    private void menu() {
        mainMenu = new JPanel();
        frame.add(mainMenu);
        mainMenu.setLayout(new GridBagLayout());
        newCategory = new JButton();
        delCategory = new JButton();
        addOrRemoveTr = new JButton();
        save = new JButton();
        exit = new JButton();
        viewSpending = new JButton();
        back = new JButton();
        addMenuContents();
        addEventListeners();
        addMenuEventListeners();
    }

    // EFFECTS: adds elements to main menu panel
    private void addMenuContents() {
        addNewCategoryBtn();
        addDelCategoryBtn();
        addAddOrRemoveTrBtn();
        addSaveBtn();
        addExitBtn();
        addViewSpendingBtn();
        addBackBtn();
    }

    // EFFECTS: adds new category button to main menu panel
    private void addNewCategoryBtn() {
        newCategory.setText("Create New Spending Category");
        gridbag = new GridBagConstraints();
        gridbag.gridx = 0;
        gridbag.gridy = 0;
        gridbag.gridwidth = 3;
        gridbag.ipadx = 12;
        gridbag.anchor = GridBagConstraints.NORTHWEST;
        gridbag.insets = new Insets(29, 104, 0, 107);
        mainMenu.add(newCategory, gridbag);

        mainMenu.repaint();
        mainMenu.revalidate();
        frame.getContentPane();
    }

    // EFFECTS: adds delete category button to main meny panel
    private void addDelCategoryBtn() {
        delCategory.setText("Delete Spending Category");
        gridbag = new GridBagConstraints();
        gridbag.gridx = 0;
        gridbag.gridy = 2;
        gridbag.gridwidth = 3;
        gridbag.ipadx = 43;
        gridbag.anchor = GridBagConstraints.NORTHWEST;
        gridbag.insets = new Insets(12, 104, 0, 107);
        mainMenu.add(delCategory, gridbag);

        mainMenu.repaint();
        mainMenu.revalidate();
        frame.getContentPane();
    }

    // EFFECTS: adds add/remove transaction button to main menu panel
    private void addAddOrRemoveTrBtn() {
        addOrRemoveTr.setText("Add or Remove Transaction");
        gridbag = new GridBagConstraints();
        gridbag.gridx = 0;
        gridbag.gridy = 1;
        gridbag.gridwidth = 3;
        gridbag.ipadx = 32;
        gridbag.anchor = GridBagConstraints.NORTHWEST;
        gridbag.insets = new Insets(12, 104, 0, 107);
        mainMenu.add(addOrRemoveTr, gridbag);

        mainMenu.repaint();
        mainMenu.revalidate();
        frame.getContentPane();
    }

    // EFFECTS: adds event listeners to menu buttons (back, save, exit)
    private void addMenuEventListeners() {
        back.addMouseListener(new MouseAdapter() {
            public void mouseReleased(MouseEvent evt) {
                frame.remove(mainMenu);
                appFrame();
                frame.getContentPane();
            }
        });
        exit.addMouseListener(new MouseAdapter() {
            public void mouseReleased(MouseEvent evt) {
                new PrintLogAction();
                System.exit(0);
            }
        });
        save.addMouseListener(new MouseAdapter() {
            public void mouseReleased(MouseEvent evt) {
                saveWorkRoom();
            }
        });
    }

    // MODIFIES: json file
    // EFFECTS: adds save button to main menu panel
    private void addSaveBtn() {
        save.setText("Save");
        gridbag = new GridBagConstraints();
        gridbag.gridx = 1;
        gridbag.gridy = 4;
        gridbag.anchor = GridBagConstraints.NORTHWEST;
        gridbag.insets = new Insets(47, 12, 32, 0);
        mainMenu.add(save, gridbag);

        mainMenu.repaint();
        mainMenu.revalidate();
        frame.getContentPane();
    }

    // EFFECTS: closes applications
    private void addExitBtn() {
        exit.setText("Exit");
        gridbag = new GridBagConstraints();
        gridbag.gridx = 2;
        gridbag.gridy = 4;
        gridbag.anchor = GridBagConstraints.NORTHWEST;
        gridbag.insets = new Insets(47, 12, 32, 107);
        mainMenu.add(exit, gridbag);

        mainMenu.repaint();
        mainMenu.revalidate();
        frame.getContentPane();
    }

    // EFFECTS: adds view spending button to main menu panel
    private void addViewSpendingBtn() {
        viewSpending.setText("View Spending");
        gridbag = new GridBagConstraints();
        gridbag.gridx = 0;
        gridbag.gridy = 3;
        gridbag.gridwidth = 3;
        gridbag.ipadx = 114;
        gridbag.anchor = GridBagConstraints.NORTHWEST;
        gridbag.insets = new Insets(12, 104, 0, 107);
        mainMenu.add(viewSpending, gridbag);

        mainMenu.repaint();
        mainMenu.revalidate();
        frame.getContentPane();
    }

    // EFFECTS: adds back button to main menu panel
    private void addBackBtn() {
        back.setText("Back");
        gridbag = new GridBagConstraints();
        gridbag.gridx = 0;
        gridbag.gridy = 4;
        gridbag.anchor = GridBagConstraints.NORTHWEST;
        gridbag.insets = new Insets(47, 104, 32, 0);
        mainMenu.add(back, gridbag);

        mainMenu.repaint();
        mainMenu.revalidate();
        frame.getContentPane();
    }

    // EFFECTS: adds event listeners to remaining main menu buttons
    private void addEventListeners() {
        newCategory.addMouseListener(new MouseAdapter() {
            public void mouseReleased(MouseEvent evt) {
                addCategory();
            }
        });
        delCategory.addMouseListener(new MouseAdapter() {
            public void mouseReleased(MouseEvent evt) {
                delCategory();
            }
        });
        addOrRemoveTr.addMouseListener(new MouseAdapter() {
            public void mouseReleased(MouseEvent evt) {
                addOrRemoveTransaction();
            }
        });
        viewSpending.addMouseListener(new MouseAdapter() {
            public void mouseReleased(MouseEvent evt) {
                viewCategoriesList();
                addBackButton();
            }
        });
    }

    // MODIFIES: SpendingCategories
    // EFFECTS: Adds a spending category to user's spending app
    private void addCategory() {
        frame.remove(mainMenu);
        frame.getContentPane();

        createNewCategory = new JPanel();
        createNewCategory.setLayout(new GridBagLayout());
        newCategoryInputText = new JTextField();
        newCategoryTextFieldLabel = new JLabel();
        addCategoryToListBtn = new JButton();
        frame.pack();

        openAddCategoryPanel();
    }

    // EFFECTS: opens add category panel
    private void openAddCategoryPanel() {

        newCategoryInputText.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                newCategoryInputText.getText();
            }
        });

        addCategoryPanel();
        addExtraButtonsToNewCategoryPanel();
        frame.add(createNewCategory);
        createNewCategory.repaint();
        createNewCategory.revalidate();
        frame.getContentPane();

        addCategoryToListBtn.addMouseListener(new MouseAdapter() {
            public void mouseReleased(MouseEvent evt) {
                addCategoryToList();
            }
        });
    }

    // EFFECTS: sets layout for  add category panel
    private void addCategoryPanel() {
        gridbag = new GridBagConstraints();
        gridbag.gridx = 1;
        gridbag.gridy = 0;
        gridbag.gridheight = 2;
        gridbag.anchor = GridBagConstraints.NORTHWEST;
        gridbag.insets = new Insets(57, 12, 0, 0);
        newCategoryInputText.setColumns(8);
        createNewCategory.add(newCategoryInputText, gridbag);

        newCategoryTextFieldLabel.setText("Category Name:");
        gridbag = new GridBagConstraints();
        gridbag.gridx = 0;
        gridbag.gridy = 0;
        gridbag.anchor = GridBagConstraints.NORTHWEST;
        gridbag.insets = new Insets(62, 47, 0, 0);
        createNewCategory.add(newCategoryTextFieldLabel, gridbag);

        addCategoryToListBtn.setText("Add");
        gridbag = new GridBagConstraints();
        gridbag.gridx = 2;
        gridbag.gridy = 0;
        gridbag.gridheight = 3;
        gridbag.anchor = GridBagConstraints.NORTHWEST;
        gridbag.insets = new Insets(57, 18, 86, 57);
        createNewCategory.add(addCategoryToListBtn, gridbag);
    }

    // EFFECTS: adds back, save, and exit buttons to new category panel
    private void addExtraButtonsToNewCategoryPanel() {
        gridbag = new GridBagConstraints();
        gridbag.gridx = 0;
        gridbag.gridy = 2;
        createNewCategory.add(back, gridbag);
        back.addMouseListener(new MouseAdapter() {
            public void mouseReleased(MouseEvent evt) {
                frame.remove(createNewCategory);
                frame.remove(loadFilePanel);
                frame.remove(newFilePanel);
                menu();
                frame.getContentPane();
            }
        });
        gridbag = new GridBagConstraints();
        gridbag.gridx = 1;
        gridbag.gridy = 2;
        createNewCategory.add(save, gridbag);
        gridbag = new GridBagConstraints();
        gridbag.gridx = 2;
        gridbag.gridy = 2;
        createNewCategory.add(exit, gridbag);
    }

    // MODIFIES: this
    // EFFECTS: adds category to list of categories
    private void addCategoryToList() {
        String name = newCategoryInputText.getText();
        spendingCategories.addSpendingCategory(new SpendingCategory(name, 0.00));
        frame.remove(createNewCategory);
        frame.add(mainMenu);
        frame.getContentPane();
        mainMenu.revalidate();
        mainMenu.repaint();
    }

    // MODIFIES: this
    // EFFECT: opens delete category panel
    private void delCategory() { // inspired by: https://docs.oracle.com/javase/tutorial/displayCode.html?code=https://docs.oracle.com/javase/tutorial/uiswing/examples/components/ListDemoProject/src/components/ListDemo.java
        viewCategoriesList();
        categoriesList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        categoriesList.setSelectedIndex(0);
        categoriesList.addListSelectionListener(this);

        del = new JButton();
        del.setText("Delete");
        gridbag = new GridBagConstraints();
        gridbag.gridx = 1;
        gridbag.gridy = 0;
        viewCategoriesPanel.add(del, gridbag);
        del.addActionListener(new DeleteCategoryListener());
        addBackButtonToDeleteCategoryPanel();
    }

    // EFFECTS: adds back button to delete category panel
    private void addBackButtonToDeleteCategoryPanel() {
        backFromList = new JButton();
        backFromList.setText("Back");
        gridbag = new GridBagConstraints();
        gridbag.gridy = 4;
        viewCategoriesPanel.add(backFromList, gridbag);

        backFromList.addMouseListener(new MouseAdapter() {
            public void mouseReleased(MouseEvent evt) {
                frame.remove(viewCategoriesPanel);
                frame.remove(categoriesList);
                pack();
                menu();
                mainMenu.revalidate();
                mainMenu.repaint();
                getContentPane();
            }
        });
    }


    @Override
    public void valueChanged(ListSelectionEvent e) { // inspired by: https://docs.oracle.com/javase/tutorial/displayCode.html?code=https://docs.oracle.com/javase/tutorial/uiswing/examples/components/ListDemoProject/src/components/ListDemo.java
        if (e.getValueIsAdjusting() == false) {
            // del.setEnabled(categoriesList.getSelectedIndex() != -1);
        }

    }

    // MODIFIES: this
    // EFFECTS: deletes selected category
    private class DeleteCategoryListener implements ActionListener { // inspired by: https://docs.oracle.com/javase/tutorial/displayCode.html?code=https://docs.oracle.com/javase/tutorial/uiswing/examples/components/ListDemoProject/src/components/ListDemo.java
        public void actionPerformed(ActionEvent e) {
            int index = categoriesList.getSelectedIndex();
            listModel.remove(index);
            spendingCategories.remove(index);

            int size = listModel.getSize();

            if (size == 0) {
                del.setEnabled(false);

            } else {
                if (index == listModel.getSize()) {
                    index--;
                }

                categoriesList.setSelectedIndex(index);
                categoriesList.ensureIndexIsVisible(index);
            }
        }
    }

    // EFFECTS: constructs layout for add  or remove transactions main page
    private void addOrRemoveTransaction() {
        addOrRemovePanel = new JPanel();
        addTransactionBtn = new JButton();
        removeTransactionBtn = new JButton();
        addOrRemovePanel.setLayout(new GridBagLayout());
        addTransactionBtn.setLayout(new GridBagLayout());
        removeTransactionBtn.setLayout(new GridBagLayout());

        addOrRemoveTransactionBtns();
    }

    // EFFECTS: Adds buttons to add/remove transaction panel
    private void addOrRemoveTransactionBtns() {
        frame.remove(mainMenu);
        frame.add(addOrRemovePanel);
        addTransactionBtn();
        removeTransactionBtn();
        addBackFromTransactionsBtn();
        addOrRemovePanel.revalidate();
        addOrRemovePanel.repaint();
        getContentPane();
        pack();
    }

    // EFFECTS: adds add transaction button
    private void addTransactionBtn() {
        gridbag = new GridBagConstraints();
        gridbag.gridx =  0;
        gridbag.gridy = 0;
        addTransactionBtn.setText("Add");
        addOrRemovePanel.add(addTransactionBtn, gridbag);

        addTransactionBtn.addMouseListener(new MouseAdapter() {
            public void mouseReleased(MouseEvent evt) {
                frame.remove(addOrRemovePanel);
                openAddTransactionPanel();

            }
        });
    }

    // EFFECTS: adds remove transaction button
    private void removeTransactionBtn() {
        gridbag = new GridBagConstraints();
        gridbag.gridx = 2;
        gridbag.gridy = 0;
        addOrRemovePanel.add(removeTransactionBtn);
        removeTransactionBtn.setText("Remove");

        removeTransactionBtn.addMouseListener(new MouseAdapter() {
            public void mouseReleased(MouseEvent evt) {
                frame.remove(addOrRemovePanel);
                openRemoveTransactionPanel();

            }
        });
    }

    // EFFECTS: adds back button to add transaction panel
    private void addBackFromTransactionsBtn() {
        backFromAddTransaction = new JButton();
        backFromAddTransaction.setText("Back");
        gridbag = new GridBagConstraints();
        gridbag.gridx = 0;
        gridbag.gridy = 6;
        gridbag.gridwidth = 2;
        addOrRemovePanel.add(backFromAddTransaction, gridbag);

        backFromAddTransaction.addMouseListener(new MouseAdapter() {
            public void mouseReleased(MouseEvent evt) {
                frame.remove(addOrRemovePanel);
                frame.pack();
                frame.getContentPane();
                menu();
            }
        });
    }

    // EFFECTS: opens panel to remove transactions
    private void openRemoveTransactionPanel() {
        frame.remove(addOrRemovePanel);
        removeTransactionPanel = new JPanel();
        removeTransaction = new JButton();
        transactionCombo = new JComboBox<>();
        transactionsList = new JList<>();
        transactionScrollPane = new JScrollPane();
        frame.add(removeTransactionPanel);

        addComboBox();
        frame.getContentPane();
        transactionCombo.addItemListener(new ItemChangeListener());
        addRemoveButton();
        addBackButtonToRemoveTransactionPanel();
    }

    // EFFECTS: Adds combo box to remove transactions panel
    private void addComboBox() {
        comboBoxModel = new DefaultComboBoxModel<>();

        for (SpendingCategory sc : spendingCategories) {
            comboBoxModel.addElement(sc);
        }

        transactionCombo = new JComboBox<>(comboBoxModel);

        gridbag = new GridBagConstraints();
        gridbag.gridx = 0;
        gridbag.gridy = 1;
        removeTransactionPanel.add(transactionCombo, gridbag);
        removeTransactionPanel.repaint();
        removeTransactionPanel.revalidate();
    }

    class ItemChangeListener implements ItemListener {
        @Override
        public void itemStateChanged(ItemEvent e) {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                removeTransactionPanel.remove(transactionsList);
                int index = transactionCombo.getSelectedIndex();
                sc = spendingCategories.getCategoryAtIndex(index);
                ArrayList<Transaction> tr = sc.getTransactions();
                addTransactionsList(tr);
            }
        }
    }

    // EFFECTS: adds each transaction from the selected combo box input to a list
    private void addTransactionsList(ArrayList<Transaction> transactions) {
        transactionsListModel = new DefaultListModel<Transaction>() {

            @Override
            public int getSize() {
                return transactions.size();
            }

            @Override
            public Transaction getElementAt(int index) {
                return transactions.get(index);
            }
        };

        for (Transaction t : transactions) {
            transactionsListModel.addElement(t);
        }
        transactionsList = new JList<>(transactionsListModel);
        removeTransactionPanel.setLayout(new GridBagLayout());
        addTransactionScrollPane();
        transactionsList.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
        setContentPane(transactionScrollPane);
        frame.add(removeTransactionPanel);
        addTransactionList();
        removeTransactionPanel.repaint();
        removeTransactionPanel.revalidate();
        getContentPane();
    }

    // EFFECTS: adds scroll pane to remove transactions panel
    private void addTransactionScrollPane() {
        transactionScrollPane.setViewportView(transactionsList);
        gridbag = new GridBagConstraints();
        gridbag.gridx = 0;
        gridbag.gridy = 2;
        removeTransactionPanel.add(transactionScrollPane, gridbag);
    }

    private void addTransactionList() {
        gridbag = new GridBagConstraints();
        gridbag.gridx = 0;
        gridbag.gridy = 2;
        removeTransactionPanel.add(transactionsList, gridbag);
    }

    private void addBackButtonToRemoveTransactionPanel() {
        backFromList = new JButton();
        backFromList.setText("Back");
        gridbag = new GridBagConstraints();
        gridbag.gridx = 0;
        gridbag.gridy = 6;
        removeTransactionPanel.add(backFromList, gridbag);

        backFromList.addMouseListener(new MouseAdapter() {
            public void mouseReleased(MouseEvent evt) {
                menu();
                frame.remove(removeTransactionPanel);
                frame.remove(addOrRemovePanel);
                frame.remove(addTransactionPanel);
                frame.remove(transactionScrollPane);
                frame.pack();
                frame.getContentPane();
            }
        });
    }


    // EFFECTS: adds remove transaction button to panel
    public void addRemoveButton() {
        remove = new JButton();
        remove.setText("Remove");
        gridbag = new GridBagConstraints();
        gridbag.gridx = 0;
        gridbag.gridy = 4;
        removeTransactionPanel.add(remove, gridbag);
        remove.addActionListener(new DeleteTransactionListener());
    }

    // MODIFIES: this
    // EFFECTS: deletes selected transaction
    private class DeleteTransactionListener implements ActionListener { // inspired by: https://docs.oracle.com/javase/tutorial/displayCode.html?code=https://docs.oracle.com/javase/tutorial/uiswing/examples/components/ListDemoProject/src/components/ListDemo.java
        public void actionPerformed(ActionEvent e) {
            int index = transactionsList.getSelectedIndex();
            transactionsListModel.remove(index);
            sc.removeTransaction(index + 1);
            transactionsList.updateUI();

            int size = transactionsListModel.getSize();

            if (size == 0) {
                remove.setEnabled(false);

            } else {
                if (index == transactionsListModel.getSize()) {
                    index--;
                }

                transactionsList.setSelectedIndex(index);
                transactionsList.ensureIndexIsVisible(index);
            }
        }
    }


    // EFFECTS: opens add transaction panel
    private void openAddTransactionPanel() {
        frame.remove(addOrRemovePanel);
        viewCategoriesList();
        addTransactionInputs();
    }

    // EFFECTS: displays list of spending categories
    private void viewCategoriesList() {
        listModel = new DefaultListModel<>();

        for (SpendingCategory sc : spendingCategories) {
            listModel.addElement(sc);
        }
        categoriesList = new  JList(listModel);
        viewCategoriesPanel = new JPanel();
        viewCategoriesScrollPane = new JScrollPane();
        viewCategoriesPanel.setLayout(new GridBagLayout());

        addScrollPane();
        categoriesList.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setContentPane(viewCategoriesScrollPane);
        setResizable(true);

        frame.remove(mainMenu);
        frame.add(viewCategoriesPanel);
        viewCategoriesPanel.add(categoriesList);
        viewCategoriesPanel.repaint();
        viewCategoriesPanel.revalidate();

    }

    // EFFECTS: adds category list scroll pane to panel
    private void addScrollPane() {
        viewCategoriesScrollPane.setViewportView(categoriesList);
        gridbag = new GridBagConstraints();
        viewCategoriesPanel.add(viewCategoriesScrollPane, gridbag);
    }

    // EFFECTS: adds back button to view categories panel
    private void addBackButton() {
        backFromList = new JButton();
        backFromList.setText("Back");
        gridbag = new GridBagConstraints();
        gridbag.gridy = 4;
        viewCategoriesPanel.add(backFromList, gridbag);

        backFromList.addMouseListener(new MouseAdapter() {
            public void mouseReleased(MouseEvent evt) {
                frame.remove(viewCategoriesPanel);
                frame.remove(categoriesList);
                //frame.remove(addTransactionPanel);
                pack();
                menu();
                mainMenu.revalidate();
                mainMenu.repaint();
                getContentPane();
            }
        });
    }

    // EFFECTS: adds input fields to add transaction panel
    private void addTransactionInputs() {
        addTransactionPanel = new JPanel();
        addLabel();
        addAmountField();
        addDateLabel();
        addDateField();
        saveTransaction();
        addTransactionPanel.setLayout(new GridBagLayout());
        gridbag = new GridBagConstraints();
        gridbag.gridwidth = 500;
        frame.add(addTransactionPanel, gridbag);
        addBackFromAddTransactionButton();

        transactionAmount.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                transactionAmount.getText();
            }
        });

        dateField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                dateField.getText();
            }
        });

    }

    // EFFECTS: adds back button to view categories panel
    private void addBackFromAddTransactionButton() {
        backFromList = new JButton();
        backFromList.setText("Back");
        gridbag = new GridBagConstraints();
        gridbag.gridy = 4;
        viewCategoriesPanel.add(backFromList, gridbag);

        backFromList.addMouseListener(new MouseAdapter() {
            public void mouseReleased(MouseEvent evt) {
                frame.remove(viewCategoriesPanel);
                frame.remove(categoriesList);
                frame.remove(addTransactionPanel);
                pack();
                menu();
                mainMenu.revalidate();
                mainMenu.repaint();
                getContentPane();
            }
        });
    }

    // EFFECTS: adds amount field label to add transaction panel
    private void addLabel() {
        amountLabel = new JLabel();
        amountLabel.setText("Amount:");
        gridbag = new GridBagConstraints();
        gridbag.gridx = 0;
        gridbag.gridy = 3;
        addTransactionPanel.add(amountLabel, gridbag);
    }

    // EFFECTS: adds amount field to add transaction panel
    private void addAmountField() {
        transactionAmount = new JTextField();
        gridbag = new GridBagConstraints();
        transactionAmount.setColumns(3);
        gridbag.anchor = GridBagConstraints.WEST;
        gridbag.gridx = 1;
        gridbag.gridy = 3;
        addTransactionPanel.add(transactionAmount, gridbag);
    }

    // EFFECTS: adds input date field label to panel
    private void addDateLabel() {
        dateLabel = new JLabel();
        dateLabel.setText("Date:");
        gridbag = new GridBagConstraints();
        gridbag.gridx = 2;
        gridbag.gridy = 3;
        addTransactionPanel.add(dateLabel, gridbag);
    }

    // EFFECTS: adds input date field to panel
    private void addDateField() {
        model = new UtilDateModel();
        datePanel = new JDatePanelImpl(model, new Properties());
        datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
        dateField = new JTextField();
        gridbag = new GridBagConstraints();
        dateField.setColumns(1);
        gridbag.gridx = 3;
        gridbag.gridy = 3;
        addTransactionPanel.add(datePicker, gridbag);
    }

    // MODIFIES: this
    // EFFECTS: adds transaction to list of transactions
    private void saveTransaction() {
        saveTransactionBtn = new JButton();
        saveTransactionBtn.setText("Add");
        gridbag = new GridBagConstraints();
        gridbag.gridx = 3;
        gridbag.gridy = 4;
        addTransactionPanel.add(saveTransactionBtn, gridbag);
        categoriesList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        categoriesList.setSelectedIndex(0);
        categoriesList.addListSelectionListener(this);
        saveTransactionBtn.addActionListener(new AddTransactionListener());
    }

    // MODIFIES: this
    // EFFECTS: adds transaction to selected category
    private class AddTransactionListener implements ActionListener { // inspired by: https://docs.oracle.com/javase/tutorial/displayCode.html?code=https://docs.oracle.com/javase/tutorial/uiswing/examples/components/ListDemoProject/src/components/ListDemo.java
        public void actionPerformed(ActionEvent e) {
            int index = categoriesList.getSelectedIndex();
            SpendingCategory sc = spendingCategories.getCategoryAtIndex(index);
            String amount = transactionAmount.getText();
            Double amountDbl = Double.parseDouble(amount);
            Date selectedDate = (Date) datePicker.getModel().getValue();
            DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
            String reportDate = df.format(selectedDate);
            sc.addTransaction(new Transaction(amountDbl, reportDate));
            EventLog.getInstance().logEvent(new Event(amountDbl + "added to balance."));
            sc.addToBalance(amountDbl);
            categoriesList.updateUI();

            int size = listModel.getSize();

            if (size == 0) {
                del.setEnabled(false);

            } else {
                if (index == listModel.getSize()) {
                    index--;
                }

                categoriesList.setSelectedIndex(index);
                categoriesList.ensureIndexIsVisible(index);
            }
        }
    }

    // EFFECTS: formats selected date
    public class DateLabelFormatter extends JFormattedTextField.AbstractFormatter {

        private String datePattern = "yyyy-MM-dd";
        private SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);

        @Override
        public Object stringToValue(String text) throws ParseException {
            return dateFormatter.parseObject(text);
        }

        @Override
        public String valueToString(Object value) throws ParseException {
            if (value != null) {
                Calendar cal = (Calendar) value;
                return dateFormatter.format(cal.getTime());
            }

            return "";
        }
    }

    // EFFECTS: saves the spending data to file
    private void saveWorkRoom() { // inspired by: WorkRoomApp
        try {
            jsonWriter.open();
            jsonWriter.write(spendingCategories);
            jsonWriter.close();
            System.out.println("Saved " + spendingCategories.getName() + " to " + JSON_DATA);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_DATA);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads spending data from file
    private void loadWorkRoom() { // inspired by: WorkRoomApp
        try {
            spendingCategories = jsonReader.read();
            System.out.println("Loaded " + spendingCategories.getName() + " from " + JSON_DATA);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_DATA);
        }
    }

    private class PrintLogAction extends AbstractAction {
        PrintLogAction() {
            super("Print log to...");
            for (Iterator<Event> e = EventLog.getInstance().iterator(); e.hasNext();) {
                Event ev  = e.next();
                System.out.println(ev);

            }
        }

        @Override
        public void actionPerformed(ActionEvent e) {
        }
    }


    // EFFECTS: runs the spending application
    public SpendingApp() throws FileNotFoundException {
        spendingCategories = new SpendingCategories("Sara's Spending App");
        jsonWriter = new JsonWriter(JSON_DATA);
        jsonReader = new JsonReader(JSON_DATA);
        appFrame();

    }

}