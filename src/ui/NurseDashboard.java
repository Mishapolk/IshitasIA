package ui;

import db.StudentDAO;
import db.InventoryDAO;
import db.InventoryDAO.Item;
import db.MedicationDAO;
import db.MedicationDAO.Record;
import db.AppointmentDAO;
import model.Student;
import model.User;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.time.LocalDate;

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
        checkLowStock();
        showTodayAppointments();
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

        JPanel bottom = new JPanel();
        JButton inventoryBtn = new JButton("Inventory");
        inventoryBtn.addActionListener(e -> new InventoryFrame().setVisible(true));
        JButton medBtn = new JButton("Record Medication");
        medBtn.addActionListener(e -> new MedicationForm().setVisible(true));
        JButton apptBtn = new JButton("Appointments");
        apptBtn.addActionListener(e -> new AppointmentFrame().setVisible(true));
        bottom.add(inventoryBtn);
        bottom.add(medBtn);
        bottom.add(apptBtn);
        add(bottom, BorderLayout.SOUTH);
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
            StringBuilder sb = new StringBuilder();
            sb.append("ID: ").append(s.getId()).append('\n')
              .append("Name: ").append(s.getName()).append('\n')
              .append("Class: ").append(s.getStudentClass()).append('\n')
              .append("Conditions: ").append(s.getConditions()).append('\n')
              .append("Allergies: ").append(s.getAllergies()).append('\n')
              .append("Emergency Contact: ").append(s.getEmergencyContact()).append("\n\nMedication History:\n");
            List<Record> meds = MedicationDAO.historyForStudent(s.getId());
            for (Record r : meds) {
                sb.append(r.getTime()).append(" - ").append(r.getMedicine()).append(" (" + r.getDosage() + ")\n");
            }
            if (meds.isEmpty()) sb.append("No records\n");
            infoArea.setText(sb.toString());
        } else {
            infoArea.setText("Student not found");
        }
    }

    private void checkLowStock() {
        List<Item> items = InventoryDAO.getAll();
        StringBuilder alerts = new StringBuilder();
        for (Item i : items) {
            if (i.getQuantity() <= i.getThreshold()) {
                alerts.append(i.getName()).append(" low (" + i.getQuantity() + ")\n");
            }
        }
        if (alerts.length() > 0) {
            JOptionPane.showMessageDialog(this, alerts.toString(), "Low Stock Alerts", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void showTodayAppointments() {
        List<AppointmentDAO.Appointment> appts = AppointmentDAO.getAppointmentsForDate(LocalDate.now().toString());
        if (!appts.isEmpty()) {
            StringBuilder sb = new StringBuilder("Today's Appointments:\n");
            for (AppointmentDAO.Appointment a : appts) {
                sb.append(a.getTimeSlot()).append(" - ").append(a.getStudentName()).append('\n');
            }
            JOptionPane.showMessageDialog(this, sb.toString());
        }
    }
}
