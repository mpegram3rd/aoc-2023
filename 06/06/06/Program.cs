// Very limited time today so just going for a quick and dirty solution. 
// Even skipped parsing the data file.

// Sample data        
// process(new long[]{7, 15, 30}, new long[]{9, 40, 200}, 1);
// process(new long[] {71530}, new long[] {940200}, 2);

// Problem data
process(new long[]{56, 97, 77, 93}, new long[]{499, 2210, 1097, 1440}, 1);
process(new long[]{56977793}, new long[]{499221010971440}, 2);

static void process(long[] times, long[] distances, long solutionNum)
{
    long total = 1;
    for (long idx = 0; idx < times.Length; idx++)
    {
        total *= findOptions(times[idx], distances[idx]);
    }
    Console.WriteLine($"Solution {solutionNum}: {total}");
}

// Strategy:
// - Find first value that works
// - This ends up being the same distance from the FRONT as it is from the BACK so that's your range
// - Figure out the range length
// NOTE: Turns out the "optimal" solution is a more simple formula that starts by using sqrt(time * time -4 * distance) 
static long findOptions(long time, long distance)
{
    long startVal = 2;
    while (startVal * (time - startVal) <= distance)
        startVal++;
    
    // last useful value will be the same distance from the end as start was from the beginning
    long end = time - startVal;

    return end - startVal + 1;
}