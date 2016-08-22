package com.ind.tired.web.documentation;

import com.ind.tired.web.html.HtmlWriter;

public class JsonRequestResponse {
    public static Describable none() {
        return new None();
    }

    public static Describable requestResponseSchema(String request, String response) {
        return new Both(new JsonSchema(request), new JsonSchema(response));
    }

    public static Describable requestSchema(String request) {
        return new RequestOnly(new JsonSchema(request));
    }

    public static Describable responseSchema(String response) {
        return new ResponseOnly(new JsonSchema(response));
    }

    public static class Both implements Describable {
        private final JsonSchema request;
        private final JsonSchema response;

        public Both(JsonSchema request, JsonSchema response) {
            this.request = request;
            this.response = response;
        }

        @Override
        public void describeTo(HtmlWriter h) {
            h.h2("Request and responseSchema content");
            h.h3("JSON Input");
            request.describeTo(h);
            h.h3("JSON Output");
            response.describeTo(h);
        }
    }

    public static class RequestOnly implements Describable {
        private final JsonSchema request;

        public RequestOnly(JsonSchema request) {
            this.request = request;
        }

        @Override
        public void describeTo(HtmlWriter h) {
            h.h3("JSON Input");
            request.describeTo(h);
            h.h3("<< No JSON Output >>");
        }
    }

    public static class ResponseOnly implements Describable {
        private final JsonSchema response;

        public ResponseOnly(JsonSchema response) {
            this.response = response;
        }

        @Override
        public void describeTo(HtmlWriter h) {
            h.h3("<< No JSON Input >>");
            h.h3("JSON Output");
            response.describeTo(h);
        }
    }

    public static class None implements Describable {
        @Override
        public void describeTo(HtmlWriter h) {
            h.h3("<< No JSON Input or Output >>");
        }
    }
}
