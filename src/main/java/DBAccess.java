import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class DBAccess {
    public static void main (String[] args) {
        String url = "jdbc:mysql://localhost:3306/calendarDB";
        String username = "root";
        String password = "123456";

        try (Connection connection = DriverManager.getConnection(url, username, password)){
            PreparedStatement statement01 = connection.prepareStatement("CREATE DATABASE IF NOT EXISTS calendarDB");


            PreparedStatement statement02 = connection.prepareStatement("CREATE TABLE IF NOT EXISTS teams (id int, name varchar(20), league varchar(20), sport varchar(50), PRIMARY KEY(id))");
            PreparedStatement statement03 = connection.prepareStatement("CREATE TABLE IF NOT EXISTS events (id int, _home int, _away int, startDateTime DATETIME, endDateTime DATETIME, result varchar(30), PRIMARY KEY(id), FOREIGN KEY(_home) REFERENCES teams(id), FOREIGN KEY(_away) REFERENCES teams(id), CONSTRAINT columns_cannot_equal CHECK (_home <> _away))");

           /* PreparedStatement statement04 = connection.prepareStatement("INSERT INTO teams (id, name, league, sport) VALUES (123, 'Graz FC', 'Austrian B', 'Football'), (765, 'Salzburg FC', 'Netherland B', 'Football')");*/
            PreparedStatement statement05 = connection.prepareStatement("INSERT INTO events (id, _home, _away, startDateTime, endDateTime, result) VALUES (21, 123, 123, '2022-01-11 13:23:44', '2022-01-11 16:23:44', '2-0')");



            statement01.execute();
            statement02.execute();
            statement03.execute();
            /*statement04.execute();*/
            statement05.execute();



        } catch(Exception error){
            error.printStackTrace();
        }
    }
}
