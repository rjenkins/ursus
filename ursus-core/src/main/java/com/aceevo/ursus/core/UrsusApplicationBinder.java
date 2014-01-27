package com.aceevo.ursus.core;

import com.aceevo.ursus.config.UrsusJerseyApplicationConfiguration;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

public class UrsusApplicationBinder extends AbstractBinder {

    private final UrsusJerseyApplicationConfiguration ursusApplicationConfiguration;

    public UrsusApplicationBinder(UrsusJerseyApplicationConfiguration ursusApplicationConfiguration) {
        this.ursusApplicationConfiguration = ursusApplicationConfiguration;
    }

    @Override
    protected void configure() {
        bind(ursusApplicationConfiguration);
    }
}