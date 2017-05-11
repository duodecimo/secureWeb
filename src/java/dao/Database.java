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
        String result;
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(
                    "jdbc:postgresql://localhost/secureweb",
                    "postgres", 
                    "postgres");
            statement = connection.createStatement();
            String sql = "CREATE TABLE userinfo("
                    + "id SERIAL PRIMARY KEY, "
                    + "name varchar(225) NOT NULL UNIQUE, "
                    + "password varchar(225))";
            try {
                statement.execute(sql);
            } catch (SQLException sQLException) {
                System.out.println("Sql Exception Error: " + sQLException.getMessage());
                System.out.println("Command: " + sql);
            }
            sql = "SELECT name FROM userinfo";
            try {
                resultSet = statement.executeQuery(sql);
                boolean adminExits = false;
                while(resultSet.next()) {
                    result = resultSet.getString("name");
                    System.out.println("Read from table userinfo: " + result);
                    if(result.equals("admin")) {
                        adminExits = true;
                    }
                }
                if(!adminExits) {
                    sql = "INSERT INTO userinfo(name, password) VALUES('admin', '123')";
                    try {
                        statement.execute(sql);
                    } catch (SQLException sQLException) {
                        System.out.println("Sql Exception Error: " + sQLException.getMessage());
                        System.out.println("Command: " + sql);
                    }
                    sql = "INSERT INTO userinfo(name, password) VALUES('name', 'pass')";
                    try {
                        statement.execute(sql);
                    } catch (SQLException sQLException) {
                        System.out.println("Sql Exception Error: " + sQLException.getMessage());
                        System.out.println("Command: " + sql);
                    }
                }
            } catch (SQLException sQLException) {
                System.out.println("Sql Exception Error: " + sQLException.getMessage());
                System.out.println("Command: " + sql);
            }
            return connection;
        } catch (SQLException ex) {
            System.out.println("Database.getConnection() Error --> " + ex.getMessage());
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
