package models;

public class Range implements Comparable<Range> {
    private final long srcFirst;
    private final long srcLast;
    private final long destFirst;

    public Range(long srcFirst, long destFirst, long count) {
        this.srcFirst = srcFirst;
        this.srcLast = srcFirst + count - 1;
        this.destFirst = destFirst;
    }

    /**
     * Attempts to map the key to a destination value.
     * If not found returns a -1.
     * @param keyVal key value in map to check
     * @return destination value for key or -1 if not found.
     */
    public long findDestination(long keyVal) {
        if (keyVal < srcFirst || keyVal > srcLast) {
            return -1;
        }
        return destFirst + (keyVal - srcFirst);
    }
    public long getSrcFirst() {
        return srcFirst;
    }

    public long getSrcLast() {
        return srcLast;
    }

    @Override
    public int compareTo(Range rm) {
        return (int)(this.getSrcFirst() - rm.getSrcFirst());
    }

}