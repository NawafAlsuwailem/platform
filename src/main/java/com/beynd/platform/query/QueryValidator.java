package com.beynd.platform.query;

@FunctionalInterface
public interface QueryValidator<T> {
    void validate(Query<T> query);
}
