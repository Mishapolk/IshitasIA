package db;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AppointmentDAO {
    public static class Appointment {
        private int id;
        private int studentId;
        private String studentName;
        private String date;
        private String timeSlot;
        public Appointment(int id, int studentId, String studentName, String date, String timeSlot) {
            this.id = id; this.studentId = studentId; this.studentName = studentName; this.date = date; this.timeSlot = timeSlot;
        }
        public int getId() { return id; }
        public int getStudentId() { return studentId; }
        public String getStudentName() { return studentName; }
        public String getDate() { return date; }
        public String getTimeSlot() { return timeSlot; }
    }

    public static boolean addAppointment(int studentId, String date, String timeSlot) {
        if (isBooked(date, timeSlot)) return false;
        try {
            Connection conn = Database.getConnection();
            PreparedStatement ps = conn.prepareStatement("INSERT INTO appointments(student_id,date,time_slot) VALUES(?,?,?)");
            ps.setInt(1, studentId);
            ps.setString(2, date);
            ps.setString(3, timeSlot);
            ps.executeUpdate();
            ps.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean isBooked(String date, String timeSlot) {
        try {
            Connection conn = Database.getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT COUNT(*) FROM appointments WHERE date=? AND time_slot=?");
            ps.setString(1, date);
            ps.setString(2, timeSlot);
            ResultSet rs = ps.executeQuery();
            boolean booked = rs.next() && rs.getInt(1) > 0;
            rs.close();
            ps.close();
            return booked;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public static List<Appointment> getAppointmentsForDate(String date) {
        List<Appointment> list = new ArrayList<>();
        try {
            Connection conn = Database.getConnection();
            PreparedStatement ps = conn.prepareStatement(
                "SELECT a.id,a.student_id,s.name,a.date,a.time_slot FROM appointments a JOIN students s ON a.student_id=s.id WHERE a.date=? ORDER BY a.time_slot");
            ps.setString(1, date);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Appointment(
                    rs.getInt(1),
                    rs.getInt(2),
                    rs.getString(3),
                    rs.getString(4),
                    rs.getString(5)
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
