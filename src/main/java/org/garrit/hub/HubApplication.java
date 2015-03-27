package org.garrit.hub;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

import org.garrit.common.messages.statuses.Status;

/**
 * Main entry point for the hub service.
 *
 * @author Samuel Coleman <samuel@seenet.ca>
 * @since 1.0.0
 */
public class HubApplication extends Application<HubConfiguration>
{
    private Status status;
    private MessageDistributor distributor;

    public static void main(String[] args) throws Exception
    {
        new HubApplication().run(args);
    }

    @Override
    public String getName()
    {
        return HubApplication.class.getName();
    }

    @Override
    public void initialize(Bootstrap<HubConfiguration> bootstrap)
    {
    }

    @Override
    public void run(HubConfiguration config, Environment env) throws Exception
    {
        this.distributor = new MessageDistributor(config.getExecutor(), config.getJudge(), config.getReporter());

        this.status = new Status(config.getName());
        this.status.setCapabilityStatus(this.distributor);

        final StatusResource statusResource = new StatusResource(this.status);
        final ExecuteResource executeResource = new ExecuteResource(this.distributor);
        final JudgeResource judgeResource = new JudgeResource(this.distributor);
        final ReportResource reportResource = new ReportResource(this.distributor);

        env.jersey().register(statusResource);
        env.jersey().register(executeResource);
        env.jersey().register(judgeResource);
        env.jersey().register(reportResource);

        final StatusHealthCheck statusHealthCheck = new StatusHealthCheck(status);

        env.healthChecks().register("status", statusHealthCheck);
    }
}