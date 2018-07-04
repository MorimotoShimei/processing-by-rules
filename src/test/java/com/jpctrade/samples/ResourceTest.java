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

public class ResourceTest {

    private HttpServer server;
    private WebTarget target;
    static final String myPath = "samples";
    static final String mySubPath = "myresource";

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
    public void testPost() throws JSONException {
        String json = "{\"a\":\"000\",\"b\":\"111\"}";
        String responseMsg = target.path(myPath).request().post(null, String.class);
        // 余計な要素があってもマッチ
        Assert.assertThat(responseMsg, SameJSONAs.sameJSONAs(json).allowingExtraUnexpectedFields());
    }

    @Test
    public void testGet() {
        String responseMsg = target.path(myPath).request().get(String.class);
        Assert.assertEquals("Got it!", responseMsg);
    }

    /**
     * Test to see that the message "Got it!" is sent in the response.
     */
    @Test
    public void testGetMyresource() {
        String responseMsg = target.path(myPath + "/" + mySubPath).request().get(String.class);
        Assert.assertEquals("Got my resource!", responseMsg);
    }

    @Test
    public void testPostMyresource() throws JSONException {
        String json = "[\"b\",\"a\"]";
        String responseMsg = target.path(myPath + "/" + mySubPath).request().post(null, String.class);
        // 順番が入れ替わっていてもマッチ
        Assert.assertThat(responseMsg, SameJSONAs.sameJSONAs(json).allowingAnyArrayOrdering());
    }
}
