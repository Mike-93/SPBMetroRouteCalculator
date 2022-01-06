import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class RouteWithTwoConnectionsStrategy implements RouteStrategy{

    private final RouteOnTheLineStrategy routeOnTheLineStrategy = new RouteOnTheLineStrategy();

    private List<Station> getRouteViaConnectedLine(Station from, Station to, StationIndex stationIndex) {
        Set<Station> fromConnected = stationIndex.getConnectedStations(from);
        Set<Station> toConnected = stationIndex.getConnectedStations(to);
        for (Station srcStation : fromConnected) {
            for (Station dstStation : toConnected) {
                if (srcStation.getLine().equals(dstStation.getLine())) {
                    return routeOnTheLineStrategy.getRoute(srcStation, dstStation, stationIndex);
                }
            }
        }
        return null;
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
                List<Station> connectedLineRoute =
                        getRouteViaConnectedLine(srcStation, dstStation, stationIndex);
                if (connectedLineRoute == null) {
                    continue;
                }
                ArrayList<Station> way = new ArrayList<>();
                way.addAll(routeOnTheLineStrategy.getRoute(from, srcStation, stationIndex));
                way.addAll(connectedLineRoute);
                way.addAll(routeOnTheLineStrategy.getRoute(dstStation, to, stationIndex));
                if (route.isEmpty() || route.size() > way.size()) {
                    route.clear();
                    route.addAll(way);
                }
            }
        }

        return route;
    }
}
