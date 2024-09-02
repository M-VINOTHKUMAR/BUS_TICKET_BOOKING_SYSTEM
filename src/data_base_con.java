import java.sql.*;

public class data_base_con {
    private  static  final String url = "jdbc:mysql://localhost:3306/bus_ticket_booking_system";
    private  static  final  String username = "root";
    private  static  final  String password = "Vinsbk@4174";
    public static Connection getConnection() throws SQLException {
            return DriverManager.getConnection(url, username, password);
        }
        public  static ResultSet read(String query) throws SQLException {
            Connection con = getConnection();
            PreparedStatement stmt = con.prepareStatement(query);
            return stmt.executeQuery();
        }
        public  static int update(String query) throws SQLException {
            Connection con = getConnection();
            Statement stmt = con.createStatement();
            return stmt.executeUpdate(query);
        }
    }
