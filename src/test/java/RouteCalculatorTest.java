import core.Line;
import core.Station;
import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;

public class RouteCalculatorTest extends TestCase {

    List <Station> routeOnTheLine;
    List <Station> routeWithOneConnection;
    List <Station> routeWithTwoConnections;

    Station from, to;
    Station oneLine1, twoLine1, threeLine1, oneLine2, twoLine2, threeLine2, oneLine3, twoLine3, threeLine3, fourLine4;
    StationIndex stationIndex;
    RouteCalculator calculator;

    @Override
    protected void setUp() throws Exception {

        routeOnTheLine = new ArrayList<>();
        routeWithOneConnection = new ArrayList<>();
        routeWithTwoConnections = new ArrayList<>();

        Line line1 = new Line(1, "Первая");
        Line line2 = new Line(2, "Вторая");
        Line line3 = new Line(3, "Третья");

        oneLine1 = new Station("Лесная", line1);
        twoLine1 = new Station("Городская", line1);
        threeLine1 = new Station("Снежная", line1);
        oneLine2 = new Station("Придорожная", line2);
        twoLine2 = new Station("Московская", line2);
        threeLine2 = new Station("Петровская", line2);
        oneLine3 = new Station("Арбузная", line3);
        twoLine3 = new Station("Дынная", line3);
        threeLine3 = new Station("Мороковная", line3);
        fourLine4 = new Station("Огуречная", line3);

        routeOnTheLine.add(oneLine1);
        routeOnTheLine.add(twoLine1);
        routeOnTheLine.add(threeLine1);

        routeWithOneConnection.add(oneLine1);
        routeWithOneConnection.add(twoLine1);
        routeWithOneConnection.add(twoLine3);
        routeWithOneConnection.add(oneLine3);

        routeWithTwoConnections.add(oneLine1);
        routeWithTwoConnections.add(twoLine1);
        routeWithTwoConnections.add(twoLine3);
        routeWithTwoConnections.add(threeLine3);
        routeWithTwoConnections.add(twoLine2);
        routeWithTwoConnections.add(oneLine2);

        stationIndex = new StationIndex();
        stationIndex.addLine(line1);
        stationIndex.addLine(line2);
        stationIndex.addLine(line3);

        Line stationIndexLine1 = stationIndex.getLine(1);
        stationIndexLine1.addStation(oneLine1);
        stationIndexLine1.addStation(twoLine1);
        stationIndexLine1.addStation(threeLine1);
        Line stationIndexLine2 = stationIndex.getLine(2);
        stationIndexLine2.addStation(oneLine2);
        stationIndexLine2.addStation(twoLine2);
        stationIndexLine2.addStation(threeLine2);
        Line stationIndexLine3 = stationIndex.getLine(3);
        stationIndexLine3.addStation(oneLine3);
        stationIndexLine3.addStation(twoLine3);
        stationIndexLine3.addStation(threeLine3);
        stationIndexLine3.addStation(fourLine4);

        stationIndex.addStation(oneLine1);
        stationIndex.addStation(twoLine1);
        stationIndex.addStation(threeLine1);
        stationIndex.addStation(oneLine2);
        stationIndex.addStation(twoLine2);
        stationIndex.addStation(threeLine2);
        stationIndex.addStation(oneLine3);
        stationIndex.addStation(twoLine3);
        stationIndex.addStation(threeLine3);
        stationIndex.addStation(fourLine4);

        List<Station> connectionStations = new ArrayList<>();
        connectionStations.add(twoLine1);
        connectionStations.add(twoLine3);
        stationIndex.addConnection(connectionStations);
        List<Station> connectionStations1 = new ArrayList<>();
        connectionStations1.add(twoLine2);
        connectionStations1.add(threeLine3);
        stationIndex.addConnection(connectionStations1);

        calculator = new RouteCalculator(stationIndex);



    }


    public void testCalculateDuration() {
        double actual = RouteCalculator.calculateDuration(routeWithTwoConnections);
        double expected = 14.5;
        assertEquals(expected, actual);
    }


    public void testGetShortestRouteOnTheLine() {
        List<Station> actual = calculator.getShortestRoute(oneLine1, threeLine1);
        List <Station> expected = routeOnTheLine;
        assertEquals(expected, actual);
    }

    public void testGetShortestRouteWithOneConnections() {
        List<Station> actual = calculator.getShortestRoute(oneLine1, oneLine3);
        List <Station> expected = routeWithOneConnection;
        assertEquals(expected, actual);
    }

      public void testGetShortestRouteWithTwoConnections() {
         List<Station> actual = calculator.getShortestRoute(oneLine1, oneLine2);
         List <Station> expected = routeWithTwoConnections;
         assertEquals(expected, actual);
    }

}
