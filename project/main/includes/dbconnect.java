package includes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class dbconnect {

    public static Connection conDB()
    {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost/zero-idea", "root", "");
            System.out.println(con);
            return con;

        } catch (ClassNotFoundException | SQLException ex) {

            System.err.println("ConnectionUtil : "+ex.getMessage());
            return null;
        }

    }

}
