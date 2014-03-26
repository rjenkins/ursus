package com.aceevo.ursus.config;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class UrsusJDBCConfiguration {

    public static class Database {

        @JsonProperty
        private String migrationFile;

        // The default auto-commit state of connections created by this pool. If not set, default is JDBC driver
        // default (If not set then the setAutoCommit method will not be called.)

        @JsonProperty
        private Boolean defaultAutoCommit;

        // The default read-only state of connections created by this pool. If not set then the setReadOnly method will
        // not be called. (Some drivers don't support read only mode, ex: Informix)
        @JsonProperty
        private Boolean defaultReadOnly;

        // The default TransactionIsolation state of connections created by this pool. Defined in java.sql.Connection

        // int TRANSACTION_NONE             = 0;
        // int TRANSACTION_READ_UNCOMMITTED = 1;
        // int TRANSACTION_READ_COMMITTED   = 2;
        // int TRANSACTION_REPEATABLE_READ  = 4;
        // int TRANSACTION_SERIALIZABLE     = 8;

        // If not set, the method will not be called and it defaults to the JDBC driver.
        @JsonProperty
        private Integer defaultTransactionIsolation;

        // The default catalog of connections created by this pool.
        @JsonProperty
        private String defaultCatalog;

        @Valid
        @JsonProperty
        @NotNull
        private String driverClass;

        @Valid
        @JsonProperty
        @NotNull
        private String user;

        @Valid
        @JsonProperty
        @NotNull
        private String password;

        @Valid
        @JsonProperty
        @NotNull
        private String url;

        // The maximum number of active connections that can be allocated from this pool at the same time. The default value is 100
        @JsonProperty
        private Integer maxAtive;

        // The maximum number of connections that should be kept in the pool at all times. Default value is maxActive:100 Idle
        // connections are checked periodically (if enabled) and connections that been idle for longer than minEvictableIdleTimeMillis
        // will be released. (also see testWhileIdle)
        @JsonProperty
        private Integer maxIdle;

        // The minimum number of established connections that should be kept in the pool at all times.
        // The connection pool can shrink below this number if validation queries fail.
        // Default value is derived from initialSize:10 (also see testWhileIdle)
        @JsonProperty
        private Integer minIdle;

        // The initial number of connections that are created when the pool is started. Default value is 10
        @JsonProperty
        private Integer initialSize;

        // The maximum number of milliseconds that the pool will wait (when there are no available connections)
        // for a connection to be returned before throwing an exception. Default value is 30000 (30 seconds)
        @JsonProperty
        private Integer maxWait;

        // The indication of whether objects will be validated before being borrowed from the pool.
        // If the object fails to validate, it will be dropped from the pool, and we will attempt to borrow another.
        // NOTE - for a true value to have any effect, the validationQuery parameter must be set to a non-null string.
        // In order to have a more efficient validation, see validationInterval. Default value is false
        @JsonProperty
        private Boolean testOnBorrow;

        // The indication of whether objects will be validated before being returned to the pool.
        // NOTE - for a true value to have any effect, the validationQuery parameter must be set to a non-null string.
        // The default value is false.
        private Boolean testOnReturn;

        // The indication of whether objects will be validated by the idle object evictor (if any).
        // If an object fails to validate, it will be dropped from the pool. NOTE - for a true value to have any effect,
        // the validationQuery parameter must be set to a non-null string.
        // The default value is false and this property has to be set in order for the pool cleaner/test thread is to
        // run (also see timeBetweenEvictionRunsMillis)
        @JsonProperty
        private Boolean testWhileIdle;

        // The SQL query that will be used to validate connections from this pool before returning them to the caller.
        // If specified, this query does not have to return any data, it just can't throw a SQLException.
        // The default value is null. Example values are SELECT 1(mysql), select 1 from dual(oracle), SELECT 1(MS Sql Server)
        @JsonProperty
        private String validationQuery;

        // The name of a class which implements the org.apache.tomcat.jdbc.pool.Validator interface and provides
        // a no-arg constructor (may be implicit). If specified, the class will be used to create a Validator instance
        // which is then used instead of any validation query to validate connections.
        // The default value is null. An example value is com.mycompany.project.SimpleValidator.
        @JsonProperty
        private String validatorClassName;

        // The number of milliseconds to sleep between runs of the idle connection validation/cleaner thread.
        // This value should not be set under 1 second. It dictates how often we check for idle, abandoned connections,
        // and how often we validate idle connections. The default value is 5000 (5 seconds).
        @JsonProperty
        private Integer timeBetweenEvictionRunsMillis;

        // Property not used in tomcat-jdbc-pool.
        @JsonProperty
        private Integer numTestsPerEvictionRun;

        // The minimum amount of time an object may sit idle in the pool before it is eligible for eviction.
        // The default value is 60000 (60 seconds).
        @JsonProperty
        private Integer minEvictableIdleTimeMillis;

        // Property not used.
        // Access can be achieved by calling unwrap on the pooled connection. see javax.sql.DataSource interface, or
        // call getConnection through reflection or cast the object as javax.sql.PooledConnection
        @JsonProperty
        private Boolean accessToUnderlyingConnectionAllowed;

        // Flag to remove abandoned connections if they exceed the removeAbandonedTimeout.
        // If set to true a connection is considered abandoned and eligible for removal if it has been in use longer
        // than the removeAbandonedTimeout Setting this to true can recover db connections from applications that fail
        // to close a connection. See also logAbandoned The default value is false.
        @JsonProperty
        private Boolean removeAbandoned;

        // Timeout in seconds before an abandoned(in use) connection can be removed. The default value is 60 (60 seconds).
        // The value should be set to the longest running query your applications might have.
        @JsonProperty
        private Integer removeAbandonedTimeout;

        // Flag to log stack traces for application code which abandoned a Connection.
        // Logging of abandoned Connections adds overhead for every Connection borrow because a stack trace has to be generated.
        // The default value is false.
        @JsonProperty
        private Boolean logAbandoned;

        // The connection properties that will be sent to our JDBC driver when establishing new connections.
        // Format of the string must be [propertyName=property;]* NOTE - The "user" and "password" properties will be
        // passed explicitly, so they do not need to be included here. The default value is null.
        @JsonProperty
        private String connectionProperties;

        // A custom query to be run when a connection is first created. The default value is null.
        @JsonProperty
        private String initSQL;

        // A semicolon separated list of classnames extending org.apache.tomcat.jdbc.pool.JdbcInterceptor class.
        // See Configuring JDBC interceptors below for more detailed description of syntaz and examples.
        //  These interceptors will be inserted as an interceptor into the chain of operations on a java.sql.Connection object.
        // The default value is null.

        // Predefined interceptors:
        // org.apache.tomcat.jdbc.pool.interceptor.
        //      ConnectionState - keeps track of auto commit, read only, catalog and transaction isolation level.
        // org.apache.tomcat.jdbc.pool.interceptor.
        //      StatementFinalizer - keeps track of opened statements, and closes them when the connection is returned to the pool.

        // More predefined interceptors are described in detail in the JDBC Interceptors section.
        @JsonProperty
        private String jdbcInterceptors;

        // Avoid excess validation, only run validation at most at this frequency - time in milliseconds.
        // If a connection is due for validation, but has been validated previously within this interval,
        // it will not be validated again. The default value is 30000 (30 seconds).
        @JsonProperty
        private Long validationInterval;

        // Register the pool with JMX or not. The default value is true.
        @JsonProperty
        private Boolean jmxEnabled;

        // Set to true if you wish that calls to getConnection should be treated fairly in a true FIFO fashion.
        // This uses the org.apache.tomcat.jdbc.pool.FairBlockingQueue implementation for the list of the idle connections.
        // The default value is true. This flag is required when you want to use asynchronous connection retrieval.
        // Setting this flag ensures that threads receive connections in the order they arrive.
        // During performance tests, there is a very large difference in how locks and lock waiting is implemented.

        // When fairQueue=true there is a decision making process based on what operating system the system is running.
        // If the system is running on Linux (property os.name=Linux. To disable this Linux specific behavior and still
        // use the fair queue, simply add the property org.apache.tomcat.jdbc.pool.FairBlockingQueue.ignoreOS=true to
        // your system properties before the connection pool classes are loaded.
        @JsonProperty
        private Boolean fairQueue;

        // Connections that have been abandoned (timed out) wont get closed and reported up unless the number of
        // connections in use are above the percentage defined by abandonWhenPercentageFull.
        // The value should be between 0-100. The default value is 0, which implies that connections are eligible for
        // closure as soon as removeAbandonedTimeout has been reached.
        @JsonProperty
        private Integer abandonWhenPercentageFull;

        // Time in milliseconds to keep this connection. When a connection is returned to the pool, the pool will check
        // to see if the now - time-when-connected > maxAge has been reached, and if so, it closes the connection rather
        // than returning it to the pool. The default value is 0, which implies that connections will be left open and
        // no age check will be done upon returning the connection to the pool.
        @JsonProperty
        private Long maxAge;

        // Set to true if you wish the ProxyConnection class to use String.equals and set to false when you wish to use
        // == when comparing method names. This property does not apply to added interceptors as those are
        // configured individually. The default value is true.
        @JsonProperty
        private Boolean useEquals;

        // Timeout value in seconds. Default value is 0. Similar to to the removeAbandonedTimeout value but instead of
        // treating the connection as abandoned, and potentially closing the connection, this simply logs the warning
        // if logAbandoned is set to true. If this value is equal or less than 0, no suspect checking will be performed.
        // Suspect checking only takes place if the timeout value is larger than 0 and the connection was not abandoned
        // or if abandon check is disabled. If a connection is suspect a WARN message gets logged and a
        // JMX notification gets sent once.
        @JsonProperty
        private Integer suspectTimeout;

        // If autoCommit==false then the pool can terminate the transaction by calling rollback on the connection as it
        // is returned to the pool Default value is false.
        @JsonProperty
        private Boolean rollbackOnReturn;

        // If autoCommit==false then the pool can complete the transaction by calling commit on the connection as it is
        // returned to the pool If rollbackOnReturn==true then this attribute is ignored. Default value is false.
        @JsonProperty
        private Boolean commitOnReturn;

        // By default, the jdbc-pool will ignore the DataSource.getConnection(username,password) call, and simply
        // return a previously pooled connection under the globally configured properties username and password,
        // for performance reasons.

        // The pool can however be configured to allow use of different credentials each time a connection is requested.
        // To enable the functionality described in the DataSource.getConnection(username,password) call, simply set
        // the property alternateUsernameAllowed to true.
        // Should you request a connection with the credentials user1/password1 and the connection was previously
        // connected using different user2/password2, the connection will be closed, and reopened with the requested
        // credentials. This way, the pool size is still managed on a global level, and not on a per schema level.

        // The default value is false.
        // This property was added as an enhancement to bug 50025.
        @JsonProperty
        private Boolean alternateUsernameAllowed;

        // Set this to true if you wish to put a facade on your connection so that it cannot be reused after it has
        // been closed. This prevents a thread holding on to a reference of a connection it has already called closed on,
        // to execute queries on it. Default value is true.
        @JsonProperty
        private Boolean useDisposableConnectionFacade;

        // Set this to true to log errors during the validation phase to the log file. If set to true, errors will be
        // logged as SEVERE. Default value is false for backwards compatibility.
        @JsonProperty
        private Boolean logValidationErrors;

        // Set this to true to propagate the interrupt state for a thread that has been interrupted
        // (not clearing the interrupt state). Default value is false for backwards compatibility.
        @JsonProperty
        private Boolean propagateInterruptState;

        public String getDriverClass() {
            return driverClass;
        }

        public void setDriverClass(String driverClass) {
            this.driverClass = driverClass;
        }

        public String getUser() {
            return user;
        }

        public void setUser(String user) {
            this.user = user;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public Boolean getDefaultAutoCommit() {
            return defaultAutoCommit;
        }

        public void setDefaultAutoCommit(Boolean defaultAutoCommit) {
            this.defaultAutoCommit = defaultAutoCommit;
        }

        public Boolean getDefaultReadOnly() {
            return defaultReadOnly;
        }

        public void setDefaultReadOnly(Boolean defaultReadOnly) {
            this.defaultReadOnly = defaultReadOnly;
        }

        public Integer getDefaultTransactionIsolation() {
            return defaultTransactionIsolation;
        }

        public void setDefaultTransactionIsolation(Integer defaultTransactionIsolation) {
            this.defaultTransactionIsolation = defaultTransactionIsolation;
        }

        public String getDefaultCatalog() {
            return defaultCatalog;
        }

        public void setDefaultCatalog(String defaultCatalog) {
            this.defaultCatalog = defaultCatalog;
        }

        public Integer getMaxAtive() {
            return maxAtive;
        }

        public void setMaxAtive(Integer maxAtive) {
            this.maxAtive = maxAtive;
        }

        public Integer getMaxIdle() {
            return maxIdle;
        }

        public void setMaxIdle(Integer maxIdle) {
            this.maxIdle = maxIdle;
        }

        public Integer getMinIdle() {
            return minIdle;
        }

        public void setMinIdle(Integer minIdle) {
            this.minIdle = minIdle;
        }

        public Integer getInitialSize() {
            return initialSize;
        }

        public void setInitialSize(Integer initialSize) {
            this.initialSize = initialSize;
        }

        public Integer getMaxWait() {
            return maxWait;
        }

        public void setMaxWait(Integer maxWait) {
            this.maxWait = maxWait;
        }

        public Boolean getTestOnBorrow() {
            return testOnBorrow;
        }

        public void setTestOnBorrow(Boolean testOnBorrow) {
            this.testOnBorrow = testOnBorrow;
        }

        public Boolean getTestOnReturn() {
            return testOnReturn;
        }

        public void setTestOnReturn(Boolean testOnReturn) {
            this.testOnReturn = testOnReturn;
        }

        public Boolean getTestWhileIdle() {
            return testWhileIdle;
        }

        public void setTestWhileIdle(Boolean testWhileIdle) {
            this.testWhileIdle = testWhileIdle;
        }

        public String getValidationQuery() {
            return validationQuery;
        }

        public void setValidationQuery(String validationQuery) {
            this.validationQuery = validationQuery;
        }

        public String getValidatorClassName() {
            return validatorClassName;
        }

        public void setValidatorClassName(String validatorClassName) {
            this.validatorClassName = validatorClassName;
        }

        public Integer getTimeBetweenEvictionRunsMillis() {
            return timeBetweenEvictionRunsMillis;
        }

        public void setTimeBetweenEvictionRunsMillis(Integer timeBetweenEvictionRunsMillis) {
            this.timeBetweenEvictionRunsMillis = timeBetweenEvictionRunsMillis;
        }

        public Integer getNumTestsPerEvictionRun() {
            return numTestsPerEvictionRun;
        }

        public void setNumTestsPerEvictionRun(Integer numTestsPerEvictionRun) {
            this.numTestsPerEvictionRun = numTestsPerEvictionRun;
        }

        public Integer getMinEvictableIdleTimeMillis() {
            return minEvictableIdleTimeMillis;
        }

        public void setMinEvictableIdleTimeMillis(Integer minEvictableIdleTimeMillis) {
            this.minEvictableIdleTimeMillis = minEvictableIdleTimeMillis;
        }

        public Boolean getAccessToUnderlyingConnectionAllowed() {
            return accessToUnderlyingConnectionAllowed;
        }

        public void setAccessToUnderlyingConnectionAllowed(Boolean accessToUnderlyingConnectionAllowed) {
            this.accessToUnderlyingConnectionAllowed = accessToUnderlyingConnectionAllowed;
        }

        public Boolean getRemoveAbandoned() {
            return removeAbandoned;
        }

        public void setRemoveAbandoned(Boolean removeAbandoned) {
            this.removeAbandoned = removeAbandoned;
        }

        public Integer getRemoveAbandonedTimeout() {
            return removeAbandonedTimeout;
        }

        public void setRemoveAbandonedTimeout(Integer removeAbandonedTimeout) {
            this.removeAbandonedTimeout = removeAbandonedTimeout;
        }

        public Boolean getLogAbandoned() {
            return logAbandoned;
        }

        public void setLogAbandoned(Boolean logAbandoned) {
            this.logAbandoned = logAbandoned;
        }

        public String getConnectionProperties() {
            return connectionProperties;
        }

        public void setConnectionProperties(String connectionProperties) {
            this.connectionProperties = connectionProperties;
        }

        public String getInitSQL() {
            return initSQL;
        }

        public void setInitSQL(String initSQL) {
            this.initSQL = initSQL;
        }

        public String getJdbcInterceptors() {
            return jdbcInterceptors;
        }

        public void setJdbcInterceptors(String jdbcInterceptors) {
            this.jdbcInterceptors = jdbcInterceptors;
        }

        public Long getValidationInterval() {
            return validationInterval;
        }

        public void setValidationInterval(Long validationInterval) {
            this.validationInterval = validationInterval;
        }

        public Boolean getJmxEnabled() {
            return jmxEnabled;
        }

        public void setJmxEnabled(Boolean jmxEnabled) {
            this.jmxEnabled = jmxEnabled;
        }

        public Boolean getFairQueue() {
            return fairQueue;
        }

        public void setFairQueue(Boolean fairQueue) {
            this.fairQueue = fairQueue;
        }

        public Integer getAbandonWhenPercentageFull() {
            return abandonWhenPercentageFull;
        }

        public void setAbandonWhenPercentageFull(Integer abandonWhenPercentageFull) {
            this.abandonWhenPercentageFull = abandonWhenPercentageFull;
        }

        public Long getMaxAge() {
            return maxAge;
        }

        public void setMaxAge(Long maxAge) {
            this.maxAge = maxAge;
        }

        public Boolean getUseEquals() {
            return useEquals;
        }

        public void setUseEquals(Boolean useEquals) {
            this.useEquals = useEquals;
        }

        public Integer getSuspectTimeout() {
            return suspectTimeout;
        }

        public void setSuspectTimeout(Integer suspectTimeout) {
            this.suspectTimeout = suspectTimeout;
        }

        public Boolean getRollbackOnReturn() {
            return rollbackOnReturn;
        }

        public void setRollbackOnReturn(Boolean rollbackOnReturn) {
            this.rollbackOnReturn = rollbackOnReturn;
        }

        public Boolean getCommitOnReturn() {
            return commitOnReturn;
        }

        public void setCommitOnReturn(Boolean commitOnReturn) {
            this.commitOnReturn = commitOnReturn;
        }

        public Boolean getAlternateUsernameAllowed() {
            return alternateUsernameAllowed;
        }

        public void setAlternateUsernameAllowed(Boolean alternateUsernameAllowed) {
            this.alternateUsernameAllowed = alternateUsernameAllowed;
        }

        public Boolean getUseDisposableConnectionFacade() {
            return useDisposableConnectionFacade;
        }

        public void setUseDisposableConnectionFacade(Boolean useDisposableConnectionFacade) {
            this.useDisposableConnectionFacade = useDisposableConnectionFacade;
        }

        public Boolean getLogValidationErrors() {
            return logValidationErrors;
        }

        public void setLogValidationErrors(Boolean logValidationErrors) {
            this.logValidationErrors = logValidationErrors;
        }

        public Boolean getPropagateInterruptState() {
            return propagateInterruptState;
        }

        public void setPropagateInterruptState(Boolean propagateInterruptState) {
            this.propagateInterruptState = propagateInterruptState;
        }

        public String getMigrationFile() {
            return migrationFile;
        }

        public void setMigrationFile(String migrationFile) {
            this.migrationFile = migrationFile;
        }
    }
}
