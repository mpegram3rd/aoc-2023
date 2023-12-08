const { stdin, stdout } = require('node:process');

// Defines a "mapped tree" Since data is loaded sequentially and may reference unknown nodes
// the key side is the node name, and the value side holds a node with node name references for
// each branch
module.exports = (function() {
    const treeData = {
        moves: [],
        nodes: new Map(),
        root: null
    }

    this.setMoves = (line) => {
        treeData.moves = line.split('');
    }

    this.getMoves = () => {
        return treeData.moves;
    }

    this.addNode = (line) => {
        // line looks like "xyz = (abc, def"
        let keyValueParts = line.split('=');
        let key = keyValueParts[0].trim();   // this should give "xyz" and " (abc, def)"

        let nodeStr = keyValueParts[1].trim(); // remove spaces leaving "(abc, def)"
        // remove parens and split on the comma
        let nodeParts = nodeStr.substring(1, nodeStr.length - 1).split(','); // "abc" and " def"
        let node = {
            name: key,
            left: nodeParts[0],
            right: nodeParts[1].trim(),
            visitCount: 0     // Hedging my bets this is going to be needed in part 2.
        }
        treeData.nodes.set(key, node);

        if (node.name === "AAA") {
            treeData.root = node
        }
    }


    this.reset= () => {
        treeData.nodes.forEach((_, node) => node.visitCount = 0);
    }

    this.walk = (startPointGenerator, terminalPointFound) => {

        this.reset();
        let count = 0;
        let movePtr = 0;
        let currentPoints = startPointGenerator(treeData);
        while (!terminalPointFound(currentPoints)) {
            count++;
            let nextMove = treeData.moves[movePtr];
            // stdout.write("Move: " + nextMove);
            // map current point to next node based on nextMove
            currentPoints = currentPoints.map((node) => {
                // stdout.write (node.name + " -> ");
                let nextPoint =  nextMove === 'L' ? treeData.nodes.get(node.left)
                                        : treeData.nodes.get(node.right);
                // stdout.write(nextPoint.name + ", ");
                return nextPoint;
            });
            // console.log();

            movePtr++;
            if (movePtr >= treeData.moves.length)
                movePtr = 0;
        }

        return count;
    }

    return this;
})();