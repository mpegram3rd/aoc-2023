class GAMECARD:

    def __init__(self, _line):
        # remove the leading "Card #:"
        parts = _line.split(":")

        # Breaks up the parts of the game data from the scratch card into winning values and values to play
        game_data = parts[1].split("|")
        self._winners = set(map(int, filter(lambda val: len(val) > 0, (game_data[0].split(" ")))))
        self._plays = list(map(int, filter(lambda val: len(val) > 0, (game_data[1].split(" ")))))
        self._count = 1  # how many copies do we have

    # Figures out how many winning matches we have
    def wins(self):
        count = 0
        for val in self._plays:
            if val in self._winners:
               count = count + 1
        return count

    # scores the card using the solution 1 algorithm
    def score(self):
        wins = self.wins()
        return 0 if wins == 0 else 1 << (wins - 1)

    # Increase the number of copies of the card
    def increment(self, _value):
        self._count = self._count + _value

    # report the number of copies of the card
    def how_many(self):
        return self._count
