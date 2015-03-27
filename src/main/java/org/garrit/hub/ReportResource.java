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

import org.garrit.common.messages.Judgement;

/**
 * Receive judgements and pass them on to the reporter.
 *
 * @author Samuel Coleman <samuel@seenet.ca>
 * @since 1.0.0
 */
@RequiredArgsConstructor
@Path("/report/{id}")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Slf4j
public class ReportResource
{
    private final MessageDistributor distributor;

    @POST
    public Response report(@PathParam("id") int id, Judgement judgement)
    {
        try
        {
            this.distributor.distribute(judgement);
        }
        catch (IOException e)
        {
            log.error("Failed to distribute judgement", e);
            return Response.status(Status.INTERNAL_SERVER_ERROR).build();
        }

        return Response.status(Status.OK).build();
    }
}