package com.ind.tired.functional;

import com.ind.data.DataAccess;
import com.ind.tired.util.Maths;
import com.ind.tired.web.Routes;
import com.ind.tired.web.Uri;
import com.ind.tired.web.WebServer;
import com.typesafe.config.Config;

import java.math.BigDecimal;

public class TestSetup {
    private WebServer webServer;
    public final Config config;
    public DataAccess dataAccess;

    public TestSetup(Config config) {
        this.config = config;
    }

    public void setup() {
        System.setProperty("log.directory", "logs");

        final String version = "test-version";
        dataAccess = new DataAccess(config);
        new Migrator(config).process(dataAccess);

        final Uri uri = new Uri(config.getString("web.host"), config.getInt("web.port"));

        webServer = new WebServer(version,config, uri, new Routes());
        webServer.start();

    }

    public void tearDown() throws Exception {
        webServer.stop();
    }
}
