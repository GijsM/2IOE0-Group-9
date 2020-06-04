package app.Game.AI;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AStar {
    private final List<Node> open;
    private final List<Node> closed;
    private final List<Node> path;
    private final int[][] room;
    private Node current;
    private final int xStart;
    private final int yStart;
    private int xEnd, yEnd;
    private final boolean diag;

    static class Node implements Comparable {
        public Node parent;
        public int x, y; // Node coordinates
        public double g; // Cost of path from start to this node
        public double h; // Cost of path from this node to the goal
        Node(Node parent, int xPos, int yPos, double g, double h) {
            this.parent = parent;
            this.x = xPos;
            this.y = yPos;
            this.g = g;
            this.h = h;
        }
        // Compare using the f value (g + h)
        @Override
        public int compareTo(Object o) {
            Node that = (Node) o;
            return (int)((this.g + this.h) - (that.g + that.h));
        }
    }

    AStar(int[][] room, int xStart, int yStart, boolean diag) {
        this.open = new ArrayList<>();
        this.closed = new ArrayList<>();
        this.path = new ArrayList<>();
        this.room = room;
        this.current = new Node(null, xStart, yStart, 0, 0);
        this.xStart = xStart;
        this.yStart = yStart;
        this.diag = diag;
    }

    // Returns the path to the goal coordinates if one exists
    public List<Node> findPathTo(int xEnd, int yEnd) {
        this.xEnd = xEnd;
        this.yEnd = yEnd;
        this.closed.add(this.current);
        addNeighborsToOpenList();
        while (this.current.x != this.xEnd || this.current.y != this.yEnd) {
            if (this.open.isEmpty()) { // Nothing to examine
                return null;
            }
            this.current = this.open.get(0); // get first node (lowest f score)
            this.open.remove(0); // remove it
            this.closed.add(this.current); // and add to the closed
            addNeighborsToOpenList();
        }
        this.path.add(0, this.current);
        while (this.current.x != this.xStart || this.current.y != this.yStart) {
            this.current = this.current.parent;
            this.path.add(0, this.current);
        }
        return this.path;
    }

    // Find a node in the given list
    private static boolean findNeighborInList(List<Node> array, Node node) {
        return array.stream().anyMatch((n) -> (n.x == node.x && n.y == node.y));
    }

    // Computes the distance from the current node to the end node
    private double distance(int dx, int dy) {
        if (this.diag) { // If diagonal movement is allowed
            return Math.hypot(this.current.x + dx - this.xEnd, this.current.y + dy - this.yEnd); // return direct distance
        } else {
            return Math.abs(this.current.x + dx - this.xEnd) + Math.abs(this.current.y + dy - this.yEnd); // else return "Manhattan distance"
        }
    }
    private void addNeighborsToOpenList() {
        Node node;
        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                if (!this.diag && x != 0 && y != 0) {
                    continue; // skip if diagonal movement is not allowed
                }
                node = new Node(this.current, this.current.x + x, this.current.y + y, this.current.g, this.distance(x, y));
                if ((x != 0 || y != 0) // If this node is not the current node
                        && this.current.x + x >= 0 && this.current.x + x < this.room[0].length // check room boundaries
                        && this.current.y + y >= 0 && this.current.y + y < this.room.length
                        && this.room[this.current.y + y][this.current.x + x] != -1 // check if square is walkable
                        && this.room[this.current.y + y][this.current.x + x] != -2
                        && this.room[this.current.y + y][this.current.x + x] != -3
                        && !findNeighborInList(this.open, node) && !findNeighborInList(this.closed, node)) { // if not already done
                    node.g = node.parent.g + 1.; // Horizontal/vertical cost = 1.0
                    node.g += room[this.current.y + y][this.current.x + x]; // add movement cost for this square

                    // diagonal cost = sqrt(hor_cost² + vert_cost²)
                    if (diag && x != 0 && y != 0) {
                        node.g += .6;	// Diagonal movement cost = 1.6
                    }

                    this.open.add(node);
                }
            }
        }
        Collections.sort(this.open);
    }

    // Perform the A* algorithm and return a path
    public static void PerformAStar(int[][] room){
        AStar as = new AStar(room, 2, 1, false); // Create new AStar instance with starting points
        List<Node> path = as.findPathTo(6, 8); // Create a list containing the path to the goal node
        if (path != null) {
            path.forEach((n) -> {
                System.out.print("[" + n.x + ", " + n.y + "] "); // Print out the path from start to end
                room[n.y][n.x] = -1;
            });
            System.out.printf("\nTotal cost: %.02f\n", path.get(path.size() - 1).g); // Print the cost of the path

            for (int[] room_row : room) {
                for (int room_entry : room_row) {
                    switch (room_entry) {
                        case 0: // Current entry is empty
                            System.out.print("_");
                            break;
                        case -1: // Current entry is part of the path
                            System.out.print("*");
                            break;
                        case -2: // Current entry is blocked
                            System.out.print("X");
                            break;
                        case -3: // Current entry is a rock
                            System.out.print("O");
                            break;
                        default: // All other cases
                            System.out.print("#");
                    }
                }
                System.out.println();
            }
        }
    }
}