package com.aceevo.ursus.config;

import ch.qos.logback.classic.Level;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created with IntelliJ IDEA.
 * User: rayjenkins
 * Date: 1/26/14
 * Time: 5:30 PM
 * To change this template use File | Settings | File Templates.
 */
public class UrsusConfiguration {

    @JsonProperty(required = false)
    private Logging logging;

    @JsonProperty(required = false)
    private UrsusJDBCConfiguration.Database database;

    public Logging getLogging() {
        return logging;
    }

    public void setLogging(Logging logging) {
        this.logging = logging;
    }

    public UrsusJDBCConfiguration.Database getDatabase() {
        return database;
    }

    public void setDatabase(UrsusJDBCConfiguration.Database database) {
        this.database = database;
    }

    public static class Logging {

        @JsonProperty
        private Level level = Level.INFO;

        @JsonProperty(required = false)
        private String fileName;

        public Level getLevel() {
            return level;
        }

        public void setLevel(Level level) {
            this.level = level;
        }

        public String getFileName() {
            return fileName;
        }

        public void setFileName(String fileName) {
            this.fileName = fileName;
        }
    }

}
