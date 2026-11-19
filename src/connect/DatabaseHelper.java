package connect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseHelper {
    // Sua 4 thong so nay cho dung SQL Server tren may ban.
    private static final String SERVER = "localhost";
    private static final String PORT = "1433";
    private static final String DATABASE = "QuanLySinhVien";
    private static final String USER = "sa";
    private static final String PASSWORD = "123456";

    private static final String URL =
        "jdbc:sqlserver://" + SERVER + ":" + PORT
        + ";databaseName=" + DATABASE
        + ";encrypt=true;trustServerCertificate=true;";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
