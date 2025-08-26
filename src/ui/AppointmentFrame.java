package ui;

import db.AppointmentDAO;
import db.StudentDAO;
import db.AppointmentDAO.Appointment;
import model.Student;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

public class AppointmentFrame extends JFrame {
    private JTextField dateField;
    private JComboBox<String> timeBox;
    private JTextField studentIdField;
    private JTextArea listArea;

    public AppointmentFrame() {
        super("Appointments");
        setSize(400,300);
        setLocationRelativeTo(null);
        initComponents();
        loadAppointments();
    }

    private void initComponents() {
        setLayout(new BorderLayout());
        JPanel form = new JPanel();
        form.add(new JLabel("Date:"));
        dateField = new JTextField(LocalDate.now().toString(),10);
        form.add(dateField);
        form.add(new JLabel("Time:"));
        timeBox = new JComboBox<>(new String[]{
            "09:00","09:30","10:00","10:30","11:00","11:30","12:00","12:30","13:00","13:30","14:00"
        });
        form.add(timeBox);
        form.add(new JLabel("Student ID:"));
        studentIdField = new JTextField(5);
        form.add(studentIdField);
        JButton bookBtn = new JButton("Book");
        bookBtn.addActionListener(e -> book());
        form.add(bookBtn);
        JButton viewBtn = new JButton("View");
        viewBtn.addActionListener(e -> loadAppointments());
        form.add(viewBtn);
        add(form, BorderLayout.NORTH);

        listArea = new JTextArea();
        listArea.setEditable(false);
        add(new JScrollPane(listArea), BorderLayout.CENTER);
    }

    private void book() {
        try {
            int studentId = Integer.parseInt(studentIdField.getText());
            Student s = StudentDAO.findById(studentId);
            if (s == null) {
                JOptionPane.showMessageDialog(this, "Student not found");
                return;
            }
            String date = dateField.getText();
            String time = (String) timeBox.getSelectedItem();
            boolean ok = AppointmentDAO.addAppointment(studentId, date, time);
            if (!ok) {
                JOptionPane.showMessageDialog(this, "Slot already booked");
            }
            loadAppointments();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Enter valid student ID");
        }
    }

    private void loadAppointments() {
        String date = dateField.getText();
        List<Appointment> list = AppointmentDAO.getAppointmentsForDate(date);
        listArea.setText("");
        for (Appointment a : list) {
            listArea.append(a.getTimeSlot() + " - " + a.getStudentName() + "\n");
        }
        if (list.isEmpty()) {
            listArea.setText("No appointments");
        }
    }
}
