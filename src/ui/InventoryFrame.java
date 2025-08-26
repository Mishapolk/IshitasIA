package ui;

import db.InventoryDAO;
import db.InventoryDAO.Item;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class InventoryFrame extends JFrame {
    private DefaultTableModel model;
    private JTable table;
    private JComboBox<Item> itemBox;

    public InventoryFrame() {
        super("Inventory Management");
        setSize(500,300);
        setLocationRelativeTo(null);
        initComponents();
    }

    private void initComponents() {
        model = new DefaultTableModel(new Object[]{"Item","Quantity","Threshold"},0);
        table = new JTable(model);
        itemBox = new JComboBox<>();
        reload();
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel controls = new JPanel(new GridLayout(2,4));
        JTextField nameField = new JTextField();
        JTextField qtyField = new JTextField();
        JTextField thField = new JTextField();
        JButton addBtn = new JButton("Add Item");
        addBtn.addActionListener(e -> {
            try {
                InventoryDAO.addItem(nameField.getText(), Integer.parseInt(qtyField.getText()), Integer.parseInt(thField.getText()));
                nameField.setText(""); qtyField.setText(""); thField.setText("");
                reload();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Enter valid numbers");
            }
        });
        controls.add(new JLabel("Name"));
        controls.add(nameField);
        controls.add(new JLabel("Qty"));
        controls.add(qtyField);
        controls.add(new JLabel("Threshold"));
        controls.add(thField);
        controls.add(new JLabel());
        controls.add(addBtn);

        JPanel usagePanel = new JPanel();
        JTextField useField = new JTextField(5);
        JButton useBtn = new JButton("Use Selected");
        useBtn.addActionListener(e -> {
            Item selected = (Item) itemBox.getSelectedItem();
            if (selected != null) {
                try {
                    int use = Integer.parseInt(useField.getText());
                    int newQty = Math.max(0, selected.getQuantity() - use);
                    InventoryDAO.updateQuantity(selected.getId(), newQty);
                    reload();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Enter valid quantity");
                }
            }
        });
        usagePanel.add(new JLabel("Item:"));
        usagePanel.add(itemBox);
        usagePanel.add(new JLabel("Use quantity:"));
        usagePanel.add(useField);
        usagePanel.add(useBtn);

        JPanel bottom = new JPanel(new BorderLayout());
        bottom.add(controls, BorderLayout.NORTH);
        bottom.add(usagePanel, BorderLayout.SOUTH);
        add(bottom, BorderLayout.SOUTH);
    }

    private void reload() {
        model.setRowCount(0);
        itemBox.removeAllItems();
        List<Item> items = InventoryDAO.getAll();
        for (Item i : items) {
            model.addRow(new Object[]{i.getName(), i.getQuantity(), i.getThreshold()});
            itemBox.addItem(i);
        }
        if (itemBox.getItemCount() > 0) {
            itemBox.setSelectedIndex(0);
        }
    }
}
