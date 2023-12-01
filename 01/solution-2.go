package main

import (
	"fmt"
	"golang.org/x/exp/maps"
	"sort"
	"strings"
)

var ValuesMap = map[string]int{
	"0":     0,
	"1":     1,
	"2":     2,
	"3":     3,
	"4":     4,
	"5":     5,
	"6":     6,
	"7":     7,
	"8":     8,
	"9":     9,
	"zero":  0,
	"one":   1,
	"two":   2,
	"three": 3,
	"four":  4,
	"five":  5,
	"six":   6,
	"seven": 7,
	"eight": 8,
	"nine":  9,
}

type ValuePosition struct {
	position int
	value    int
}

type Solution2Accumulator struct {
	total int
}

func (a *Solution2Accumulator) processLine(line string) {
	var values []ValuePosition

	// Step through each value in the map and find the first and last occurence of each
	for _, key := range maps.Keys(ValuesMap) {
		var last = -1
		first := strings.Index(line, key)

		// If found then add it to our collection of value/positions and see if there's a last.
		if first >= 0 {
			values = append(values, ValuePosition{first, ValuesMap[key]})
			last = strings.LastIndex(line, key)

			// no point in adding the last position if it is the same as the first.
			if first != last {
				values = append(values, ValuePosition{last, ValuesMap[key]})
			}
		}
	}

	if len(values) > 0 {
		// Sort values by position so we can easily choose the first and last
		sort.SliceStable(values, func(i, j int) bool {
			return values[i].position < values[j].position
		})
		a.total += values[0].value*10 + values[len(values)-1].value
	}
}

func (a *Solution2Accumulator) execute() {
	fmt.Println("Solution 2 Accumulated value: ", a.total)
}
