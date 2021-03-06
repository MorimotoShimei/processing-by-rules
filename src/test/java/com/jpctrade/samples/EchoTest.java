package com.jpctrade.samples;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import org.glassfish.grizzly.http.server.HttpServer;
import org.json.JSONException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import com.jpctrade.Main;
import org.junit.Assert;
import uk.co.datumedge.hamcrest.json.SameJSONAs;

public class EchoTest {
    private HttpServer server;
    private WebTarget target;
    static final String myPath = "samples";
    static final String myEchoPath = "echo";

    @Before
    public void setUp() throws Exception {
        // start the server
        server = Main.startServer();
        // create the client
        Client c = ClientBuilder.newClient();

        target = c.target(Main.API_BASE_URI);
    }

    @After
    public void tearDown() throws Exception {
        server.shutdown();
    }

    @Test
    public void testGetEcho() {
        String responseMsg = target.path(myPath + "/" + myEchoPath + "/" + "GetEchoTest").request().get(String.class);
        Assert.assertEquals("GetEchoTest", responseMsg);
    }

    @Test
    public void testPostEcho() throws JSONException {
        String json = "[\"b\",\"a\"]";
        String responseMsg = target.path(myPath + "/" + myEchoPath).request().post(null, String.class);
        // 順番が入れ替わっていてもマッチ
        Assert.assertThat(responseMsg, SameJSONAs.sameJSONAs(json).allowingAnyArrayOrdering());
    }
}
