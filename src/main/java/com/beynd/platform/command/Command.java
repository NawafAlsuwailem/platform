package com.beynd.platform.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Command<T> {
    private T data;
    public static <T> Command<T> of(T data) {
        return Command.<T>builder().data(data).build();
    }
}
