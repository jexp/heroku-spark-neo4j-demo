import spark.Request;
import spark.Response;
import spark.Route;

import static java.lang.System.getenv;
import static spark.Spark.get;
import static spark.Spark.setPort;
import org.neo4j.rest.graphdb.RestGraphDatabase;
import org.neo4j.graphdb.GraphDatabaseService;

public class Main {

    public static void main(String[] args) {
        setPort(Integer.parseInt(getenv("PORT")));
        get(new Route("/") {
            public Object handle(Request request, Response response) {
                return "Hello World! <a href='/neo'>Neo4j Reference Node</a>";
            }
        });
        final GraphDatabaseService gdb = new RestGraphDatabase(getenv("NEO4J_REST_URL"), getenv("NEO4J_LOGIN"), getenv("NEO4J_PASSWORD"));
        get(new Route("/neo") {
            public Object handle(Request request, Response response) {
                return "Hello World from the Neo4j-Node "+gdb.getReferenceNode();
            }
        });
    }
}
