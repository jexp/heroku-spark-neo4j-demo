This [Heroku](http://heroku.com) Demo uses the

[Spark Java Web Framework](http://sparkjava.com) by Per Wendel [@perwendel](http://twitter.com/perwendel).

And the [Neo4j](http://neo4j.org) Graph Database [Heroku Add-On](http://addons.heroku.com/neo4j) via the
[Java Rest Bindings](https://github.com/neo4j/java-rest-binding).

The Demo can be found at [http://spark-neo4j.heroku.com](http://spark-neo4j.heroku.com)

Code is quote simple:

````
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
````