
class Part (val partNum: Int,
            val xrange: IntRange,
            val y: Int) {

    // Generate a list of x/y coordinates adjacent to the part
    fun adjacencyList(): Array<String> {
        var coords = arrayOf<String>()

        // Row above:
        val xprocess = (xrange.first - 1 )..(xrange.last + 1)
        val yprocess = (y-1)..(y+1)

        yprocess.forEach {yVal ->
            xprocess.forEach {xVal ->
                // capture coordinate as long as it does not overlap with the positions of the part number
                if (yVal != y || (!xrange.contains(xVal))) {
                    coords += ("$xVal:$yVal")
                }
            }
        }
        return coords
    }

    override fun toString(): String {
        return "Part# $partNum  x = $xrange, y = "+ (y + 1)
    }
}