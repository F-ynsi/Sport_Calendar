import java.sql.Date;

public class Event {
    private int id;
    private String homeTeamName;
    private String awayTeamName;
    private Date startDateTime;
    private Date endDateTime;
    private String result;

    public Event(int id, String homeTeamName, String awayTeamName, Date startDateTime, Date endDateTime, String result) {
        this.id = id;
        this.homeTeamName = homeTeamName;
        this.awayTeamName = awayTeamName;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.result = result;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHomeTeamName() {
        return homeTeamName;
    }

    public void setHomeTeamName(String homeTeamName) {
        this.homeTeamName = homeTeamName;
    }

    public String getAwayTeamName() {
        return awayTeamName;
    }

    public void setAwayTeamName(String awayTeamName) {
        this.awayTeamName = awayTeamName;
    }

    public Date getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(Date startDateTime) {
        this.startDateTime = startDateTime;
    }

    public Date getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(Date endDateTime) {
        this.endDateTime = endDateTime;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
