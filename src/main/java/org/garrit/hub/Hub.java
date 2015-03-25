package org.garrit.hub;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

import org.garrit.common.statuses.Status;

/**
 * Main entry point for the hub service.
 *
 * @author Samuel Coleman <samuel@seenet.ca>
 * @since 1.0.0
 */
public class Hub extends Application<HubConfiguration>
{
    private Status status;

    public static void main(String[] args) throws Exception
    {
        new Hub().run(args);
    }

    @Override
    public String getName()
    {
        return Hub.class.getName();
    }

    @Override
    public void initialize(Bootstrap<HubConfiguration> bootstrap)
    {
    }

    @Override
    public void run(HubConfiguration config, Environment env) throws Exception
    {
        this.status = new Status(config.getName());
        final StatusResource statusResource = new StatusResource(this.status);

        env.jersey().register(statusResource);

        final StatusHealthCheck statusHealthCheck = new StatusHealthCheck(status);

        env.healthChecks().register("status", statusHealthCheck);
    }
}