package ui;

import db.MedicationDAO;

import javax.swing.*;
import java.awt.*;

public class MedicationForm extends JFrame {
    public MedicationForm() {
        super("Record Medication");
        setSize(350,200);
        setLocationRelativeTo(null);
        initComponents();
    }

    private void initComponents() {
        JPanel panel = new JPanel(new GridLayout(4,2));
        JTextField studentField = new JTextField();
        JTextField medField = new JTextField();
        JTextField doseField = new JTextField();
        panel.add(new JLabel("Student ID:"));
        panel.add(studentField);
        panel.add(new JLabel("Medicine:"));
        panel.add(medField);
        panel.add(new JLabel("Dosage:"));
        panel.add(doseField);
        JButton submit = new JButton("Record");
        submit.addActionListener(e -> {
            try {
                int sid = Integer.parseInt(studentField.getText());
                String med = medField.getText();
                String dose = doseField.getText();
                if (MedicationDAO.hasReceivedToday(sid, med)) {
                    JOptionPane.showMessageDialog(this, "Medicine already given today!");
                } else {
                    MedicationDAO.record(sid, med, dose);
                    JOptionPane.showMessageDialog(this, "Recorded");
                    dispose();
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid student ID");
            }
        });
        panel.add(new JLabel());
        panel.add(submit);
        add(panel);
    }
}
