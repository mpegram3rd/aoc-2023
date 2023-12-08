package main

import (
	"sort"
	"strconv"
	"strings"
)

// Rank mappings.  Converts sorted duplication patterns into a rank score
var RankMap = map[string]int{
	"5":     7, // Five of a kind
	"41":    6, // Four of a kind
	"32":    5, // Full house
	"311":   4, // Three of a kind
	"221":   3, // Two pair
	"2111":  2, // One pair
	"11111": 1, // no pairs
}

// Hand of cards
type Hand struct {
	cards   string
	bet     int64
	rank    int
	sortVal int64
}

// Constructor
func NewHand(line string, wildcard string, mapFunc hexMapFunc) Hand {
	hand := Hand{}
	hand.parse(line)
	hand.convertToHex(wildcard, mapFunc)
	return hand
}

///// Value getters

func (hand *Hand) GetCards() string {
	return hand.cards
}

func (hand *Hand) GetBet() int64 {
	return hand.bet
}

func (hand *Hand) GetSortValue() int64 {
	return hand.sortVal
}

// Parses a line of input and populates the hand model based off the line data
func (hand *Hand) parse(line string) {
	parts := strings.Split(line, " ")
	cards := parts[0]
	bet, _ := strconv.ParseInt(parts[1], 10, 64)
	hand.cards = cards
	hand.bet = bet
}

// Figures out the hand rankings
func (hand *Hand) rankCards(wildcard string) int {

	cardMap := make(map[string]int)

	wildcounts := 0 // track wildcards found

	// Find the number of occurrences of each card
	for _, card := range hand.cards {
		cardStr := string(card)
		if wildcard == cardStr {
			wildcounts += 1
		} else {
			cardMap[cardStr] = cardMap[cardStr] + 1
		}
	}

	// Pull counts out of the card map as an int[] so we can sort
	counts := make([]int, 0, len(cardMap))
	for key := range cardMap {
		counts = append(counts, cardMap[key])
	}

	// sort frequency descending
	sort.Slice(counts, func(x, y int) bool {
		return counts[x] > counts[y]
	})

	// add wildcard tally to highest counts value to increase ranking of the hand
	if len(counts) > 0 {
		counts[0] = counts[0] + wildcounts
	} else {
		counts = append(counts, wildcounts)
	}

	// now reassemble the counts into a string, so we can look up the ranks
	rankString := ""
	for _, count := range counts {
		rankString += strconv.Itoa(count)
	}
	hand.rank = RankMap[rankString]
	return RankMap[rankString]
}

// This is the real power player in the algorithm here's how things work
//  1. We convert the cards string into a hexadecimal string (so we can sort them!)
//  2. We run the ranking algorithm to figure out what rank this hand is (will apply wildcards if any found)
//  3. The "rank" is added as the PREFIX to the hexadecimal string (this means sorting will make the highest ranked
//     ones come later increasing their multiple.
//  4. Finally, the hex string {rank}{hexadecimal-version-of-cards} can be converted to an int64
//  5. That gives us a "sortVal" that can be used with natural integer based sorting.
func (hand *Hand) convertToHex(wildcard string, mapFunc hexMapFunc) {
	hexCards := mapFunc(hand.cards)
	hexCards = strconv.Itoa(hand.rankCards(wildcard)) + hexCards

	hexVal, _ := strconv.ParseInt(hexCards, 16, 64)
	hand.sortVal = hexVal
}
