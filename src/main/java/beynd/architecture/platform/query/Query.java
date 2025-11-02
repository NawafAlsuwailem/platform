package beynd.architecture.platform.query;

import beynd.architecture.platform.error.Error;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Query<T> {
    private T id;
    private T data;
}
