import java.sql.*;
import  java.util.*;
public class Bus_location {
    static Scanner in=new Scanner(System.in);
    public static void bus_location() throws SQLException {
        System.out.print("ENTER THE BUS NAME: ");
        String busName=in.nextLine( );
        busName=Bus_availability.suitableString(busName);
                    String query = STR."SELECT bd.bus_name, COALESCE(bs.stop_name, br.start_location) AS current_stop, COALESCE(bt.arrival_time, NULL) AS stop_arrival_time, COALESCE(bt.departure_time, NULL) AS stop_departure_time FROM buses_details bd JOIN trips t ON bd.bus_id = t.bus_id LEFT JOIN bus_timing bt ON bd.bus_id = bt.bus_id AND bt.arrival_time <= NOW() AND bt.departure_time >= NOW() LEFT JOIN bus_stops bs ON bt.stop_id = bs.stop_id JOIN bus_routes br ON t.route_id = br.route_id WHERE bd.bus_name = '\{busName}' AND (t.departure_datetime <= NOW() AND t.arrival_datetime >= NOW() OR t.departure_datetime > NOW()) ORDER BY bt.arrival_time DESC LIMIT 1";
        ResultSet  rs=data_base_con.read(query);
            if (rs.next()) {
                System.out.println("Bus Name: " + rs.getString("bus_name"));
                System.out.println("Current Stop: " + rs.getString("current_stop"));
                System.out.println("Arrival Time: " + rs.getTime("stop_arrival_time"));
                System.out.println("Departure Time: " + rs.getTime("stop_departure_time"));
            }
            else {
                System.out.println("No information available for the bus: " + busName);
            }
    }
}
