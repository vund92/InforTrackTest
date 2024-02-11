package reqres.pojos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.poiji.annotation.ExcelCell;
import com.poiji.annotation.ExcelCellName;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder(toBuilder = true)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class CreateReqresUser extends  BasePojo{
    @ExcelCell(0)
    @JsonIgnore
    private String testCaseName;
    @ExcelCellName("Name")
    private String name;
    @ExcelCellName("Job")
    private String job;
}
