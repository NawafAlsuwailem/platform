package beynd.architecture.platform.error;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Error {
    private String code;
    private String description;
    private Integer status;
    private Boolean isTechnical;
}
