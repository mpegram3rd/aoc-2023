const fileProcessor = require('../libs/js-libs/fileprocessor');
const lineProcessor = require('./lineprocessor');
const solution1 = require('./solution1');  // calc1 / calc2
const solution2 = require('./solution2');  // calc1 / calc2

const accumulator = []
fileProcessor.process('input.txt', lineProcessor, accumulator)
    .then(() =>  {
        solution1.findValidGames(accumulator);
        solution2.findBoxesRequired(accumulator);
    })
    .catch((err) => {
        console.error(err);
    });
