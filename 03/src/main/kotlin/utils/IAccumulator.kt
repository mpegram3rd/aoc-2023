package utils

interface IAccumulator {
    fun processLine(line: String)
    fun execute()
}