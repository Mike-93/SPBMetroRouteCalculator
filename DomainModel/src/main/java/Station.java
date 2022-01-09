import lombok.Data;

import java.util.Objects;

@Data
public class Station implements Comparable<Station> {
    private final Line line;
    private final String name;

    public Station(String name, Line line)
    {
        this.name = name;
        this.line = line;
    }

    @Override
    public int compareTo(Station station) {
        int lineComparison = line.compareTo(station.getLine());
        if (lineComparison != 0) {
            return lineComparison;
        }
        return name.compareToIgnoreCase(station.getName());
    }

    @Override
    public boolean equals(Object obj) {
        return compareTo((Station) obj) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(line, name);
    }

    @Override
    public String toString() {
        return name;
    }
}