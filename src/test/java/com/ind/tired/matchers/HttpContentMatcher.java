package com.ind.tired.matchers;

import com.ind.tired.functional.WebResponse;
import org.hamcrest.FeatureMatcher;
import org.hamcrest.Matchers;


public class HttpContentMatcher extends FeatureMatcher<WebResponse, String> {
    public HttpContentMatcher(String content) {
        super(Matchers.equalTo(content), "HTTP Content", "content");
    }

    @Override
    protected String featureValueOf(WebResponse actual) {
        return actual.content;
    }

    public static HttpContentMatcher hasContent(String content) {
        return new HttpContentMatcher(content);
    }
}
