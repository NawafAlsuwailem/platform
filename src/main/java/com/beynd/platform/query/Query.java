package com.beynd.platform.query;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Query<T>{
    private T data;
    public static <T> Query<T> of(T data) {
        return Query.<T>builder().data(data).build();
    }
}
