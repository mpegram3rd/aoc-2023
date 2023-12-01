package main

import (
	"blt.com/utils"
	"os"
)

func main() {

	filePath := os.Args[1] // read file path from commandline

	solution1FP := utils.FileProcessor{Filename: filePath, Accumulator: &Solution1Accumulator{0}}
	solution1FP.ProcessFile()

	solution2FP := utils.FileProcessor{filePath, &Solution2Accumulator{0}}
	solution2FP.ProcessFile()

}
