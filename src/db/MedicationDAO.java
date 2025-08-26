package db;

import java.sql.*;

import java.util.ArrayList;
import java.util.List;

public class MedicationDAO {
    public static class Record {
        private int id;
        private String medicine;
        private String dosage;
        private String time;
        public Record(int id, String medicine, String dosage, String time) {
            this.id = id; this.medicine = medicine; this.dosage = dosage; this.time = time;
        }
        public int getId() { return id; }
        public String getMedicine() { return medicine; }
        public String getDosage() { return dosage; }
        public String getTime() { return time; }
    }

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

    public static List<Record> historyForStudent(int studentId) {
        List<Record> list = new ArrayList<>();
        try {
            Connection conn = Database.getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM medications WHERE student_id=? ORDER BY time_given DESC");
            ps.setInt(1, studentId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Record(
                    rs.getInt("id"),
                    rs.getString("medicine"),
                    rs.getString("dosage"),
                    rs.getString("time_given")
                ));
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
