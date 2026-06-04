import java.sql.*;
import java.util.Scanner;

public class StudentManagementApp {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        // This line triggers your DatabaseManager to auto-create the file and table!
        DatabaseManager.initializeDatabase();

        while (true) {
            System.out.println("\n=== STUDENT MANAGEMENT SYSTEM (SQL DATABASE) ===");
            System.out.println("1. Add Student (Create)");
            System.out.println("2. Display All Students (Read)");
            System.out.println("3. Update Student Record (Update)");
            System.out.println("4. Delete Student Record (Delete)");
            System.out.println("5. Exit");
            System.out.print("Select an option (1-5): ");

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    createStudent();
                    break;
                case "2":
                    readStudents();
                    break;
                case "3":
                    updateStudent();
                    break;
                case "4":
                    deleteStudent();
                    break;
                case "5":
                    System.out.println("Exiting system. Goodbye!");
                    System.exit(0);
                default:
                    System.out.println("Invalid option! Please enter a number between 1 and 5.");
            }
        }
    }

    // 1. CREATE: Insert a new row into the database table
    private static void createStudent() {
        System.out.println("\n--- Add New Student ---");
        System.out.print("Enter Student ID: ");
        String id = scanner.nextLine().trim();

        System.out.print("Enter Name: ");
        String name = scanner.nextLine().trim();

        System.out.print("Enter Age: ");
        int age = Integer.parseInt(scanner.nextLine().trim());

        System.out.print("Enter Course: ");
        String course = scanner.nextLine().trim();

        String sql = "INSERT INTO students(id, name, age, course) VALUES(?,?,?,?)";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, id);
            pstmt.setString(2, name);
            pstmt.setInt(3, age);
            pstmt.setString(4, course);
            pstmt.executeUpdate();
            
            System.out.println("Success: Student record inserted into SQL table!");
        } catch (SQLException e) {
            System.out.println("Error inserting student: " + e.getMessage());
        }
    }

    // 2. READ: Fetch and display all rows from the database table
    private static void readStudents() {
        System.out.println("\n--- Current Student Records from SQL ---");
        String sql = "SELECT * FROM students";

        try (Connection conn = DatabaseManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            boolean hasData = false;
            while (rs.next()) {
                hasData = true;
                System.out.printf("ID: %-8s | Name: %-20s | Age: %-5d | Course: %s\n",
                        rs.getString("id"),
                        rs.getString("name"),
                        rs.getInt("age"),
                        rs.getString("course"));
            }
            if (!hasData) {
                System.out.println("No records found in the database table.");
            }
        } catch (SQLException e) {
            System.out.println("Error reading data: " + e.getMessage());
        }
    }

    // 3. UPDATE: Modify an existing row in the table
    private static void updateStudent() {
        System.out.println("\n--- Update Student Record ---");
        System.out.print("Enter Student ID to update: ");
        String id = scanner.nextLine().trim();

        System.out.print("Enter New Name: ");
        String name = scanner.nextLine().trim();

        System.out.print("Enter New Age: ");
        int age = Integer.parseInt(scanner.nextLine().trim());

        System.out.print("Enter New Course: ");
        String course = scanner.nextLine().trim();

        String sql = "UPDATE students SET name = ?, age = ?, course = ? WHERE id = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, name);
            pstmt.setInt(2, age);
            pstmt.setString(3, course);
            pstmt.setString(4, id);
            
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Success: Database row updated successfully.");
            } else {
                System.out.println("Error: Student ID not found.");
            }
        } catch (SQLException e) {
            System.out.println("Error updating data: " + e.getMessage());
        }
    }

    // 4. DELETE: Erase a row from the table matching the ID
    private static void deleteStudent() {
        System.out.println("\n--- Delete Student Record ---");
        System.out.print("Enter Student ID to delete: ");
        String id = scanner.nextLine().trim();

        String sql = "DELETE FROM students WHERE id = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, id);
            int rowsAffected = pstmt.executeUpdate();
            
            if (rowsAffected > 0) {
                System.out.println("Success: Row completely erased from database.");
            } else {
                System.out.println("Error: Student ID not found.");
            }
        } catch (SQLException e) {
            System.out.println("Error deleting data: " + e.getMessage());
        }
    }
}