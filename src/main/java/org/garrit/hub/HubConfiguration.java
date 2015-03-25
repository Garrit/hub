package org.garrit.hub;

import io.dropwizard.Configuration;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class HubConfiguration extends Configuration
{
    private String name;
}