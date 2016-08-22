package com.ind.tired.distributed;

import com.ind.tired.functional.Node;
import com.ind.tired.functional.WebResponse;
import com.ind.model.Currency;
import com.ind.model.RateSet;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;

import static com.ind.tired.web.json.Decoders.decode;
import static com.ind.tired.web.json.util.JsonMiner.read;
import static com.typesafe.config.ConfigValueFactory.fromAnyRef;
import static java.lang.String.format;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class DistributionTest {
    private final Config baseConfig = ConfigFactory.defaultApplication();

    @Ignore
    @Test public void
    posting_a_new_terminal_owner_gets_put_to_the_slave() throws IOException {
        final Node master = node("3333");
        final Node slave1 = node("3334");
        final Node slave2 = node("3335");
        final Node slave3 = node("3336");

        final RateSet posted = restore(master.restActions.postRateSet("my-rate-set", Currency.EUR));

        assertThat(getRateSet(slave1, posted), equalTo(posted));
        assertThat(getRateSet(slave2, posted), equalTo(posted));
        assertThat(getRateSet(slave3, posted), equalTo(posted));
    }

    private WebResponse getRateSet(Node node, RateSet posted) throws IOException {
        return node.restActions.getRateSet(posted.id);
    }

    private RateSet restore(WebResponse response) throws IOException {
        return decode(RateSet.class, read(response.content));
    }

    private Node node(String port) {
        final Node node = new Node(baseConfig
                .withValue("web.port", fromAnyRef(port)));
        node.start();
        return node;
    }
}
