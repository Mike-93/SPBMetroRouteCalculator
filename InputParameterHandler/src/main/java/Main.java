import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;

import java.util.List;
import java.util.Scanner;

public class Main {
    private static Logger logger;
    private static final Marker SEARCH_MARKER = MarkerManager.getMarker("search");
    private static final Marker EXCEPTION_MARKER = MarkerManager.getMarker("exceptions");
    private static final Marker INPUT_ERRORS_MARKER = MarkerManager.getMarker("inputErrors");

    private static Scanner scanner;
    private static Parser parser = new Parser();

    public static void main(String[] args) {

        StationIndex stationIndex = parser.createStationIndex();
        RouteCalculator calculator = new RouteCalculator(stationIndex);

        logger = LogManager.getLogger();

        System.out.println("Программа расчёта маршрутов метрополитена Санкт-Петербурга\n");
        scanner = new Scanner(System.in);
        for (; ; ) {
            try {
                Station from = takeStation("Введите станцию отправления:", stationIndex);
                Station to = takeStation("Введите станцию назначения:", stationIndex);

                List<Station> route = calculator.getShortestRoute(from, to);
                System.out.println("Маршрут:");
                printRoute(route);

                System.out.println("Длительность: " +
                        RouteCalculator.calculateDuration(route) + " минут");
            } catch (Exception exception) {
                logger.error(EXCEPTION_MARKER, exception.toString());
            }
        }
    }

    private static void printRoute(List<Station> route) {
        Station previousStation = null;
        for (Station station : route) {
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

    private static Station takeStation(String message, StationIndex stationIndex) {
        for (; ; ) {
            System.out.println(message);
            String line = scanner.nextLine().trim();
            Station station = stationIndex.getStation(line);
            if (station != null) {
                logger.info(SEARCH_MARKER, "Поиск станции " + line);
                return station;
            }
            logger.warn(INPUT_ERRORS_MARKER, "Станция не найдена " + line);
            System.out.println("Станция не найдена :(");
        }
    }

}
