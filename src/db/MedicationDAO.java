package db;

import java.sql.*;

public class MedicationDAO {
    public static void record(int studentId, String medicine, String dosage) {
        try {
            Connection conn = Database.getConnection();
            PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO medications(student_id, medicine, dosage, time_given) VALUES(?,?,?,datetime('now'))");
            ps.setInt(1, studentId);
            ps.setString(2, medicine);
            ps.setString(3, dosage);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean hasReceivedToday(int studentId, String medicine) {
        try {
            Connection conn = Database.getConnection();
            PreparedStatement ps = conn.prepareStatement(
                "SELECT COUNT(*) FROM medications WHERE student_id=? AND medicine=? AND date(time_given)=date('now')");
            ps.setInt(1, studentId);
            ps.setString(2, medicine);
            ResultSet rs = ps.executeQuery();
            boolean exists = rs.next() && rs.getInt(1) > 0;
            rs.close();
            ps.close();
            return exists;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
