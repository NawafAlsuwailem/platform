package com.beynd.platform.query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

public abstract class AbstractQueryBaseService<T, R> implements QueryBaseService<T, R> {

    protected final Logger log = LoggerFactory.getLogger(getClass());
    protected QueryValidator<T> validator = query -> {};

    @Autowired(required = false)
    protected void setValidator(QueryValidator<T> validator){
        if (validator != null){
            this.validator = validator;
        }
    }

    @Override
    public QueryResult<R> handle(Query<T> query) {
        String traceId = getTraceId();
        try {
            log.info("[Core Handle] {}", query);
            validator.validate(query);
            R result = execute(query);
            return QueryResult.success(result, getSource(), traceId);
        } catch (Exception ex){
            log.error("[Core Handle] {}", query, ex);
            return QueryResult.failure(ex.getMessage(), traceId);
        }
    }

    protected abstract R execute(Query<T> query);

    protected String getSource() {
        return "platform-query";
    }

    protected String getTraceId() {
        return UUID.randomUUID().toString();
    }

    public QueryResult<R> decorate(Query<T> query){
        return new QueryDecorator<>(this).handle(query);
    }
}

