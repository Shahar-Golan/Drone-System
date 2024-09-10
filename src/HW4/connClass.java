package HW4;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import HW2.ChiefManager;

public class connClass {
    public static Connection conn;

    public static Connection getConn() throws ClassNotFoundException {
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/database", "root", "");
            return conn;
        } catch (SQLException e) {
            System.out.println(e);
            return null;
        }
    }

    public static void createTable() throws ClassNotFoundException {
        try {
            conn = getConn();
            Statement stmt = conn.createStatement();
            // I will create the table just once because of the IF
            String sql = "CREATE TABLE IF NOT EXISTS Cheif_Managers "      
                       + "(id VARCHAR(255), firstName VARCHAR(255), lastName VARCHAR(255), userName VARCHAR(255), password VARCHAR(255))";
            stmt.executeUpdate(sql);
            stmt.close();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public static void insertChiefManager(ChiefManager chiefManager) throws ClassNotFoundException {
        try {
            Connection conn = getConn();
            // Check if the ChiefManager with this ID already exists
            PreparedStatement checkStmt = conn.prepareStatement("SELECT count(*) FROM cheif_managers WHERE id = ?");
            checkStmt.setString(1, chiefManager.getId());
            ResultSet rs = checkStmt.executeQuery();

            // Only proceed if the ID does not exist
            if (rs.next() && rs.getInt(1) == 0) {
                PreparedStatement stmt = conn.prepareStatement(
                    "INSERT INTO cheif_managers (id, firstName, lastName, userName, password) VALUES (?, ?, ?, ?, ?)");
                stmt.setString(1, chiefManager.getId());
                stmt.setString(2, chiefManager.getFirstName());
                stmt.setString(3, chiefManager.getLastName());
                stmt.setString(4, chiefManager.getUserName());
                stmt.setString(5, chiefManager.getPassword());

                stmt.executeUpdate();
                stmt.close();
            } 

            checkStmt.close();
            rs.close();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }


}


    


    
    