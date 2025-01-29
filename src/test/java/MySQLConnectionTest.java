import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLConnectionTest {
    public static void main(String[] args) {
        String url = "jdbc:mysql://autorack.proxy.rlwy.net:45564/railway";
        String user = "root";
        String password = "rKgnVgttGmHMohVqZDqUcnQehKKcCUlS";

        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            System.out.println("Conexão com o MySQL bem-sucedida!");
        } catch (SQLException e) {
            System.err.println("Erro ao conectar ao MySQL: " + e.getMessage());
        }
    }
}
