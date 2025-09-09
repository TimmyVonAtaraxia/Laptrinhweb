package JDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DBConnection {
    private final String serverName = "localhost";
    private final String dbName = "LTWCT3";
    private final String portNumber = "1433";
    private final String instance = ""; // Nếu không dùng named instance, để trống

    public Connection getConnectionW() throws Exception {
        String url = "jdbc:sqlserver://" + serverName + ":" + portNumber
                + ";databaseName=" + dbName
                + ";integratedSecurity=true;encrypt=true;trustServerCertificate=true;";
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        return DriverManager.getConnection(url);
    }

    public static void main(String[] args) {
        try {
            DBConnection dbConn = new DBConnection();
            Connection conn = dbConn.getConnectionW();
            Statement stmt = conn.createStatement();

            // Test insert vào Table_1
            stmt.executeUpdate("INSERT INTO Table_1 (ID, NAME, DIACHI) VALUES (2, 'Trung', 'HCM')");

            // Test select từ User (nếu bảng tồn tại)
            ResultSet rs = stmt.executeQuery("SELECT * FROM [User]");
            while (rs.next()) {
                System.out.println(rs.getInt("ID") + " " + rs.getString("username")
                        + " " + rs.getString("email"));
            }

            conn.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            System.err.println("Lỗi chi tiết: " + ex.getMessage());
        }
    }
}