
public class RouteCalculator {

    private final MetroMap metroMap;
    private final RouteOnTheLineStrategy routeOnTheLine = new RouteOnTheLineStrategy();
    private final RouteWithOneConnectionStrategy routeWithOneConnection = new RouteWithOneConnectionStrategy();
    private final RouteWithTwoConnectionsStrategy routeWithTwoConnection = new RouteWithTwoConnectionsStrategy();

    private static final double INTER_STATION_DURATION = 2.5;
    private static final double INTER_CONNECTION_DURATION = 3.5;

    public RouteCalculator(MetroMap metroMap) {
        this.metroMap = metroMap;
    }

    public Route getShortestRoute(Station from, Station to) {

        Route routeA = routeOnTheLine.getRoute(from, to, metroMap);
        Route routeB = routeWithOneConnection.getRoute(from, to, metroMap);
        Route routeC = routeWithTwoConnection.getRoute(from, to, metroMap);

        if (routeA != null) {
            return routeA;
        }

        if (routeB != null && routeC != null) {
            if (calculateDuration(routeB) > calculateDuration(routeC)) {
                return routeC;
            } else {
                return routeB;
            }
        }

        return routeC;
    }

    public static double calculateDuration(Route route) {
        double duration = 0;
        Station previousStation = null;
        for (int i = 0; i < route.getStations().size(); i++) {
            Station station = route.getStations().get(i);
            if (i > 0) {
                duration += previousStation.getLine().equals(station.getLine()) ?
                        INTER_STATION_DURATION : INTER_CONNECTION_DURATION;
            }
            previousStation = station;
        }
        return duration;
    }
}