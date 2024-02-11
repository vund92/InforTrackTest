package reqres.pojos;

import com.poiji.annotation.ExcelCell;
import com.poiji.annotation.ExcelCellName;
import com.poiji.annotation.ExcelUnknownCells;
import lombok.ToString;

import java.util.List;
import java.util.Map;

@ToString
public class ReqresUserPoiji {

    @ExcelCell(0)
    private int id;
    @ExcelCellName("Name")
    private String name;
    @ExcelCellName("Job")
    private String job;

    @ExcelUnknownCells
    private Map<String,String> extracells;
}
