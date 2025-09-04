import java.sql.*;

public class DBCheck {
    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // 👈 This is crucial
           Connection c = DriverManager.getConnection(
    "jdbc:mysql://127.0.0.1:3306/bankmanagementsystem?useSSL=false&allowPublicKeyRetrieval=true",
    "root",
    "AbhirupSecure@2025"
);

            System.out.println("✅ Connected to database: " + c.getCatalog());
            c.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
