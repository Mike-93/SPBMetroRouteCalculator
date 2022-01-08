import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class RouteWithOneConnectionStrategy implements RouteStrategy {

    private final RouteOnTheLineStrategy routeOnTheLineStrategy = new RouteOnTheLineStrategy();

    private boolean isConnected(Station station1, Station station2, MetroMap metroMap) {
        Set<Station> connected = metroMap.getConnectedStations(station1);
        return connected.contains(station2);
    }

    @Override
    public Route getRoute(Station from, Station to, MetroMap metroMap) {
        if (from.getLine().equals(to.getLine())) {
            return null;
        }
        Route route = new Route();
        ArrayList<Station> stationList = new ArrayList<>();

        List<Station> fromLineStations = from.getLine().getStations();
        List<Station> toLineStations = to.getLine().getStations();
        for (Station srcStation : fromLineStations) {
            for (Station dstStation : toLineStations) {
                if (isConnected(srcStation, dstStation, metroMap)) {
                    ArrayList<Station> way = new ArrayList<>();
                    way.addAll(routeOnTheLineStrategy.getRoute(from, srcStation, metroMap).getStations());
                    way.addAll(routeOnTheLineStrategy.getRoute(dstStation, to, metroMap).getStations());
                    if (stationList.isEmpty() || stationList.size() > way.size()) {
                        stationList.clear();
                        stationList.addAll(way);
                    }
                }
            }
        }
        if (stationList.size() == 0) return null;
        {
            route.setStations(stationList);
            return route;
        }
    }
}
