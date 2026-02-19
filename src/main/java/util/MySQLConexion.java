package util;

import java.sql.*;

public class MySQLConexion {

    public static Connection getConexion() {
        Connection con = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/bdqhatux?useUnicode=true&characterEncoding=UTF-8&serverTimezone=America/Lima";
            String usr = "root";
            String psw = "root";
            con = DriverManager.getConnection(url, usr, psw);
        } catch (ClassNotFoundException ex) {
            System.out.println("No hay Driver!!");
        } catch (SQLException ex) {
            System.out.println("Error con la BD ");
        }
        return con;
    }
}
