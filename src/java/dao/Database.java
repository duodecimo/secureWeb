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
import java.util.logging.Level;
import java.util.logging.Logger;
 
public class Database {
    public static Connection getConnection() {
        Connection connection;
        Statement statement;
        ResultSet resultSet;
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(
                    "jdbc:postgresql://localhost/secureweb",
                    "postgres", 
                    "postgres");
            statement = connection.createStatement();
            String sql = "CREATE TABLE user("
                    + "id SERIAL NOT NULL PRIMARY KEY, "
                    + "name varchar(225) NOT NULL UNIQUE, "
                    + "password varchar(225))";
            try {
                statement.execute(sql);
            } catch (SQLException sQLException) {
                System.out.println("Sql Exception Error: " + sQLException);
                System.out.println("Command:  " + sql);
            }
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
        } catch (SQLException ex) {
            System.out.println("Database.getConnection() Error -->" + ex.getMessage());
            return null;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
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
