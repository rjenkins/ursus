package com.aceevo.ursus.core;

import com.aceevo.ursus.config.UrsusApplicationConfiguration;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

public class UrsusApplicationBinder extends AbstractBinder {

    private final UrsusApplicationConfiguration ursusApplicationConfiguration;

    public UrsusApplicationBinder(UrsusApplicationConfiguration ursusApplicationConfiguration) {
        this.ursusApplicationConfiguration = ursusApplicationConfiguration;
    }

    @Override
    protected void configure() {
        bind(ursusApplicationConfiguration);
    }
}