package core;

import java.util.Map;

public class FetchResponse {
    private final Object body;
    private final Map<String, String> headers;
    private final Map<String, String> cookies;

    private FetchResponse(Builder builder) {
        this.body = builder.body;
        this.headers = builder.headers;
        this.cookies = builder.cookies;
    }

    public static Builder builder() {
        return new Builder();
    }

    public Object getBody() {
        return body;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public Map<String, String> getCookies() {
        return cookies;
    }

    public static class Builder {
        private Object body;
        private Map<String, String> headers;
        private Map<String, String> cookies;

        public Builder body(Object body) {
            this.body = body;
            return this;
        }

        public Builder headers(Map<String, String> headers) {
            this.headers = headers;
            return this;
        }

        public Builder cookies(Map<String, String> cookies) {
            this.cookies = cookies;
            return this;
        }

        public FetchResponse build() {
            return new FetchResponse(this);
        }
    }
}