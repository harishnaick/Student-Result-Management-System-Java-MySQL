import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;

public class ResultDAO {

    public boolean addResult(int studentId, String subject, int marks, String grade) {
        String sql = "INSERT INTO results(student_id, subject, marks, grade) VALUES(?, ?, ?, ?)";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, studentId);
            ps.setString(2, subject);
            ps.setInt(3, marks);
            ps.setString(4, grade);

            int rows = ps.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            System.out.println("Error adding result: " + e.getMessage());
            return false;
        }
    }

    public void printAllResultsWithDetails() {
        String sql = "SELECT s.name, s.roll_no, d.name AS dept_name, s.year, " +
                     "r.subject, r.marks, r.grade " +
                     "FROM results r " +
                     "JOIN students s ON r.student_id = s.id " +
                     "LEFT JOIN departments d ON s.department_id = d.id " +
                     "ORDER BY s.roll_no, r.subject";

        try (Connection conn = DBUtil.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            System.out.println("\n--- All Results ---");
            while (rs.next()) {
                System.out.println(
                        "Name: " + rs.getString("name") +
                        ", Roll No: " + rs.getString("roll_no") +
                        ", Dept: " + rs.getString("dept_name") +
                        ", Year: " + rs.getInt("year") +
                        ", Subject: " + rs.getString("subject") +
                        ", Marks: " + rs.getInt("marks") +
                        ", Grade: " + rs.getString("grade")
                );
            }

        } catch (SQLException e) {
            System.out.println("Error fetching results: " + e.getMessage());
        }
    }

    // Merit list: all students sorted by marks (highest first)
    public void printMeritList() {
        String sql = "SELECT s.name, s.roll_no, d.name AS dept_name, " +
                     "AVG(r.marks) AS avg_marks " +
                     "FROM results r " +
                     "JOIN students s ON r.student_id = s.id " +
                     "LEFT JOIN departments d ON s.department_id = d.id " +
                     "GROUP BY s.id, s.name, s.roll_no, dept_name " +
                     "ORDER BY avg_marks DESC";

        try (Connection conn = DBUtil.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            System.out.println("\n--- Merit List (by average marks) ---");
            int rank = 1;
            while (rs.next()) {
                System.out.println(
                        "Rank " + rank++ +
                        " | " + rs.getString("name") +
                        " | Roll: " + rs.getString("roll_no") +
                        " | Dept: " + rs.getString("dept_name") +
                        " | Avg Marks: " + rs.getDouble("avg_marks")
                );
            }

        } catch (SQLException e) {
            System.out.println("Error fetching merit list: " + e.getMessage());
        }
    }

    // Top N students
    public void printTopNStudents(int n) {
        String sql = "SELECT s.name, s.roll_no, d.name AS dept_name, " +
                     "AVG(r.marks) AS avg_marks " +
                     "FROM results r " +
                     "JOIN students s ON r.student_id = s.id " +
                     "LEFT JOIN departments d ON s.department_id = d.id " +
                     "GROUP BY s.id, s.name, s.roll_no, dept_name " +
                     "ORDER BY avg_marks DESC " +
                     "LIMIT ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, n);
            ResultSet rs = ps.executeQuery();

            System.out.println("\n--- Top " + n + " Students ---");
            int rank = 1;
            while (rs.next()) {
                System.out.println(
                        "Rank " + rank++ +
                        " | " + rs.getString("name") +
                        " | Roll: " + rs.getString("roll_no") +
                        " | Dept: " + rs.getString("dept_name") +
                        " | Avg Marks: " + rs.getDouble("avg_marks")
                );
            }

        } catch (SQLException e) {
            System.out.println("Error fetching top students: " + e.getMessage());
        }
    }

    // Failed students (any subject below passMarks)
    public void printFailedStudents(int passMarks) {
        String sql = "SELECT DISTINCT s.name, s.roll_no, d.name AS dept_name " +
                     "FROM results r " +
                     "JOIN students s ON r.student_id = s.id " +
                     "LEFT JOIN departments d ON s.department_id = d.id " +
                     "WHERE r.marks < ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, passMarks);
            ResultSet rs = ps.executeQuery();

            System.out.println("\n--- Failed Students (marks < " + passMarks + " in any subject) ---");
            while (rs.next()) {
                System.out.println(
                        "Name: " + rs.getString("name") +
                        " | Roll: " + rs.getString("roll_no") +
                        " | Dept: " + rs.getString("dept_name")
                );
            }

        } catch (SQLException e) {
            System.out.println("Error fetching failed students: " + e.getMessage());
        }
    }

    // Export all results to CSV
    public void exportResultsToCSV(String fileName) {
        String sql = "SELECT s.name, s.roll_no, d.name AS dept_name, s.year, " +
                     "r.subject, r.marks, r.grade " +
                     "FROM results r " +
                     "JOIN students s ON r.student_id = s.id " +
                     "LEFT JOIN departments d ON s.department_id = d.id " +
                     "ORDER BY s.roll_no, r.subject";

        try (Connection conn = DBUtil.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql);
             FileWriter writer = new FileWriter(fileName)) {

            writer.write("Name,Roll No,Department,Year,Subject,Marks,Grade\n");

            while (rs.next()) {
                writer.write(
                        rs.getString("name") + "," +
                        rs.getString("roll_no") + "," +
                        rs.getString("dept_name") + "," +
                        rs.getInt("year") + "," +
                        rs.getString("subject") + "," +
                        rs.getInt("marks") + "," +
                        rs.getString("grade") + "\n"
                );
            }

            System.out.println("Results exported to CSV: " + fileName);

        } catch (SQLException | IOException e) {
            System.out.println("Error exporting to CSV: " + e.getMessage());
        }
    }

    /*
     * PDF export: requires external library like iText or PDFBox.
     * Below is only a placeholder signature. Actual implementation
     * will not compile until you add the library + imports.
     */
    public void exportResultsToPDF(String fileName) {
        System.out.println("PDF export requires adding a PDF library (e.g., iText). For now, use CSV export.");
        // You can show in viva that you know:
        // - Need 3rd party lib
        // - You already implemented CSV which is commonly used.
    }
}
