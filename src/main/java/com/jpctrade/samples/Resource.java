package com.jpctrade.samples;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.Map;
import java.util.Arrays;
import java.util.List;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("samples")
public class Resource {

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, String> post() {
        Map<String, String> map = new HashMap<>();
        map.put("a", "000");
        map.put("b", "111");
        map.put("c", "555");
        return map;
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String get() {
        return "Got it!";
    }

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    @GET
    @Path("myresource")
    @Produces(MediaType.APPLICATION_JSON)
    public String getMyresource() {
        return "Got my resource!";
    }

    @POST
    @Path("myresource")
    @Produces(MediaType.APPLICATION_JSON)
    public List<String> postMyresource() {
        return Arrays.asList("a","b");
    }
}
