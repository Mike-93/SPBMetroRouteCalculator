import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class RouteWithTwoConnectionsStrategy implements RouteStrategy{

    private final RouteOnTheLineStrategy routeOnTheLineStrategy = new RouteOnTheLineStrategy();

    private List<Station> getRouteViaConnectedLine(Station from, Station to, MetroMap metroMap) {
        Set<Station> fromConnected = metroMap.getConnectedStations(from);
        Set<Station> toConnected = metroMap.getConnectedStations(to);
        for (Station srcStation : fromConnected) {
            for (Station dstStation : toConnected) {
                if (srcStation.getLine().equals(dstStation.getLine())) {
                    return routeOnTheLineStrategy.getRoute(srcStation, dstStation, metroMap).getStations();
                }
            }
        }
        return null;
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
                List<Station> connectedLineRoute =
                        getRouteViaConnectedLine(srcStation, dstStation, metroMap);
                if (connectedLineRoute == null) {
                    continue;
                }
                ArrayList<Station> way = new ArrayList<>();
                way.addAll(routeOnTheLineStrategy.getRoute(from, srcStation, metroMap).getStations());
                way.addAll(connectedLineRoute);
                way.addAll(routeOnTheLineStrategy.getRoute(dstStation, to, metroMap).getStations());
                if (stationList.isEmpty() || stationList.size() > way.size()) {
                    stationList.clear();
                    stationList.addAll(way);
                }
            }
        }
        route.setStations(stationList);
        return route;
    }
}
