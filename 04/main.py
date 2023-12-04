import gamecard


# Logic for processing wins for solution 2
def sol2_process_wins(cards, card, idx):
    wins = card.wins()
    start = idx + 1 if idx < len(cards) + 1 else idx
    end = idx + wins + 1 if idx + wins < len(cards) else idx
    for x in range(start, end):
        cards[x].increment(card.how_many())


file1 = open('input.txt', 'r')
Lines = file1.readlines()

cards = []
for line in Lines:
    cards.append(gamecard.GAMECARD(line))

answer1 = 0
for idx, card in enumerate(cards):
    answer1 += card.score()
    sol2_process_wins(cards, card, idx)

print("Solution 1: ", answer1)

answer2 = 0
for card in cards:
    answer2 += card.how_many()

print("Solution 2: ", answer2)
