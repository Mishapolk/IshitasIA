package ui;

import db.StudentDAO;
import model.Student;
import model.User;

import javax.swing.*;
import java.awt.*;

public class NurseDashboard extends JFrame {
    private User nurse;
    private JTextField searchField;
    private JTextArea infoArea;

    public NurseDashboard(User nurse) {
        super("Nurse Dashboard");
        this.nurse = nurse;
        setSize(500,400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        initComponents();
    }

    private void initComponents() {
        JPanel top = new JPanel();
        searchField = new JTextField(20);
        JButton searchBtn = new JButton("Search Student");
        searchBtn.addActionListener(e -> searchStudent());
        top.add(searchField);
        top.add(searchBtn);
        infoArea = new JTextArea();
        infoArea.setEditable(false);
        add(top, BorderLayout.NORTH);
        add(new JScrollPane(infoArea), BorderLayout.CENTER);
    }

    private void searchStudent() {
        String term = searchField.getText();
        Student s = null;
        try {
            int id = Integer.parseInt(term);
            s = StudentDAO.findById(id);
        } catch (NumberFormatException ex) {
            s = StudentDAO.findByName(term);
        }
        if (s != null) {
            infoArea.setText("ID: " + s.getId() + "\n" +
                "Name: " + s.getName() + "\n" +
                "Class: " + s.getStudentClass() + "\n" +
                "Conditions: " + s.getConditions() + "\n" +
                "Allergies: " + s.getAllergies() + "\n" +
                "Emergency Contact: " + s.getEmergencyContact());
        } else {
            infoArea.setText("Student not found");
        }
    }
}
