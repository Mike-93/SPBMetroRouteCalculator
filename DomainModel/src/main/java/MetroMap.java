import lombok.Data;

import java.util.List;

@Data
public class MetroMap {
    private List <Line> lineList;
    private List <Station> stationList;
}
