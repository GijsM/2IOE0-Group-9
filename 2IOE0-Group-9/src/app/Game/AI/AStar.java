package app.Game.AI;

import javax.print.attribute.IntegerSyntax;
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
                        && this.room[this.current.y + y][this.current.x + x] != 0 // check if square is walkable
                        && this.room[this.current.y + y][this.current.x + x] != 4
                        && this.room[this.current.y + y][this.current.x + x] != -1
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
    public static void PerformAStar(int[][] room) {
        AStar as = new AStar(room, 2, 2, false); // Create new AStar instance with starting points
        List<Node> path = as.findPathTo(6, 8); // Create a list containing the path to the goal node

        int playerX = 0;
        int playerY = 0;

        for(int i = 0; i < room.length; i ++){
            for(int j = 0; j < room.length; j++){
                if(room[j][i] == 5){
                    playerX = i;
                    playerY = j;
                    break;
                }
            }
        }
        List<List<Integer>> dangerZone = FindDangerZones(playerX, playerY, room);

        for(int i = 0; i < room.length; i++){
            for(int j = 0; j < room.length; j++){
                if(j == room.length - 1){
                    System.out.println(" " + room[i][j]);
                } else{
                    System.out.print(" " + room[i][j]);
                }
            }
        }

        if (path != null) {
            path.forEach((n) -> {
                System.out.print("[" + n.x + ", " + n.y + "] "); // Print out the path from start to end
                room[n.y][n.x] = -1;
            });
            System.out.printf("\nTotal cost: %.02f\n", path.get(path.size() - 1).g); // Print the cost of the path

            for (int[] room_row : room) {
                for (int room_entry : room_row) {
                    switch (room_entry) {
                        case -1: // Current entry is part of the path
                            System.out.print("*");
                            break;
                        case 0: // Current entry is blocked
                            System.out.print("X");
                            break;
                        case 4: // Current entry is a rock
                            System.out.print("O");
                            break;
                        case 5: // Current entry is the player
                            System.out.print("x");
                            break;
                        default: // All other cases
                            System.out.print("_");
                    }
                }
                System.out.println();
            }
        }
    }

    // Calculate all coordinates that the player can shoot at
    public static List<List<Integer>> FindDangerZones(int playerX, int playerY, int[][] room){
        List<List<Integer>> dangers = new ArrayList<>();
        List<List<Integer>> safeSpots = new ArrayList<>();

        // Find horizontal coordinates
        Boolean foundStone = false;

        for(int i = playerX; i < room.length - 1; i++){
            if(room[playerY][i] == 4){
                foundStone = true;
            }
            else if (foundStone) {
                AddCoords(i, playerY, safeSpots);
            }
            else {
                AddCoords(i, playerY, dangers);
            }



        }
        foundStone = false;
        for(int i = playerX - 1; i > 0; i--){
            if(room[playerY][i] == 4){
                foundStone = true;
            }
            else if(foundStone) {
                AddCoords(i, playerY, safeSpots);
            } else {
                AddCoords(i, playerY, dangers);
            }

        }
        foundStone = false;

        for(int i = playerY + 1; i < room[playerX].length - 1; i++){
            if(room[i][playerX] == 4){
                foundStone = true;
            }
            else if(foundStone) {
                AddCoords(playerX, i, safeSpots);
            }
            else {
                AddCoords(playerX, i, dangers);
            }

        }
        foundStone = false;
        // Find vertical coordinates
        for(int i = playerY - 1; i > 0; i--){
            if(room[i][playerX] == 4){
                foundStone = true;
            }
            else if(foundStone) {
                AddCoords(playerX, i, safeSpots);
            }
            else {
                AddCoords(playerX, i, dangers);
            }

        }
        foundStone = false;
        // Calculate diagonals in all 4 directions
        CalculateDiagonals(dangers, playerX, playerY, 1, 1, room, safeSpots);
        CalculateDiagonals(dangers, playerX, playerY, -1, 1, room, safeSpots);
        CalculateDiagonals(dangers, playerX, playerY, -1, -1, room, safeSpots);
        CalculateDiagonals(dangers, playerX, playerY, 1, -1, room, safeSpots);

        for(int i = 0; i < dangers.size(); i++){
            System.out.print(dangers.get(i) + " ");
        }
        System.out.println("/n");
        for(int i = 0; i < safeSpots.size(); i++){
            System.out.print(safeSpots.get(i) + " ");
        }
        setValues(safeSpots, room);
        System.out.println();
        return dangers;
    }

    // Calculate the diagonal range of the player
    public static void CalculateDiagonals(List<List<Integer>> dangers, int player_X, int player_Y,
                                          int xVal, int yVal, int[][] room, List<List<Integer>> safeSpots){
        player_X += xVal;
        player_Y += yVal;
        boolean foundStone = false;
        while(!(player_X <= 0 || player_Y <= 0 || player_X >= room.length - 1 || player_Y >= room.length - 1)){
            if(room[player_Y][player_X] == 4){
                foundStone = true;
            }
            else if (foundStone) {
                AddCoords(player_X, player_Y, safeSpots);
            }
            else {
                AddCoords(player_X, player_Y, dangers);
            }

            player_X += xVal;
            player_Y += yVal;
        }
    }

    // Add coordinates to dangers list
    public static void AddCoords(int x, int y, List<List<Integer>> dangers){
        List<Integer> coords = new ArrayList();
        coords.add(x);
        coords.add(y);
        dangers.add(coords);
    }
    public static void setValues(List<List<Integer>> safeSpots, int[][] room) {
        int x;
        int y;
        for(int i = 0; i < safeSpots.size(); i++){
            y = safeSpots.get(i).get(0);
            x = safeSpots.get(i).get(1);

            room[x][y] -= 3;
        }
        for(int i = 0; i < safeSpots.size(); i++){
            y = safeSpots.get(i).get(0);
            x = safeSpots.get(i).get(1);

            setNeighbours(x - 1, y, room);
            setNeighbours(x, y - 1, room);
            setNeighbours(x + 1, y, room);
            setNeighbours(x  , y + 1, room);

            //Diagonal safespots
//            setNeighbours(x - 1, y - 1, room);
//            setNeighbours(x - 1, y + 1, room);
//            setNeighbours(x + 1, y - 1, room);
//            setNeighbours(x + 1, y + 1, room);
        }
    }

    public static void setNeighbours(int x, int y, int[][] room) {
        if (room[x][y] == 9) {
            room[x][y] -= 2;
        }
    }
    
    // Perform the A* algorithm and return a value for qlearner
    public static double AStarql(int[][] room, int xEnemy, int yEnemy, int xItem, int yItem) {
        AStar as = new AStar(room, xEnemy, yEnemy, false); // Create new AStar instance with starting points
        List<Node> path = as.findPathTo(xItem, yItem); // Create a list containing the path to the goal node
        return path.get(path.size() - 1).g;
    }
    
}