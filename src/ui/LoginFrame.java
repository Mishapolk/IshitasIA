package ui;

import db.AuthService;
import model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;

public class LoginFrame extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;

    public LoginFrame() {
        super("School Clinic Login");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(350, 200);
        setLocationRelativeTo(null);
        initComponents();
    }

    private void initComponents() {
        JPanel panel = new JPanel(new GridLayout(3,2));
        panel.add(new JLabel("Username:"));
        usernameField = new JTextField();
        panel.add(usernameField);
        panel.add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        panel.add(passwordField);
        JButton loginBtn = new JButton("Login");
        loginBtn.addActionListener(e -> handleLogin());
        panel.add(new JLabel());
        panel.add(loginBtn);
        add(panel);
    }

    private void handleLogin() {
        String user = usernameField.getText();
        String pass = new String(passwordField.getPassword());
        try {
            User u = AuthService.authenticate(user, pass);
            if (u != null) {
                JOptionPane.showMessageDialog(this, "Welcome " + u.getUsername());
                dispose();
                if ("nurse".equals(u.getRole())) {
                    new NurseDashboard(u).setVisible(true);
                } else {
                    new TeacherSlipForm(u).setVisible(true);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Invalid credentials");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage());
        }
    }
}
