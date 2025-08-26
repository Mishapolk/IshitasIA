package ui;

import db.Database;
import model.User;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TeacherSlipForm extends JFrame {
    private User teacher;
    private JTextField studentIdField;
    private JTextField studentNameField;
    private JTextField classField;
    private JTextArea complaintArea;

    public TeacherSlipForm(User teacher) {
        super("Digital Nurse Slip");
        this.teacher = teacher;
        setSize(400,400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        initComponents();
    }

    private void initComponents() {
        JPanel panel = new JPanel(new GridLayout(5,2));
        panel.add(new JLabel("Student ID:"));
        studentIdField = new JTextField();
        panel.add(studentIdField);
        panel.add(new JLabel("Student Name:"));
        studentNameField = new JTextField();
        panel.add(studentNameField);
        panel.add(new JLabel("Class:"));
        classField = new JTextField();
        panel.add(classField);
        panel.add(new JLabel("Complaint:"));
        complaintArea = new JTextArea(3,20);
        panel.add(new JScrollPane(complaintArea));
        JButton submitBtn = new JButton("Send to Nurse");
        submitBtn.addActionListener(e -> submitSlip());
        add(panel, BorderLayout.CENTER);
        add(submitBtn, BorderLayout.SOUTH);
    }

    private void submitSlip() {
        try {
            Connection conn = Database.getConnection();
            PreparedStatement ps = conn.prepareStatement("INSERT INTO slips(student_id, teacher_name, complaint, time_sent, status) VALUES(?,?,?,?,?)");
            ps.setString(1, studentIdField.getText());
            ps.setString(2, teacher.getUsername());
            ps.setString(3, complaintArea.getText());
            ps.setString(4, LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
            ps.setString(5, "pending");
            ps.executeUpdate();
            ps.close();
            JOptionPane.showMessageDialog(this, "Slip sent to nurse");
            studentIdField.setText("");
            studentNameField.setText("");
            classField.setText("");
            complaintArea.setText("");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error sending slip");
        }
    }
}
