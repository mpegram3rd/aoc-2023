import utils.IAccumulator

class Accumulator : IAccumulator {
    private var sequences  = ArrayList<IntArray>()
    override fun processLine(line: String) {
    }

    override fun execute() {
        solution1()
        solution2()
    }

    fun solution1() {
        var total = 0
        println("Solution 1: $total")
    }

    fun solution2() {
        var total = 0
        println("Solution 2: $total")
    }
}
