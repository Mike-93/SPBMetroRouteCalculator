import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RouteOnTheLineStrategy implements RouteStrategy{

    @Override
    public Route getRoute(Station from, Station to, MetroMap metroMap) {
        if (!from.getLine().equals(to.getLine())) {
            return null;
        }
        Route route = new Route();
        ArrayList<Station> stationList = new ArrayList<>();
        List<Station> stations = from.getLine().getStations();
        int direction = 0;
        for (Station station : stations) {
            if (direction == 0) {
                if (station.equals(from)) {
                    direction = 1;
                } else if (station.equals(to)) {
                    direction = -1;
                }
            }

            if (direction != 0) {
                stationList.add(station);
            }

            if ((direction == 1 && station.equals(to)) ||
                    (direction == -1 && station.equals(from))) {
                break;
            }
        }
        if (direction == -1) {
            Collections.reverse(stationList);
        }
        route.setStations(stationList);
        return route;
    }
}
