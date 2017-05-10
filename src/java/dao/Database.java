/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author duo
 */
package dao;
 
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
 
public class Database {
    public static Connection getConnection() {
        Connection connection;
        Statement statement;
        ResultSet resultSet;
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/secureweb",
                    "postgres", 
                    "postgres");
            statement = connection.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS user("
                    + "id SERIAL NOT NULL PRIMARY KEY, "
                    + "name varchar(225) NOT NULL UNIQUE, "
                    + "password varchar(225)";
            statement.execute(sql);
            resultSet = statement.executeQuery("SELECT name FROM user");
            boolean adminExits = false;
            while(resultSet.next()) {
                if(resultSet.getString("name").equals("admin")) {
                    adminExits = true;
                }
            }
            if(!adminExits) {
                sql = "INSERT INTO user(name, password) VALUES('admin', '123')";
                statement.execute(sql);
                sql = "INSERT INTO user(name, password) VALUES('user', 'password')";
                statement.execute(sql);
            }
            return connection;
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println("Database.getConnection() Error -->" + ex.getMessage());
            return null;
        }
    }
 
    public static void close(Connection connection) {
        try {
            connection.close();
        } catch (Exception ex) {
        }
    }
}
