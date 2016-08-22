package com.ind.tired.web;

public class WebCall {
    public final WebRequest request;
    public final WebResult result;

    public WebCall(WebRequest request, WebResult result) {
        this.request = request;
        this.result = result;
    }
}
