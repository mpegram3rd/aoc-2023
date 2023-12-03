package utils

interface IFileProcessor {
    val filenname : String
    val accumulator : IAccumulator
    fun processFile()
}