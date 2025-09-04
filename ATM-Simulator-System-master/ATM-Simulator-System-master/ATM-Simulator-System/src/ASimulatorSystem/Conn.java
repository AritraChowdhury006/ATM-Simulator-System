package ASimulatorSystem;

import java.sql.*;

public class Conn {
    public Connection c;
    public Statement s;

    public Conn() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            c = DriverManager.getConnection(
                "jdbc:mysql://127.0.0.1:3306/bankmanagementsystem?useSSL=false&allowPublicKeyRetrieval=true",
                "root",
                "AbhirupSecure@2025"
            );
            s = c.createStatement();
            System.out.println("✅ Connected to database: " + c.getCatalog());
        } catch (Exception e) {
            System.err.println("❌ Database connection failed: " + e.getMessage());
        }
    }
}
