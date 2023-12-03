import utils.IAccumulator

class Accumulator : IAccumulator {
    private var y = 0
    private var parts = ArrayList<Part>()
    private var symbols = mutableMapOf<String, Symbol>()
    private val nonSymbolSet = setOf('0','1','2','3','4','5','6','7','8','9','.')

    override fun processLine(line: String) {
        var x = 0
        var numVal = 0
        var xRangeStart = 0
        line.toCharArray().forEach {
            // See if we found a symbol
            if (!nonSymbolSet.contains(it)) {
                val symbol = Symbol(it, x, y)
                val key = "" + symbol.x + ":" + symbol.y
                symbols[key] = symbol
            }
            // check for a number
            val numCheck = it - '0'
            if (numCheck in 0..9) {

                if (numVal == 0) { // Found first number, start our range
                    xRangeStart = x
                }
                numVal = (numVal * 10) + numCheck
            }
            else { // It's not a number see if we're tracking a number and need to save it as a part
                if (numVal > 0) {
                    parts.add(Part(numVal, xRangeStart..<x, y))
                }
                xRangeStart = 0
                numVal = 0
            }
            x++
        }
        if (numVal > 0) { // Catch edge case where number is at end of line
            parts.add(Part(numVal, xRangeStart..<x, y))
        }
        y++
    }

    override fun execute() {
        solution1()
        solution2()
    }

    fun solution1() {
        val total = parts.fold(0) { acc, part ->
            val isAdjacent = part.adjacencyList().fold(false) { found, coordinate ->
                found.or(symbols.contains(coordinate))
            }
            if (isAdjacent) (acc + part.partNum) else acc
        }
        println("Solution 1: $total")

    }

    fun solution2() {
        // Extract only the gears
        val gears = symbols.filter { s -> s.value.display == '*' }

        // now process each part and associate any parts that are adjacent to a gear
        parts.forEach {p ->
            p.adjacencyList().forEach { coord ->
                val gear = gears[coord]
                gear?.adjacentParts?.add(p)
            }
        }

        // Now accumulate the sum of the gear ratios (partNum * partNum) for any gear that has exactly 2 associated parts
        val total = gears.values.fold(0L) { acc, gear ->
            if (gear.adjacentParts.count() == 2)
                acc + (gear.adjacentParts[0].partNum * gear.adjacentParts[1].partNum)
            else
                acc
        }
        println("Solution 2: $total")
    }
}
