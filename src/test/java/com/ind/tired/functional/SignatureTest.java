package com.ind.tired.functional;

import com.ind.tired.web.Method;
import com.ind.tired.web.Signature;
import org.junit.Test;

import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class SignatureTest {
    @Test public void
    uri_parameters() {
        assertTrue(Signature.of(Method.GET, "/terminal/${terminalId}").matches(Method.GET, "/terminal/123456", null));

        final Signature signature = Signature.of(Method.GET, "/much/${more}/${complicated}/url");

        final String url = "/much/morevar/complicatedvar/url";
        assertTrue(signature.matches(Method.GET, url, null));
        assertTrue(signature.parameters(url).get("more").equals("morevar"));
        assertTrue(signature.parameters(url).get("complicated").equals("complicatedvar"));
    }

    @Test public void
    query_parameters() {
        final Signature signature = Signature.of(Method.GET, "/terminal/${terminalId}?[paramKey=${paramValue}]");
        assertFalse(signature.matches(Method.GET, "/terminal/123456", null));
        assertTrue(signature.matches(Method.GET, "/terminal/123456", "paramKey=theKey"));
        assertFalse(signature.matches(Method.GET, "/terminal/123456", "notParamKey=theKey"));
        assertTrue(signature.parameters("/terminal/123456").get("terminalId").equals("123456"));
    }

    @Test public void
    multiple_query_parameters() {
        final Signature signature = Signature.of(Method.GET, "/terminal/${terminalId}?[paramKey=${paramValue}]&[a=${b}]");
        assertFalse(signature.matches(Method.GET, "/terminal/123456", "paramKey=theKey"));
        assertFalse(signature.matches(Method.GET, "/terminal/123456", "notParamKey=theKey"));
        assertFalse(signature.matches(Method.GET, "/terminal/123456", "a=b"));
        assertFalse(signature.matches(Method.GET, "/terminal/123456", "something=whatever"));
        assertTrue(signature.matches(Method.GET, "/terminal/123456", "a=b&paramKey=whatever"));
        assertTrue(signature.matches(Method.GET, "/terminal/123456", "a=b&paramKey=whatever&somethingElse=doesntmatter"));
    }

    @Test public void
    partial_matches() {
        final Signature signature = Signature.of(Method.GET, "/terminal/somethingElse${terminalId}");
        assertThat(signature.parameters("/terminal/somethingElse123456").get("terminalId"), equalTo("123456"));
    }

    @Test public void
    multiple_partial_matches() {
        final Signature signature = Signature.of(Method.GET, "/terminal/somethingElse${terminalId}/whatever-${thing}");
        final Map<String, String> parameters = signature.parameters("/terminal/somethingElse123456/whatever-98765");
        assertThat(parameters.get("terminalId"), equalTo("123456"));
        assertThat(parameters.get("thing"), equalTo("98765"));
    }
}
