package org.garrit.hub;

import java.io.IOException;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.garrit.common.messages.Execution;

/**
 * Receive executions and pass them on to the judge.
 *
 * @author Samuel Coleman <samuel@seenet.ca>
 * @since 1.0.0
 */
@RequiredArgsConstructor
@Path("/judge/{id}")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Slf4j
public class JudgeResource
{
    private final MessageDistributor distributor;

    @POST
    public Response judge(@PathParam("id") int id, Execution execution)
    {
        try
        {
            this.distributor.distribute(execution);
        }
        catch (IOException e)
        {
            log.error("Failed to distribute execution", e);
            return Response.status(Status.INTERNAL_SERVER_ERROR).build();
        }

        return Response.status(Status.OK).build();
    }
}