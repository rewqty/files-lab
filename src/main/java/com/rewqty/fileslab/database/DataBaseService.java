package com.rewqty.fileslab.database;

import java.sql.*;

public class DataBaseService {
    private final Connection connection;

    public DataBaseService() {
        this.connection = getMysqlConnection();
    }
    public static Connection getMysqlConnection() {
        try {
            DriverManager.registerDriver((Driver) Class.forName("com.mysql.cj.jdbc.Driver").newInstance());

            StringBuilder url = new StringBuilder();
            url.
                    append("jdbc:mysql://").
                    append("localhost:").
                    append("3306/").
                    append("fileslab?").
                    append("createDatabaseIfNotExist=true");

            Connection connection = DriverManager.getConnection(url.toString(), "admin", "admin");


            if(!tableExist(connection, "users")) {
                createTable(connection);
            }
            return connection;

        } catch (SQLException | InstantiationException | IllegalAccessException | ClassNotFoundException e ) {
            e.printStackTrace();
        }
        return null;
    }
    public static boolean tableExist(Connection dbConnection, String tableName) {
        try (ResultSet rs = dbConnection.getMetaData().getTables(null, null, tableName,
                new String[] {"TABLE"})) {
            while (rs.next()) {
                String tName = rs.getString("TABLE_NAME");
                if (tName != null && tName.equals(tableName)) {
                    return true;
                }
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public static void createTable(Connection connection) {
        try {
            String query = "CREATE TABLE `users` (" +
                    "`Id` INT PRIMARY KEY AUTO_INCREMENT, " +
                    "`login` varchar(20) NOT NULL UNIQUE, " +
                    "`email` varchar(254) NOT NULL UNIQUE, " +
                    "`pass` varchar(100) NOT NULL" +
                    ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;";
            connection.createStatement().executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public boolean columnValueExist(String column, String value) {
        try(ResultSet rs = getStatement().executeQuery(
                String.format(
                        "SELECT %s FROM users WHERE %s = '%s';", column, column, value))){
            while(rs.next()) {
                String tValue = rs.getString(column);
                if (tValue != null) {
                    return true;
                }
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public Statement getStatement() throws SQLException {
        return connection.createStatement();
    }
}
