import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;

public class ExpenseUI {

    JFrame f;
    DefaultTableModel model;
    JTable table;

    JTextField date, amount, desc, search;
    JComboBox<String> cat;

    public ExpenseUI() {

        f = new JFrame("💰 Smart Expense Manager");
        f.setSize(1000, 600);
        f.setLayout(new BorderLayout());

        // ===== TOP PANEL =====
        JPanel p = new JPanel();

        date = new JTextField(8);
        amount = new JTextField(6);
        desc = new JTextField(8);
        search = new JTextField(10);

        cat = new JComboBox<>(new String[]{"Food","Travel","Other"});

        JButton add = new JButton("Add");
        JButton update = new JButton("Edit");
        JButton delete = new JButton("Delete");

        JButton sortAmt = new JButton("Sort Amt");
        JButton sortDate = new JButton("Sort Date");

        JButton saveFile = new JButton("Save File");
        JButton loadFile = new JButton("Load File");

        JButton saveDB = new JButton("Save DB");
        JButton loadDB = new JButton("Load DB");

        JButton importFile = new JButton("Import File");

        JButton total = new JButton("Total");

        // ADD ALL
        p.add(new JLabel("Date"));
        p.add(date);
        p.add(new JLabel("Amt"));
        p.add(amount);
        p.add(new JLabel("Desc"));
        p.add(desc);
        p.add(cat);

        p.add(add);
        p.add(update);
        p.add(delete);

        p.add(sortAmt);
        p.add(sortDate);

        p.add(saveFile);
        p.add(loadFile);
        p.add(saveDB);
        p.add(loadDB);

        p.add(importFile);
        p.add(total);

        p.add(new JLabel("Search"));
        p.add(search);

        f.add(p, BorderLayout.NORTH);

        // ===== TABLE =====
        model = new DefaultTableModel(new String[]{"Date","Cat","Amt","Desc"},0);
        table = new JTable(model);
        f.add(new JScrollPane(table), BorderLayout.CENTER);

        // ===== ACTIONS =====

        // ADD
        add.addActionListener(e -> {
            try {
                Expense ex = new Expense(
                        date.getText(),
                        cat.getSelectedItem().toString(),
                        Double.parseDouble(amount.getText()),
                        desc.getText()
                );
                ExpenseManager.addExpense(ex);
                refresh();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(f,"Invalid Input");
            }
        });

        // EDIT
        update.addActionListener(e -> {
            int i = table.getSelectedRow();
            if (i >= 0) {
                ExpenseManager.list.get(i).date = date.getText();
                ExpenseManager.list.get(i).category = cat.getSelectedItem().toString();
                ExpenseManager.list.get(i).amount = Double.parseDouble(amount.getText());
                ExpenseManager.list.get(i).description = desc.getText();
                refresh();
            }
        });

        // DELETE
        delete.addActionListener(e -> {
            int i = table.getSelectedRow();
            if (i >= 0) {
                ExpenseManager.list.remove(i);
                refresh();
            }
        });

        // SORT
        sortAmt.addActionListener(e -> {
            ExpenseManager.sortByAmount();
            refresh();
        });

        sortDate.addActionListener(e -> {
            ExpenseManager.sortByDate();
            refresh();
        });

        // SAVE / LOAD FILE
        saveFile.addActionListener(e -> {
            ExpenseManager.saveFile();
            JOptionPane.showMessageDialog(f,"Saved to File");
        });

        loadFile.addActionListener(e -> {
            ExpenseManager.loadFile();
            refresh();
        });

        // DB
        saveDB.addActionListener(e -> {
            ExpenseManager.saveToDB();
            JOptionPane.showMessageDialog(f,"Saved to DB");
        });

        loadDB.addActionListener(e -> {
            ExpenseManager.loadFromDB();
            refresh();
        });

        // IMPORT ANY FILE
        importFile.addActionListener(e -> {
            JFileChooser fc = new JFileChooser();
            if (fc.showOpenDialog(f) == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();
                ExpenseManager.importFromFile(file.getAbsolutePath());
                refresh();
            }
        });

        // SEARCH
        search.addActionListener(e -> {
            model.setRowCount(0);
            for (Expense ex : ExpenseManager.search(search.getText())) {
                model.addRow(new Object[]{
                        ex.date, ex.category, ex.amount, ex.description
                });
            }
        });

        // TOTAL
        total.addActionListener(e -> {
            JOptionPane.showMessageDialog(f,
                    "Total = " + ExpenseManager.total());
        });

        // TABLE CLICK AUTO FILL
        table.getSelectionModel().addListSelectionListener(e -> {
            int i = table.getSelectedRow();
            if (i >= 0) {
                date.setText(model.getValueAt(i,0).toString());
                cat.setSelectedItem(model.getValueAt(i,1));
                amount.setText(model.getValueAt(i,2).toString());
                desc.setText(model.getValueAt(i,3).toString());
            }
        });

        f.setVisible(true);
    }

    void refresh() {
        model.setRowCount(0);
        for (Expense e : ExpenseManager.list) {
            model.addRow(new Object[]{
                    e.date, e.category, e.amount, e.description
            });
        }
    }

    public static void main(String[] args) {
        new ExpenseUI();
    }
}