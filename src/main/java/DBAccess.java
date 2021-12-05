import java.sql.*;
import java.util.ArrayList;

public class DBAccess {
        static String url = "jdbc:mysql://localhost:3306/calendarDB";
        static String username = "root";
        static String password = "123456";

    public static ArrayList<Team> fetchTeams(){
        String sql = "SELECT * FROM teams";
        ArrayList<Team> teams = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet result = preparedStatement.executeQuery();

            while (result.next()) {
                int id = result.getInt("id");
                String name = result.getString("name");
                String league = result.getString("league");
                String sport = result.getString("sport");
                teams.add(new Team(id, name, league, sport));
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return teams;
    }

    public static ArrayList<Event> fetchEvents(){
        String sql = "SELECT * FROM events";
        ArrayList<Event> events = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet result = preparedStatement.executeQuery();

            while (result.next()) {
                int id = result.getInt("id");
                String homeTeamName = fetchTeamName(result.getInt("_home"));
                String awayTeamName = fetchTeamName(result.getInt("_away"));
                Date startDateTime = result.getDate("startDateTime");
                Date endDateTime = result.getDate("endDateTime");
                String gameResult = result.getString("result");
                events.add(new Event(id, homeTeamName, awayTeamName, startDateTime, endDateTime, gameResult));
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return events;
    }

    private static String fetchTeamName(int teamId) throws NullPointerException {
        String sql = "SELECT * FROM teams where id = ? LIMIT 1";
       try (Connection connection = DriverManager.getConnection(url, username, password)){
           PreparedStatement preparedStatement = connection.prepareStatement(sql);
           preparedStatement.setInt(1, teamId);
           ResultSet result = preparedStatement.executeQuery();
           if (result.next()) {
               return result.getString("name");
           }
       } catch (Exception e){
           e.printStackTrace();
       }
       return null;
    }

    public void initializedDB() {
        try (Connection connection = DriverManager.getConnection(url, username, password)){
            PreparedStatement statement01 = connection.prepareStatement("CREATE DATABASE IF NOT EXISTS calendarDB");

            PreparedStatement statement02 = connection.prepareStatement("CREATE TABLE IF NOT EXISTS teams (id int, name varchar(20), league varchar(20), sport varchar(50), PRIMARY KEY(id))");
            PreparedStatement statement03 = connection.prepareStatement("CREATE TABLE IF NOT EXISTS events (id int, _home int, _away int, startDateTime DATETIME, endDateTime DATETIME, result varchar(30), PRIMARY KEY(id), FOREIGN KEY(_home) REFERENCES teams(id), FOREIGN KEY(_away) REFERENCES teams(id), CONSTRAINT columns_cannot_equal CHECK (_home <> _away))");
            PreparedStatement statement04 = connection.prepareStatement("INSERT INTO teams (id, name, league, sport) VALUES (123, 'Graz FC', 'Austrian B', 'Football'), (765, 'Salzburg FC', 'Netherland B', 'Football')");
            PreparedStatement statement05 = connection.prepareStatement("INSERT INTO events (id, _home, _away, startDateTime, endDateTime, result) VALUES (21, 123, 123, '2022-01-11 13:23:44', '2022-01-11 16:23:44', '2-0')");

            statement01.execute();
            statement02.execute();
            statement03.execute();
            statement04.execute();
            statement05.execute();

        } catch(Exception error){
            error.printStackTrace();
        }
    }
}
