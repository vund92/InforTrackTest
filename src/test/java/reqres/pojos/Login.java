package reqres.pojos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.poiji.annotation.ExcelCell;
import com.poiji.annotation.ExcelCellName;
import lombok.*;
import utils.RandomDataGenerator_Reqres;
import utils.RandomDataTypeNames_Reqres;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder(toBuilder = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Login {

    @ExcelCell(0)
    @JsonIgnore
    private String testCaseName;

    @ExcelCellName("Email")
    private String email = RandomDataGenerator_Reqres.getRandomDataFor(RandomDataTypeNames_Reqres.EMAIL);
    @ExcelCellName("Password")
    private String password = RandomDataGenerator_Reqres.getRandomDataFor(RandomDataTypeNames_Reqres.PASSWORD);
}
