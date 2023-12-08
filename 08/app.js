const fileProcessor = require("../libs/js-libs/fileprocessor");
const lineProcessor = require("./lineprocessor");
const treeMap = require("./treemap")


fileProcessor.process('input.txt', lineProcessor, treeMap)
    .then(() =>  {
        solution1(treeMap);
        solution2(treeMap);
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

// Entry point is any node that ends with "xxA"
// Exit criteria is that all the current nodes end with "xxZ"
function solution2(treeMap) {
    let moves = treeMap.walk(
        (tree) => {
            let startNodes = [];
            tree.nodes.forEach((node, key) => {
                if (key.endsWith("A"))
                    startNodes.push(node);
            });
            return startNodes;
        },  // start point generator
        (nodes) => {   // terminal point checker all nodes must meet the condition.
            return nodes.reduce((result, node) => result && node.name.endsWith("Z"), true);
        });
    console.log("Solution 2: "+ moves);
}