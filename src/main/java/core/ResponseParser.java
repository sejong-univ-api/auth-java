package core;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.Response;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ResponseParser {
    private static final ObjectMapper mapper = new ObjectMapper();

    public static FetchResponse parseResponse(Response response) {
        FetchResponse fetchResponse = FetchResponse.builder()
                                                   .body(parseBody(response))
                                                   .headers(parseHeaders(response))
                                                   .cookies(parseCookies(response))
                                                   .build();

        return fetchResponse;
    }

    private static Object parseBody(Response response) {
        String contentType = response.header("Content-Type", "");
        if (response.body() == null) {
            return null;
        }

        String rawBody = response.body().toString();
        if (contentType.equals("application/json")) {
            try {
                return mapper.readTree(rawBody);
            } catch (JsonProcessingException exception) {
                return "";
            }
        }

        return rawBody;
    }

    private static Map<String, String> parseHeaders(Response response) {
        Map<String, String> headers = new HashMap<>();

        for (String name : response.headers().names()) {
            headers.put(name, response.header(name));
        }

        return Collections.unmodifiableMap(headers);
    }

    private static Map<String, String> parseCookies(Response response) {
        Map<String, String> cookies = new HashMap<>();

        for (String cookie : response.headers("Set-Cookie")) {
            String[] parts = cookie.split(";");
            String[] keyValue = parts[0].split("=");
            if (keyValue.length == 2) {
                cookies.put(keyValue[0], keyValue[1]);
            }
        }

        return Collections.unmodifiableMap(cookies);
    }
}
