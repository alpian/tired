package com.ind.tired.functional;

import com.typesafe.config.Config;

public class Node {
    private final Config config;
    private final TestSetup testSetup;
    public final RestActions restActions;
    private final Client client;
    private UsernamePassword.Changeable usernamePassword = new UsernamePassword.Changeable("admin", "changeit");

    public Node(Config config) {
        this.config = config;
        testSetup = new TestSetup(config);
        restActions = new RestActions(config);
        client = new Client(
                config.getString("web.host"),
                config.getInt("web.port"),
                usernamePassword,
                new StdoutLog());
    }

    public void start() {
        testSetup.setup();
        client.start();
    }
}
