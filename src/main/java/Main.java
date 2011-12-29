import spark.Request;
import spark.Response;
import spark.Route;

import static spark.Spark.get;
import static spark.Spark.setPort;

public class Main {

    public static void main(String[] args) {
        setPort(Integer.parseInt(System.getenv("PORT")));
        get(new Route("/hello") {
            public Object handle(Request request, Response response) {
                return "Hello World!";
            }
        });
    }
}
