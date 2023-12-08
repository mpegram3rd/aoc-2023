package main

import (
	"fmt"
	"sort"
	"strings"
)

type DataAccumulator struct {
	lines []string
}

func NewDataAccumulator() DataAccumulator {
	return DataAccumulator{make([]string, 0)}
}
func (da *DataAccumulator) ProcessLine(line string) {
	da.lines = append(da.lines, line)
}

func (da *DataAccumulator) Execute() {
	da.solution("Solution 1", "", sol1HexMapper)
	da.solution("Solution 2", "J", sol2HexMapper)
}

// primary solution processing
func (da *DataAccumulator) solution(title string, wildcard string, solHexMap hexMapFunc) {

	// Convert lines to card hands
	hands := make([]Hand, 0, len(da.lines))
	for _, line := range da.lines {
		hands = append(hands, NewHand(line, wildcard, solHexMap))
	}

	// sort from lowest to highest sorting value
	sort.Slice(hands, func(x, y int) bool {
		return hands[x].GetSortValue() < hands[y].GetSortValue()
	})

	total := int64(0)

	// Now start tallying
	for idx, hand := range hands {
		total += (int64(idx + 1)) * hand.GetBet()
	}

	fmt.Printf("%s Accumulated value: %d \n4", title, total)
}

// Using functional approach ... pass in one of these 2 functions as hand to hex mapper
type hexMapFunc func(string) string

func sol1HexMapper(cards string) string {
	// Fun gotcha... better convert the ACES to hex first or we screw it up
	// if you convert 10s to A and then replace the As
	hexCards := strings.Replace(cards, "A", "E", -1)
	hexCards = strings.Replace(hexCards, "T", "A", -1)
	hexCards = strings.Replace(hexCards, "J", "B", -1)
	hexCards = strings.Replace(hexCards, "Q", "C", -1)
	hexCards = strings.Replace(hexCards, "K", "D", -1)

	return hexCards
}

func sol2HexMapper(cards string) string {
	// Fun gotcha... better convert the ACES to hex first or we screw it up
	// if you convert 10s to A and then replace the As
	hexCards := strings.Replace(cards, "A", "E", -1)
	hexCards = strings.Replace(hexCards, "T", "A", -1)
	hexCards = strings.Replace(hexCards, "J", "1", -1)
	hexCards = strings.Replace(hexCards, "Q", "C", -1)
	hexCards = strings.Replace(hexCards, "K", "D", -1)

	return hexCards
}
