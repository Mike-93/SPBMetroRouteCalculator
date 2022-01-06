import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class RouteWithOneConnectionStrategy implements RouteStrategy {

    private final RouteOnTheLineStrategy routeOnTheLineStrategy = new RouteOnTheLineStrategy();

    private boolean isConnected(Station station1, Station station2, StationIndex stationIndex) {
        Set<Station> connected = stationIndex.getConnectedStations(station1);
        return connected.contains(station2);
    }

    @Override
    public List<Station> getRoute(Station from, Station to, StationIndex stationIndex) {
        if (from.getLine().equals(to.getLine())) {
            return null;
        }

        ArrayList<Station> route = new ArrayList<>();

        List<Station> fromLineStations = from.getLine().getStations();
        List<Station> toLineStations = to.getLine().getStations();
        for (Station srcStation : fromLineStations) {
            for (Station dstStation : toLineStations) {
                if (isConnected(srcStation, dstStation, stationIndex)) {
                    ArrayList<Station> way = new ArrayList<>();
                    way.addAll(routeOnTheLineStrategy.getRoute(from, srcStation, stationIndex));
                    way.addAll(routeOnTheLineStrategy.getRoute(dstStation, to, stationIndex));
                    if (route.isEmpty() || route.size() > way.size()) {
                        route.clear();
                        route.addAll(way);
                    }
                }
            }
        }
        if (route.size() == 0) return null;
        {
            return route;
        }
    }
}
