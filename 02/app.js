const fileProcessor = require('../libs/js-libs/fileprocessor');
const calc = require('./calc1');  // calc1 / calc2

const accumulator = {value: 0};
fileProcessor.process('input.txt', calc.calculator, accumulator)
    .then(() =>  {
        console.log(`Result ${accumulator.value}`);
    })
    .catch((err) => {
        console.error(err);
    });
