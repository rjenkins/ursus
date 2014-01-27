package com.aceevo.ursus.core;

import com.aceevo.ursus.config.UrsusJerseyApplicationConfiguration;
import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class UrsusApplicationTest {

    private static class TestApplicationConfiguration extends UrsusJerseyApplicationConfiguration {}

    @Test
    public void testCreateApplication() {
        TestUrsusApplication testUrsusApplication = new TestUrsusApplication(new String[]{"server", "src/test/resources/testursusapplication.yml"});
        assertNotNull(testUrsusApplication);
    }


    class TestUrsusApplication extends UrsusJerseyApplication<TestApplicationConfiguration> {
        public TestUrsusApplication(String[] args) {
            super(args);
        }

        @Override
        protected void boostrap(TestApplicationConfiguration testApplicationConfiguration) {
            //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        protected void run(HttpServer httpServer) {
            //To change body of implemented methods use File | Settings | File Templates.
        }
    }

}
