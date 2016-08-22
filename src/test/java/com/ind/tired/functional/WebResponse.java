package com.ind.tired.functional;

import com.ind.tired.util.HttpContentExtraction;
import org.apache.http.HttpResponse;

public class WebResponse {
    public final HttpResponse response;
    public final String content;

    public WebResponse(HttpResponse response, String content) {
        this.response = response;
        this.content = content;
    }

    @Override
    public String toString() {
        return "WebResponse{" +
                "responseSchema=" + response +
                ", content='" + content + '\'' +
                '}';
    }

    public static WebResponse wrap(HttpResponse response) {
        return new WebResponse(response, HttpContentExtraction.contentOf(response));
    }
}
