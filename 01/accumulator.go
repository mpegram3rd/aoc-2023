package main

import (
	"bufio"
	"log"
	"os"
)

type IFileProcessor interface {
	processFile()
}

type IAccumulator interface {
	processLine(string)
	execute()
}

type FileProcessor struct {
	filename    string
	accumulator IAccumulator
}

func (fp FileProcessor) processFile() {
	// open file and check for errors
	file, err := os.Open(fp.filename)

	defer file.Close()

	if err != nil {
		log.Fatal(err)
	}

	garbIn := bufio.NewScanner(file)
	for garbIn.Scan() {
		fp.accumulator.processLine(garbIn.Text())
	}

	fp.accumulator.execute()
}
