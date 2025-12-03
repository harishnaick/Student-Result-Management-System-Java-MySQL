import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DepartmentDAO {

    public boolean addDepartment(String name) {
        String sql = "INSERT INTO departments(name) VALUES(?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, name);
            int rows = ps.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            System.out.println("Error adding department: " + e.getMessage());
            return false;
        }
    }

    public List<String> getAllDepartments() {
        List<String> list = new ArrayList<>();
        String sql = "SELECT name FROM departments ORDER BY name";

        try (Connection conn = DBUtil.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                list.add(rs.getString("name"));
            }

        } catch (SQLException e) {
            System.out.println("Error fetching departments: " + e.getMessage());
        }

        return list;
    }

    public int getDepartmentIdByName(String name) {
        String sql = "SELECT id FROM departments WHERE name = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            }
        } catch (SQLException e) {
            System.out.println("Error fetching department id: " + e.getMessage());
        }
        return -1;
    }

    public String getDepartmentNameById(int id) {
        String sql = "SELECT name FROM departments WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("name");
            }
        } catch (SQLException e) {
            System.out.println("Error fetching department name: " + e.getMessage());
        }
        return null;
    }
}
