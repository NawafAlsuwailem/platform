package com.beynd.platform.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommandDecorator<T, R> implements CommandBaseService<T, R> {

    private final CommandBaseService<T, R> delegate;
    private final Logger log;

    public CommandDecorator(CommandBaseService<T, R> delegate) {
        this.delegate = delegate;
        this.log = LoggerFactory.getLogger(delegate.getClass());
    }

    @Override
    public CommandResult<R> handle(Command<T> command) {
        String traceId = java.util.UUID.randomUUID().toString();
        long start = System.currentTimeMillis();

        try {
            log.info("[Decorated Command Start] traceId={}, command={}", traceId, command);
            CommandResult<R> result = delegate.handle(command);
            log.info("[Command Success] traceId={}, result={}", traceId, result.getData());
            return CommandResult.<R>builder()
                    .data(result.getData())
                    .timestamp(result.getTimestamp())
                    .source(result.getSource())
                    .traceId(traceId)
                    .status(CommandResult.Status.SUCCESS)
                    .message("Decorated: " + result.getMessage())
                    .build();
        } catch (Exception ex) {
            log.error("[Command Failed] traceId={}, error={}", traceId, ex.getMessage(), ex);
            return CommandResult.failure(ex.getMessage(), traceId);
        } finally {
            log.info("[Command Duration] traceId={}, time={}ms", traceId, System.currentTimeMillis() - start);
        }
    }
}
