package com.beynd.platform.query;

public interface QueryBaseService<T, R> {
    QueryResult<R> handle(Query<T> query);
}
