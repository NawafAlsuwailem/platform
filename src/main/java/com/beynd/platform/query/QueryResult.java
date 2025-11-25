package com.beynd.platform.query;

import lombok.Builder;
import lombok.Getter;

import java.time.Instant;

@Getter
@Builder
public class QueryResult<R> {
    private final R data;
    private final Instant timestamp;
    private final String source;
    private final String traceId;
    private final Status status;
    private final String message;

    public enum Status {
        SUCCESS,
        FAILURE
    }

    public static <R> QueryResult<R> success(R data, String source, String traceId) {
        return QueryResult.<R>builder()
                .data(data)
                .timestamp(Instant.now())
                .source(source)
                .traceId(traceId)
                .status(Status.SUCCESS)
                .message("Query executed successfully")
                .build();
    }

    public static <R> QueryResult<R> failure(String message, String traceId) {
        return QueryResult.<R>builder()
                .timestamp(Instant.now())
                .status(Status.FAILURE)
                .message(message)
                .traceId(traceId)
                .build();
    }

}
