package org.garrit.hub;

import java.io.IOException;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.garrit.common.messages.RegisteredSubmission;
import org.garrit.common.messages.Submission;

/**
 * Receive submissions and pass them on to the executor.
 *
 * @author Samuel Coleman <samuel@seenet.ca>
 * @since 1.0.0
 */
@RequiredArgsConstructor
@Path("/execute")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Slf4j
public class ExecuteResource
{
    private final MessageDistributor distributor;
    private final IdGenerator idGenerator;

    @POST
    public Response judge(Submission submission)
    {
        RegisteredSubmission registeredSubmission = new RegisteredSubmission(submission);
        registeredSubmission.setId(this.idGenerator.getId(submission));

        try
        {
            this.distributor.distribute(registeredSubmission);
        }
        catch (IOException e)
        {
            log.error("Failed to distribute submission", e);
            return Response.status(Status.INTERNAL_SERVER_ERROR).build();
        }

        return Response.status(Status.OK).entity(registeredSubmission).build();
    }
}