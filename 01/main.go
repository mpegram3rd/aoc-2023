package main

import (
	"bufio"
	"fmt"
	"log"
	"os"
	"regexp"
	"strconv"
)

func main() {

	filePath := os.Args[1] // read file path from commandline

	// open file and check for errors
	file, err := os.Open(filePath)

	defer file.Close()

	if err != nil {
		log.Fatal(err)
	}

	garbIn := bufio.NewScanner(file)
	accumulator := 0

	for garbIn.Scan() {
		line := garbIn.Text()
		re := regexp.MustCompile("[0-9]")
		numsStr := re.FindAllString(line, -1)
		var nums []int
		for _, element := range numsStr {
			val, _ := strconv.Atoi(element)
			nums = append(nums, val)
		}
		if len(nums) > 0 {
			accumulator += nums[0]*10 + nums[len(nums)-1]
		}
	}
	fmt.Println("Accumulated value: ", accumulator)
}
