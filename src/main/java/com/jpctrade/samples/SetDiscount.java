package com.jpctrade.samples;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import java.io.InputStream;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Arrays;

@Path("samples")
public class SetDiscount {
    @GET
    @Path("setdiscount/{pathParam}")
    @Produces(MediaType.TEXT_PLAIN)
    public String get(@PathParam("pathParam") String pathParam) {
        return pathParam;
    }

    @POST
    @Path("setdiscount")
    @Produces(MediaType.APPLICATION_JSON)
    public List<String> post(InputStream is) throws Exception {
        return Arrays.asList("a", "b");
    }
}
