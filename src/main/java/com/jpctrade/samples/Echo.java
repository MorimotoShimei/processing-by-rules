package com.jpctrade.samples;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import java.util.List;
import java.util.Arrays;
import java.io.InputStream;
import javax.ws.rs.core.MediaType;

@Path("samples")
public class Echo {

    @GET
    @Path("echo/{pathParam}")
    @Produces(MediaType.TEXT_PLAIN)
    public String get(@PathParam("pathParam") String pathParam) {
        return pathParam;
    }

    @POST
    @Path("echo")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Object> post(InputStream is) throws Exception {
        return Arrays.asList("a","b");
    }
}
