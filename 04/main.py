import gamecard

# Stub for solution 2 logic

answer1 = 0
file1 = open('input.txt', 'r')
Lines = file1.readlines()

cards = []
for line in Lines:
    cards.append(gamecard.GAMECARD(line))

for card in cards:
    answer1 += card.score()

print("Solution 1: ", answer1)
