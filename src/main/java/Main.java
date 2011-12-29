import org.neo4j.graphdb.Node;
import spark.Request;
import spark.Response;
import spark.Route;

import static java.lang.System.getenv;
import static spark.Spark.get;
import static spark.Spark.setPort;
import org.neo4j.rest.graphdb.RestGraphDatabase;
import org.neo4j.graphdb.GraphDatabaseService;

import java.util.Date;

public class Main {

    private static final String LAST_HELLO = "lastHello";

    public static void main(String[] args) {
        final GraphDatabaseService gdb = new RestGraphDatabase(getenv("NEO4J_REST_URL"), getenv("NEO4J_LOGIN"), getenv("NEO4J_PASSWORD"));
        setPort(Integer.parseInt(getenv("PORT")));
        get(new Route("/") {
            public Object handle(Request request, Response response) {
                gdb.getReferenceNode().setProperty(LAST_HELLO,System.currentTimeMillis());
                response.type("text/html");
                return "Hello World! <a href='/neo'>Neo4j Reference Node</a>";
            }
        });
        get(new Route("/neo") {
            public Object handle(Request request, Response response) {
                final Node node = gdb.getReferenceNode();
                final Date lastHello = new Date((Long) node.getProperty(LAST_HELLO, 0L));
                return "Hello World from the Neo4j-Node id "+ node.getId()+" last hello "+ lastHello;
            }
        });
    }
}
