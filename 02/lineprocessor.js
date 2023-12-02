module.exports = (function() {

    this.processLine = (line, accumulator)  => {
        let parts = line.split(':');
        let gameNum = Number(parts[0].substring(5));
        let plays = parsePlays(parts[1]);
        accumulator.push({
            game: gameNum,
            plays: plays
        });
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
