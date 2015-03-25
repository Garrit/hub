package org.garrit.hub;

import org.garrit.common.statuses.Status;

import com.codahale.metrics.health.HealthCheck;

public class StatusHealthCheck extends HealthCheck
{
    private final Status status;

    public StatusHealthCheck(Status status)
    {
        this.status = status;
    }

    @Override
    protected Result check()
    {
        if (this.status.getCapabilityStatuses().size() == 0)
            return Result.unhealthy("The service is reporting no capabilities");

        return Result.healthy();
    }
}