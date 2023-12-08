const fileProcessor = require("../libs/js-libs/fileprocessor");
const lineProcessor = require("./lineprocessor");
const treeMap = require("./treemap")


fileProcessor.process('input.txt', lineProcessor, treeMap)
    .then(() =>  {
        solution1(treeMap);
    })
    .catch((err) => {
        console.error(err);
    });

// Entry point is just root
// Exit criteria is that the node(s) are all "ZZZ"
function solution1(treeMap) {
    let moves = treeMap.walk(
        (tree) => { return [tree.root]; },  // start point generator
        (nodes) => {   // terminal point checker all nodes must meet the condition.
            return nodes.reduce((result, node) => result && node.name === "ZZZ", true);
        });
    console.log("Solution 1: "+ moves);
}