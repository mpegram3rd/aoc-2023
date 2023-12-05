package utils;

import models.Range;
import models.RangeMap;

import java.util.*;

public class Accumulator {

    private String seedsData;
    private final Map<String, RangeMap> mappers;
    private RangeMap currentRangeMap;

    public Accumulator() {
        seedsData = "";
        mappers = new HashMap<>();
    }

    public void parseLine(String line) {
        if (line.startsWith("seeds:")) {
            parseSeeds(line);
            return;
        }
        if (line.endsWith("map:")) {
            currentRangeMap = createRangeMap(line);
            mappers.put(currentRangeMap.getFrom(), currentRangeMap);
            return;
        }
        if (line.isEmpty())
            return;
        parseRanges(line, currentRangeMap);
    }

    public void solution1() {
        System.out.println("Processing solution 1...");
        long shortestDistance = Long.MAX_VALUE;
        long iterations = 0;
        List<Long> seedVals = Arrays.stream(seedsData.trim().split(" "))
                        .map(Long::parseLong)
                        .toList();

        for (long seed : seedVals) {
            long distance = seedDistance(seed);
            shortestDistance = Math.min(distance, shortestDistance);
            iterations++;
        }

        System.out.println("Solution 1 - Shortest distance is " + shortestDistance + " and took " + iterations + " iterations");
    }

    // TODO should be able to fold the meat of this into a single method that sol1 and 2 can use
    public void solution2() {
        System.out.println("Processing solution 2...");
        long shortestDistance = Long.MAX_VALUE;
        long iterations = 0;

        int index = 0;
        String [] seedInfo = seedsData.trim().split(" ");

        while (index < seedInfo.length) {
            long start = Long.parseLong(seedInfo[index]);
            long end = start + Long.parseLong(seedInfo[index + 1]) - 1;
            System.out.println("  - Processing Seed range: " + start+ " - " +  end);
            index = index + 2;
            for (long seed = start; seed <= end; seed++) {
                long distance = seedDistance(seed);
                shortestDistance = Math.min(distance, shortestDistance);
                iterations++;
            }
        }

        System.out.println("Solution 2 - Shortest distance is " + shortestDistance + " and took " + iterations + " iterations");
    }


    private long seedDistance(long key) {
        RangeMap lookupMap = mappers.get("seed");
//        System.out.print("Seed " + key + ", ");

        do {
            key = lookupMap.mapValue(key);
//            System.out.print(lookupMap.getTo() + " " + key + ", ");
            lookupMap = mappers.get(lookupMap.getTo());
        } while (lookupMap != null);
//        System.out.println();
        return key;
    }

    private void parseRanges(String line, RangeMap currentRangeMap) {
        String [] parts = line.trim().split(" ");
        long dest = Long.parseLong(parts[0]);
        long src = Long.parseLong(parts[1]);
        long count = Long.parseLong(parts[2]);
        currentRangeMap.addRange(new Range(src, dest, count));
    }

    /**
     * Splits the line "x-to-y map:" to extract the from and to
     * then creates a new range map.
     *
     * @param line line to parse
     * @return a new RangeMap
     */
    private RangeMap createRangeMap(String line) {
        int parseableEnd = line.lastIndexOf(" map:");
        String[] parts = line.substring(0, parseableEnd).trim().split("-");
        return new RangeMap(parts[0], parts[2]);
    }

    /**
     * Parse the seed line "seeds: 1, 2, 3"
     * @param line line to parse
     */
    private void parseSeeds(String line) {
        String [] parts = line.split(":");
        seedsData = parts[1].trim();
    }

}
