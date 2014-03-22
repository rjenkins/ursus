package com.aceevo.ursus.config;

import ch.qos.logback.classic.Level;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UrsusConfiguration {

    @JsonProperty
    private Logging logging;

    @JsonProperty
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

        @JsonProperty
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
