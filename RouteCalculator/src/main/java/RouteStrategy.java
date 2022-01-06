import java.util.List;

public interface RouteStrategy {

    List<Station> getRoute(Station from, Station to, StationIndex stationIndex);

}
