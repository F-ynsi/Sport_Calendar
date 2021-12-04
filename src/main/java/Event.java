import java.sql.Date;

public class Event {
    private int id;
    private int homeTeamId;
    private int awayTeamId;
    private Date StartDateTime;
    private Date endDateTime;
    private String result;

    public Event(int id, int homeTeamId, int awayTeamId, Date startDateTime, Date endDateTime, String result) {
        this.id = id;
        this.homeTeamId = homeTeamId;
        this.awayTeamId = awayTeamId;
        StartDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.result = result;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getHomeTeamId() {
        return homeTeamId;
    }

    public void setHomeTeamId(int homeTeamId) {
        this.homeTeamId = homeTeamId;
    }

    public int getAwayTeamId() {
        return awayTeamId;
    }

    public void setAwayTeamId(int awayTeamId) {
        this.awayTeamId = awayTeamId;
    }

    public Date getStartDateTime() {
        return StartDateTime;
    }

    public void setStartDateTime(Date startDateTime) {
        StartDateTime = startDateTime;
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
