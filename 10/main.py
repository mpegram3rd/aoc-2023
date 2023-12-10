import sys

compass_map = {   # map of valid joints based on direction facing on the compass
    "N": { "7", "|", "F"},
    "S": { "J", "|", "L"},
    "E": { "L", "-", "F"},
    "W": { "7", "-", "J"}
}

pipe_types = { # Pipes and their endpoint compass directions
    "|": {"N", "S"},
    "-": {"E", "W"},
    "L": {"N", "W"},
    "J": {"N", "E"},
    "7": {"E", "S"},
    "F": {"W", "S"}
}


def find_start_pipe(tiles, start_col, start_row):
    valid_directions = []

    ## Check if the item NORTH of start could viably connect to starting point
    if start_row > 0 and tiles[start_row - 1][start_col] in compass_map["N"]:
        valid_directions.append("S") # Add the direction of the start point relative to what we checked

    ## Check if the item SOUTH of start could viably connect to starting point
    if start_row + 1 < len(tiles) and tiles[start_row + 1][start_col] in compass_map["S"]:
        valid_directions.append("N") # Add the direction of the start point relative to what we checked

    ## Check if the item EAST of start could viably connect to starting point
    if start_col > 0 and tiles[start_row][start_col - 1] in compass_map["E"]:
        valid_directions.append("W") # Add the direction of the start point relative to what we checked

    ## Check if the item WEST of start could viably connect to starting point
    if start_col + 1 < len(tiles[0]) and tiles[start_row][start_col + 1] in compass_map["W"]:
        valid_directions.append("E") # Add the direction of the start point relative to what we checked

    ## Problem states there can only ever be 2 connections so we should always have only 2 valid directions
    ## Valid directions contains the only valid directions one could travel through the pipes from the start point
    ## using that find the intersection of the valid pipe types (value side of compass_map) which will
    ## give us the only viable pipe type S could be.
    result = list(compass_map[valid_directions[0]].intersection(compass_map[valid_directions[1]]))
    return result[0]

# Take advantage of breadcrumbing in the pipe to simplify our tile grid so it only contains dirt and connected pipes
def remove_non_connected_elements():
    for row_num, row in enumerate(tiles):
        for col_num, col in enumerate(row):
            if row[col_num] != '@':  # if not in the loop, make it dirt
                row[col_num] = '.'

def measure_pipe(col, row, entry_direction):
    distance = 0
    fitting = tiles[row][col] # capture current fitting so we can drop a breadcrumb
    if fitting != '@':  # if not a breadcrumb keep going
        tiles[row][col] = '@' # drop the breadcrumb
        #calculate direction by taking the entry direction and finding the OTHER element in the pipe type's directions
        exit_direction = list(pipe_types[fitting].difference(entry_direction))[0]
        if exit_direction == "N":
            distance += measure_pipe(col, row - 1, "S") # entry direction is opposite of exit direction
        elif exit_direction == "S":
            distance += measure_pipe(col, row + 1, "N")
        elif exit_direction == "E":
            distance += measure_pipe(col - 1, row, "W")
        elif exit_direction == "W":
            distance += measure_pipe(col + 1, row, "E")

        # restore original pipe fitting (remove breadcrumbs when we unwind)
        tiles[row][col] = fitting
        return distance + 1
    else: # reached the end so tidy up grid
        remove_non_connected_elements()
    return distance

sys.setrecursionlimit(20000)  # there's a non-recursive way to handle this, but recursion took less time for me to do
file1 = open("input.txt", 'r')
Lines = file1.readlines()

tiles = []  # will be a 2d array of tiles
row_index = 0
start_row = -1
start_col = -1

for line in Lines:
    tiles.append(list(line.strip()))
    start_loc = line.find('S')
    if start_loc > -1:
        start_row = row_index
        start_col = start_loc
    row_index = row_index + 1

print("Rows: ", len(tiles))
print("Columns: ", len(tiles[0]))
print("Starting location is: ", start_col, ", ", start_row)

tiles[start_row][start_col] = find_start_pipe(tiles, start_col, start_row)
print("Starting Pipe is", tiles[start_row][start_col])

# just pick the first direction available from the pipe's directions
initial_direction = list(pipe_types[tiles[start_row][start_col]])[0]
loop_length = measure_pipe(start_col, start_row, initial_direction)
print("Loop distance = ", loop_length)
print("Solution 1 is:", (loop_length/ 2))

# Solution 2 I think needs the shoelace algo, but today's been too long a day to tackle that.


