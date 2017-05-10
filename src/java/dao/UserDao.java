package dao;
import java.sql.*;
  
public class UserDao {      
     public static boolean login(String user, String password) {
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            connection = Database.getConnection();
            ps = connection.prepareStatement(
                    "select name, password from user where name= ? and password= ? ");
            ps.setString(1, user);
            ps.setString(2, password);
  
            ResultSet rs = ps.executeQuery();
            if (rs.next()) // found
            {
                System.out.println(rs.getString("user"));
                return true;
            }
            else {
                return false;
            }
        } catch (Exception ex) {
            System.out.println("Error in login() -->" + ex.getMessage());
            if(connection == null) {
                System.out.println("null connection!");
            }
            return false;
        } finally {
            Database.close(connection);
        }
    }
}
