package main

import (
	"os"
)

func main() {

	filePath := os.Args[1] // read file path from commandline

	solution1FP := FileProcessor{filename: filePath, accumulator: &Solution1Accumulator{0}}
	solution1FP.processFile()

	solution2FP := FileProcessor{filename: filePath, accumulator: &Solution2Accumulator{0}}
	solution2FP.processFile()

}
