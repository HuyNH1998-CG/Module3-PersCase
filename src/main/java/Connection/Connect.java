package Connection;

import java.sql.Connection;
import java.sql.DriverManager;

public class Connect {
    private static final String jdbcURL = "jdbc:mysql://localhost:3306/module3case?userSSL=false&useUnicode=yes&characterEncoding=UTF-8";
    private static final String jdbcUsername = "root";
    private static final String jdbcPassword = "Kanze9152";

    public Connect() {
    }

    public Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(jdbcURL,jdbcUsername,jdbcPassword);
        } catch (Exception e){
            e.printStackTrace();
        }
        return connection;
    }
}
