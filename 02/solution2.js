module.exports = (function() {

    // Tallies up the "power" of each game
    // The "power" is determined by multiplying the highest red/green/blue values of each play
    // The "power" from each game is added to the sum total to get the result.
    this.findBoxesRequired = (parsedData) => {
        // Process each game played
        let total = parsedData.reduce((value, gameData) => {
            // process each pull from the bag and see if one of the color values exceeds the highest found in so far
            let maxNeeded = gameData.plays.reduce((result, play) => {  // process each play
                    // Check each color and update the max in the reducer if needed.
                    Object.keys(play).forEach(key =>  {
                        if (play[key] > result[key])
                            result[key] = play[key];
                    });
                    return result;
                },
                {   // Initial values
                    "red": 0,
                    "green": 0,
                    "blue": 0
                }
            );
            // You can't just multiply them directly because if one value is 0 it'll make it all 0.
            let power = 1;
            if (maxNeeded.red > 0)
                power *= maxNeeded.red;
            if (maxNeeded.green > 0)
                power *= maxNeeded.green;
            if (maxNeeded.blue > 0)
                power *= maxNeeded.blue;

            // Add this game's power to the total accumulator
            value += power;
            return value;
        }, 0);
        console.log('Solution 2 result = ' + total);
    }
    return this;
})();
