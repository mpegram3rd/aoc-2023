const lineReader = require('../libs/js-libs/linereader');
const calc = require('./calc1');  // calc1 / calc2

const accumulator = {value: 0};
lineReader.process('input.txt', calc.calculator, accumulator)
    .then(() =>  {
        console.log(`Result ${accumulator.value}`);
    })
    .catch((err) => {
        console.error(err);
    });
