package utils

import java.io.BufferedReader
import java.io.FileReader

class FileProcessor(override val filenname: String,
                    override val accumulator: IAccumulator) : IFileProcessor{
    override fun processFile() {
        var reader: BufferedReader? = null

        try {
            reader = BufferedReader(FileReader(filenname))
            var line: String?

            while (reader.readLine().also { line = it } != null) {
                // Process each non-null line (line?.let)
                line?.let { accumulator.processLine(it) }
            }
        } catch (e: Exception) {
            println("An error occurred: ${e.message}")
        } finally {
            try {
                reader?.close()
            } catch (e: Exception) {
                println("An error occurred while closing the file: ${e.message}")
            }
        }
    }
}