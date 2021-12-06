import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class DBAccess {
    static CONFIG config = new CONFIG();
    public static ArrayList<Team> fetchTeams(){
        String sql = "SELECT * FROM teams";
        ArrayList<Team> teams = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(config.getUrl(), config.getUsername(), config.getPassword())) {
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
        DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.FULL, FormatStyle.MEDIUM);
        try (Connection connection = DriverManager.getConnection(config.getUrl(), config.getUsername(), config.getPassword())) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet result = preparedStatement.executeQuery();

            while (result.next()) {
                int id = result.getInt("id");
                String homeTeamName = fetchTeamName(result.getInt("_homeTeamId"));
                String awayTeamName = fetchTeamName(result.getInt("_awayTeamId"));
                String sport = result.getString("sport");
                LocalDateTime startDateTime = DateTimeUtil.parseLocalDateTime(result.getString("startDateTime"));
                LocalDateTime endDateTime = DateTimeUtil.parseLocalDateTime(result.getString("endDateTime"));
                String gameResult = result.getString("result");
                events.add(new Event(id, homeTeamName, awayTeamName, sport, startDateTime.format(DateTimeUtil.formatter), endDateTime.format(DateTimeUtil.formatter), gameResult));
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return events;
    }

    public static List<Event> fetchEventsBySport(String sport) {
        return fetchEvents().stream().filter(event -> Objects.equals(event.getSport(), sport)).collect(Collectors.toList());
    }

    private static String fetchTeamName(int teamId) throws NullPointerException {
        String sql = "SELECT * FROM teams where id = ? LIMIT 1";
       try (Connection connection = DriverManager.getConnection(config.getUrl(), config.getUsername(), config.getPassword())){
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

    public static void initializedDB() {
        try (Connection connection = DriverManager.getConnection(config.getUrl(), config.getUsername(), config.getPassword())){
            PreparedStatement stm0 = connection.prepareStatement("DROP DATABASE calendarDB");
            PreparedStatement stm01 = connection.prepareStatement("CREATE DATABASE IF NOT EXISTS calendarDB");
            PreparedStatement stm02 = connection.prepareStatement("USE calendarDB");
            PreparedStatement stm03 = connection.prepareStatement("CREATE TABLE IF NOT EXISTS teams (id int AUTO_INCREMENT PRIMARY KEY, name varchar(150) NOT NULL, league varchar(150), sport varchar(150) NOT NULL)");
            PreparedStatement stm04 = connection.prepareStatement("CREATE TABLE IF NOT EXISTS events (id int AUTO_INCREMENT PRIMARY KEY, _homeTeamId int NOT NULL, _awayTeamId int NOT NULL, sport varchar(150), startDateTime DATETIME, endDateTime DATETIME, result varchar(150), FOREIGN KEY(_homeTeamId) REFERENCES teams(id), FOREIGN KEY(_awayTeamId) REFERENCES teams(id), CONSTRAINT columns_cannot_equal CHECK (_homeTeamId <> _awayTeamId))");
            PreparedStatement stm05 = connection.prepareStatement("INSERT INTO teams (id, name, league, sport) VALUES (123, 'Graz FC', 'Austrian B', 'Football'), (765, 'Salzburg FC', 'Netherland B', 'Football'), (3, 'Go Go RabBits', 'Tirol A', 'Ice Hockey'), (4, 'Ice Hulks', 'Tirol A', 'Ice Hockey'), (77, 'Breakers', 'Wiener BH', 'Ice Hockey'), (88, 'Rapid', 'Bundesliga', 'Football')");
            PreparedStatement stm06 = connection.prepareStatement("INSERT INTO events (id, _homeTeamId, _awayTeamId, sport, startDateTime, endDateTime, result) VALUES (21, 123, 765, 'Football', '2021-01-11 13:23:44', '2021-01-11 16:23:44', '2-0'), (666, 765, 123, 'Football', '2023-04-01 20:00:00', '2023-04-01 22:00:00', 'N/A'), (23, 3, 4, 'Ice Hockey', '2021-12-23 19:00:00', '2021-12-23 21:00:00', 'N/A'), (990, 88, 123, 'Football', '2022-05-09 18:15:00', '2022-05-09 19:50:00', 'N/A'), (298, 77, 4, 'Ice Hockey', '2022-07-10 14:00:00', '2022-07-10 17:00:00', 'N/A'), (110, 765, 88, 'Football', '2022-03-12 11:15:00', '2022-03-12 13:20:00', 'N/A')");

            stm0.execute();
            stm01.execute();
            stm02.execute();
            stm03.execute();
            stm04.execute();
            stm05.execute();
            stm06.execute();

        } catch(Exception error){
            error.printStackTrace();
        }
    }
}
