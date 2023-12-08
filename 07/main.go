package main

import (
	"blt.com/utils"
	"os"
)

func main() {

	filePath := os.Args[1] // read file path from commandline

	da := NewDataAccumulator()
	processor := utils.FileProcessor{Filename: filePath, Accumulator: &da}
	processor.ProcessFile()
}
