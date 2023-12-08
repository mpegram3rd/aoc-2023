const fileProcessor = require("../libs/js-libs/fileprocessor");
const lineProcessor = require("./lineprocessor");
const treeMap = require("./treemap")


fileProcessor.process('input.txt', lineProcessor, treeMap)
    .then(() =>  {
        let moves = treeMap.walk("ZZZ");
        console.log("Solution 1: "+ moves);

    })
    .catch((err) => {
        console.error(err);
    });
