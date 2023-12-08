module.exports = (function() {

    this.processLine = (line, accumulator)  => {
        if (accumulator.getMoves().length === 0) {  // First line is moves
            accumulator.setMoves(line);
            return;
        }

        if (line.length === 0) // skip empty lines
            return;

        accumulator.addNode(line);
    }
    return this;
})();
