package com.beynd.platform.command;

import lombok.Builder;
import lombok.Getter;

import java.time.Instant;

@Getter
@Builder
public class CommandResult<R> {

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

    public static <R> CommandResult<R> success(R data, String source, String traceId) {
        return CommandResult.<R>builder()
                .data(data)
                .timestamp(Instant.now())
                .source(source)
                .traceId(traceId)
                .status(Status.SUCCESS)
                .message("Command executed successfully")
                .build();
    }

    public static <R> CommandResult<R> failure(String message, String traceId) {
        return CommandResult.<R>builder()
                .timestamp(Instant.now())
                .status(Status.FAILURE)
                .message(message)
                .traceId(traceId)
                .build();
    }
}
