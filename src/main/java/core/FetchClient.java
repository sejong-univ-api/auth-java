package core;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import core.exception.ConnectionException;
import okhttp3.*;

import java.io.IOException;
import java.util.Map;

public class FetchClient {
    private static final OkHttpClient client = new OkHttpClient();
    private static final ObjectMapper mapper = new ObjectMapper();

    public static FetchResponse post(String url, Map<String, Object> data, PostOption option) {
        Request request = new Request.Builder()
                .url(url)
                .post(createRequestBody(data, option))
                .headers(createHeaders(option))
                .build();

        try(Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new ConnectionException("api 응답 오류");
            }

            return ResponseParser.parseResponse(response);
        } catch (IOException e) {
            throw new ConnectionException("api 응답 오류");
        }
    }

    private static RequestBody createRequestBody(Map<String, Object> data, PostOption option) {
        if (option.isFormUrlencoded()) {
            FormBody.Builder builder = new FormBody.Builder();
            data.forEach((k, v) -> builder.add(k, v.toString()));
            return builder.build();
        }
        try {
            String json = mapper.writeValueAsString(data);
            MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
            return RequestBody.create(mediaType, json);
        } catch (JsonProcessingException exception) {
            return RequestBody.create(MediaType.parse("application/json; charset=utf-8"), "");
        }
    }

    private static Headers createHeaders(PostOption option) {
        Map<String, String> customHeaders = option.getCustomHeaders();
        Headers.Builder headersBuilder = new Headers.Builder();
        customHeaders.forEach((k, v) -> headersBuilder.add(k, v));
        return headersBuilder.build();
    }
}
