// Defines a "mapped tree" Since data is loaded sequentially and may reference unknown nodes
// the key side is the node name, and the value side holds a node with node name references for
// each branch
module.exports = (function() {
    const treeData = {
        moves: [],
        nodes: new Map(),
        root: null
    }

    // Parse the moves
    this.setMoves = (line) => {
        treeData.moves = line.split('');
    }

    // Retrieve the moves
    this.getMoves = () => {
        return treeData.moves;
    }

    // Parses a node
    this.addNode = (line) => {
        // line looks like "xyz = (abc, def)"
        let keyValueParts = line.split('=');
        let key = keyValueParts[0].trim();   // this should give "xyz" and " (abc, def)"

        let nodeStr = keyValueParts[1].trim(); // remove spaces leaving "(abc, def)"
        // remove parens and split on the comma
        let nodeParts = nodeStr.substring(1, nodeStr.length - 1).split(','); // "abc" and " def"
        let node = {
            name: key,
            left: nodeParts[0],
            right: nodeParts[1].trim(),
        }
        treeData.nodes.set(key, node);

        if (node.name === "AAA") {
            treeData.root = node
        }
    }

    // Walks the tree with lambdas:
    // - startPointGenerator supplies the logic to determine the starting node(s)
    // - terminalPointFound supplies the logic to determine that we have arrived at an ending node
    this.walk = (startPointGenerator, terminalPointFound) => {

        let count = 0;
        let movePtr = 0;
        let currentPoints = startPointGenerator(treeData); // extract starting point(s)
        let terminalDistances = []; // model for capturing distance to termination point

        // Keep processing until all points have reached a termination point
        while (currentPoints.length > 0) {
            count++;
            let nextMove = treeData.moves[movePtr];

            // map current point to next node based on nextMove
            currentPoints = currentPoints.map((node) =>
                nextMove === 'L' ? treeData.nodes.get(node.left)
                                : treeData.nodes.get(node.right)
            );

            // Check if we found a terminal point.  If so capture the distance and filter out the completed path
            currentPoints = currentPoints.filter(node => {
                let found = terminalPointFound(node);

                // if found we need to capture the distance
                if (found) {
                    terminalDistances.push(count);
                }
                return !found; // we don't want to process found endpoints
            })

            // Find the next move
            movePtr++;
            if (movePtr >= treeData.moves.length)
                movePtr = 0;
        }

        // After all paths have made it to a terminal point, take the LCM of those path's distances
        return terminalDistances.reduce((acc, distance) => { // find LCM of distances
            let bigNum = Math.max(acc, distance);
            let smallNum = Math.min(acc, distance);

            let result = bigNum;
            while (result % smallNum !== 0) {
                result += bigNum;
            }
            return result;
        }, 1);
    }

    return this;
})();