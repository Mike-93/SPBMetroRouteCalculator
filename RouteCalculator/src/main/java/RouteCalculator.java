
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class RouteCalculator {

    private final StationIndex stationIndex;
    private final RouteOnTheLineStrategy routeOnTheLine = new RouteOnTheLineStrategy();
    private final RouteWithOneConnectionStrategy routeWithOneConnection = new RouteWithOneConnectionStrategy();
    private final RouteWithTwoConnectionsStrategy routeWithTwoConnection = new RouteWithTwoConnectionsStrategy();

    private static final double interStationDuration = 2.5;
    private static final double interConnectionDuration = 3.5;

    public RouteCalculator(StationIndex stationIndex) {
        this.stationIndex = stationIndex;
    }

    public List<Station> getShortestRoute(Station from, Station to) {
        List<Station> route = routeOnTheLine.getRoute(from, to, stationIndex);
        if (route != null) {
            return route;
        }

        route = routeWithOneConnection.getRoute(from, to, stationIndex);
        if (route != null) {
            return route;
        }

        route = routeWithTwoConnection.getRoute(from, to, stationIndex);
        return route;
    }

    public static double calculateDuration(List<Station> route) {
        double duration = 0;
        Station previousStation = null;
        for (int i = 0; i < route.size(); i++) {
            Station station = route.get(i);
            if (i > 0) {
                duration += previousStation.getLine().equals(station.getLine()) ?
                        interStationDuration : interConnectionDuration;
            }
            previousStation = station;
        }
        return duration;
    }
}