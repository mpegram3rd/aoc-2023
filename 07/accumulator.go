package main

import (
	"fmt"
	"sort"
)

type DataAccumulator struct {
	hands []Hand
}

func NewDataAccumulator() DataAccumulator {
	return DataAccumulator{make([]Hand, 0)}
}
func (da *DataAccumulator) ProcessLine(line string) {
	da.hands = append(da.hands, NewHand(line))
}

func (da *DataAccumulator) Execute() {
	da.solution1()
}

func (da *DataAccumulator) solution1() {

	// sort from lowest to highest sorting value
	sort.Slice(da.hands, func(x, y int) bool {
		return da.hands[x].GetSortValue() < da.hands[y].GetSortValue()
	})

	total := int64(0)

	// Now start tallying
	for idx, hand := range da.hands {
		fmt.Printf("%d - %s\n", (idx + 1), hand.ToString())
		total += (int64(idx + 1)) * hand.GetBet()
	}

	fmt.Println("Solution 1 Accumulated value: ", total)
}
