package main

import (
	"bufio"
	"log"
	"os"
)

// Interface for file processor so it can be passed around
type IFileProcessor interface {
	processFile()
}

// Interface for a line by line accumulator
type IAccumulator interface {
	processLine(string)
	execute()
}

// File processor structure that we can hang an IFileProcessor implementation off of
type FileProcessor struct {
	filename    string
	accumulator IAccumulator
}

// Opens and iterates through a file using the attached IAccumulator
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

	// Run any final processing after the file has been processed.
	fp.accumulator.execute()
}
