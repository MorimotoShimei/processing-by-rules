package com.jpctrade;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.server.StaticHttpHandler;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import java.io.IOException;
import java.net.URI;
import java.io.File;
import java.util.Optional;

/**
 * Main class.
 *
 */
public class Main {
    private static final String protocol = "http://";
    private static Optional<String> host = Optional.ofNullable(System.getenv("HOSTNAME"));
    private static Optional<String> port = Optional.ofNullable(System.getenv("PORT"));
    private static final String apiPath = "api";

    public static final String API_BASE_URI = protocol + host.orElse("localhost") + ":" + port.orElse("8080") + "/" + apiPath + "/";
    public static final String STATIC_BASE_URI = protocol + host.orElse("localhost") + ":" + port.orElse("8080") + "/";

    /**
     * Starts Grizzly HTTP server exposing JAX-RS resources defined in this application.
     * @return Grizzly HTTP server.
     * @throws IOException
     */
    public static HttpServer startServer() throws IOException {
        final ResourceConfig rc = new ResourceConfig().packages("com.jpctrade.samples");
        final StaticHttpHandler staticHttpHandler = new StaticHttpHandler(
                new File(".").getAbsolutePath() + "/src/main/resources/static");

        HttpServer server = GrizzlyHttpServerFactory.createHttpServer(URI.create(API_BASE_URI), rc, false);
        server.getServerConfiguration().addHttpHandler(staticHttpHandler, "/");
        server.start();

        return server;
    }

    /**
     * Main method.
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        HttpServer server = startServer();

        System.out.println("Grizzly2 web server bootup");
        System.out.println(String.format(
                "Jersey api started with WADL available at " + "%sapplication.wadl",
                API_BASE_URI));
        System.out.println("Hit enter to stop it...");
        System.in.read();
        server.shutdown();
    }
}

