package com.ind.tired.functional;

import com.ind.tired.client.Client;
import com.ind.tired.client.UsernamePassword;
import com.ind.tired.util.StdoutLog;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import com.typesafe.config.ConfigValueFactory;
import org.apache.http.HttpStatus;
import org.junit.After;
import org.junit.Before;

import java.io.File;
import java.math.BigDecimal;

import static com.ind.tired.matchers.HttpStatusMatcher.hasStatus;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.anyOf;

public class FunctionalBase {
    private Config config;
    protected TestSetup testSetup;
    protected RestActions restActions;
    protected Client client;
    private UsernamePassword.Changeable usernamePassword = new UsernamePassword.Changeable("admin", "changeit");

    public static BigDecimal d(String s) {
        return new BigDecimal(s);
    }

    public void assertSuccess(WebResponse... responses) {
        for (WebResponse response : responses) {
            assertThat(response, anyOf(hasStatus(HttpStatus.SC_OK), hasStatus(HttpStatus.SC_CREATED)));
        }
    }

    public void assertStatusEqualsNotFound(WebResponse response) {
        assertThat(response, hasStatus(HttpStatus.SC_NOT_FOUND));
    }

    @Before
    public void
    setup() throws Exception {
        final String configPropertiesFilename = System.getProperty("config.properties");
        final Config defaultConfig;
        if (configPropertiesFilename != null) {
            defaultConfig = ConfigFactory.parseFile(new File(configPropertiesFilename)).withFallback(ConfigFactory.defaultApplication());
        } else {
            defaultConfig = ConfigFactory.defaultApplication();
        }
        config = defaultConfig.withValue("web.port", ConfigValueFactory.fromAnyRef("3333"));

        testSetup = new TestSetup(config);
        testSetup.setup();
        restActions = new RestActions(config);
        client = new Client(
                config.getString("web.host"),
                config.getInt("web.port"),
                usernamePassword,
                new StdoutLog()).start();
    }

    @After
    public void
    tearDown() throws Exception {
        testSetup.tearDown();
    }
}