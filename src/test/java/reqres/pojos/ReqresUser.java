package reqres.pojos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.poiji.annotation.ExcelCell;
import com.poiji.annotation.ExcelCellName;
import lombok.*;
import utils.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder(toBuilder = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReqresUser {

    @ExcelCell(0)
    @JsonIgnore
    private String testCaseName;

    @ExcelCellName("Name")
    private String name;
    @ExcelCellName("Job")
    private String job;
}
