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
        (tree) => [tree.root],  // start point generator
        (node) => node.name === "ZZZ"  // termination point logic
    );
    console.log("Solution 1: "+ moves);
}

// Entry point is any node that ends with "xxA"
// Exit criteria is that all the current nodes end with "xxZ"
function solution2(treeMap) {
    let moves = treeMap.walk(
        (tree) => {
            // logic for finding all starting points
            let startNodes = [];
            tree.nodes.forEach((node, key) => {
                if (key.endsWith("A"))
                    startNodes.push(node);
            });
            return startNodes;
        },
        (node) => node.name.endsWith("Z")  // termination point logic
    );
    console.log("Solution 2: "+ moves);
}