import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseManager {
    // This creates a local database file named school.db in your folder
    private static final String URL = "jdbc:sqlite:school.db";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    // This automatically creates the database table structure
    public static void initializeDatabase() {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS students (" +
                                "id TEXT PRIMARY KEY, " +
                                "name TEXT NOT NULL, " +
                                "age INTEGER, " +
                                "course TEXT" +
                                ");";
        
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            
            stmt.execute(createTableSQL);
            System.out.println("Database system initialized. Table is ready!");
            
        } catch (SQLException e) {
            System.out.println("Error initializing database: " + e.getMessage());
        }
    }
}