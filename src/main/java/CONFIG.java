public class CONFIG {
    public String getUrl() {
        return "jdbc:mysql://localhost:3306/calendarDB";
    }

    public String getUsername() {
        return "root";
    }

    public String getPassword() {
        return "123456";
    }

    public boolean isDBInitialized() {
        return false;
    }
}
