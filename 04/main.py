# Remove empty values (' 3' results in a '' and a 3 for example)
def remove_empties(_valstr):
    return filter(lambda val: len(val) > 0, _valstr)


# Breaks up the parts of the game data from the scratch card into winning values and values to play
def parse(_line):
    scrubbed = line[10:]
    game_data = scrubbed.split("|")
    winners = set(map(int, remove_empties(game_data[0].split(" "))))
    plays = list(map(int, remove_empties(game_data[1].split(" "))))
    return [winners, plays]


# Each Number in plays that matches a winning number increases the value by a power of 2
def solution1(_gamecard):
    total = 0
    winners = _gamecard[0]
    plays = _gamecard[1]
    for val in plays:
        if val in winners:
            total = 1 if total == 0 else total << 1

    return total

# Stub for solution 2 logic
def solution2(_gamecard):
    return 0


answer1 = 0
answer2 = 0
file1 = open('input.txt', 'r')
Lines = file1.readlines()

for line in Lines:
    gamecard = parse(line)

    answer1 += solution1(gamecard)

print("Solution 1: ", answer1)
