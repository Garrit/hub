package org.garrit.hub;

import java.io.IOException;
import java.net.URI;

import lombok.RequiredArgsConstructor;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.HttpClients;
import org.garrit.common.messages.Execution;
import org.garrit.common.messages.Judgement;
import org.garrit.common.messages.RegisteredSubmission;
import org.garrit.common.messages.statuses.NegotiatorStatus;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Responsible for distributing messages to endpoints.
 *
 * @author Samuel Coleman <samuel@seenet.ca>
 * @since 1.0.0
 */
@RequiredArgsConstructor
public class MessageDistributor implements NegotiatorStatus
{
    private final URI executor;
    private final URI judge;
    private final URI reporter;

    public void distribute(RegisteredSubmission execution)
            throws JsonProcessingException, ClientProtocolException, IOException
    {
        this.distribute(execution, this.executor.resolve("execute"));
    }

    public void distribute(Execution execution)
            throws JsonProcessingException, ClientProtocolException, IOException
    {
        this.distribute(execution, this.judge.resolve("judge"));
    }

    public void distribute(Judgement execution)
            throws JsonProcessingException, ClientProtocolException, IOException
    {
        this.distribute(execution, this.reporter.resolve("report"));
    }

    private void distribute(RegisteredSubmission submission, URI target)
            throws JsonProcessingException, ClientProtocolException, IOException
    {
        ObjectMapper mapper = new ObjectMapper();

        HttpClient client = HttpClients.createDefault();
        HttpPost post = new HttpPost(target);
        HttpEntity body = new ByteArrayEntity(mapper.writeValueAsBytes(submission));
        post.setHeader("Content-Type", "application/json");
        post.setEntity(body);

        client.execute(post);
    }
}