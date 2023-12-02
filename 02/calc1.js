module.exports = (function() {

    // How many of each color cube did the elf say were in the bag.
    BagContents = {
        "red": 12,
        "green": 13,
        "blue": 14
    }

    // Lambda for performing calculations on each line processed
    // Line format: "Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green"
    this.calculator = (line, accumulator) => {
        let parts = line.split(':');
        let gameNum = Number(parts[0].substring(5));
        let plays = parsePlays(parts[1]);
        let tooMany = plays.reduce((result, play) => {  // process each play
            result += Object.keys(play).reduce((violations, key) =>  {
                violations += (play[key] > BagContents[key] ? 1 : 0);
                return violations
            }, 0);
            return result
        }, 0);
        accumulator.value += tooMany === 0 ? gameNum : 0;
    }

    // Semicolon's separate iterations, commas separate color totals for the separation
    // Example of data format passed in: "3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green""
    function parsePlays(game) {
        let iterations = game.split(';'); // Break up plays
        return iterations.map(play => {
            let colors = play.split(','); // Break up colors in the play
            let boxmap = {}
            colors.forEach(color => {
                let boxdata = color.trim().split(' ');  // split the color and the number
                boxmap[boxdata[1]] = Number(boxdata[0]);
            });
            return boxmap;
        });
    }
    return this;
})();
