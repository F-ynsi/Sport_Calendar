import io.javalin.Javalin;

import java.util.ArrayList;

public class Server {
    public static void main(String[] args) {
        ArrayList<Team> teams = DBAccess.getAllTeams();

        Javalin app = Javalin.create().start(8080);
        app.get("/calendar", ctx -> ctx.json(teams));
    }
}



