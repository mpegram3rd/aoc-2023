package models;

import java.util.ArrayList;
import java.util.List;

public class RangeMap {

    private final String from, to;
    private long minKey, maxKey;
    private List<Range> ranges;

    public RangeMap(String from, String to) {
        this.from = from;
        this.to = to;
        ranges = new ArrayList<>();
        minKey = Long.MAX_VALUE;
        maxKey = Long.MIN_VALUE;
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

    public void optimize() {
        // sort the ranges
        ranges = ranges.stream().sorted().toList();

        ranges.forEach(r -> {
            if (r.getSrcFirst() < minKey)
                minKey = r.getSrcFirst();
            if (r.getSrcLast() > maxKey)
                maxKey = r.getSrcLast();
        });
    }

    public long mapValue(long value) {
        if (value < minKey || value > maxKey) {
            return value;
        }
        for (Range r : ranges) {
            long val = r.findDestination(value);
            if (val != -1)
                return val;
        }
        return value;
    }

}
