package com.aceevo.ursus.spi.liquibase;

import com.aceevo.ursus.config.UrsusJDBCConfiguration;

/**
 * Created with IntelliJ IDEA.
 * User: rayjenkins
 * Date: 3/25/14
 * Time: 5:56 PM
 * To change this template use File | Settings | File Templates.
 */
public interface LiquibaseService {

    void runLiquibaseCommand(UrsusJDBCConfiguration.Database ursusJDBCConfiguration, String command);
}
