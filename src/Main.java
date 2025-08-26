import ui.LoginFrame;
import db.Database;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        try {
            Database.getConnection();
        } catch (Exception e) {
            System.err.println("Database initialization failed: " + e.getMessage());
        }
        SwingUtilities.invokeLater(() -> {
            new LoginFrame().setVisible(true);
        });
    }
}
