import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class Server {
    public static void main(String[] args) {
        ArrayList<Event> events = DBAccess.fetchEvents();

        Javalin app = Javalin.create(config -> config.addStaticFiles("./public", Location.EXTERNAL)).start(8080);

        app.get("/home", ctx -> {
            FileReader file = new FileReader("./public/index.html");
            BufferedReader bufferedReader = new BufferedReader(file);
            StringBuilder result = new StringBuilder();
            String rd;
            while ( (rd = bufferedReader.readLine()) != null) {
                result.append(rd);
            }
            ctx.result(result.toString()).contentType("text/html");
            file.close();
            bufferedReader.close();
        });

        app.get("/calendar", ctx -> ctx.json(events));
    }
}



