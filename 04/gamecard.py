class GAMECARD:

    def __init__(self, _line):
        # Breaks up the parts of the game data from the scratch card into winning values and values to play
        self.num = map(int, _line[4:10].strip())
        scrubbed = _line[10:]
        game_data = scrubbed.split("|")
        self._winners = set(map(int, filter(lambda val: len(val) > 0, (game_data[0].split(" ")))))
        self._plays = list(map(int, filter(lambda val: len(val) > 0, (game_data[1].split(" ")))))

    def score(self):
        total = 0
        for val in self._plays:
            if val in self._winners:
                total = 1 if total == 0 else total << 1

        return total
