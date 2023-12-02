module.exports = (function() {

    // How many of each color cube did the elf say were in the bag.
    let BagContents = {
        "red": 12,
        "green": 13,
        "blue": 14
    }

    // Tallies game numbers that don't exceed the max bag contents for each color
    this.findValidGames = (parsedData) => {
        // Processes each game
        let total = parsedData.reduce((value, gameData) => {
            // process each pull from the bag
            let tooMany = gameData.plays.reduce((result, play) => {
                // Process each color to see if it exceeds the max for the BagContents
                result += Object.keys(play).reduce((violations, key) =>  {
                    violations += (play[key] > BagContents[key] ? 1 : 0);
                    return violations
                }, 0);
                return result
            }, 0);

            // Add the game number to the tally only if there were no violations (tooMany == 0)
            value += tooMany === 0 ? gameData.game : 0;
            return value;
        }, 0);
        console.log('Solution 1 result = ' + total);
    }
    return this;
})();
