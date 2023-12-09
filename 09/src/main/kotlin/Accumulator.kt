import utils.IAccumulator

class Accumulator : IAccumulator {
    private var sequences  = ArrayList<IntArray>()
    override fun processLine(line: String) {
        val numStrs = line.split(" ")
        sequences.add(numStrs.map { it.toInt() }.toIntArray())
    }

    override fun execute() {
        solution1()
        solution2()
    }

    fun solution1() {
        val total = sequences.fold(0) { acc, sequence ->
            acc + findNext(sequence)
        }

        println("Solution 1: $total")
    }

    fun solution2() {
        var total = 0
        println("Solution 2: $total")
    }

    // Recursively reduce the sequence until its all 0's then tally up the edge
    fun findNext(sequence: IntArray): Int {
        var nextVal = 0

        // if we haven't hit all 0's reduce the sequence and call recursively again
        if (!sequence.fold(true) {acc, num -> (acc && (num == 0))}) {

            val diffs = ArrayList<Int>()
            var previous = 0
            // Calculate the differentials between the neighboring nums in the sequence
            sequence.forEachIndexed { idx, num ->
                if (idx != 0)
                    diffs.add(num - previous)
                previous = num
            }
            // the next value is whatever the "nextVal" in the next reduce set + the edge value of this set.
            nextVal = findNext(diffs.toIntArray()) + sequence[sequence.size - 1]
        }

        return nextVal
    }
}
