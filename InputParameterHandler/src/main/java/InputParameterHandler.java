import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;

import java.util.List;
import java.util.Scanner;

public class InputParameterHandler {
    private static Logger logger;
    private static final Marker SEARCH_MARKER = MarkerManager.getMarker("search");
    private static final Marker EXCEPTION_MARKER = MarkerManager.getMarker("exceptions");
    private static final Marker INPUT_ERRORS_MARKER = MarkerManager.getMarker("inputErrors");
    private static final String SRC_MESSAGE = "Введите станцию отправления:";
    private static final String DST_MESSAGE = "Введите станцию назначения:";
    private static final Parser parser = new Parser();
    private static Scanner scanner;


    public static void main(String[] args) {

        MetroMap metroMap = parser.createMetroMap();
        RouteCalculator calculator = new RouteCalculator(metroMap);

        logger = LogManager.getLogger();

        System.out.println("Программа расчёта маршрутов метрополитена Санкт-Петербурга\n");
        scanner = new Scanner(System.in);
        for (; ; ) {
            try {
                Station from = takeStation(SRC_MESSAGE, metroMap);
                Station to = takeStation(DST_MESSAGE, metroMap);

                Route route = calculator.getShortestRoute(from, to);
                System.out.println("Маршрут:");
                printRoute(route);

                System.out.println("Длительность: " +
                        RouteCalculator.calculateDuration(route) + " минут");
            } catch (Exception exception) {
                logger.error(EXCEPTION_MARKER, exception.toString());
            }
        }
    }

    private static void printRoute(Route route) {
        List<Station> stationList = route.getStations();
        Station previousStation = null;
        for (Station station : stationList) {
            if (previousStation != null) {
                Line prevLine = previousStation.getLine();
                Line nextLine = station.getLine();
                if (!prevLine.equals(nextLine)) {
                    System.out.println("\tПереход на станцию " +
                            station.getName() + " (" + nextLine.getName() + " линия)");
                }
            }
            System.out.println("\t" + station.getName());
            previousStation = station;
        }
    }

    private static Station takeStation(String message, MetroMap metroMap) {
        for (; ; ) {
            System.out.println(message);
            String line = scanner.nextLine().trim();
            logger.info(SEARCH_MARKER, "Поиск станции " + line);
            try {
                Station station = metroMap.getStation(line);
                if (station != null) {
                    return station;
                } else {
                    System.out.println("Станция не найдена :(");
                    throw new Exception("Station not found");
                }
            } catch (Exception exception) {
                logger.warn(INPUT_ERRORS_MARKER, "Станция не найдена " + line);
            }

        }
    }

}
