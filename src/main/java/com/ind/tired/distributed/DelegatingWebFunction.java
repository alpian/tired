package com.ind.tired.distributed;

import com.ind.tired.web.WebCall;
import com.ind.tired.web.WebRequest;
import com.ind.tired.web.WebResult;

import java.util.function.Function;

public class DelegatingWebFunction implements Function<WebRequest, WebResult> {
    private final Function<WebRequest, WebResult> delegate;
    private final Function<WebCall, WebResult> interceptor;

    public DelegatingWebFunction(
            Function<WebRequest, WebResult> delegate,
            Function<WebCall, WebResult> interceptor)
    {
        this.delegate = delegate;
        this.interceptor = interceptor;
    }

    @Override
    public WebResult apply(WebRequest request) {
        return interceptor.apply(new WebCall(request, delegate.apply(request)));
    }
}
