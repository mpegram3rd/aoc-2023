package models;

import java.util.ArrayList;
import java.util.List;

public class RangeMap {

    private final String from, to;
    private long minKey, maxKey;
    private List<Range> ranges;
    private Range lastMatch;

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
        reset();
    }

    public void reset() {
        lastMatch = null;
    }

    public long mapValue(long value) {
        if (value < minKey || value > maxKey) {
            return value;
        }
        long val = lastMatch != null ? lastMatch.findDestination(value) : -1;
        if (val != -1)
            return val;
        for (Range r : ranges) {
            val = r.findDestination(value);
            if (val != -1) {
                lastMatch = r;
                return val;
            }
        }
        return value;
    }

}
