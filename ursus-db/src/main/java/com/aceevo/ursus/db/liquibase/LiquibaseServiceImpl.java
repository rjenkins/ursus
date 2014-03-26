package com.aceevo.ursus.db.liquibase;


import com.aceevo.ursus.config.UrsusJDBCConfiguration;
import com.aceevo.ursus.db.UrsusJDBCDataSource;
import com.aceevo.ursus.spi.liquibase.LiquibaseService;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ClassLoaderResourceAccessor;

/**
 * Created with IntelliJ IDEA.
 * User: rayjenkins
 * Date: 3/25/14
 * Time: 6:34 PM
 * To change this template use File | Settings | File Templates.
 */
public class LiquibaseServiceImpl implements LiquibaseService {

    private static enum Liquibase_Command {dropall, update}

    @Override
    public void runLiquibaseCommand(UrsusJDBCConfiguration.Database ursusJDBCConfiguration, String command) {
        try {
            UrsusJDBCDataSource ursusJDBCDataSource = new UrsusJDBCDataSource(ursusJDBCConfiguration);
            Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(ursusJDBCDataSource.getConnection()));
            Liquibase liquibase = new Liquibase(ursusJDBCConfiguration.getMigrationFile(), new ClassLoaderResourceAccessor(), database);

            Liquibase_Command liquibaseCommand = Liquibase_Command.valueOf(command.toLowerCase());

            if (liquibaseCommand.equals(Liquibase_Command.dropall))
                liquibase.dropAll();
            else if (liquibaseCommand.equals(Liquibase_Command.update))
                liquibase.update(null);

        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
