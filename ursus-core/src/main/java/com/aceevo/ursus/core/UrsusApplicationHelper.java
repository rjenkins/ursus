package com.aceevo.ursus.core;

import ch.qos.logback.core.FileAppender;
import com.aceevo.ursus.config.UrsusConfiguration;
import com.aceevo.ursus.config.UrsusConfigurationFactory;
import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UrsusApplicationHelper<T extends UrsusConfiguration> {

    protected void printBanner(Logger LOGGER, String name) {
        try {
            final String banner = Resources.toString(Resources.getResource("banner.txt"),
                    Charsets.UTF_8)
                    .replace("\n", String.format("%n"));
            LOGGER.info(String.format("Starting {}%n{}"), name, banner);
        } catch (Exception ex) {
            // don't display the banner if there isn't one
            LOGGER.info("Starting {}", name);
        }
    }

    protected void configureLogging(T configuration) {

        // set logging level and file programmatically if defined
        if (configuration.getLogging() != null) {
            ch.qos.logback.classic.Logger rootLogger = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
            FileAppender fileAppender = (FileAppender) rootLogger.getAppender("FILE");

            rootLogger.detachAppender(fileAppender);
            rootLogger.setLevel(configuration.getLogging().getLevel());

            if (configuration.getLogging().getFileName() != null) {
                fileAppender.setFile(configuration.getLogging().getFileName());
            }
            rootLogger.addAppender(fileAppender);
            fileAppender.start();
        }
    }

    /**
     * Determine our default YAML configuration file name and parse
     *
     * @return and instance of type T which extends {@link com.aceevo.ursus.config.UrsusJerseyApplicationConfiguration}
     */
    protected T parseConfiguration(String configurationFile, Class configurationClass) {

        //Fetch Server Configuration
        UrsusConfigurationFactory ursusConfigurationFactory = new UrsusConfigurationFactory(configurationFile, configurationClass);
        return (T) ursusConfigurationFactory.getConfiguration();
    }

}
