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

public class SetDiscountTest {

    private HttpServer server;
    private WebTarget target;
    static final String myPath = "samples";
    static final String mySubPath = "setdiscount";

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
        String json = "[\"b\",\"a\"]";
        String responseMsg = target.path(myPath + "/" + mySubPath).request().post(null, String.class);
        // 余計な要素があってもマッチ
        // Assert.assertThat(responseMsg, SameJSONAs.sameJSONAs(json).allowingExtraUnexpectedFields());
        Assert.assertThat(responseMsg, SameJSONAs.sameJSONAs(json).allowingAnyArrayOrdering());
    }

    @Test
    public void testGet() {
        String responseMsg = target.path(myPath + "/" + mySubPath + "/" + "test").request().get(String.class);
        Assert.assertEquals("test", responseMsg);
    }
}
