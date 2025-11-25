package com.beynd.platform.query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class QueryDecorator<T, R> implements QueryBaseService<T, R> {

    private final QueryBaseService<T, R> delegate;
    private final Logger log;

    public QueryDecorator(QueryBaseService<T, R> delegate){
        this.delegate = delegate;
        this.log = LoggerFactory.getLogger(delegate.getClass());
    }

    @Override
    public QueryResult<R> handle(Query<T> query) {
        String traceId = getTraceId();
        try {
            log.info("[Decorator Start] traceId={}, query={}", traceId, query);
            QueryResult<R> result = delegate.handle(query);
            log.info("[Decorator Success] traceId={}, results={}", traceId, result.getData());
            return QueryResult.<R>builder()
                    .data(result.getData())
                    .source(result.getSource() != null ? result.getSource() :  "decorated-service")
                    .timestamp(result.getTimestamp())
                    .status(QueryResult.Status.SUCCESS)
                    .message(result.getMessage())
                    .build();
        } catch (Exception ex){
            log.error("[Decorator Failed] traceId{}, error{}", traceId, ex.getMessage(), ex);
            return QueryResult.failure(ex.getMessage(), traceId);
        }
    }

    protected String getTraceId() {
        return java.util.UUID.randomUUID().toString();
    }
}
