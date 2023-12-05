package models;

import java.util.ArrayList;
import java.util.List;

public class RangeMap {

    private final String from, to;
    private List<Range> ranges;
    private boolean sorted;

    public RangeMap(String from, String to) {
        this.from = from;
        this.to = to;
        ranges = new ArrayList<>();
        sorted = false;
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
        sorted = false;
    }

    public long mapValue(long value) {
        if (!sorted) {
            ranges = ranges.stream().sorted().toList();
        }
        for (Range r : ranges) {
            long val = r.findDestination(value);
            if (val != -1)
                return val;
        }
        return value;
    }

}
