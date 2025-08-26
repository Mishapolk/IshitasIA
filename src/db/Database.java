package db;

import java.io.File;
import java.sql.*;

public class Database {
    private static final String DB_URL = "jdbc:sqlite:data/clinic.db";
    private static Connection conn;

    public static Connection getConnection() throws SQLException {
        if (conn == null || conn.isClosed()) {
            new File("data").mkdirs();
            conn = DriverManager.getConnection(DB_URL);
            initialize();
        }
        return conn;
    }

    private static void initialize() throws SQLException {
        Statement stmt = conn.createStatement();
        // users table
        stmt.executeUpdate("CREATE TABLE IF NOT EXISTS users (id INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT UNIQUE, password TEXT, role TEXT)");
        // students table
        stmt.executeUpdate("CREATE TABLE IF NOT EXISTS students (id INTEGER PRIMARY KEY, name TEXT, class TEXT, photo TEXT, conditions TEXT, allergies TEXT, emergency_contact TEXT)");
        // slips table
        stmt.executeUpdate("CREATE TABLE IF NOT EXISTS slips (id INTEGER PRIMARY KEY AUTOINCREMENT, student_id INTEGER, teacher_name TEXT, complaint TEXT, time_sent TEXT, nurse_notes TEXT, status TEXT)");
        // inventory table
        stmt.executeUpdate("CREATE TABLE IF NOT EXISTS inventory (id INTEGER PRIMARY KEY AUTOINCREMENT, item TEXT UNIQUE, quantity INTEGER, threshold INTEGER)");
        // medications table
        stmt.executeUpdate("CREATE TABLE IF NOT EXISTS medications (id INTEGER PRIMARY KEY AUTOINCREMENT, student_id INTEGER, medicine TEXT, dosage TEXT, time_given TEXT)");
        // insert default users if none exist
        ResultSet rs = stmt.executeQuery("SELECT COUNT(*) AS count FROM users");
        if (rs.next() && rs.getInt("count") == 0) {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO users(username,password,role) VALUES(?,?,?)");
            ps.setString(1, "nurse");
            ps.setString(2, "nurse123");
            ps.setString(3, "nurse");
            ps.executeUpdate();
            ps.setString(1, "teacher");
            ps.setString(2, "teacher123");
            ps.setString(3, "teacher");
            ps.executeUpdate();
            ps.close();
        }
        rs.close();

        // sample student records
        ResultSet rsStudents = stmt.executeQuery("SELECT COUNT(*) AS count FROM students");
        if (rsStudents.next() && rsStudents.getInt("count") == 0) {
            PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO students(id,name,class,photo,conditions,allergies,emergency_contact) VALUES(?,?,?,?,?,?,?)");
            ps.setInt(1, 1);
            ps.setString(2, "Alice Johnson");
            ps.setString(3, "5A");
            ps.setString(4, "");
            ps.setString(5, "Asthma");
            ps.setString(6, "Peanuts");
            ps.setString(7, "Mrs. Johnson 1234567890");
            ps.executeUpdate();
            ps.setInt(1, 2);
            ps.setString(2, "Bob Smith");
            ps.setString(3, "6B");
            ps.setString(4, "");
            ps.setString(5, "None");
            ps.setString(6, "N/A");
            ps.setString(7, "Mr. Smith 0987654321");
            ps.executeUpdate();
            ps.setInt(1, 3);
            ps.setString(2, "Charlie Brown");
            ps.setString(3, "5A");
            ps.setString(4, "");
            ps.setString(5, "Diabetes");
            ps.setString(6, "Nuts");
            ps.setString(7, "Mrs. Brown 1122334455");
            ps.executeUpdate();
            ps.close();
        }
        rsStudents.close();

        // sample inventory records
        ResultSet rsInv = stmt.executeQuery("SELECT COUNT(*) AS count FROM inventory");
        if (rsInv.next() && rsInv.getInt("count") == 0) {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO inventory(item,quantity,threshold) VALUES(?,?,?)");
            ps.setString(1, "Bandage");
            ps.setInt(2, 20);
            ps.setInt(3, 5);
            ps.executeUpdate();
            ps.setString(1, "Paracetamol");
            ps.setInt(2, 30);
            ps.setInt(3, 10);
            ps.executeUpdate();
            ps.setString(1, "Antiseptic");
            ps.setInt(2, 15);
            ps.setInt(3, 3);
            ps.executeUpdate();
            ps.close();
        }
        rsInv.close();

        // sample slips
        ResultSet rsSlips = stmt.executeQuery("SELECT COUNT(*) AS count FROM slips");
        if (rsSlips.next() && rsSlips.getInt("count") == 0) {
            PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO slips(student_id, teacher_name, complaint, time_sent, nurse_notes, status) VALUES(?,?,?,?,?,?)");
            ps.setInt(1, 1);
            ps.setString(2, "Mr. Adams");
            ps.setString(3, "Headache");
            ps.setString(4, "2024-05-01 09:15");
            ps.setString(5, "Given rest");
            ps.setString(6, "resolved");
            ps.executeUpdate();
            ps.setInt(1, 2);
            ps.setString(2, "Ms. Blake");
            ps.setString(3, "Stomachache");
            ps.setString(4, "2024-05-02 10:30");
            ps.setString(5, "");
            ps.setString(6, "pending");
            ps.executeUpdate();
            ps.close();
        }
        rsSlips.close();

        // sample medication history
        ResultSet rsMed = stmt.executeQuery("SELECT COUNT(*) AS count FROM medications");
        if (rsMed.next() && rsMed.getInt("count") == 0) {
            PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO medications(student_id, medicine, dosage, time_given) VALUES(?,?,?,?)");
            ps.setInt(1, 1);
            ps.setString(2, "Paracetamol");
            ps.setString(3, "10ml");
            ps.setString(4, "2024-04-20 09:00");
            ps.executeUpdate();
            ps.setInt(1, 3);
            ps.setString(2, "Insulin");
            ps.setString(3, "5 units");
            ps.setString(4, "2024-04-21 12:00");
            ps.executeUpdate();
            ps.close();
        }
        rsMed.close();

        stmt.close();
    }
}
