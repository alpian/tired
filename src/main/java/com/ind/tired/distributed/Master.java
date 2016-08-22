package com.ind.tired.distributed;

import com.ind.tired.web.WebCall;
import com.ind.tired.web.WebResult;

import java.util.Set;
import java.util.function.Function;

public class Master implements Function<WebCall, WebResult> {
    private final Set<Slave> slaves;

    public Master(Set<Slave> slaves) {
        this.slaves = slaves;
    }

    public WebResult apply(WebCall webCall) {
        return null;
    }
}
