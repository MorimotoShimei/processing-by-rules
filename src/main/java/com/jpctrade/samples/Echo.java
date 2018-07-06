package com.jpctrade.samples;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import java.io.InputStream;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.MediaType;

@Path("samples")
public class Echo {

    @GET
    @Path("echo/{pathParam}")
    @Produces(MediaType.TEXT_PLAIN)
    public Response get(@PathParam("pathParam") String pathParam) {
        return Response.ok(pathParam).build();
    }

    @POST
    @Path("echo")
    @Produces(MediaType.TEXT_PLAIN)
    public Response post(InputStream is) {
        return Response.ok(is).build();
    }
}
