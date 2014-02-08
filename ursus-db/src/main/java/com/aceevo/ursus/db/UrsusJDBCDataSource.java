package com.aceevo.ursus.db;

import com.aceevo.ursus.config.UrsusJDBCConfiguration;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;

public class UrsusJDBCDataSource extends DataSource {

    private final PoolProperties poolProperties = new PoolProperties();

    public UrsusJDBCDataSource(UrsusJDBCConfiguration.Database database) {

        if (database.getDefaultAutoCommit() != null)
            poolProperties.setDefaultAutoCommit(database.getDefaultAutoCommit());
        if (database.getDefaultReadOnly() != null)
            poolProperties.setDefaultReadOnly(database.getDefaultReadOnly());
        if (database.getDefaultCatalog() != null)
            poolProperties.setDefaultCatalog(database.getDefaultCatalog());
        if (database.getDefaultTransactionIsolation() != null)
            poolProperties.setDefaultTransactionIsolation(database.getDefaultTransactionIsolation());
        if (database.getMaxAtive() != null)
            poolProperties.setMaxActive(database.getMaxAtive());
        if (database.getMaxIdle() != null)
            poolProperties.setMaxIdle(database.getMaxIdle());
        if (database.getMaxWait() != null)
            poolProperties.setMaxWait(database.getMaxWait());
        if (database.getInitialSize() != null)
            poolProperties.setInitialSize(database.getInitialSize());
        if (database.getTestOnBorrow() != null)
            poolProperties.setTestOnBorrow(database.getTestOnBorrow());
        if (database.getTestOnReturn() != null)
            poolProperties.setTestOnReturn(database.getTestOnReturn());
        if (database.getTestWhileIdle() != null)
            poolProperties.setTestWhileIdle(database.getTestWhileIdle());
        if (database.getValidationQuery() != null)
            poolProperties.setValidationQuery(database.getValidationQuery());
        if (database.getValidatorClassName() != null)
            poolProperties.setValidatorClassName(database.getValidatorClassName());
        if (database.getTimeBetweenEvictionRunsMillis() != null)
            poolProperties.setTimeBetweenEvictionRunsMillis(database.getTimeBetweenEvictionRunsMillis());
        if (database.getNumTestsPerEvictionRun() != null)
            poolProperties.setNumTestsPerEvictionRun(database.getNumTestsPerEvictionRun());
        if (database.getMinEvictableIdleTimeMillis() != null)
            poolProperties.setMinEvictableIdleTimeMillis(database.getMinEvictableIdleTimeMillis());
        if (database.getAccessToUnderlyingConnectionAllowed() != null)
            poolProperties.setAccessToUnderlyingConnectionAllowed(database.getAccessToUnderlyingConnectionAllowed());
        if (database.getRemoveAbandoned() != null)
            poolProperties.setRemoveAbandoned(database.getRemoveAbandoned());
        if (database.getRemoveAbandonedTimeout() != null)
            poolProperties.setRemoveAbandonedTimeout(database.getRemoveAbandonedTimeout());
        if (database.getLogAbandoned() != null)
            poolProperties.setLogAbandoned(database.getLogAbandoned());
        if (database.getConnectionProperties() != null)
            poolProperties.setConnectionProperties(database.getConnectionProperties());
        if (database.getInitSQL() != null)
            poolProperties.setInitSQL(database.getInitSQL());
        if (database.getJdbcInterceptors() != null)
            poolProperties.setJdbcInterceptors(database.getJdbcInterceptors());
        if (database.getValidationInterval() != null)
            poolProperties.setValidationInterval(database.getValidationInterval());
        if (database.getJmxEnabled() != null)
            poolProperties.setJmxEnabled(true);
        if (database.getFairQueue() != null)
            poolProperties.setFairQueue(database.getFairQueue());
        if (database.getAbandonWhenPercentageFull() != null)
            poolProperties.setAbandonWhenPercentageFull(database.getAbandonWhenPercentageFull());
        if (database.getMaxAge() != null)
            poolProperties.setMaxAge(database.getMaxAge());
        if (database.getUseEquals() != null)
            poolProperties.setUseEquals(database.getUseEquals());
        if (database.getSuspectTimeout() != null)
            poolProperties.setSuspectTimeout(database.getSuspectTimeout());
        if (database.getRollbackOnReturn() != null)
            poolProperties.setRollbackOnReturn(database.getRollbackOnReturn());
        if (database.getCommitOnReturn() != null)
            poolProperties.setCommitOnReturn(database.getCommitOnReturn());
        if (database.getAlternateUsernameAllowed() != null)
            poolProperties.setAlternateUsernameAllowed(database.getAlternateUsernameAllowed());
        if (database.getUseDisposableConnectionFacade() != null)
            poolProperties.setUseDisposableConnectionFacade(database.getUseDisposableConnectionFacade());
        if (database.getLogValidationErrors() != null)
            poolProperties.setLogValidationErrors(database.getLogValidationErrors());
        if (database.getPropagateInterruptState() != null)
            poolProperties.setPropagateInterruptState(database.getPropagateInterruptState());

        poolProperties.setUrl(database.getUrl());
        poolProperties.setDriverClassName(database.getDriverClass());
        poolProperties.setUsername(database.getUser());
        poolProperties.setPassword(database.getPassword());

        setPoolProperties(poolProperties);
    }
}
