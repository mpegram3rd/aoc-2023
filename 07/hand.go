package main

import (
	"fmt"
	"sort"
	"strconv"
	"strings"
)

var RankMap = map[string]int{
	"5":     7, // Five of a kind
	"14":    6, // Four of a kind
	"23":    5, // Full house
	"113":   4, // Three of a kind
	"122":   3, // Two pair
	"1112":  2, // One pair
	"11111": 1, // no pairs
}

type Hand struct {
	cards   string
	bet     int64
	rank    int
	sortVal int64
}

func NewHand(line string) Hand {
	hand := Hand{}
	hand.parse(line)
	hand.convertToHex()
	return hand
}

func (hand *Hand) GetCards() string {
	return hand.cards
}

func (hand *Hand) GetBet() int64 {
	return hand.bet
}

func (hand *Hand) GetSortValue() int64 {
	return hand.sortVal
}

func (hand *Hand) parse(line string) {
	parts := strings.Split(line, " ")
	cards := parts[0]
	bet, _ := strconv.ParseInt(parts[1], 10, 64)
	hand.cards = cards
	hand.bet = bet
}

func (hand *Hand) rankCards() int {

	cardMap := make(map[string]int)

	// Find the number of occurrences of each card
	for _, card := range hand.cards {
		cardStr := string(card)
		cardMap[cardStr] = cardMap[cardStr] + 1
	}

	// Sort the occurrences high to low, then build a string
	counts := make([]int, 0, len(cardMap))
	for key := range cardMap {
		counts = append(counts, cardMap[key])
	}
	sort.Ints(counts)

	// now reassemble the counts into a string, so we can look up the ranks
	rankString := ""
	for _, count := range counts {
		rankString += strconv.Itoa(count)
	}
	hand.rank = RankMap[rankString]
	return RankMap[rankString]
}

func (hand *Hand) convertToHex() {
	// Fun gotcha... better convert the ACES to hex first or we screw it up
	// if you convert 10s to A and then replace the As
	hexCards := strings.Replace(hand.cards, "A", "E", -1)
	hexCards = strings.Replace(hexCards, "T", "A", -1)
	hexCards = strings.Replace(hexCards, "J", "B", -1)
	hexCards = strings.Replace(hexCards, "Q", "C", -1)
	hexCards = strings.Replace(hexCards, "K", "D", -1)
	hexCards = strconv.Itoa(hand.rankCards()) + hexCards

	hexVal, _ := strconv.ParseInt(hexCards, 16, 64)
	hand.sortVal = hexVal
}

func (hand *Hand) ToString() string {
	return fmt.Sprintf("Cards: %s Bet: %d Ranking: %d SortVal: %d", hand.cards, hand.bet, hand.rank, hand.sortVal)
}
