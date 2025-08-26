package db;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InventoryDAO {
    public static class Item {
        private int id;
        private String name;
        private int quantity;
        private int threshold;
        public Item(int id, String name, int quantity, int threshold) {
            this.id = id;
            this.name = name;
            this.quantity = quantity;
            this.threshold = threshold;
        }
        public int getId() { return id; }
        public String getName() { return name; }
        public int getQuantity() { return quantity; }
        public int getThreshold() { return threshold; }
        @Override public String toString() { return name; }
    }

    public static List<Item> getAll() {
        List<Item> list = new ArrayList<>();
        try {
            Connection conn = Database.getConnection();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM inventory");
            while (rs.next()) {
                list.add(new Item(rs.getInt("id"), rs.getString("item"), rs.getInt("quantity"), rs.getInt("threshold")));
            }
            rs.close();
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static void addItem(String name, int qty, int threshold) {
        try {
            Connection conn = Database.getConnection();
            PreparedStatement ps = conn.prepareStatement("INSERT INTO inventory(item,quantity,threshold) VALUES(?,?,?)");
            ps.setString(1, name);
            ps.setInt(2, qty);
            ps.setInt(3, threshold);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateQuantity(int id, int qty) {
        try {
            Connection conn = Database.getConnection();
            PreparedStatement ps = conn.prepareStatement("UPDATE inventory SET quantity=? WHERE id=?");
            ps.setInt(1, qty);
            ps.setInt(2, id);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
