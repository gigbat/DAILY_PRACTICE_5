import java.sql.*;
import java.util.Date;

public class SqlDateAndEscape {
    public static void main(String[] args) throws ClassNotFoundException {
        String username = "root";
        String password = "root";
        String connectionUrl = "jdbc:mysql://localhost:3307/traindb?verifyServerCertificate=false&useSSL=false&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
        Class.forName("com.mysql.cj.jdbc.Driver");

        try(Connection connection = DriverManager.getConnection(connectionUrl, username, password);
            Statement statement = connection.createStatement()) {
            createTable(statement);
            putValue(connection);
            showResult(statement);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private static void createTable(Statement statement) throws SQLException {
        statement.execute("drop table if exists books");
        statement.executeUpdate("create table if not exists books (id mediumint not null primary key auto_increment, name varchar(30), date DATE)");
    }

    private static void putValue(Connection connection) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("insert into books (name, date) values ('someone', ?)");
        preparedStatement.setDate(1, new java.sql.Date(System.currentTimeMillis()));
        preparedStatement.execute();
    }

    public static void showResult(Statement statement) throws SQLException {
        ResultSet resultSet = statement.executeQuery("select * from books");
        while (resultSet.next()) {
            System.out.println(resultSet.getDate("date"));
        }
    }
}
