package db;

import java.sql.*;

public class Database {
    private static final String DB_URL = "jdbc:sqlite:clinic.db";
    private static Connection conn;

    public static Connection getConnection() throws SQLException {
        if (conn == null || conn.isClosed()) {
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
        stmt.close();
    }
}
