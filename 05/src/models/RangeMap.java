package models;

import java.util.ArrayList;
import java.util.List;

public class RangeMap {

    private final String from, to;
    private final List<Range> ranges;

    public RangeMap(String from, String to) {
        this.from = from;
        this.to = to;
        ranges = new ArrayList<>();
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public void addRange(Range range)
    {
        ranges.add(range);
    }

    public long mapValue(long value) {
        for (Range r : ranges) {
            long val = r.findDestination(value);
            if (val != -1)
                return val;
        }
        return value;
    }

}
