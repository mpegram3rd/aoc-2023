package models;

public class Range implements Comparable<Range> {
    private final long srcFirst;
    private final long destFirst;
    private final long count;

    public Range(long srcFirst, long destFirst, long count) {
        this.srcFirst = srcFirst;
        this.destFirst = destFirst;
        this.count = count;
    }

    public long getSrcFirst() {
        return srcFirst;
    }

    public long getSrcLast() {
        return srcFirst + count - 1;
    }

    public long getDestFirst() {
        return destFirst;
    }

    /**
     * Attempts to map the key to a destination value.
     * If not found returns a -1.
     * @param keyVal key value in map to check
     * @return destination value for key or -1 if not found.
     */
    public long findDestination(long keyVal) {
        if (keyVal >= getSrcFirst() && keyVal <= getSrcLast()) {
            return getDestFirst() + (keyVal - getSrcFirst());
        }
        return -1;
    }

    @Override
    public int compareTo(Range rm) {
        return (int)(this.getSrcFirst() - rm.getSrcFirst());
    }

}