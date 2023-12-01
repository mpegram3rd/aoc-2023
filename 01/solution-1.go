package main

import (
	"fmt"
	"regexp"
	"strconv"
)

// Treated as a constant
var NumsOnly = regexp.MustCompile("[0-9]")

type Solution1Accumulator struct {
	total int
}

func (a *Solution1Accumulator) processLine(line string) {
	numsStr := NumsOnly.FindAllString(line, -1)
	var nums []int
	for _, element := range numsStr {
		val, _ := strconv.Atoi(element)
		nums = append(nums, val)
	}
	if len(nums) > 0 {
		a.total += nums[0]*10 + nums[len(nums)-1]
	}

}

func (a *Solution1Accumulator) execute() {
	fmt.Println("Accumulated value: ", a.total)
}
