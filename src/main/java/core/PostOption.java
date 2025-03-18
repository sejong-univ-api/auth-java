package core;
    import java.util.HashMap;
    import java.util.Map;

    public class PostOption {
        private boolean formUrlencoded;
        private Map<String, String> customHeaders;

        private PostOption(Builder builder) {
            this.formUrlencoded = builder.formUrlencoded;
            this.customHeaders = builder.customHeaders;
        }

        public boolean isFormUrlencoded() {
            return formUrlencoded;
        }

        public Map<String, String> getCustomHeaders() {
            return customHeaders;
        }

        public static class Builder {
            private boolean formUrlencoded = false;
            private Map<String, String> customHeaders = new HashMap<>();

            public Builder formUrlencoded(boolean formUrlencoded) {
                this.formUrlencoded = formUrlencoded;
                return this;
            }

            public Builder customHeaders(Map<String, String> customHeaders) {
                this.customHeaders = customHeaders;
                return this;
            }

            public PostOption build() {
                return new PostOption(this);
            }
        }

        public static Builder builder() {
            return new Builder();
        }
    }