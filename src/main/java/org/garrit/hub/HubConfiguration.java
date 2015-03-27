package org.garrit.hub;

import io.dropwizard.Configuration;

import java.net.URI;
import java.nio.file.Path;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class HubConfiguration extends Configuration
{
    private String name;
    private URI executor;
    private URI judge;
    private URI reporter;

    private Path idTracker;
}