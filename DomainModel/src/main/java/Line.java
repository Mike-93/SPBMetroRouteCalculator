import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Line implements Comparable<Line> {
    private int number;
    private String name;
    private List<Station> stations;

    public Line(int number, String name)
    {
        this.number = number;
        this.name = name;
        stations = new ArrayList<>();
    }

    public void addStation(Station station)
    {
        stations.add(station);
    }

    @Override
    public int compareTo(Line line) {
        return Integer.compare(number, line.getNumber());
    }

    @Override
    public boolean equals(Object obj) {
        return compareTo((Line) obj) == 0;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}