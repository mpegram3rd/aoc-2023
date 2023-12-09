import utils.IAccumulator

class Accumulator : IAccumulator {
    private var sequences  = ArrayList<IntArray>()
    override fun processLine(line: String) {
        val numStrs = line.split(" ")
        sequences.add(numStrs.map { it.toInt() }.toIntArray())
    }

    override fun execute() {
        solve(1) { next, seq -> next + seq[seq.size - 1] }
        solve(2) { next, seq -> seq[0] - next }
    }

    private fun solve(num: Int, edgeLambda:(Int, IntArray) -> Int) {
        val total = this.sequences.fold(0) { acc, sequence ->
            acc + findEdge(sequence, edgeLambda)
        }

        println("Solution $num: $total")
    }

    // Recursively reduce the sequence until its all 0's then tally up the edge
    private fun findEdge(sequence: IntArray, getEdgeValue: (Int, IntArray) -> Int): Int {
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
            nextVal =  getEdgeValue(findEdge(diffs.toIntArray(), getEdgeValue), sequence)
        }

        return nextVal
    }
}
